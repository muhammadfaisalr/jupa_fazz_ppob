package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.product.ProductResponse
import id.muhammadfaisal.jupafazz.databinding.ItemProductDetailBinding
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter

class ProductDetailAdapter(val context: Context, val products: List<ProductEntity>) : RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_detail, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val binding = ItemProductDetailBinding.bind(itemView)

        private lateinit var context: Context
        private lateinit var productResponse: ProductEntity

        fun bind(context: Context, productResponse: ProductEntity) {
            this.context = context
            this.productResponse = productResponse

            this.binding.let {
                it.textTitle.text = productResponse.product
                it.textPrice.text = Formatter.currency(productResponse.price.toLong(), "ID", true)

                Font.setInto((context as AppCompatActivity), Font.Rubik.REGULAR, it.textTitle)
                Font.setInto((context as AppCompatActivity), Font.Rubik.MEDIUM, it.textPrice)
            }

            this.itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (p0 == this.itemView) {
                BottomSheets.detailProduct((this.context as AppCompatActivity), this.productResponse, true)
            }
        }
    }
}