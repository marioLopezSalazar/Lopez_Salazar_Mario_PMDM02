package com.iesaguadulce.lopez_salazar_mario_pmdm02;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.FragmentCharacterDetailBinding;
import com.squareup.picasso.Picasso;


public class CharacterDetailFragment extends Fragment {

    private FragmentCharacterDetailBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            binding.name.setText(getArguments().getString("name"));
            binding.description.setText(
                    String.format("%s %s",
                    getArguments().getString("description"),
                    getArguments().getString("detail"))
            );
            String skillsFormatted =
                    String.format("<b>%s:</b> %s",
                            getString(R.string.skills),
                            getArguments().getString("skills"));
            binding.skills.setText(Html.fromHtml(skillsFormatted, Html.FROM_HTML_MODE_LEGACY));
            Picasso.get().load(getArguments().getString("picture")).into(binding.picture);

            String toastText = getString(R.string.selectedCharacter) + binding.name.getText();
            Toast.makeText(requireContext(), toastText, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(requireContext(), getString(R.string.noDataCharacter), Toast.LENGTH_SHORT).show();

            // Volver al fragmento anterior
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Cambia el título del ActionBar
        AppCompatActivity activity;
        ActionBar actionBar;
        if( (activity = (AppCompatActivity) getActivity()) != null && (actionBar = activity.getSupportActionBar()) != null)
            actionBar.setTitle(R.string.character_detail);
    }
}