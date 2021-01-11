package com.coconino.coconinoapp.ui.CreateAccount;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.coconino.coconinoapp.R;
import com.coconino.coconinoapp.databinding.CreateAccountBinding;

public class CreateAccountFragment extends Fragment {
    
    private CreateAccountBinding binding;
    private CreateAccountViewModel createAccountViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CreateAccountBinding.inflate(inflater, container, false);
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

        createAccountViewModel = new ViewModelProvider(this,
                new CreateAccountViewModelFactory(requireActivity().getApplicationContext()))
                .get(CreateAccountViewModel.class);

        // Why don't I just make this public...?
        createAccountViewModel.getStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireActivity().getApplicationContext(),
                        createAccountViewModel.getStatus().getValue(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.editTextPassword.getText().toString()
                        .equals(binding.editTextConfirmPassword.getText().toString())) {
                    binding.editTextConfirmPassword.setError(getString(R.string.error_passwords_do_not_match));
                    binding.editTextPassword.setError(getString(R.string.error_passwords_do_not_match));
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS
                        .matcher(binding.editTextEmailAddress.getText().toString())
                        .matches()) {
                    binding.editTextEmailAddress.setError("Enter valid email address.");
                    return;
                }
                createAccountViewModel.createAccount(binding.editTextUsername.getText().toString(),
                        binding.editTextPassword.getText().toString(),
                        binding.editTextEmailAddress.getText().toString());
            }
        });
    }
}
