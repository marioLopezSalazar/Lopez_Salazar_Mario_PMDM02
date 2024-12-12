package com.iesaguadulce.lopez_salazar_mario_pmdm02.character_detail;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.R;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.FragmentCharacterDetailBinding;
import com.squareup.picasso.Picasso;


/**
 * Fragment to display character details.
 *
 * @author Mario López Salazar
 * @version 1.1
 */
public class CharacterDetailFragment extends Fragment {

    /**
     * ViewBinding to handle the view and access its elements.
     */
    private FragmentCharacterDetailBinding binding;


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
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * Fills in the UI elements with the character's information and shows a Toast message to the user
     * indicating the selected character. This method is called after the view has been created.
     *
     * @param view               The View, returned by onCreateView method.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Getting Character information from the Bundle object (set when navigating to this fragment):
        if (getArguments() != null) {

            // Filling in the name:
            binding.name.setText(getArguments().getString("name"));

            // Composing and filling in the full description:
            binding.description.setText(
                    String.format("%s %s",
                            getArguments().getString("description"),
                            getArguments().getString("detail"))
            );

            // HTML formatting and filling in the skills:
            String skillsFormatted =
                    String.format("<b>%s:</b> %s",
                            getString(R.string.skills),
                            getArguments().getString("skills"));
            binding.skills.setText(Html.fromHtml(skillsFormatted, Html.FROM_HTML_MODE_LEGACY));

            // Downloading and setting the picture:
            Picasso.get().load(getArguments().getString("picture")).into(binding.picture);

            // Displaying a Toast message informing the user about the selected character:
            String toastText = getString(R.string.selectedCharacter) + binding.name.getText();
            Toast.makeText(requireContext(), toastText, Toast.LENGTH_SHORT).show();
        }

        // When Bundle object encapsulating Character information cannot be found:
        else {
            // Displaying a Toast message informing the fail:
            Toast.makeText(requireContext(), getString(R.string.noDataCharacter), Toast.LENGTH_SHORT).show();

            // Returning to Character List:
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
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
            actionBar.setTitle(R.string.character_detail);
    }

}