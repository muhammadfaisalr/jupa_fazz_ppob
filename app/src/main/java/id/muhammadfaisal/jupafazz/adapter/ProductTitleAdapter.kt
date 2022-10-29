package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.product.ProductResponse
import id.muhammadfaisal.jupafazz.databinding.ItemProductTitleBinding
import id.muhammadfaisal.jupafazz.helper.DatabaseHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font

class ProductTitleAdapter(val context: Context, val categories: List<String>) : RecyclerView.Adapter<ProductTitleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductTitleAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_title, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemProductTitleBinding.bind(itemView)

        fun bind(
            context: Context,
            category: String
        ) {
            this.binding.let {
                it.textTitle.text = category
                it.recyclerView.layoutManager = GridLayoutManager(context, 2)
                it.recyclerView.adapter = ProductDetailAdapter(context, DatabaseHelper.productDao(context).getAllByCategory(category), Constant.CLS_NAME.CREATE_TRX_ACT, null)

                Font.setInto((context as AppCompatActivity), Font.Rubik.MEDIUM, it.textTitle)
            }
        }
    }
}