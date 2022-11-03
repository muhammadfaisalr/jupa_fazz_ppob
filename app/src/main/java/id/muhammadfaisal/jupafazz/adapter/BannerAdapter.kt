package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.banner.BannerResponse
import id.muhammadfaisal.jupafazz.databinding.ItemBannerBinding

class BannerAdapter(val context: Context, val outletBanners: ArrayList<BannerResponse>) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false))
    }

    override fun onBindViewHolder(holder: BannerAdapter.ViewHolder, position: Int) {
        holder.bind(this.context, this.outletBanners[position])
    }

    override fun getItemCount(): Int {
        return outletBanners.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemBannerBinding.bind(this.itemView)

        fun bind(context: Context, bannerResponse: BannerResponse) {
            this.binding.apply {
                this.textTitle.text = bannerResponse.title
                this.textSubtitle.text = bannerResponse.subtitle

                Glide.with(context).load(bannerResponse.image).into(this.image)
            }
        }
    }
}