package com.coconino.coconinoapp.ui.CreateAccount;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.coconino.coconinoapp.model.DBHelper;
import com.coconino.coconinoapp.ui.CreateAccount.CreateAccountViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class CreateAccountViewModelFactory implements ViewModelProvider.Factory {

    Context context;

    public CreateAccountViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateAccountViewModel.class)) {
            return (T) new CreateAccountViewModel(new DBHelper(context));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
