package bassem.udacity.session5.ui.realestates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bassem.udacity.session5.R
import bassem.udacity.session5.data.model.RealEstate
import bassem.udacity.session5.databinding.RealEstateItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RealEstateAdapter :
    ListAdapter<RealEstate, RealEstateAdapter.ViewHolder>(RealEstateDiffCallback()) {

    class ViewHolder private constructor(val binding: RealEstateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RealEstate) {
            with(binding) {
                val context = root.context
                Picasso.with(context)
                    .load(item.imageUrl.replace("http", "https"))
                    .error(R.mipmap.ic_launcher)
                    .into(imageviewIcon, object : Callback {
                        override fun onSuccess() {
                            loading.visibility = View.GONE
                        }

                        override fun onError() {
                            loading.visibility = View.GONE
                        }

                    })

                imageviewIcon.contentDescription =
                    context.getString(R.string.real_estate_description, item.type.name, item.price)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RealEstateItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class RealEstateDiffCallback : DiffUtil.ItemCallback<RealEstate>() {
    override fun areItemsTheSame(oldItem: RealEstate, newItem: RealEstate): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RealEstate, newItem: RealEstate): Boolean =
        oldItem == newItem

}