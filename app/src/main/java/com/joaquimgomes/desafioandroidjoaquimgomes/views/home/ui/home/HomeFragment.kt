package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaquimgomes.desafioandroidjoaquimgomes.R
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.SetToastMessage
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.VerifyNetwork
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Character
import com.joaquimgomes.desafioandroidjoaquimgomes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var toast: SetToastMessage
    private lateinit var verifyNetwork: VerifyNetwork

    private lateinit var charactersRecyclerView: RecyclerView
    private lateinit var imgLoadingContainer: ConstraintLayout
    private lateinit var imgErrorNoNetwork: ConstraintLayout
    private lateinit var imgErrorNoData: ConstraintLayout
    private lateinit var btnLoadMoreCharacterData: Button

    private var networkIsConnected: Boolean = false
    private var valueQueryServerOffSet = 0
    private val sizeCharacterPagination = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = activity?.let { ViewModelProviders.of(it).get(HomeViewModel::class.java) }!!

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        clearAllCharactersInfo()
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
            homeViewModel.getAllCharactersInfo(valueQueryServerOffSet)
            observerAllCharacters()
            showImgLoading()
        } else {
            showNoNetworkError()
        }

    }

    private fun observerAllCharacters() {

        homeViewModel.charactersServerData.observe(viewLifecycleOwner, Observer { characters ->

            if (characters == null) {
                hideImgLoading()
                showImgNoServerData()
            } else {
                displayCharacters(characters)
            }
        })

        homeViewModel.hasAllServerCharacterDataLoaded.observe(viewLifecycleOwner, Observer{

            if (it) {
                btnLoadMoreCharacterData.isEnabled = false
            }

        })

    }

    private fun displayCharacters(characters: List<Character>) {

        if (characters.isNotEmpty()) {

            val characterAdapter =
                HomeCharactersAdapter(characters) { character ->
                    navigateToCharacterDetails(character)
                }

            charactersRecyclerView.adapter = characterAdapter
            characterAdapter.notifyDataSetChanged()
            hideImgLoading()
            showButtonLoadMoreData()

            btnLoadMoreCharacterData.setOnClickListener {

                valueQueryServerOffSet = valueQueryServerOffSet.plus(sizeCharacterPagination)

                homeViewModel.getAllCharactersInfo(valueQueryServerOffSet)
                characterAdapter.notifyDataSetChanged()

            }
        }

    }

    private fun navigateToCharacterDetails(character: Character) {

        homeViewModel.getAllCharacterComics(character.id)

        val characterName = character.name
        val characterDescription = character.description
        val characterImage =
            character.characterThumbnail.path + "." + character.characterThumbnail.extension

        var argsToFragmentCharacterDetails: NavDirections? = null

        argsToFragmentCharacterDetails =
            HomeFragmentDirections.actionHomeFragmentToCharacterDetailsFragment(
                characterName,
                characterImage,
                characterDescription
            )

        this.findNavController().navigate(argsToFragmentCharacterDetails)
    }

    private fun setupViews() {

        charactersRecyclerView.layoutManager = LinearLayoutManager(this.context)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)

    }

    private fun setBindingsVariables() {

        imgLoadingContainer = binding.fragmentHomeWInternetLoadingPlaceholderContainer
        imgErrorNoNetwork = binding.fragmentHomeWOInternetLoadingPlaceholderContainer
        imgErrorNoData = binding.fragmentHomeNoServerDataPlaceholderContainer
        charactersRecyclerView = binding.fragmentHomeMainRecyclerViewCharacters
        btnLoadMoreCharacterData = binding.fragmentHomeButtonLoadMoreData

    }

    private fun showImgLoading() {
        imgLoadingContainer.isVisible = true
    }

    private fun showImgNoServerData() {
        toast.setToastMessage(context, R.string.error_no_server_data)
        imgErrorNoData.isVisible = true
    }

    private fun hideImgLoading() {
        imgLoadingContainer.isVisible = false
    }

    private fun showNoNetworkError() {
        toast.setToastMessage(context, R.string.no_network)
        imgErrorNoNetwork.isVisible = true
    }

    private fun clearAllCharactersInfo() {
        homeViewModel.clearAllCharactersInfo()
    }

    private fun showButtonLoadMoreData() {
        btnLoadMoreCharacterData.isVisible = true
    }

}