package id.muhammadfaisal.jupafazz.utils

class Constant {
    class Key {
        companion object {
            const val PRODUCT_ENT = "PRODUCT_ENT"
            const val PRODUCT_V = "PRODUCT_V"
            const val BUNDLING = "BUNDLING"
            const val WHATSAPP = "WHATSAPP"
            const val SESSION = "SESSION"
            const val CATEGORY = "CATEGORY"
            const val NAME = "NAME"
            const val TITLE = "TITLE"
            const val BALANCE = "BALANCE"
            const val DESCRIPTION = "DESCRIPTION"
            const val IS_SHOW_REASON = "IS_SHOW_REASON"
            const val PERMISSIONS = "PERMISSIONS"
        }
    }

    class Status {
        companion object {
            const val SUCCESS = "Sukses"
            const val FAILED = "Gagal"
            const val WAITING = "Menunggu Pembayaran"
            const val ON_PROCESS = "Sedang di Proses"
            const val CANCEL = "Dibatalkan"
        }
    }

    class URL {
        companion object {
            const val BASE = "http://api.jupafazz.jupagroup.com/"

            //Auth
            const val REGISTER = "request/auth/api/register"
            const val RESEND_OTP = "request/auth/api/resend-otp"
            const val VERIFICATION_OTP = "request/auth/api/verif-otp"
            const val LOGIN = "request/auth/api/login"
            const val START_SESSION = "request/auth/api/session"

            //Users
            const val USER_DETAIL = "request/users/api/detail"

            //Product
            const val GET_PRODUCT = "request/product/api/all"
            const val GET_PRODUCT_V = "request/product/api/version"
        }
    }

    class CATEGORY {
        companion object {
            const val DATA = "Data"
            const val E_MONEY = "E-Money"
            const val GAMES = "Games"
            const val PLN = "PLN"
            const val SMS_PACKAGE = "Paket SMS & Telpon"
            const val PREPAID_BALANCE = "Pulsa"
            const val VOUCHER = "Voucher"
        }
    }

    class TABLE {
        companion object {
            const val PRODUCT = "m_product"
        }
    }
}