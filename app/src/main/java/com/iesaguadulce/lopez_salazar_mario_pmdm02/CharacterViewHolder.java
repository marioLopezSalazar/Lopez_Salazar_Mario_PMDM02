package com.iesaguadulce.lopez_salazar_mario_pmdm02;

import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.CharacterCardviewBinding;
import com.squareup.picasso.Picasso;

public class CharacterViewHolder extends RecyclerView.ViewHolder {

    private final CharacterCardviewBinding binding;

    public CharacterViewHolder(CharacterCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Character character){
        binding.name.setText(character.getName());
        Picasso.get().load(character.getPicture()).into(binding.image);
        binding.description.setText(character.getDescription());

        binding.executePendingBindings();
    }
}
