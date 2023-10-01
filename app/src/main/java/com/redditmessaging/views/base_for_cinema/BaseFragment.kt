package com.redditmessaging.views.base_for_cinema


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.redditmessaging.model.datasource.AppState

abstract class BaseFragment<T : AppState, B : ViewBinding>(
    private val inflateBinding: (
        inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean
    ) -> B
) : Fragment() {
    private var _binding: B? = null

    protected val binding: B
        get() = _binding!!

    private val gson = Gson()

    private var snack: Snackbar? = null
    protected var isNetworkAvailable: Boolean = false

    protected val checkSDKversion = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding.invoke(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
