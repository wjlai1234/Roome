package com.example.roome.hotel




import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.databinding.RecyclerItemLastSearchBinding
import com.example.roome.databinding.RecyclerItemPopularDestinationsBinding
import com.squareup.picasso.Picasso

class HotelPopularDestinationsAdapter(private val hotelList: ArrayList<Hotel>) :
    RecyclerView.Adapter<HotelPopularDestinationsAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerItemPopularDestinationsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel, viewHolder: ViewHolder) {
            binding.popularDestination= hotel
            binding.ivPopularDestination.setOnClickListener {
                val intent = Intent(viewHolder.itemView.context, HotelDetailsActivity::class.java)
                intent.putExtra("object", hotel)
                viewHolder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemPopularDestinationsBinding.inflate(LayoutInflater.from(viewGroup.context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = viewHolder.bind(hotelList[position],viewHolder)
    override fun getItemCount() = hotelList.size



}

