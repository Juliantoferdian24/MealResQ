// ini adapter buat tampilin recyclerview yang ada di HOME

package com.example.mealresq

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class ListRestaurantAdapter(private val listRestoran: ArrayList<Restaurant>) : RecyclerView.Adapter<ListRestaurantAdapter.ListViewHolder>(), Filterable {

    var restaurantFilterList = ArrayList<Restaurant>()

    init{
        restaurantFilterList = listRestoran
    }

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
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_restaurant, viewGroup, false)
        return ListViewHolder(view)
    }

    // ini fungsi yang buat ngedit textview dll yang ada didalem row_player.xml
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val restoran = restaurantFilterList[position]
        holder.namaRestoran.text = restoran.name
        Picasso.get().load(restoran.photo).into(holder.fotoRestoran)
//        holder.fotoRestoran.setBackgroundResource(restoran.photo)
//        Glide.with(holder.itemView.context)
//            .load(restoran.photo)
//            .apply(RequestOptions().override(100, 100))
//            .into(holder.fotoRestoran.background)
        holder.ratingRestoran.text = restoran.rating
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listRestoran[holder.adapterPosition])
        }
    }

    interface OnHapusClicked {
        fun onHapusClicked(data: Restaurant)
    }

    // ini biar row nya bisa di klik
    interface OnItemClickCallback {
        fun onItemClicked(data: Restaurant)
    }

    // ini ga penting
    override fun getItemCount(): Int {
        return restaurantFilterList.size
    }

    // ini kelas buat daftarin komponen yang ada di row_player.xml ke holder
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var namaRestoran: TextView = itemView.findViewById(R.id.namaRestoran)
        var fotoRestoran: ImageView = itemView.findViewById(R.id.fotoRestoran)
        var ratingRestoran: TextView = itemView.findViewById(R.id.ratingRestoran)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()){
                    restaurantFilterList = listRestoran
                } else{
                    val resultList = ArrayList<Restaurant>()
                    for (restoran in listRestoran){
                        if (restoran.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))){
                            resultList.add(restoran)
                        }
                    }
                    restaurantFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = restaurantFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                restaurantFilterList = results?.values as ArrayList<Restaurant>
                notifyDataSetChanged()
            }

        }
    }
}