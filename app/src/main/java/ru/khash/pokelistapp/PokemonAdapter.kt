package ru.khash.pokelistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
interface OnPokemonClickListener {
    fun onPokemonClick(pokemonItem: PokemonItem)
}
class PokemonAdapter(private var items: List<PokemonItem>,private val listener: OnPokemonClickListener) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
//    private var items: List<PokemonItem>,


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonItem = items[position]
        holder.bind(pokemonItem)
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<PokemonItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class PokemonViewHolder(itemView: View,private val listener: OnPokemonClickListener) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.pokemon_image_view)
        private val nameView: TextView = itemView.findViewById(R.id.pokemon_name_text_view)

        fun bind(pokemonItem: PokemonItem) {
            nameView.text = pokemonItem.name
            Glide.with(itemView.context)
                .load(pokemonItem.imageUrl)
                .into(imageView)

            itemView.setOnClickListener {
                listener.onPokemonClick(pokemonItem)
            }
        }
    }
}

