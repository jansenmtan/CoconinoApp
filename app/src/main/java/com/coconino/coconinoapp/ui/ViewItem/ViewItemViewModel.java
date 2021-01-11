package com.coconino.coconinoapp.ui.ViewItem;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coconino.coconinoapp.model.object.Product;

// Note:
// Should probably follow this
// https://github.com/sortagreg/Android-MVVM-Example
public class ViewItemViewModel extends ViewModel {
    private MutableLiveData<Product> productMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Product> getProductMutableLiveData() {
        return productMutableLiveData;
    }

    public Product getProduct() {
        return productMutableLiveData.getValue();
    }

    public void setProductMutableLiveData(Product productMutableLiveData) {
        this.productMutableLiveData.setValue(productMutableLiveData);
    }
}
