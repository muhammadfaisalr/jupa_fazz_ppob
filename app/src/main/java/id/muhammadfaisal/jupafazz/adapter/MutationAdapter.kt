package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.mutation.MutationResponse
import id.muhammadfaisal.jupafazz.databinding.ItemMutationBinding
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Formatter

class MutationAdapter(val context: Context, var mutations: ArrayList<MutationResponse>) : RecyclerView.Adapter<MutationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutationAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mutation, parent, false))
    }

    override fun onBindViewHolder(holder: MutationAdapter.ViewHolder, position: Int) {
        holder.bind(this.context, this.mutations[position])
    }

    override fun getItemCount(): Int {
        return this.mutations.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val binding = ItemMutationBinding.bind(itemView)

        fun bind(context: Context, mutationResponse: MutationResponse) {
            this.binding.apply {
                this.textDepositId.text = mutationResponse.description
                this.textDate.text = mutationResponse.createDate
                this.textQuantity.text = "${mutationResponse.type} ${Formatter.currency(mutationResponse.balance.toLong(), "ID", true)}"

                if (mutationResponse.type == "-") {
                    this.cardBackground.setCardBackgroundColor(context.getColor(R.color.error_bg))
                    this.textQuantity.setTextColor(context.getColor(R.color.error_text))
                } else {
                    this.cardBackground.setCardBackgroundColor(context.getColor(R.color.light_blue))
                    this.textQuantity.setTextColor(context.getColor(R.color.dark_blue))
                }

                (context as AppCompatActivity)
                Font.setInto(context, Font.Rubik.MEDIUM, this.textQuantity, this.textDepositId)
                Font.setInto(context, Font.Rubik.REGULAR, this.textDate)
            }
        }

    }
}