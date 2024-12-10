package com.iesaguadulce.lopez_salazar_mario_pmdm02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.CharacterCardviewBinding;

public class CharacterRecyclerviewAdapter extends RecyclerView.Adapter<CharacterViewHolder> {

    private final ArrayList<Character> characters;
    private final Context context;

    public CharacterRecyclerviewAdapter(ArrayList<Character> characters, Context context) {
        this.characters = characters;
        this.context = context;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CharacterCardviewBinding binding = CharacterCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.bind(character);
        holder.itemView.setOnClickListener(
                view -> characterClicked(character, view)
        );
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    private void characterClicked(Character character, View view) {
        ((MainActivity) context).launchCharacterDetail(character);
    }
}
