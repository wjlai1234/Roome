package com.example.roome.hotel




import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roome.R
import com.example.roome.databinding.RecyclerItemBestDealsBinding
import com.example.roome.databinding.RecyclerItemLastSearchBinding
import com.squareup.picasso.Picasso

class HotelLastSearchAdapter(private val hotelList: ArrayList<Hotel>) :
    RecyclerView.Adapter<HotelLastSearchAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerItemLastSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel, viewHolder: ViewHolder) {
            binding.lastSearch= hotel
            binding.ivLastSearch.setOnClickListener {
                val intent = Intent(viewHolder.itemView.context, HotelDetailsActivity::class.java)
                intent.putExtra("object", hotel)
                viewHolder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemLastSearchBinding.inflate(LayoutInflater.from(viewGroup.context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = viewHolder.bind(hotelList[position],viewHolder)
    override fun getItemCount() = hotelList.size


}

