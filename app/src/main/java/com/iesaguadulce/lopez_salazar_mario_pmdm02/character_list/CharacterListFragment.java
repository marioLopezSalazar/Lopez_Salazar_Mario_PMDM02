package com.iesaguadulce.lopez_salazar_mario_pmdm02.character_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.snackbar.Snackbar;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.R;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.FragmentCharacterListBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.model.Character;
import java.util.ArrayList;

/**
 * Fragment to display a RecyclerView containing a list of characters.
 *
 * @author Mario López Salazar
 * @version 1.1
 */
public class CharacterListFragment extends Fragment {


    /**
     * ViewBinding to handle the view and access its elements.
     */
    private FragmentCharacterListBinding binding;


    /**
     * Used to prevent the Welcome SnackBar from being displayed repeatedly.
     */
    private boolean snackBarShown = false;


    /**
     * Called to inflate the fragment's interface view.
     *
     * @param inflater           The LayoutInflater object used to inflate any views in the fragment.
     * @param container          Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return The created view.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * Loads the RecyclerView with the characters list and shows a Welcome SnackBar.
     * This method is called after the view has been created.
     *
     * @param view               The View, returned by onCreateView method.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initializing the RecyclerView with a LayoutManager to manage its visual elements:
        binding.charactersRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Requesting the Character collection from the MainActivity:
        ArrayList<Character> characters = ((MainActivity) requireContext()).loadCharacters();

        // Setting an Adapter to perform data binding (data model <--> recyclerview):
        binding.charactersRecyclerview.setAdapter(new CharacterRecyclerviewAdapter(characters, getActivity()));

        // Displaying a welcome SnackBar message (only the first time):
        if (!snackBarShown) {
            Snackbar.make(view, R.string.welcome, Snackbar.LENGTH_SHORT).show();
            snackBarShown = true;
        }
    }


    /**
     * Called when the Fragment is visible to the user.
     * Used to arrange the ToolBar text.
     */
    @Override
    public void onStart() {
        super.onStart();

        AppCompatActivity activity;
        ActionBar actionBar;
        if ((activity = (AppCompatActivity) getActivity()) != null && (actionBar = activity.getSupportActionBar()) != null)
            actionBar.setTitle(R.string.character_list);
    }
}