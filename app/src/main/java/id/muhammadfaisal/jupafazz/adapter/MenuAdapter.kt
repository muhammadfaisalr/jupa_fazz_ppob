package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ItemMenuBinding
import id.muhammadfaisal.jupafazz.dummy.Menu
import id.muhammadfaisal.jupafazz.utils.Font

class MenuAdapter(val context: Context, val menus: ArrayList<Menu>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, this.menus[position])
    }

    override fun getItemCount(): Int {
        return this.menus.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemMenuBinding.bind(itemView);

        fun bind(context: Context, menu: Menu) {
            this.binding.let {

                Font.setInto((context as AppCompatActivity), Font.Rubik.REGULAR, it.text)

                it.text.text = menu.title
                it.image.setImageResource(menu.image)
            }
        }
    }
}