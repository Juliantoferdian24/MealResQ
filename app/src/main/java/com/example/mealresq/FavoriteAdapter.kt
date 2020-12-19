// ini adapter buat tampilin recyclerview yang ada di HOME

package com.example.mealresq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val listFav: ArrayList<Restaurant>) : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onHapusClickCallback: OnHapusClicked

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnHapusClickCallBack(onHapusClickCallback: OnHapusClicked){
        this.onHapusClickCallback = onHapusClickCallback
    }


    // ini fungsi buat holder yang nge inflate layout row_player.xml (ada di folder res/layout)
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.fav_row, viewGroup, false)
        return ListViewHolder(view)
    }

    // ini fungsi yang buat ngedit textview dll yang ada didalem row_player.xml
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val restoran = listFav[position]
        holder.namaMenu.text = restoran.name
        holder.namaRestoran.text = restoran.name
        holder.ratingRestoran.text = restoran.rating
        Picasso.get().load(restoran.photo).into(holder.fotoMenu)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFav[holder.adapterPosition])
        }
    }

    interface OnHapusClicked {
        fun onHapusClicked(data: Menu)
    }

    // ini biar row nya bisa di klik
    interface OnItemClickCallback {
        fun onItemClicked(data: Restaurant)
    }

    // ini ga penting
    override fun getItemCount(): Int {
        return listFav.size
    }

    // ini kelas buat daftarin komponen yang ada di row_player.xml ke holder
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var namaRestoran: TextView = itemView.findViewById(R.id.namaRestoranFav)
        var namaMenu: TextView = itemView.findViewById(R.id.textView8Fav)
        var fotoMenu: ImageView = itemView.findViewById(R.id.imageViewFav)
        var ratingRestoran: TextView = itemView.findViewById(R.id.ratingRestoran)
    }
}