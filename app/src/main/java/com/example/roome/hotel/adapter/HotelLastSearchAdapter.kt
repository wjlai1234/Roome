package com.example.roome.hotel.adapter




import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roome.databinding.RecyclerItemLastSearchBinding
import com.example.roome.hotel.Hotel
import com.example.roome.hotel.HotelDetailsActivity

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

