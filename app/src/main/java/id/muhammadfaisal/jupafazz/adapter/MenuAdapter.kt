package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.activity.CreateTransactionActivity
import id.muhammadfaisal.jupafazz.databinding.ItemMenuBinding
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity
import id.muhammadfaisal.jupafazz.dummy.Menu
import id.muhammadfaisal.jupafazz.helper.DatabaseHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font

class MenuAdapter(val context: Context, val menus: List<String>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, this.menus[position])
    }

    override fun getItemCount(): Int {
        return this.menus.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val binding = ItemMenuBinding.bind(itemView);

        private lateinit var context: Context
        private lateinit var category: String

        fun bind(context: Context, category: String) {
            this.context = context
            this.category = category
            this.binding.let {

                Font.setInto((context as AppCompatActivity), Font.Rubik.REGULAR, it.text)

                it.text.text = category
                it.image.setImageResource(imageCategory(category))
            }

            this.itemView.setOnClickListener(this)
        }

        private fun imageCategory(categoryP : String): Int {
            val image = when (categoryP) {
                Constant.CATEGORY.DATA -> {
                    R.drawable.ic_internet
                }
                Constant.CATEGORY.E_MONEY -> {
                    R.drawable.ic_wallet
                }
                Constant.CATEGORY.GAMES -> {
                    R.drawable.ic_game
                }
                Constant.CATEGORY.PLN -> {
                    R.drawable.ic_electricity
                }
                Constant.CATEGORY.SMS_PACKAGE -> {
                    R.drawable.ic_phone
                }
                Constant.CATEGORY.PREPAID_BALANCE -> {
                    R.drawable.ic_smartphone
                }
                else -> {
                    R.drawable.ic_voucher
                }
            }

            return image
        }

        override fun onClick(p0: View?) {
            if (p0 == this.itemView) {
                val bundle = Bundle()
                bundle.putString(Constant.Key.CATEGORY, this.category)
                GeneralHelper.move(this.context, CreateTransactionActivity::class.java, bundle, false)
            }
        }
    }
}