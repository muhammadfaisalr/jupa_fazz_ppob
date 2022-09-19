package id.muhammadfaisal.jupafazz.utils

import android.content.Context

class Preferences {
    companion object {

        const val SP_NAME = "JUPAFAZZ_PREFERENCES"

        fun save(context: Context, key: String, value: Any) {
            val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {
                when (value) {
                    is String -> {
                        putString(key, value)
                    }
                    is Int -> {
                        putInt(key, value)
                    }
                    is Float -> {
                        putFloat(key, value)
                    }
                    is Boolean -> {
                        putBoolean(key, value)
                    }
                    is Long -> {
                        putLong(key, value)
                    }
                }

                apply()
            }
        }

        fun get(context : Context, key : String) : Any? {
            val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.all[key]
        }

        fun delete(context: Context, key : String) {
            val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.edit().remove(key).apply()
        }
    }
}