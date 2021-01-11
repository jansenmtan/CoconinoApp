package com.coconino.coconinoapp;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.coconino.coconinoapp.model.DBHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
// https://developer.android.com/training/testing/unit-testing/local-unit-tests
// https://stackoverflow.com/questions/8499554/android-junit-test-for-sqliteopenhelper
@RunWith(AndroidJUnit4.class)
public class DBHelperUnitTest {

    Context context;
    DBHelper dbHelper;
    final int declaredTableCount = 10;
    final int declaredProductCount = 6;

    @Before
    public void onSetup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbHelper = new DBHelper(context);
    }

    @Test
    public void login_InvalidCredentials() {
        DBHelper.ExitStatus loginStatus = dbHelper.login("wrongUsername", "wrongPassword");
        assertEquals(DBHelper.ExitStatus.INVALID_CREDENTIALS, loginStatus);
    }

    @Test
    public void login_ValidCredentials() {
        DBHelper.ExitStatus loginStatus = dbHelper.login("highclimber15", "HighCl1mber9");
        assertEquals(DBHelper.ExitStatus.SUCCESS, loginStatus);
    }

    @Test
    public void createAccount_ValidCredentials() {
        DBHelper.ExitStatus createAccountStatus = dbHelper.createAccount(java.util.UUID.randomUUID().toString(),
                "testPassword",
                "testEmail@testDomain.tst");
        assertEquals(DBHelper.ExitStatus.SUCCESS, createAccountStatus);
    }

    @Test
    public void searchForItems_PatternAll_CountEqualToDeclaredProductCount() {
        assertEquals(declaredProductCount, dbHelper.searchForItems(".*").size());
    }

    @Test
    public void searchForItems_PatternSunhat_CountEqualToOne() {
        assertEquals(1, dbHelper.searchForItems(".*sunhat").size());
    }

    @Test
    public void getTables_CountEqualToDeclaredTableCount() {
        String tables = dbHelper.getTables();
        assertEquals(declaredTableCount, tables.split("\\s").length);
    }
}