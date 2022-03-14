package com.example.roome.hotel.adapter




import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roome.hotel.HotelPopularActivity
import com.example.roome.databinding.RecyclerItemPopularDestinationsBinding
import com.example.roome.hotel.PopularArea

class HotelPopularDestinationsAdapter(private val popularAreaList: ArrayList<PopularArea>) :
    RecyclerView.Adapter<HotelPopularDestinationsAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerItemPopularDestinationsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(popularArea: PopularArea, viewHolder: ViewHolder) {
            binding.popularDestination= popularArea
            binding.ivPopularDestination.setOnClickListener {
                val intent = Intent(viewHolder.itemView.context, HotelPopularActivity::class.java)
                intent.putExtra("object", popularArea)
                viewHolder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemPopularDestinationsBinding.inflate(LayoutInflater.from(viewGroup.context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = viewHolder.bind(popularAreaList[position],viewHolder)
    override fun getItemCount() = popularAreaList.size



}

