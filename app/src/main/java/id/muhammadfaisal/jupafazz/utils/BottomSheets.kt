package id.muhammadfaisal.jupafazz.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositMethodResponse
import id.muhammadfaisal.jupafazz.bottomsheet.*
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity

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

        fun error(activity: AppCompatActivity, t: Throwable, isShowReason: Boolean, isCancelable: Boolean) {
            t.printStackTrace()
            error(activity, activity.getString(R.string.something_wrong), t.message!!, isShowReason, isCancelable)
        }

        fun requestPermission(activity: AppCompatActivity, permissions: Array<out String>, isCancelable: Boolean) {
            val bundle = Bundle()
            bundle.putStringArray(Constant.Key.PERMISSIONS, permissions)

            val bottomSheet = RequestPermissionBottomSheetDialog()
            bottomSheet.isCancelable = isCancelable
            bottomSheet.arguments = bundle
            bottomSheet.show(activity.supportFragmentManager, BottomSheets::class.java.simpleName)
        }

        fun detailProduct(activity: AppCompatActivity, productEntity: ProductEntity, isCancelable: Boolean) {
            val bundle = Bundle()
            bundle.putSerializable(Constant.Key.PRODUCT_ENT, productEntity)

            val bottomSheet = DetailProductBottomSheetDialogFragment()
            bottomSheet.isCancelable = isCancelable
            bottomSheet.arguments = bundle
            bottomSheet.show(activity.supportFragmentManager, BottomSheets::class.java.simpleName)
        }

        fun detailDeposit(activity: AppCompatActivity, deposit: DepositMethodResponse, isCancelable: Boolean) {
            val bundle = Bundle()
            bundle.putSerializable(Constant.Key.DEPOSIT_OBJ, deposit)

            val bottomSheet = DepositAmountBottomSheetDialogFragment()
            bottomSheet.isCancelable = isCancelable
            bottomSheet.arguments = bundle
            bottomSheet.show(activity.supportFragmentManager, BottomSheets::class.java.simpleName)
        }

        fun showQR(activity: AppCompatActivity, isCancelable: Boolean) {
            val bottomSheet = QrBottomSheetDialogFragment()
            bottomSheet.isCancelable = isCancelable
            bottomSheet.show(activity.supportFragmentManager, BottomSheets::class.java.simpleName)
        }
    }
}