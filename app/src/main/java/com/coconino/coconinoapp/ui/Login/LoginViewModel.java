package com.coconino.coconinoapp.ui.Login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coconino.coconinoapp.model.DBHelper;

// Note:
// Should probably follow this
// https://github.com/sortagreg/Android-MVVM-Example
public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> status = new MutableLiveData<>();
    private DBHelper dbHelper;

    public LiveData<String> getStatus() {
        return status;
    }

    // https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ru
    // Need to construct self with DBHelper cause the DBHelper needs a Context for initialization!
    // Link above says that referencing context in a ViewModel is illegal.
    public LoginViewModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public DBHelper.ExitStatus login(String username, String password) {
        DBHelper.ExitStatus loginStatus = dbHelper.login(username, password);
        switch (loginStatus) {
            case SUCCESS:
                status.setValue("Login successful!");
                break;
            case INVALID_CREDENTIALS:
                status.setValue("Invalid credentials.");
                break;
            case INVALID_CREDENTIAL_PATTERN:
                status.setValue("Fill all fields.");
                break;
            case FAILED:
                status.setValue("Login failed.");
                break;
            default:
        }
        return loginStatus;
    }
}
