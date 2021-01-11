package com.coconino.coconinoapp.ui.ViewItem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.coconino.coconinoapp.R;
import com.coconino.coconinoapp.databinding.ViewItemBinding;
import com.coconino.coconinoapp.model.DBHelper;
import com.coconino.coconinoapp.model.object.Product;

import java.text.MessageFormat;
import java.util.Locale;

public class ViewItemFragment extends Fragment {

    private ViewItemBinding binding;
    private ViewItemViewModel viewItemViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ViewItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewItemViewModel = new ViewModelProvider(requireActivity()).get(ViewItemViewModel.class);

        viewItemViewModel.getProductMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                binding.textName.setText(product.name);
                binding.textDescription.setText(product.description);
                binding.textPrice.setText(String.format(new Locale(""),
                        "$%.2f",
                        product.listPrice));
                binding.textStockCount.setText(MessageFormat.format("{0}{1}",
                        getResources().getString(R.string.label_in_stock_prefix),
                        String.valueOf(product.stockCount)));
                binding.textBrandName.setText(product.brandName);
            }
        });

        binding.buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTextAmount.getText().toString().isEmpty()) {
                    binding.editTextAmount.setError("Fill");
                    return;
                }
                DBHelper.getStagedReceipt().addItems(viewItemViewModel.getProduct(),
                        Integer.parseInt(binding.editTextAmount.getText().toString()));
                Log.d("ViewItemFragment", DBHelper.getStagedReceipt().receiptDetailList.toString());
            }
        });
    }
}
