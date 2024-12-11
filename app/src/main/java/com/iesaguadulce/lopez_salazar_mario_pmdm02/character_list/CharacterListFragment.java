package com.iesaguadulce.lopez_salazar_mario_pmdm02.character_list;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.*;
import com.google.android.material.snackbar.Snackbar;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.model.Character;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.FragmentCharacterListBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.*;


public class CharacterListFragment extends Fragment {

    private FragmentCharacterListBinding binding;
    private boolean snackBarShown = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.charactersRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        MainActivity activity = (MainActivity) requireContext();
        if(activity != null) {
            ArrayList<Character> characters = activity.loadCharacters();
            binding.charactersRecyclerview.setAdapter(new CharacterRecyclerviewAdapter(characters, getActivity()));
        }

        if(!snackBarShown) {
            Snackbar.make(view, R.string.welcome, Snackbar.LENGTH_SHORT).show();
            snackBarShown = true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Cambia el título del ActionBar
        AppCompatActivity activity;
        ActionBar actionBar;
        if( (activity = (AppCompatActivity) getActivity()) != null && (actionBar = activity.getSupportActionBar()) != null)
            actionBar.setTitle(R.string.character_list);
    }
}