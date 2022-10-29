package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ItemProductDetailBinding
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.listener.ProductListener
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter

class ProductDetailAdapter(
    val context: Context,
    val products: List<ProductEntity>,
    val clzBefore: String,
    val productListener: ProductListener?
) : RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.products, this.clzBefore, this.productListener)

        holder.itemView.setOnClickListener {
            if (this.clzBefore == Constant.CLS_NAME.CREATE_TRX_ACT) {
                BottomSheets.detailProduct(
                    (this.context as AppCompatActivity),
                    this.products[position],
                    true
                )
            } else {
                if (this.productListener != null) {
                    this.productListener.onProductClicked(this.products[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val binding = ItemProductDetailBinding.bind(itemView)

        private lateinit var context: Context
        private lateinit var productEntities: List<ProductEntity>
        private lateinit var clzBefore: String

        private var productListener: ProductListener? = null
        private var selectedPosition = -1

        fun bind(
            context: Context,
            productResponse: List<ProductEntity>,
            clzBefore: String,
            productListener: ProductListener?
        ) {
            this.context = context
            this.productEntities = productResponse
            this.clzBefore = clzBefore

            val productEntity = this.productEntities[adapterPosition]

            this.binding.let {

                if (productEntity.productImage.isNotEmpty()) {
                    Glide
                        .with(this.context)
                        .load(Constant.URL.BASE_PRODUCT_IMAGE + productEntity.productImage)
                        .into(it.imageProduct)
                }

                it.textPrice.text = Formatter.currency(productEntity.price.toLong(), "ID", true)

                if (this.clzBefore.equals(Constant.CLS_NAME.CREATE_TRX_ACT)) {
                    it.textTitle.text = productEntity.brand
                    ViewHelper.gone(it.textPrice)
                } else {
                    ViewHelper.gone(it.imageProduct)
                    it.textTitle.text = productEntity.product
                    this.productListener = productListener
                }

                Font.setInto((context as AppCompatActivity), Font.Rubik.REGULAR, it.textTitle)
                Font.setInto((context as AppCompatActivity), Font.Rubik.MEDIUM, it.textPrice)
            }

//            this.itemView.setOnClickListener(this)
        }


        @RequiresApi(Build.VERSION_CODES.M)
        override fun onClick(p0: View?) {
            if (p0 == this.itemView) {
                if (this.clzBefore == Constant.CLS_NAME.CREATE_TRX_ACT) {
                    BottomSheets.detailProduct(
                        (this.context as AppCompatActivity),
                        this.productEntities[adapterPosition],
                        true
                    )
                } else {
                    if (this.productListener != null) {
                        this.binding.cardParent.background =
                            this.context.getDrawable(R.drawable.bg_stroke_rounded_selected)
                        this.productListener?.onProductClicked(this.productEntities[adapterPosition])
                    }
                }
            }
        }
    }
}