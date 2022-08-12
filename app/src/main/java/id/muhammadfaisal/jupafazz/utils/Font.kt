package id.muhammadfaisal.jupafazz.utils

import android.graphics.Typeface
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class Font {
    class Rubik {
        companion object {
            const val REGULAR = "fonts/rubik/regular.ttf"
            const val MEDIUM = "fonts/rubik/medium.ttf"
            const val SEMI_BOLD = "fonts/rubik/semibold.ttf"
            const val EXTRA_BOLD = "fonts/rubik/extrabold.ttf"
            const val BOLD = "fonts/rubik/bold.ttf"
            const val LIGHT = "fonts/rubik/light.ttf"
        }
    }

    companion object {
        fun setInto (activity: AppCompatActivity, typeFont : String, vararg views: Any) {
            for (view in views) {
                when (view) {
                    is TextView -> {
                        view.typeface = Typeface.createFromAsset(activity.assets, typeFont)
                    }
                    is MaterialButton -> {
                        view.typeface = Typeface.createFromAsset(activity.assets, typeFont)
                    }
                    is CheckBox -> {
                        view.typeface = Typeface.createFromAsset(activity.assets, typeFont)
                    }
                    is TextInputEditText -> {
                        view.typeface = Typeface.createFromAsset(activity.assets, typeFont)
                    }

                    is RadioButton -> {
                        view.typeface = Typeface.createFromAsset(activity.assets, typeFont)
                    }
                }
            }
        }
    }
}