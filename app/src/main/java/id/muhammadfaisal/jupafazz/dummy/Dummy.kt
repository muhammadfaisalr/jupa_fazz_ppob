package id.muhammadfaisal.jupafazz.dummy

import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.utils.Constant

data class Menu(
    val image: Int,
    val title: String
)

data class History(
    val date: Long,
    val name: String,
    val price: Long,
    val value: String,
    val type: String,
    val status: String
)

class Dummy {
    companion object {
        fun getMenus() : ArrayList<Menu> {
            val menus = ArrayList<Menu>()
            menus.add(Menu(R.drawable.ic_add, "Pulsa Reguler"))
            menus.add(Menu(R.drawable.ic_add, "Paket Data"))
            menus.add(Menu(R.drawable.ic_add, "Token PLN"))
            menus.add(Menu(R.drawable.ic_add, "Promo"))
            menus.add(Menu(R.drawable.ic_add, "Paket SMS"))
            menus.add(Menu(R.drawable.ic_add, "Paket Telepon"))
            menus.add(Menu(R.drawable.ic_add, "Hiburan"))
            menus.add(Menu(R.drawable.ic_add, "Masa Akif"))

            return menus
        }
    }
}