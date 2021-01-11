package com.coconino.coconinoapp.model.object;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class Receipt {
    public Integer id;
    public Integer customerID; // Did I make these former ints Integers just so I could null them?
    public Integer paymentID; // Yes.
    public Date dateCreated;
    public Date quotedShippingDate;
    public Date actualShippingDate;
    public String status;
    public String comments;

    // I am deciding to include the receipt details in this class.
    public List<ReceiptDetail> receiptDetailList;

    public Receipt(Integer id,
                   Integer customerID,
                   Integer paymentID, // Can be null
                   @Nullable Date dateCreated,
                   @Nullable Date quotedShippingDate,
                   @Nullable Date actualShippingDate,
                   String status,
                   String comments,
                   List<ReceiptDetail> receiptDetailList) {
        this.id = id;
        this.customerID = customerID;
        this.paymentID = paymentID;
        this.dateCreated = dateCreated;
        this.quotedShippingDate = quotedShippingDate;
        this.actualShippingDate = actualShippingDate;
        this.status = status;
        this.comments = comments;
        this.receiptDetailList = receiptDetailList;
    }

    public void addItems(Product item, int amount) {
        // If item exists in Receipt, then just add by amount
        for (ReceiptDetail receiptDetail:
                receiptDetailList) {
            if (receiptDetail.product.equals(item)) {
                receiptDetail.amount += amount;
                return;
            }
        }
        // Otherwise, add the item into the Receipt
        receiptDetailList.add(new ReceiptDetail(item, amount));
    }

    public static class ReceiptDetail {
        public Product product;
        public Integer amount;

        public ReceiptDetail(Product product, Integer amount) {
            this.product = product;
            this.amount = amount;
        }

        public String toString() {
            return product.name + ", qty: " + amount;
        }
    }
}
