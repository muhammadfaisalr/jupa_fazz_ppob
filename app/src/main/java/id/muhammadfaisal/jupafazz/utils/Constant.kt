package id.muhammadfaisal.jupafazz.utils

class Constant {
    class Key {
        companion object {
            const val BUNDLING = "BUNDLING"
            const val WHATSAPP = "WHATSAPP"
            const val SESSION = "SESSION"
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
            const val REGISTER = "request/auth/api/register"
            const val RESEND_OTP = "request/auth/api/resend-otp"
            const val VERIFICATION_OTP = "request/auth/api/verif-otp"
            const val LOGIN = "request/auth/api/login"
            const val START_SESSION = "request/auth/api/session"
        }
    }
}