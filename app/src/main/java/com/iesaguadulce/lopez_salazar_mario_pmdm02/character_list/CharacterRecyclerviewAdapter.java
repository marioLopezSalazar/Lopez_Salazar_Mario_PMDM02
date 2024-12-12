package com.iesaguadulce.lopez_salazar_mario_pmdm02.character_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.CharacterCardviewBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.model.Character;

import java.util.ArrayList;


/**
 * Adapter class for displaying a list of Character in the Characters RecyclerView.
 *
 * @author Mario López Salazar
 * @version 1.1
 */
public class CharacterRecyclerviewAdapter extends RecyclerView.Adapter<CharacterViewHolder> {

    /**
     * Collection of Character objects to be displayed in the RecyclerView.
     */
    private final ArrayList<Character> characters;

    /**
     * The execution context, usually the MainActivity.
     */
    private final Context context;


    /**
     * Constructs a new CharacterRecyclerView.
     *
     * @param characters Collection of Character objects to be displayed in the RecyclerView.
     * @param context    The execution context (normally MainActivity).
     */
    public CharacterRecyclerviewAdapter(ArrayList<Character> characters, Context context) {
        this.characters = characters;
        this.context = context;
    }


    /**
     * Called when RecyclerView needs a new CharacterViewHolder to display a character.
     * Inflates a new CardView and maintains its reference into the new CharacterViewHolder.
     * Note that it can be reused in the future to show some other characters.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View (normally CardView).
     * @return CharacterViewHolder referencing an inflated CharacterCardView.
     */
    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflating a new CardView:
        CharacterCardviewBinding binding = CharacterCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        // Creating a ViewHolder which maintains reference to the CardView:
        return new CharacterViewHolder(binding);
    }


    /**
     * Called when RecyclerView needs to show a Character in a CharacterViewHolder.
     * Sets the Character information into the CardView referenced by the CharacterViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the Character.
     * @param position The position of the Character within the Characters List.
     */
    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {

        // Retrieving the character:
        Character character = characters.get(position);

        // Setting the Character information into the CardView:
        holder.bind(character);

        // Assigning a listener to handle the click event when the user will tap on the CardView:
        holder.itemView.setOnClickListener(

                // Requiring the Activity to launch the Character Detail Fragment:
                view -> ((MainActivity) context).launchCharacterDetail(character)
        );
    }


    /**
     * Returns the total number of Characters in the list.
     *
     * @return Total number of Characters.
     */
    @Override
    public int getItemCount() {
        return characters.size();
    }

}
