package com.coconino.coconinoapp.ui.BrowseItems;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.coconino.coconinoapp.model.DBHelper;
import com.coconino.coconinoapp.ui.BrowseItems.BrowseItemsViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class BrowseItemsViewModelFactory implements ViewModelProvider.Factory {

    Context context;

    public BrowseItemsViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BrowseItemsViewModel.class)) {
            return (T) new BrowseItemsViewModel(new DBHelper(context));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
