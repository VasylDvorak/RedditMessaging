package com.redditmessaging.views.base_for_cinema


import android.animation.Animator
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.redditmessaging.R
import com.redditmessaging.model.datasource.AppState
import com.redditmessaging.model.repository.OnLineRepository
import com.redditmessaging.utils.ui.AlertDialogFragment
import com.redditmessaging.views.AnimatorDictionary
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.koin.android.ext.android.inject

abstract class BaseFragment<T : AppState, B : ViewBinding>(
    private val inflateBinding: (
        inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean
    ) -> B
) : Fragment(){
    private var _binding: B? = null

    protected val binding: B
        get() = _binding!!

    private val gson = Gson()

    private var snack: Snackbar? = null
    protected var isNetworkAvailable: Boolean = false
    private val checkConnection: OnLineRepository by inject()

    protected val checkSDKversion = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        subscribeToNetworkChange()

    }

    private fun subscribeToNetworkChange() {

        checkConnection.observe(
            this@BaseFragment
        ) {
            isNetworkAvailable = it
            if (!isNetworkAvailable) {
                snack = Snackbar.make(
                    requireView(),
                    R.string.dialog_message_device_is_offline,
                    Snackbar.LENGTH_INDEFINITE
                )
                snack?.show()
            } else {
                snack?.dismiss()
                snack = null
            }
        }
        checkConnection.currentStatus()

    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        activity?.let {
            AlertDialogFragment.newInstance(title, message)
                .show(it.supportFragmentManager, DIALOG_FRAGMENT_TAG)
        }
    }

    private fun isDialogNull(): Boolean {
        return activity?.supportFragmentManager?.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding.invoke(inflater, container, false)
        return binding.root
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        return AnimatorDictionary().setAnimator(transit, enter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DIALOG_FRAGMENT_TAG = "dialog_fragment_tag"
    }
}
