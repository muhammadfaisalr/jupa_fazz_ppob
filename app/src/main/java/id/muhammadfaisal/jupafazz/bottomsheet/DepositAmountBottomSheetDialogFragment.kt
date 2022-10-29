package id.muhammadfaisal.jupafazz.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.activity.RequestDepositActivity
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositMethodResponse
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositPaymentResponse
import id.muhammadfaisal.jupafazz.api.model.deposit.DepositRequestResponse
import id.muhammadfaisal.jupafazz.databinding.FragmentDepositAmountBottomSheetDialogBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class DepositAmountBottomSheetDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentDepositAmountBottomSheetDialogBinding

    private lateinit var methodId: String
    private lateinit var deposit: DepositMethodResponse

    private lateinit var depositRequest: DepositRequestResponse
    private lateinit var depositPayment: DepositPaymentResponse

    private lateinit var amount: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        this.binding = FragmentDepositAmountBottomSheetDialogBinding.inflate(this.layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        this.deposit = bundle.getSerializable(Constant.Key.DEPOSIT_OBJ) as DepositMethodResponse

        this.binding.textTitle.text = this.deposit.method

        Glide
            .with(requireActivity())
            .load(this.deposit.image)
            .error(com.denzcoskun.imageslider.R.drawable.error)
            .into(this.binding.image)

        this.binding.apply {
            this.textNote.text = "Anda akan mengisi saldo akun jupafazz anda melalui ${deposit.method}."

            ViewHelper.visible(this.textNote)
            ViewHelper.makeClickable(this@DepositAmountBottomSheetDialogFragment, this.buttonProcess)

            Font.setInto((requireActivity() as AppCompatActivity), Font.Rubik.MEDIUM, this.textTitle, this.buttonProcess, this.inputAmount, this.textPrefixRp)
            Font.setInto((requireActivity() as AppCompatActivity), Font.Rubik.REGULAR, this.textAmountTitle,this.textNote)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonProcess) {
            this.process()
        }
    }

    private fun process() {

        if (GeneralHelper.isInputEmpty(this.binding.inputAmount)) {
            this.binding.inputAmount.error = "Harap masukkan nominal!"
            return
        }

        this.amount = Formatter.plainCurrency(this.binding.inputAmount)

        val loading = Loading(requireContext())
        loading.setCancelable(false)
        loading.show()

        val wa = Preferences.getWhatsApp(requireContext())
        val session = Preferences.getSession(requireContext())

        var isSuccess = true

        CompositeDisposable().add(
            ApiHelper
                .depositRequest(wa, session, this.deposit.id, amount)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body != null) {
                            if (body.isSuccess) {
                                val data = body.data as Map<*, *>
                                val gson = Gson()
                                val depReq = gson.fromJson(gson.toJson(data), DepositRequestResponse::class.java)

                                this@DepositAmountBottomSheetDialogFragment.apply {
                                    this.depositRequest = depReq
                                    this.depositPayment = this.depositRequest.payment
                                }

                            } else {
                                isSuccess = false
                                if (GeneralHelper.isSessionExpire(body.message)) {
                                    //kick if session is invalid
                                    GeneralHelper.sessionExpired(requireContext())
                                    return
                                }

                                BottomSheets.error(
                                    (requireActivity() as AppCompatActivity),
                                    getString(R.string.something_wrong),
                                    body.message,
                                    isShowReason = false,
                                    isCancelable = true
                                )
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        BottomSheets.error(
                            (requireActivity() as AppCompatActivity),
                            e,
                            isShowReason = false,
                            isCancelable = true
                        )
                    }

                    override fun onComplete() {
                        loading.dismiss()

                        if (isSuccess) {
                            val bundle = Bundle()
                            bundle.putSerializable(Constant.Key.DEPOSIT_REQ_OBJ, this@DepositAmountBottomSheetDialogFragment.depositRequest)
                            bundle.putSerializable(Constant.Key.DEPOSIT_OBJ, this@DepositAmountBottomSheetDialogFragment.deposit)
                            bundle.putSerializable(Constant.Key.AMOUNT, this@DepositAmountBottomSheetDialogFragment.amount)

                            GeneralHelper.move(requireContext(), RequestDepositActivity::class.java, bundle, false)
                        }
                    }
                })
        )
    }
}