package com.coconino.coconinoapp.ui.BrowseItems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coconino.coconinoapp.model.DBHelper;
import com.coconino.coconinoapp.model.object.Product;

import java.util.List;

public class BrowseItemsViewModel extends ViewModel {
    private MutableLiveData<List<Product>> items = new MutableLiveData<>();
    private DBHelper dbHelper;

    public MutableLiveData<List<Product>> getItems() {
        return items;
    }

    public BrowseItemsViewModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void searchForItems(String pattern) {
        items.setValue(dbHelper.searchForItems(pattern));
    }
}
