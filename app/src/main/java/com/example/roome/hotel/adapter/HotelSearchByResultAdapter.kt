package com.example.roome.hotel.adapter




import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roome.databinding.RecyclerItemBestDealsBinding
import com.example.roome.databinding.RecyclerItemSearchByResultBinding
import com.example.roome.hotel.Hotel
import com.example.roome.hotel.HotelDetailsActivity

class HotelSearchByResultAdapter(private val hotelList: ArrayList<Hotel>) :
    RecyclerView.Adapter<HotelSearchByResultAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerItemSearchByResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel, viewHolder: ViewHolder) {
            binding.apply {
                searchByResult = hotel
                ivLastSearch.setOnClickListener {
                    val intent = Intent(viewHolder.itemView.context, HotelDetailsActivity::class.java)
                    intent.putExtra("object", hotel)
                    viewHolder.itemView.context.startActivity(intent)
                }

            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemSearchByResultBinding.inflate(LayoutInflater.from(viewGroup.context))
        return ViewHolder(binding)

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = viewHolder.bind(hotelList[position],viewHolder)
    override fun getItemCount() = hotelList.size

}

