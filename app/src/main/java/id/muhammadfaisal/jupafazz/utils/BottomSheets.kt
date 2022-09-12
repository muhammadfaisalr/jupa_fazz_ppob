package id.muhammadfaisal.jupafazz.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.jupafazz.bottomsheet.ErrorBottomSheetDialogFragment
import id.muhammadfaisal.jupafazz.bottomsheet.RequestPermissionBottomSheetDialog

class BottomSheets {
    companion object {
        fun error(activity: AppCompatActivity, title: String, desc: String, isShowReason: Boolean, isCancelable: Boolean) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.TITLE, title)
            bundle.putString(Constant.Key.DESCRIPTION, desc)
            bundle.putBoolean(Constant.Key.IS_SHOW_REASON, isShowReason)

            val bottomSheet = ErrorBottomSheetDialogFragment()
            bottomSheet.isCancelable = isCancelable
            bottomSheet.arguments = bundle
            bottomSheet.show(activity.supportFragmentManager, BottomSheets::class.java.simpleName)
        }

        fun requestPermission(activity: AppCompatActivity, permissions: Array<out String>, isCancelable: Boolean) {
            val bundle = Bundle()
            bundle.putStringArray(Constant.Key.PERMISSIONS, permissions)

            val bottomSheet = RequestPermissionBottomSheetDialog()
            bottomSheet.isCancelable = isCancelable
            bottomSheet.arguments = bundle
            bottomSheet.show(activity.supportFragmentManager, BottomSheets::class.java.simpleName)
        }
    }
}