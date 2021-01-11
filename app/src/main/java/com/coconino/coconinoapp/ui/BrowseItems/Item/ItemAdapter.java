package com.coconino.coconinoapp.ui.BrowseItems.Item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.coconino.coconinoapp.R;
import com.coconino.coconinoapp.model.object.Product;
import com.coconino.coconinoapp.ui.ViewItem.ViewItemViewModel;

import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Product> items;
    private ViewItemViewModel viewItemViewModel;

    public ItemAdapter(List<Product> items, ViewItemViewModel viewItemViewModel) {
        this.items = items;
        this.viewItemViewModel = viewItemViewModel;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.browse_items_item, viewGroup, false);

        return new ViewHolder(view, items, viewItemViewModel);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getItemName().setText(items.get(position).name);
        viewHolder.getItemPrice().setText(String.format(new Locale(""), "$%.2f", items.get(position).listPrice));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, itemPrice;

        // Could not have made this entire class without this document
        // https://stackoverflow.com/questions/58401386/how-to-call-navigation-action-after-click-on-item-with-recyclerview-and-databind
        public ViewHolder(View view, List<Product> items, ViewItemViewModel viewItemViewModel) {
            super(view);
            view.getRootView().setOnClickListener(v -> {
                viewItemViewModel.setProductMutableLiveData(items.get(getAdapterPosition()));
                Navigation.findNavController(v).navigate(R.id.viewItemFragment);
            });
            itemName = (TextView) view.findViewById(R.id.text_name);
            itemPrice = (TextView) view.findViewById(R.id.text_price);
        }

        public TextView getItemName() {
            return itemName;
        }

        public TextView getItemPrice() {
            return itemPrice;
        }
    }
}

