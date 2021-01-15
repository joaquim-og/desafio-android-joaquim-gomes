package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.google.android.material.textfield.TextInputEditText
import com.joaquimgomes.desafioandroidjoaquimgomes.R
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.SetToastMessage
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics
import com.joaquimgomes.desafioandroidjoaquimgomes.databinding.FragmentCharacterDetailsBinding

class CharacterDetailsFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: FragmentCharacterDetailsBinding
    private lateinit var characterNameInput: TextInputEditText
    private lateinit var characterDescriptionInput: TextInputEditText
    private lateinit var characterImageContainer: ImageView
    private lateinit var characterDescription: String
    private lateinit var characterImgUrl: String
    private lateinit var characterName: String
    private lateinit var buttonNavToComic: Button
    private lateinit var toast: SetToastMessage

    private var mostExpansiveComicImg = ""
    private var mostExpansiveComicTitle = ""
    private var mostExpansiveComicDescription = ""

    private var mostExpansiveComicPrice = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = activity?.let { ViewModelProviders.of(it).get(HomeViewModel::class.java) }!!

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false)

        toast = SetToastMessage()
        characterName = ""
        characterDescription = ""

        setBindingVariables()
        setupViews()
        observerAllComics()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonNavToComic.setOnClickListener {
            navigateToCharacterComic()
        }

    }

    private fun setBindingVariables() {

        characterImageContainer = binding.fragmentCharacterDetailsPlaceHolderImgCharacter
        characterNameInput = binding.fragmentCharacterDetailsTextFieldNameInput
        characterDescriptionInput = binding.fragmentCharacterDetailsTextFieldDescriptionInput
        buttonNavToComic = binding.fragmentCharacterDetailsButtonNavToComic

    }

    private fun setupViews() {

        val args = arguments

        characterName = args?.get("characterName").toString()
        characterImgUrl = args?.get("characterImage").toString()
        characterDescription = args?.get("characterDescription").toString()

        characterNameInput.setText(characterName)

        if (characterDescription.isEmpty()) {

            characterDescriptionInput.setText(context?.getString(R.string.text_character_no_description_in_API))

        } else {

            characterDescriptionInput.setText(characterDescription)

        }

        Glide.with(this).load(characterImgUrl).transform(FitCenter())
            .into(characterImageContainer)

    }


    private fun observerAllComics() {

        homeViewModel.characterComicsServerData.observe(viewLifecycleOwner, Observer { comics ->

            if (comics.isNullOrEmpty()) {
                toast.setToastMessage(context, R.string.error_no_server_data)
            } else {

                val mostExpansiveComicInfo = homeViewModel.getCharacterMostExpansiveComic(comics)

                if (!mostExpansiveComicInfo.isNullOrEmpty()){

                    mostExpansiveComicImg = ""
                    mostExpansiveComicTitle = ""
                    mostExpansiveComicDescription = ""
                    mostExpansiveComicPrice = ""

                    buttonNavToComic.isEnabled = true

                }
            }
        })

    }


    private fun navigateToCharacterComic() {

        Toast.makeText(context, "AQUI AS INFO DA REVISTA MAIS CARA -> $mostExpansiveComicImg | $mostExpansiveComicTitle | $mostExpansiveComicDescription | $mostExpansiveComicPrice", Toast.LENGTH_LONG).show()

//        TODO pass most expansive comic details
//
//        val argsToConfirmDialog =
//            ExpenseFragmentDirections.actionExpenseFragmentToExpenseFragmentConfirmChangesDialog(
//                category,
//                dateExpense,
//                description,
//                value,
//                idExpense,
//                urlImgExpense
//            )
//
//        this.findNavController().navigate(argsToConfirmDialog)

    }

}