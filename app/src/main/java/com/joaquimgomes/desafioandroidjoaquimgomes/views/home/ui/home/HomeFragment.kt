package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

//        homeViewModel.getCharacterComics(1009664)

        if (networkIsConnected) {
            homeViewModel.getAllCharactersInfo()
            observerAllCharacters()
            showImgLoading()
        } else {
            showNoNetworkError()
        }

    }

    private fun observerAllCharacters() {

        homeViewModel.charactersServerData.observe(viewLifecycleOwner, Observer { characters ->
            if (characters.isEmpty()) {
                toast.setToastMessage(context, R.string.error_get_expenses_from_server_no_data)
                hideImgLoading()
                showImgNoServerData()
//                showButtonReload()
            } else {
                Toast.makeText(context, "aqui A BAGAçA INDO PRO LUGAR CERTO -> $characters", Toast.LENGTH_LONG).show()
                displayCharacters(characters)
            }
        }
        )

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
        }

    }

    private fun navigateToCharacterDetails(character: Character) {

        Toast.makeText(context, character.toString(), Toast.LENGTH_LONG).show()
//        val firebaseExpenseDate = setFormatterDate.setFormatterDate(expense.date?.toDate())
//        val category = expense.category
//        val description = expense.description
//        val urlImg = expense.url_img
//        val value = expense.value.toString()
//        val idExpense = expense.id
//
//        var argsToFragmentExpense: NavDirections? = null
//
//        argsToFragmentExpense =
//            HomeFragmentDirections.actionNavigationHomeToExpenseFragment(
//                category,
//                firebaseExpenseDate,
//                description,
//                urlImg,
//                value,
//                idExpense
//            )
//
//        this.findNavController().navigate(argsToFragmentExpense)
    }

    private fun setupViews() {

        charactersRecyclerView.layoutManager = LinearLayoutManager(this.context)
        homeViewModel.clearListCharacters()

    }

    private fun setBindingsVariables() {

        imgLoadingContainer = binding.fragmentHomeWInternetLoadingPlaceholderContainer
        imgErrorNoNetwork = binding.fragmentHomeWOInternetLoadingPlaceholderContainer
        imgErrorNoData = binding.fragmentHomeNoServerDataPlaceholderContainer
        charactersRecyclerView = binding.fragmentHomeMainRecyclerViewCharacters

    }

    private fun showImgLoading() {
        imgLoadingContainer.isVisible = true
    }

    private fun showImgNoServerData() {
        imgErrorNoData.isVisible = true
    }

    private fun hideImgLoading() {
        imgLoadingContainer.isVisible = false
    }

    private fun showNoNetworkError() {
        toast.setToastMessage(context, R.string.no_network)
        imgErrorNoNetwork.isVisible = true
    }

}