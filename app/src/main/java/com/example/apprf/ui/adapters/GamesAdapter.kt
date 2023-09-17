package com.example.apprf.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apprf.data.remote.model.GameDto
import com.example.apprf.databinding.GameElementBinding
import com.squareup.picasso.Picasso

class GamesAdapter(
    private val games: List<GameDto>,
    private val onGameClicked: (GameDto) -> Unit

): RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: GameElementBinding): RecyclerView.ViewHolder(binding.root){

        val ivThumbnail = binding.ivThumbnail

        fun bind(game: GameDto){
            binding.tvTitle.text = game.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GameElementBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]

        holder.bind(game)

        //Con picaso
        /*Picasso.get()
            .load(game.thumbnail)
            .into(holder.ivThumbnail)*/

        //Con glide
        Glide.with(holder.itemView.context)
            .load(game.thumbnail)
            .into(holder.ivThumbnail)

        //procesamiento del clic del elemento
        holder.itemView.setOnClickListener{
            onGameClicked(game)
        }
    }

}