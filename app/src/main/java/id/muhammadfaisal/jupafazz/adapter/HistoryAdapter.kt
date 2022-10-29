package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.activity.DetailHistoryActivity
import id.muhammadfaisal.jupafazz.api.model.transaction.TransactionListResponse
import id.muhammadfaisal.jupafazz.databinding.ItemHistoryBodyBinding
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter

class HistoryAdapter(val context: Context, val histories: ArrayList<TransactionListResponse>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_body, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, this.histories[position])
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var binding = ItemHistoryBodyBinding.bind(itemView)

        private lateinit var context: Context
        private lateinit var history: TransactionListResponse

        fun bind(context: Context, history: TransactionListResponse) {
            this.context = context
            this.history = history

            this.statusFormat(context, history)
            this.binding.let {
                it.textProductName.text = "${history.productName} ${history.target}"
                it.textValue.text = history.orderId
                it.textStatus.text = history.status
                it.textPrice.text = Formatter.currency(history.price.toLong(), "ID", true)

                Glide.with(context)
                    .load(history.productImg)
                    .error(R.drawable.logo_square)
                    .into(it.imageProduct)

                Font.apply {
                    setInto(
                        (context as AppCompatActivity),
                        Font.Rubik.MEDIUM,
                        it.textStatus,
                        it.textPrice,
                        it.textProductName
                    )
                    setInto((context as AppCompatActivity), Font.Rubik.REGULAR, it.textValue)
                }

                ViewHelper.makeClickable(this, this.itemView)
            }
        }

        private fun statusFormat(context: Context, history: TransactionListResponse) {
            this.binding.let {
                if (history.status == Constant.Status.SUCCESS) {
                    setColorStatus(context, R.color.light_blue, R.color.dark_blue)
                } else if (history.status == Constant.Status.PROCESSING) {
                    setColorStatus(context, R.color.waiting_bg, R.color.waiting_text)
                } else if (history.status == Constant.Status.PENDING) {
                    setColorStatus(context, R.color.on_process_bg, R.color.on_process_text)
                } else if (history.status == Constant.Status.CANCELED) {
                    setColorStatus(context, R.color.error_bg, R.color.error_text)
                }
            }
        }

        private fun setColorStatus(context: Context, bgColor: Int, textColor: Int) {
            this.binding.let {
                it.cardStatus.setCardBackgroundColor(context.resources.getColor(bgColor))
                it.textStatus.setTextColor(context.resources.getColor(textColor))
            }
        }

        override fun onClick(p0: View?) {
            if (p0 == this.itemView) {
                val bundle = Bundle()
                bundle.putString(Constant.Key.INVOICE_ID, this.history.orderId)
                GeneralHelper.move(context, DetailHistoryActivity::class.java, bundle, false)
            }
        }
    }
}