package id.muhammadfaisal.jupafazz.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.databinding.ItemLoadingBinding
import id.muhammadfaisal.jupafazz.utils.Font

class Loading(context: Context): AlertDialog(context) {
    var binding = ItemLoadingBinding.bind(LayoutInflater.from(context).inflate(R.layout.item_loading, null))

    init {
        super.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.binding.apply {
            Font.setInto((context as AppCompatActivity), Font.Rubik.MEDIUM, binding.textLoading)
        }
    }

    override fun show() {
        super.show()
        super.setContentView(R.layout.item_loading)
    }
}