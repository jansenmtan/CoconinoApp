package com.coconino.coconinoapp.ui.BrowseItems;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.coconino.coconinoapp.databinding.BrowseItemsBinding;
import com.coconino.coconinoapp.model.object.Product;
import com.coconino.coconinoapp.ui.BrowseItems.Item.ItemAdapter;
import com.coconino.coconinoapp.ui.ViewItem.ViewItemViewModel;

import java.util.List;

public class BrowseItemsFragment extends Fragment {
    
    private BrowseItemsBinding binding;
    private BrowseItemsViewModel browseItemsViewModel;

    // ItemAdapter will send information about the item clicked to the ViewItemViewModel.
    private ViewItemViewModel viewItemViewModel;
    private ItemAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BrowseItemsBinding.inflate(inflater, container, false);
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
        browseItemsViewModel = new ViewModelProvider(requireActivity(),
                new BrowseItemsViewModelFactory(requireActivity().getApplicationContext()))
                .get(BrowseItemsViewModel.class);
        viewItemViewModel = new ViewModelProvider(requireActivity()).get(ViewItemViewModel.class);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.scrollToPosition(0);

        browseItemsViewModel.getItems().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                itemAdapter = new ItemAdapter(browseItemsViewModel.getItems().getValue(),
                        viewItemViewModel);
                binding.recyclerView.setAdapter(itemAdapter);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                browseItemsViewModel.searchForItems(binding.textInputEditText.getEditableText().toString());
            }
        };
        binding.textInputEditText.addTextChangedListener(afterTextChangedListener);
    }
}
