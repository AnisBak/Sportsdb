package com.anis.sportsdb.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anis.sportsdb.R
import com.anis.sportsdb.databinding.ListItemPlayerBinding
import com.anis.sportsdb.model.Player

class PlayerViewHolder(
    private val binding: ListItemPlayerBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(player: Player, onPlayerClicked: (data: Array<String?>)-> Unit) {

        binding.apply {
            playerName.text = player.strPlayer
            sport.text = player.strSport
            team1.text = player.strTeam
            team2.text = player.strTeam2
            height.text = player.strHeight
            dateBorn.text = player.dateBorn
            root.setOnClickListener{
                val data = arrayOf(
                    player.strCutout,
                    player.strPlayer,
                    player.strDescriptionEN
                )
                onPlayerClicked(data)
            }
            if (URLUtil.isValidUrl(player.strThumb)) {
                thumbnail.load(player.strThumb)
            }else{
                thumbnail.load(R.drawable.ic_baseline_broken_image_24)
            }
        }
    }
}

class PlayerAdapter(
    private val players: List<Player>,
    private val onPlayerClicked: (data: Array<String?>) -> Unit
) : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPlayerBinding.inflate(inflater, parent, false)
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = players[position]
        holder.bind(item, onPlayerClicked)
    }
    override fun getItemCount() = players.size
}
