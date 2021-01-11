package com.coconino.coconinoapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.coconino.coconinoapp.model.object.Account;
import com.coconino.coconinoapp.model.object.Customer;
import com.coconino.coconinoapp.model.object.Product;
import com.coconino.coconinoapp.model.object.Receipt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

// https://www.tutorialspoint.com/android/android_sqlite_database.htm
public class DBHelper extends SQLiteOpenHelper {

    private final Context context;
    private static Account loggedInAccount;
    private static Receipt stagedReceipt;

    public DBHelper(Context context) {
        super(context, "database_coconino.db", null, 1);
        this.context = context;
        // Stage empty receipt. Will be committed into database after payment.
        if (stagedReceipt == null) {
            stagedReceipt = new Receipt(null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    new ArrayList<Receipt.ReceiptDetail>() {
                    });
        }
        // https://stackoverflow.com/questions/6791852/android-sqliteopenhelper-why-oncreate-method-is-not-called
//        db = this.getWritableDatabase();
    }

    public static Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public static Receipt getStagedReceipt() {
        return stagedReceipt;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queries = "";
        try {
            InputStream is = context.getAssets().open("sql/assignment_database.sql");
            // https://stackoverflow.com/questions/6433399/how-do-i-create-an-sqlite-database-in-android-by-importing-from-an-sql-file
            queries = Util.toString(is);
            Log.v("DBHelper.onCreate()", queries);
        } catch (IOException e) {
            Log.e("DBHelper", e.getMessage());
        }

        for (String query :
                queries.split(";")) {
            try {
                db.execSQL(query);
            } catch (SQLiteException e) {
                Log.e("DBHelper", e.getMessage());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public String getTables() {
        SQLiteDatabase db = this.getReadableDatabase();
        String tables = "";
        Cursor c = db.rawQuery(
                "SELECT name FROM sqlite_master "
                        + "WHERE type='table' AND name NOT LIKE 'sqlite_%' AND name != 'android_metadata'",
                null
        );
        c.moveToFirst();
        while (!c.isAfterLast()) {
            tables += c.getString(0) + "\n";
            c.moveToNext();
        }

        c.close();
        return tables;
    }

    // Note: since SQLite does not return partial matches (as most regex implementations do), I change the pattern to more easily include those partial matches.
    // TODO: fix crash when inputting '[()]'
    public List<Product> searchForItems(String pattern) {
        SQLiteDatabase db = this.getReadableDatabase();
        final String columnsToSelect = "product.id, product.name, description, list_price, stock_count, "
        + "brand.name AS brand_name, brand.id";
        Cursor c = db.rawQuery(
                "SELECT " + columnsToSelect + " "
                + "FROM product "
                + "LEFT JOIN brand "
                + "ON product.brand_id = brand.id "
                + "WHERE LOWER(product.name) REGEXP '.*"+pattern+".*' "
                        + "OR LOWER(brand.name) REGEXP '.*"+pattern+".*' ",
                null
        );
        // Iterate through rows
        List<Product> items = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            items.add(new Product(Util.getColumnValuesFromRow(c)));
            c.moveToNext();
        }

        return items;
    }

    public ExitStatus login(String username, String password) {
        ExitStatus status = ExitStatus.FAILED;

        if (!Util.isValidCredentialPattern(new String[]{username, password})) {
            return ExitStatus.INVALID_CREDENTIAL_PATTERN;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT "
                        + "* "
                    + "FROM "
                        + "account "
                    + "WHERE "
                        + "username = '" + username + "' "
                    + "AND "
                        + "password = '" + password + "' ",
                null
        );
        if (c.getCount() == 1) {
            status = ExitStatus.SUCCESS;
            c.moveToFirst();
            loggedInAccount = new Account(Util.getColumnValuesFromRow(c));
        } else {
            status = ExitStatus.INVALID_CREDENTIALS;
        }

        c.close();
        return status;
    }

    // Create account, then log into said account.
    public ExitStatus createAccount(String username, String password, String email) {
        ExitStatus status = ExitStatus.FAILED;

        if (!Util.isValidCredentialPattern(new String[]{username, password, email})) {
            return ExitStatus.INVALID_CREDENTIAL_PATTERN;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT "
                        + "* "
                        + "FROM "
                        + "account "
                        + "WHERE "
                        + "username = '" + username + "' "
                        + "AND "
                        + "password = '" + password + "' "
                        + "AND "
                        + "email = '" + email + "' ",
                null
        );
        if (c.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", username); contentValues.put("password", password); contentValues.put("email", email);
            long insertStatus = db.insert("account", null, contentValues);
            if (insertStatus != -1) {
                login(username, password);
                status = ExitStatus.SUCCESS;
            } else {
                status = ExitStatus.FAILED;
            }
        } else if (c.getCount() > 0) {
            status = ExitStatus.EXISTING_CREDENTIALS;
        }

        c.close();
        return status;
    }

    public ExitStatus createCustomer(Customer customer) {
        ExitStatus status = ExitStatus.FAILED;

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("account_id", customer.accountID);
        contentValues.put("name", customer.name);
        contentValues.put("last_name", customer.lastName);
        contentValues.put("first_name", customer.firstName);
        contentValues.put("phone", customer.phone);
        contentValues.put("address_line_1", customer.addressLine1);
        contentValues.put("address_line_2", customer.addressLine2);
        contentValues.put("city", customer.city);
        contentValues.put("state", customer.state);
        contentValues.put("postal_code", customer.postalCode);
        contentValues.put("country", customer.country);
        contentValues.put("credit_limit", customer.creditLimit);
        long insertStatus = db.insert("customer", null, contentValues);

        if (insertStatus != -1) {
            status = ExitStatus.SUCCESS;
        } else {
            status = ExitStatus.FAILED;
        }

        return status;
    }

//    public ExitStatus insertStagedReceipt() {
//        ExitStatus status = ExitStatus.FAILED;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
////        public Integer id;
////        public Integer customerID; // Did I make these former ints Integers just so I could null them?
////        public Integer paymentID; // Yes.
////        public Date dateCreated;
////        public Date quotedShippingDate;
////        public Date actualShippingDate;
////        public String status;
////        public String comments;
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("id", id);
//        contentValues.put("customer_id", customerID);
//        contentValues.put("payment_id", paymentID);
//        contentValues.put("date_created", paymentID);
//        contentValues.put("quoted_shipping_date", paymentID);
//        long insertStatus = db.insert("account", null, contentValues);
//        if (insertStatus != -1) {
//            login(username, password);
//            status = ExitStatus.SUCCESS;
//        } else {
//            status = ExitStatus.FAILED;
//        }
//
//        c.close();
//        return status;
//    }

    public enum ExitStatus {
        FAILED,
        SUCCESS,
        INVALID_CREDENTIALS,
        INVALID_CREDENTIAL_PATTERN,
        EXISTING_CREDENTIALS
    }

    static class Util {
        // https://stackoverflow.com/questions/1891606/read-text-from-inputstream
        static String toString(InputStream is) throws IOException {
            char[] buf = new char[2048];
            Reader r = new InputStreamReader(is, "UTF-8");
            StringBuilder s = new StringBuilder();
            while (true) {
                int n = r.read(buf);
                if (n < 0)
                    break;
                s.append(buf, 0, n);
            }
            return s.toString();
        }

        static boolean isValidCredentialPattern(String[] credentials) {
            // Check if provided credentials are not invalid.
            // "Invalid" meaning not blank, for now.
            String invalidCredentialPattern = "\\A\\s*\\z";
            for (String credential : credentials) {
                if (credential.matches(invalidCredentialPattern)) {
                    return false;
                }
            }
            return true;
        }

        static List<Object> getColumnValuesFromRow(Cursor c) {
            List<Object> columnValues = new ArrayList<>();
            int columnCount = c.getColumnCount();
            for (int columnIdx = 0; columnIdx < columnCount; columnIdx++) {
                int columnType = c.getType(columnIdx);
                switch (columnType) {
                    case Cursor.FIELD_TYPE_NULL:
                        columnValues.add(null);
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        columnValues.add(c.getInt(columnIdx));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        columnValues.add(c.getFloat(columnIdx));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        columnValues.add(c.getString(columnIdx));
                        break;
                    case Cursor.FIELD_TYPE_BLOB:
                        columnValues.add(c.getBlob(columnIdx));
                        break;
                }
            }
            return columnValues;
        }
    }


}
