package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.banner.BannerResponse
import id.muhammadfaisal.jupafazz.databinding.ItemBannerBinding
import id.muhammadfaisal.jupafazz.helper.ViewHelper

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val binding = ItemBannerBinding.bind(this.itemView)

        private lateinit var downloadUrl : String
        private lateinit var context: Context

        fun bind(context: Context, bannerResponse: BannerResponse) {
            this.downloadUrl = bannerResponse.link
            this.context = context

            this.binding.apply {
                this.textTitle.text = bannerResponse.title
                this.textSubtitle.text = bannerResponse.subtitle

                Glide.with(context).load(bannerResponse.image).into(this.image)

                ViewHelper.makeClickable(this@ViewHolder, this.buttonDownload)
            }
        }

        override fun onClick(p0: View?) {
            if (p0 == this.binding.buttonDownload) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(this.downloadUrl)
                this.context.startActivity(intent)
            }
        }
    }
}