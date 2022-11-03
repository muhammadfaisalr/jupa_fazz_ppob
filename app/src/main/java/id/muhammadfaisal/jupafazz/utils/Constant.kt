package id.muhammadfaisal.jupafazz.utils

import id.muhammadfaisal.jupafazz.activity.CreateTransactionActivity
import id.muhammadfaisal.jupafazz.bottomsheet.DetailProductBottomSheetDialogFragment

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
            const val INVOICE_ID = "INV_ID"
            const val DETAIL_TRX = "DETAIL_TRX"
            const val DEPOSIT_OBJ = "DEPOSIT_OBJ"
            const val DEPOSIT_REQ_OBJ = "DEPOSIT_REQ_OBJ"
            const val AMOUNT = "AMOUNT"
            const val METHOD_ID = "METHOD_ID"
            const val URL = "URL"
        }
    }

    class Status {
        companion object {
            const val SUCCESS = "Success"
            const val PENDING = "Pending"
            const val PROCESSING = "Processing"
            const val FAILED = "Gagal"
            const val WAITING = "Menunggu Pembayaran"
            const val ON_PROCESS = "Sedang di Proses"
            const val CANCELED = "Canceled"
        }
    }

    class CLS_NAME {
        companion object {
            const val CREATE_TRX_ACT = "CREATE_TRX_ACT"
            const val DETAIL_PROD_BOTSHEET = "DETAIL_PROD_BOTSHEET"
        }
    }

    class URL {
        companion object {
            const val BASE = "http://api.jupafazz.jupagroup.com/"
            const val BASE_PRODUCT_IMAGE = "https://assets.jupagroup.com/images/product/"

            //Auth
            const val REGISTER = "request/auth/api/register"
            const val RESEND_OTP = "request/auth/api/resend-otp"
            const val VERIFICATION_OTP = "request/auth/api/verif-otp"
            const val LOGIN = "request/auth/api/login"
            const val START_SESSION = "request/auth/api/session"
            const val DESTROY_SESSION = "request/auth/api/destroy"

            //Users
            const val USER_DETAIL = "request/users/api/detail"
            const val USER_BALANCE = "request/users/api/balance"
            const val CHANGE_PASSWORD = "request/users/api/change"

            //Product
            const val GET_PRODUCT = "request/product/api/all"
            const val GET_PRODUCT_V = "request/product/api/version"

            //Transaction
            const val TRANSACTION = "/request/prabayar/api/transaction"
            const val DETAIL_TRANSACTION = "/request/prabayar/api/detail"
            const val LIST_TRANSACTION = "/request/prabayar/api/list"

            //Deposit
            const val DEPOSIT_METHOD = "/request/deposit/api/method"
            const val DEPOSIT_REQUEST = "/request/deposit/api/request"
            const val DEPOSIT_LIST = "/request/deposit/api/list"

            //miscellaneous
            const val BANNER = "/request/utility/api/banner"
            const val BANNER_ACTIVITY = "/request/utility/api/spanduk"
        }
    }

    class WEB {
        companion object {
            const val PRICE_LIST = "https://juraganpannel.com/daftar-harga"
            const val HELP = "https://juraganpannel.com/bantuan"
            const val TERMS = "https://juraganpannel.com/syarat-ketentuan"
            const val FAQ = "https://juraganpannel.com/pertanyaan-umum"
            const val PRIVACY_POLICY = "https://juraganpannel.com/privacy-policy"
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