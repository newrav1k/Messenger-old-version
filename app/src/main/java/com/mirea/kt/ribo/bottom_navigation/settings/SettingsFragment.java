package com.mirea.kt.ribo.bottom_navigation.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.kt.ribo.R;
import com.mirea.kt.ribo.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.theme_array);
                switch (choose[position]) {
                    case "Светлая":
                        getContext().setTheme(com.google.android.material.R.style.Base_Theme_Material3_Light);
                        break;
                    case "Тёмная":
                        getContext().setTheme(com.google.android.material.R.style.Base_Theme_Material3_Dark);
                        break;
                    case "Системная":
                        getContext().setTheme(R.style.Base_Theme_Messenger);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }
}