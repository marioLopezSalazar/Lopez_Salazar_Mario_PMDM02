package com.iesaguadulce.lopez_salazar_mario_pmdm02.character_list;

import androidx.recyclerview.widget.RecyclerView;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.CharacterCardviewBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.model.Character;
import com.squareup.picasso.Picasso;


/**
 * Maintains reference to the visual elements of a CardView corresponding to a character.
 * Also allows setting the model data into the views.
 *
 * @author Mario López Salazar
 * @version 1.1
 */
public class CharacterViewHolder extends RecyclerView.ViewHolder {

    /**
     * Reference to the visual elements of the CardView.
     */
    private final CharacterCardviewBinding binding;


    /**
     * Constructs a new ViewHolder object.
     *
     * @param binding ViewBinding corresponding to the CardView that will be stored in the new ViewHolder.
     */
    public CharacterViewHolder(CharacterCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    /**
     * Sets the character data into the visual elements of the CardView.
     *
     * @param character The character.
     */
    public void bind(Character character) {

        //Filling in the fields with the character's information (includes the picture downloading):
        binding.name.setText(character.getName());
        Picasso.get().load(character.getPicture()).into(binding.image);
        binding.description.setText(character.getDescription());

        // Forcing the refreshment of the visual elements:
        binding.executePendingBindings();
    }
}
