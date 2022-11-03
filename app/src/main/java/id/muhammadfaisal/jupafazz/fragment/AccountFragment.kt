package id.muhammadfaisal.jupafazz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.activity.*
import id.muhammadfaisal.jupafazz.api.model.BaseResponse
import id.muhammadfaisal.jupafazz.databinding.FragmentAccountBinding
import id.muhammadfaisal.jupafazz.helper.ApiHelper
import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.ui.Loading
import id.muhammadfaisal.jupafazz.utils.BottomSheets
import id.muhammadfaisal.jupafazz.utils.Constant
import id.muhammadfaisal.jupafazz.utils.Font
import id.muhammadfaisal.jupafazz.utils.Font.Companion.setInto
import id.muhammadfaisal.jupafazz.utils.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class AccountFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentAccountBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialize()
        this.data()
    }

    private fun data() {
        val name = Preferences.getName(requireContext())
        this.binding.apply {
            this.textName.text = name
        }
    }

    private fun initialize() {
        requireActivity().apply {
            (this as AppCompatActivity)
            Font.let {
                setInto(
                    this,
                    Font.Rubik.REGULAR,
                    binding.textTitleAccountSetting,
                    binding.textTitleOtherMenu
                )

                setInto(
                    this,
                    Font.Rubik.MEDIUM,
                    binding.textAccountInformation,
                    binding.textTopUpBalance,
                    binding.textMutationBalance,
                    binding.textChangePassword,
                    binding.textSK,
                    binding.textPriceList,
                    binding.textPrivacyPolicy,
                    binding.textFaq,
                    binding.textHelp,
                    binding.textLogout
                )
            }

            ViewHelper.makeClickable(
                this@AccountFragment,
                binding.textMutationBalance,
                binding.textChangePassword,
                binding.textTopUpBalance,
                binding.textPriceList,
                binding.textHelp,
                binding.textFaq,
                binding.textPrivacyPolicy,
                binding.textSK,
                binding.textLogout,
                binding.cardQr
            )
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.textMutationBalance) {
            GeneralHelper.move(requireContext(), MutationActivity::class.java, false)
        } else if (p0 == this.binding.textChangePassword) {
            GeneralHelper.move(requireContext(), ChangePasswordActivity::class.java, false)
        } else if (p0 == this.binding.textTopUpBalance) {
            GeneralHelper.move(requireContext(), MethodDepositActivity::class.java, false)
        } else if (p0 == this.binding.textPriceList) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.URL, Constant.WEB.PRICE_LIST)
            GeneralHelper.move(requireContext(), WebViewActivity::class.java, bundle,false)
        } else if (p0 == this.binding.textHelp) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.URL, Constant.WEB.HELP)
            GeneralHelper.move(requireContext(), WebViewActivity::class.java, bundle,false)
        } else if (p0 == this.binding.textFaq) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.URL, Constant.WEB.FAQ)
            GeneralHelper.move(requireContext(), WebViewActivity::class.java, bundle,false)
        } else if (p0 == this.binding.textPrivacyPolicy) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.URL, Constant.WEB.PRIVACY_POLICY)
            GeneralHelper.move(requireContext(), WebViewActivity::class.java, bundle,false)
        } else if (p0 == this.binding.textSK) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.URL, Constant.WEB.TERMS)
            GeneralHelper.move(requireContext(), WebViewActivity::class.java, bundle,false)
        } else if (p0 == this.binding.textLogout) {
            this.processLogout()
        } else if (p0 == this.binding.cardQr) {
            BottomSheets.showQR(requireActivity() as AppCompatActivity, false)
        }
    }

    private fun processLogout() {
        val loading = Loading(requireContext())
        loading.setCancelable(false)
        loading.show()

        val wa = Preferences.getWhatsApp(requireContext())
        val session = Preferences.getSession(requireContext())

        CompositeDisposable().add(
            ApiHelper.destroySession(wa, session).subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                override fun onNext(t: Response<BaseResponse>) {
                    val body = t.body()

                    if (body != null) {
                        if (body.isSuccess) {
                            Preferences.delete(requireContext(), Constant.Key.WHATSAPP)
                            Preferences.delete(requireContext(), Constant.Key.SESSION)
                            Preferences.delete(requireContext(), Constant.Key.NAME)

                            GeneralHelper.move(requireContext(), LoginActivity::class.java, null, true)
                        } else {
                            if (GeneralHelper.isSessionExpire(body.message)) {
                                GeneralHelper.sessionExpired(requireContext())
                                return
                            }

                            BottomSheets.error((requireActivity() as AppCompatActivity), getString(R.string.something_wrong), body.message, false, true)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    BottomSheets.error((requireActivity() as AppCompatActivity), e, false, true)
                }

                override fun onComplete() {
                    loading.dismiss()
                }
            })
        )
    }
}