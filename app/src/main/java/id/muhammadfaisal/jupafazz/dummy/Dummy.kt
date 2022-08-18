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

        fun getHistories() : ArrayList<History> {
            val histories = ArrayList<History>()

            histories.add(History(1660669200000, "Pulsa Three 5K", 5700, "0812344321", "PULSA", Constant.Status.SUCCESS))
            histories.add(History(1660790314097, "Pulsa Three 5K", 5700, "0812344321", "PULSA", Constant.Status.ON_PROCESS))
            histories.add(History(1660669200000, "Pulsa Three 10K", 11200, "0812344321", "PULSA", Constant.Status.FAILED))
            histories.add(History(1660669200000, "Pulsa Three 5K", 5700, "0812344321", "PULSA", Constant.Status.CANCEL))
            histories.add(History(1660669200000, "Pulsa Three 5K", 5700, "0812344321", "PULSA", Constant.Status.SUCCESS))

            return histories
        }
    }
}