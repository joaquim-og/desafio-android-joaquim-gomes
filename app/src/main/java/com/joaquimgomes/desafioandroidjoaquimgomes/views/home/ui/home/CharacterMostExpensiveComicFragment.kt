package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.google.android.material.textfield.TextInputEditText
import com.joaquimgomes.desafioandroidjoaquimgomes.R
import com.joaquimgomes.desafioandroidjoaquimgomes.databinding.FragmentCharacterMostExpensiveComicBinding

class CharacterMostExpensiveFragment : Fragment() {

    private lateinit var binding: FragmentCharacterMostExpensiveComicBinding
    private lateinit var characterMostExpensiveComicNameInput: TextInputEditText
    private lateinit var characterMostExpensiveComicInput: TextInputEditText
    private lateinit var characterMostExpensiveComicPriceInput: TextInputEditText
    private lateinit var characterMostExpensiveComicImageContainer: ImageView
    private lateinit var characterMostExpensiveComicDescription: String
    private lateinit var characterMostExpensiveComicImgUrl: String
    private lateinit var characterMostExpensiveComicName: String
    private lateinit var characterMostExpensiveComicPrice: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_character_most_expensive_comic, container, false
            )

        characterMostExpensiveComicName = ""
        characterMostExpensiveComicPrice = ""
        characterMostExpensiveComicDescription = ""
        characterMostExpensiveComicImgUrl = ""

        setBindingVariables()
        setupViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setBindingVariables() {

        characterMostExpensiveComicImageContainer =
            binding.fragmentCharacterMostExpensiveComicPlaceHolderImgCharacter
        characterMostExpensiveComicNameInput =
            binding.fragmentCharacterMostExpensiveComicTextFieldNameInput
        characterMostExpensiveComicInput =
            binding.fragmentCharacterMostExpensiveComicTextFieldDescriptionInput
        characterMostExpensiveComicPriceInput =
            binding.fragmentCharacterMostExpensiveComicTextFieldValueInput

    }

    private fun setupViews() {

        val args = arguments

        characterMostExpensiveComicName = args?.get("mostExpensiveComicTitle").toString()
        characterMostExpensiveComicPrice = args?.get("mostExpensiveComicPrice").toString()
        characterMostExpensiveComicDescription =
            args?.get("mostExpensiveComicDescription").toString()
        characterMostExpensiveComicImgUrl = args?.get("mostExpensiveComicImg").toString()

        characterMostExpensiveComicNameInput.setText(characterMostExpensiveComicName)
        characterMostExpensiveComicPriceInput.setText(characterMostExpensiveComicPrice)

        if (characterMostExpensiveComicDescription == "null" || characterMostExpensiveComicDescription.isNullOrEmpty()) {

            characterMostExpensiveComicInput.setText(context?.getString(R.string.text_comic_no_description_in_API))

        } else {

            characterMostExpensiveComicInput.setText(characterMostExpensiveComicDescription)

        }

        Glide.with(this).load(characterMostExpensiveComicImgUrl)
            .fallback(R.drawable.ic_baseline_image_not_supported_24).transform(FitCenter())
            .into(characterMostExpensiveComicImageContainer)


        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.name_most_expansive_comic)
    }

}