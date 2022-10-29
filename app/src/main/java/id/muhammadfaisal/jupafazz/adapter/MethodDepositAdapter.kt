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
import id.muhammadfaisal.jupafazz.activity.RequestDepositActivity
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositMethodResponse
import id.muhammadfaisal.jupafazz.databinding.ItemDepositMethodBinding
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant

class MethodDepositAdapter(val context: Context, private val list: ArrayList<DepositMethodResponse>) : RecyclerView.Adapter<MethodDepositAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_deposit_method, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        val binding = ItemDepositMethodBinding.bind(view)

        private lateinit var context: Context
        private lateinit var deposit: DepositMethodResponse

        fun bind(context: Context, depositMethodResponse: DepositMethodResponse) {
            this.context = context
            this.deposit = depositMethodResponse

            this.binding.apply {
                this.textTitle.text = depositMethodResponse.method
                Glide
                    .with(context)
                    .load(depositMethodResponse.image)
                    .error(com.denzcoskun.imageslider.R.drawable.error)
                    .into(this.image)
            }

            ViewHelper.makeClickable(this, this.itemView)
        }

        override fun onClick(p0: View?) {
            if (p0 == this.itemView) {
                BottomSheets.detailDeposit((this.context as AppCompatActivity), this.deposit, true)
            }
        }
    }
}