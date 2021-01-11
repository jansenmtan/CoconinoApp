package com.coconino.coconinoapp.ui.CreateAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coconino.coconinoapp.model.DBHelper;

public class CreateAccountViewModel extends ViewModel {
    private MutableLiveData<String> status = new MutableLiveData<>();
    private DBHelper dbHelper;

    public LiveData<String> getStatus() {
        return status;
    }

    // https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ru
    // Need to construct self with DBHelper cause the DBHelper needs a Context for initialization!
    // Link above says that referencing context in a ViewModel is illegal.
    public CreateAccountViewModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public DBHelper.ExitStatus createAccount(String username, String password, String email) {
        DBHelper.ExitStatus createAccountStatus = dbHelper.createAccount(username, password, email);
        switch (createAccountStatus) {
            case SUCCESS:
                status.setValue("Account creation succeeded!");
                break;
            case INVALID_CREDENTIAL_PATTERN:
                status.setValue("Fill all fields.");
                break;
            case FAILED:
                status.setValue("Account creation failed.");
                break;
            default:
        }
        return createAccountStatus;
    }
}
