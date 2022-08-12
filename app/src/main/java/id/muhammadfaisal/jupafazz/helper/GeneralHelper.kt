package id.muhammadfaisal.jupafazz.helper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.jupafazz.utils.Constant

class GeneralHelper {

    companion object {

        fun move(context: Context, clz: Class<*>, bundle: Bundle?, isForget: Boolean) {
            val intent = Intent(context, clz);

            if (bundle != null) {
                intent.putExtra(Constant.Key.BUNDLING, bundle)
            }

            context.startActivity(intent);
            if (isForget) {
                (context as AppCompatActivity).finish()
            }
        }

        fun move(context: Context, clz: Class<*>, isForget: Boolean) {
            this.move(context, clz, null, isForget)
        }
    }
}