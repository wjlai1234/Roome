package com.example.roome.hotel.adapter




import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roome.databinding.RecyclerItemBestDealsBinding
import com.example.roome.hotel.Hotel
import com.example.roome.hotel.HotelDetailsActivity

class HotelBestDealAdapter(private val hotelList: ArrayList<Hotel>) :
    RecyclerView.Adapter<HotelBestDealAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerItemBestDealsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel, viewHolder: ViewHolder) {
            binding.bestDeals= hotel
            binding.ivBestDeals.setOnClickListener {
                val intent = Intent(viewHolder.itemView.context, HotelDetailsActivity::class.java)
                intent.putExtra("object", hotel)
                viewHolder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemBestDealsBinding.inflate(LayoutInflater.from(viewGroup.context))
        return ViewHolder(binding)

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = viewHolder.bind(hotelList[position],viewHolder)
    override fun getItemCount() = hotelList.size

}

