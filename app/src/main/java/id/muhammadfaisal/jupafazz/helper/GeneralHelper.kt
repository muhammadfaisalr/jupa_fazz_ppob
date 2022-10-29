package id.muhammadfaisal.jupafazz.helper

import android.app.ActivityOptions
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.util.StringUtil
import com.google.android.material.textfield.TextInputEditText
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.activity.CreateTransactionActivity
import id.muhammadfaisal.jupafazz.activity.LoginActivity
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Preferences
import org.apache.commons.lang3.StringUtils

class GeneralHelper {

    companion object {

        fun move(context: Context, clz: Class<*>, bundle: Bundle?, isForget: Boolean) {
            Log.d(GeneralHelper::class.java.simpleName, "Moving Screen From [$context] to [${clz.simpleName}]. Bundle: [$bundle]. isForget: [$isForget].")
            val intent = Intent(context, clz);

            if (bundle != null) {
                intent.putExtra(Constant.Key.BUNDLING, bundle)
            }


            if (isForget) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }

            if (clz == CreateTransactionActivity::class.java) {
                val options =
                    ActivityOptions.makeCustomAnimation(context, R.anim.slide_up, R.anim.slide_down)
                context.startActivity(intent, options.toBundle())
            } else {
                context.startActivity(intent);
            }

            if (isForget) {
                (context as AppCompatActivity).finish()
            }
        }

        fun move(context: Context, clz: Class<*>, isForget: Boolean) {
            this.move(context, clz, null, isForget)
        }

        fun isInputEmpty(vararg inputs: EditText): Boolean {
            var isEmpty = true
            for (input in inputs) {
                if (input.text.toString().isNotEmpty()) {
                    isEmpty = false
                }
            }

            return isEmpty
        }

        fun isStringMatch(stringA: String, stringB: String): Boolean {
            var isMatch = true

            if (stringA != stringB) {
                isMatch = false
            }

            return isMatch
        }

        fun getInputValue(input: EditText): String {
            return input.text.toString()
        }

        fun copy(text: String?, context: Context) {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copy", text)
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(context, "Berhasil disalin.", Toast.LENGTH_SHORT).show()
        }


        fun getInputValues(vararg inputs: EditText): HashMap<Int, String> {
            val map = HashMap<Int, String>()

            var index = 0
            for (input in inputs) {
                if (input.text.isNotEmpty()) {
                    map[index] = input.text.toString()
                    index++
                }
            }
            return map
        }

        fun playNotification(context: Context) {
            val mp = MediaPlayer.create(context, R.raw.payment_success)
            mp.start()
        }

        fun isSessionExpire(s: String): Boolean {
            if (StringUtils.containsIgnoreCase(s, "session not found")) {
                return true
            }

            return false
        }

        fun sessionExpired(context: Context) {
            Toast.makeText(context, "Session anda telah berakhir, silahkan masuk kembali", Toast.LENGTH_SHORT).show()

            Preferences.delete(context, Constant.Key.SESSION)
            Preferences.delete(context, Constant.Key.WHATSAPP)

            GeneralHelper.move(context, LoginActivity::class.java, true)
        }
    }
}