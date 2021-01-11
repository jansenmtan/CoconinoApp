package com.coconino.coconinoapp.ui.ViewCart.Item;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.coconino.coconinoapp.R;
import com.coconino.coconinoapp.model.object.Product;
import com.coconino.coconinoapp.model.object.Receipt;

import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    // Have to get the MutableLiveData in order to remove items and update ViewCart after.
    private final MutableLiveData<Receipt> receiptMutableLiveData;
    private final Receipt receipt;

    public List<Receipt.ReceiptDetail> getItems() {
        return receipt.receiptDetailList;
    }

    public void updateReceiptMutableLiveData() {
        receiptMutableLiveData.setValue(receipt);
    }

    public ItemAdapter(MutableLiveData<Receipt> receiptMutableLiveData) {
        this.receiptMutableLiveData = receiptMutableLiveData;
        receipt = receiptMutableLiveData.getValue();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_cart_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Receipt.ReceiptDetail receiptDetail = getItems().get(position);
        Product product = receiptDetail.product;
        int amount = receiptDetail.amount;

        viewHolder.setReceiptDetail(receiptDetail);
        viewHolder.getItemName().setText(product.name);
        viewHolder.getItemPrice().setText(String.format(new Locale(""), "$%.2f", product.listPrice * amount));
        viewHolder.editTextQuantity.setText(String.valueOf(amount));

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!viewHolder.getEditTextQuantity().getText().toString().isEmpty()) {
                    receiptDetail.amount = Integer.parseInt(viewHolder.getEditTextQuantity().getText().toString());
                    viewHolder.getItemPrice().setText(String.format(new Locale(""), "$%.2f", product.listPrice * amount));
                    updateReceiptMutableLiveData();
                }
            }
        };
        viewHolder.getEditTextQuantity().addTextChangedListener(afterTextChangedListener);

        viewHolder.getButtonRemove().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItems().remove(receiptDetail);
                updateReceiptMutableLiveData();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return getItems().size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, itemPrice;
        private final EditText editTextQuantity;
        private Receipt.ReceiptDetail receiptDetail;
        private Button buttonRemove;

        // Could not have made this entire class without this document
        // https://stackoverflow.com/questions/58401386/how-to-call-navigation-action-after-click-on-item-with-recyclerview-and-databind
        public ViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.text_name);
            itemPrice = (TextView) view.findViewById(R.id.text_price);
            editTextQuantity = (EditText) view.findViewById(R.id.edit_text_amount_view_cart_item);
            buttonRemove = (Button) view.findViewById(R.id.button_remove_view_cart_item);
        }

        public TextView getItemName() {
            return itemName;
        }

        public TextView getItemPrice() {
            return itemPrice;
        }

        public EditText getEditTextQuantity() {
            return editTextQuantity;
        }

        public Button getButtonRemove() {
            return buttonRemove;
        }

        public void setReceiptDetail(Receipt.ReceiptDetail receiptDetail) {
            this.receiptDetail = receiptDetail;
        }
    }
}

