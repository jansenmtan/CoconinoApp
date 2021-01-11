package com.coconino.coconinoapp.ui.ViewCart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coconino.coconinoapp.model.object.Receipt;

import java.util.Objects;

public class ViewCartViewModel extends ViewModel {
    private final MutableLiveData<Receipt> receiptMutableLiveData = new MutableLiveData<>();

    public ViewCartViewModel(Receipt receipt) {
        this.receiptMutableLiveData.setValue(receipt);
    }

    public void removeReceiptDetail(Receipt.ReceiptDetail receiptDetail) {
        Objects.requireNonNull(receiptMutableLiveData.getValue())
                .receiptDetailList.remove(receiptDetail);
    }

    public MutableLiveData<Receipt> getReceiptMutableLiveData() {
        return receiptMutableLiveData;
    }
}
