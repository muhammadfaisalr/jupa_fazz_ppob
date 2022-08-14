package id.muhammadfaisal.jupafazz.helper

import android.view.View

class ViewHelper {
    companion object {
        fun makeClickable(listener: View.OnClickListener, vararg views: View) {
            for (view in views) {
                view.setOnClickListener(listener)
            }
        }

        fun gone(vararg views: View) {
            for (view in views) {
                view.visibility = View.GONE
            }
        }

        fun visible(vararg views: View) {
            for (view in views) {
                view.visibility = View.VISIBLE
            }
        }

        fun invisible(vararg views: View) {
            for (view in views) {
                view.visibility = View.INVISIBLE
            }
        }
    }
}