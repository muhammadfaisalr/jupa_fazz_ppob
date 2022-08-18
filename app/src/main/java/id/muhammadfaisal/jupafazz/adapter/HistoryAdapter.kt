package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ItemHistoryBodyBinding
import id.muhammadfaisal.jupafazz.dummy.History
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter

class HistoryAdapter(val context: Context, val histories: ArrayList<History>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history_body, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, this.histories[position])
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var binding = ItemHistoryBodyBinding.bind(itemView)

        fun bind(context: Context, history: History) {
            this.statusFormat(context, history)
            this.binding.let {
                it.textProductName.text = history.name
                it.textValue.text = history.value
                it.textStatus.text = history.status
                it.textPrice.text = Formatter.currency(history.price, "ID", true)

                Font.apply {
                    setInto((context as AppCompatActivity), Font.Rubik.MEDIUM, it.textStatus, it.textPrice, it.textProductName)
                    setInto((context as AppCompatActivity), Font.Rubik.REGULAR, it.textValue)
                }
            }
        }

        private fun statusFormat(context: Context, history: History) {
            this.binding.let {
                if (history.status == Constant.Status.SUCCESS) {
                    setColorStatus(context, R.color.light_blue, R.color.dark_blue)
                } else if (history.status == Constant.Status.WAITING) {
                    setColorStatus(context, R.color.waiting_bg, R.color.waiting_text)
                } else if (history.status == Constant.Status.ON_PROCESS) {
                    setColorStatus(context, R.color.on_process_bg, R.color.on_process_text)
                } else if (history.status == Constant.Status.CANCEL) {
                    setColorStatus(context, R.color.cancel_bg, R.color.cancel_text)
                } else if (history.status == Constant.Status.FAILED) {
                    setColorStatus(context, R.color.error_bg, R.color.error_text)
                }
            }
        }

        private fun setColorStatus(context: Context, bgColor:Int, textColor: Int) {
            this.binding.let {
                it.cardStatus.setCardBackgroundColor(context.resources.getColor(bgColor))
                it.textStatus.setTextColor(context.resources.getColor(textColor))
            }
        }
    }
}