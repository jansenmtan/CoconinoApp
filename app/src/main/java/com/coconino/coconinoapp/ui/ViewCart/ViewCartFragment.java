package com.coconino.coconinoapp.ui.ViewCart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.coconino.coconinoapp.R;
import com.coconino.coconinoapp.databinding.ViewCartBinding;
import com.coconino.coconinoapp.model.DBHelper;
import com.coconino.coconinoapp.model.object.Receipt;
import com.coconino.coconinoapp.ui.ViewCart.Item.ItemAdapter;

public class ViewCartFragment extends Fragment {
    private ViewCartBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ViewCartBinding.inflate(inflater, container, false);
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

        // Initialize viewmodels
        ViewCartViewModel viewCartViewModel = new ViewCartViewModel(DBHelper.getStagedReceipt());

        binding.recyclerViewViewCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewViewCart.scrollToPosition(0);

        viewCartViewModel.getReceiptMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Receipt>() {
            @Override
            public void onChanged(Receipt receipt) {
                ItemAdapter itemAdapter = new ItemAdapter(viewCartViewModel.getReceiptMutableLiveData());
                binding.recyclerViewViewCart.setAdapter(itemAdapter);
            }
        });

        binding.buttonCheckoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_viewCartFragment_to_checkoutCartFragment);
            }
        });
    }
}
