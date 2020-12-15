package com.lawlett.mafia.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V : BaseViewModel, B : ViewBinding>(private val layoutId: Int) :
    Fragment() {

    var mViewModule: V? = null
    var binding: B? = null

    private var hasInitializedRootView = false
    var rootView: View? = null

    abstract fun getViewModule(): V
    abstract fun getViewBinding(): B?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container)
    }

    private fun getPersistentView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(layoutId, container, false)
        } else {
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }
        binding = getViewBinding()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            mViewModule = getViewModule()
            setUpViewModelObs()
            setUpView()
        }
    }

    abstract fun setUpView()

    abstract fun setUpViewModelObs()
}