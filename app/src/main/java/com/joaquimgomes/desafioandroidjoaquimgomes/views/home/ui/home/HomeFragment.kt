package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.joaquimgomes.desafioandroidjoaquimgomes.R
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.SetToastMessage
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.VerifyNetwork
import com.joaquimgomes.desafioandroidjoaquimgomes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var toast: SetToastMessage
    private lateinit var verifyNetwork: VerifyNetwork

    private var networkIsConnected: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = activity?.let { ViewModelProviders.of(it).get(HomeViewModel::class.java) }!!

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        toast = SetToastMessage()
        verifyNetwork = VerifyNetwork()

        networkIsConnected = verifyNetwork.verifyAvailableNetwork(context)

        setBindingsVariables()
        setupViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (networkIsConnected) {
//            observerAllCharacters()
//            showImgLoading()
        } else {
//            showNoNetworkError()
        }

    }

    private fun setupViews() {
//        TODO("Not yet implemented")
    }

    private fun setBindingsVariables() {
//        TODO("Not yet implemented")
    }

    private fun showImgLoading() {
//        imgLoadingContainer.isVisible = true
    }

    private fun showImgGetServerData() {
//        cardMonthResume.isVisible = false
//        imgErrorNoData.isVisible = true
    }

    private fun hideImgLoading() {
//        imgLoadingContainer.isVisible = false
//        cardMonthResume.isVisible = true
    }

    private fun showNoNetworkError() {
        toast.setToastMessage(context, R.string.no_network)
//        imgErrorNoNetwork.isVisible = true
    }

}