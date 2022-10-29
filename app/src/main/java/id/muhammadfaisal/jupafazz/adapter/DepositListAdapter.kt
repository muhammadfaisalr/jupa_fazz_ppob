package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.activity.DetailDepositActivity
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositListResponse
import id.muhammadfaisal.jupafazz.databinding.ItemDepositHistoryBinding
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter

class DepositListAdapter(val context: Context, var deposits: ArrayList<DepositListResponse>) : RecyclerView.Adapter<DepositListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepositListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_deposit_history, parent, false))
    }

    override fun onBindViewHolder(holder: DepositListAdapter.ViewHolder, position: Int) {
        holder.bind(this.context, this.deposits[position])
    }

    override fun getItemCount(): Int {
        return this.deposits.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val binding = ItemDepositHistoryBinding.bind(itemView)
        private lateinit var context: Context
        private lateinit var response: DepositListResponse

        fun bind(context: Context, response: DepositListResponse) {
            this.context = context
            this.response = response

            this.binding.apply {
                this.textDepositId.text = response.depositId
                this.textDate.text = response.createDate
                this.textAmount.text = Formatter.currency(response.quantity.toLong(), "ID", true)
                this.textStatus.text = response.status

                statusFormat(context, response)
                (context as AppCompatActivity)
                Font.setInto(context, Font.Rubik.MEDIUM, this.textStatus, this.textDepositId, this.textAmount)
                Font.setInto(context, Font.Rubik.REGULAR, this.textDate)

            }
            ViewHelper.makeClickable(this, itemView)
        }

        private fun statusFormat(context: Context, response: DepositListResponse) {
            this.binding.let {
                if (response.status == Constant.Status.SUCCESS) {
                    setColorStatus(context, R.color.light_blue, R.color.dark_blue)
                } else if (response.status == Constant.Status.PROCESSING) {
                    setColorStatus(context, R.color.waiting_bg, R.color.waiting_text)
                } else if (response.status == Constant.Status.PENDING) {
                    setColorStatus(context, R.color.on_process_bg, R.color.on_process_text)
                } else if (response.status == Constant.Status.CANCELED) {
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
            if (p0 == this.itemView){
                val bundle = Bundle()
                bundle.putSerializable(Constant.Key.DEPOSIT_OBJ, this.response)

                GeneralHelper.move(this.context, DetailDepositActivity::class.java, bundle, false)
            }
        }
    }
}