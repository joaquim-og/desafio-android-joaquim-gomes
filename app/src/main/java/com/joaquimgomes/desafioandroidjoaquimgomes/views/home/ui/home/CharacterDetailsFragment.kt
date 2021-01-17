package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.google.android.material.textfield.TextInputEditText
import com.joaquimgomes.desafioandroidjoaquimgomes.R
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.GetCurrency
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.SetToastMessage
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

    private var mostExpensiveComicImg = ""
    private var mostExpensiveComicTitle = ""
    private var mostExpensiveComicDescription = ""

    private var mostExpensiveComicPrice = ""

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

        Glide.with(this).load(characterImgUrl)
            .fallback(R.drawable.ic_baseline_image_not_supported_24).transform(FitCenter())
            .into(characterImageContainer)



        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.name_character_details)
    }


    private fun observerAllComics() {

        val localeCurrency = GetCurrency().localeCurrency()

        homeViewModel.characterComicsServerData.observe(viewLifecycleOwner, Observer { comics ->

            if (comics == null) {
                toast.setToastMessage(context, R.string.error_no_server_data)
            } else {

                val mostExpensiveComicInfo = homeViewModel.getCharacterMostExpensiveComic(comics)

                if (mostExpensiveComicInfo != null) {

                    mostExpensiveComicImg = mostExpensiveComicInfo.imgComic.toString()
                    mostExpensiveComicTitle = mostExpensiveComicInfo.titleComic.toString()
                    mostExpensiveComicDescription = mostExpensiveComicInfo.descriptionComic.toString()
                    mostExpensiveComicPrice = localeCurrency.currency?.toString() + " " + mostExpensiveComicInfo.highestPrice.toString()

                    buttonNavToComic.isEnabled = true

                }
            }
        })

    }


    private fun navigateToCharacterComic() {

        val argsToMostExpensiveComic =
            CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToCharacterMostExpensiveComicFragment(
                mostExpensiveComicImg,
                mostExpensiveComicTitle,
                mostExpensiveComicDescription,
                mostExpensiveComicPrice
            )

        this.findNavController().navigate(argsToMostExpensiveComic)

    }

}