package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joaquimgomes.desafioandroidjoaquimgomes.R
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Character
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterThumbnailInfo
import com.joaquimgomes.desafioandroidjoaquimgomes.databinding.CardCharacterBinding
import java.security.AccessController.getContext

class HomeCharactersAdapter(
    private val character: List<Character>,
    val onClick: (Character) -> Unit
) : RecyclerView.Adapter<HomeCharactersAdapter.CharactersView>() {

    private var onAttach: Boolean = false
    private val duration: Long = 200

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersView =
        CharactersView(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.card_character,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = character.size

    override fun onBindViewHolder(holder: CharactersView, position: Int) {

        val character = character[position]

        holder.binding.character = character
        holder.binding.root.setOnClickListener { onClick.invoke(character) }
        holder.bind(character)
        setAnimation(holder.itemView)

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                onAttach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        super.onAttachedToRecyclerView(recyclerView)
    }

    private fun setAnimation(itemView: View) {
        itemView.alpha = 0f

        val animatorSet = AnimatorSet()
        val animator = ObjectAnimator.ofFloat(itemView, "alpha", 0f, 0.5f, 1.0f)
        ObjectAnimator.ofFloat(itemView, "alpha", 0f).start()
        animator.startDelay = duration / 2
        animator.duration = 500
        animatorSet.play(animator)
        animator.start()

    }


    inner class CharactersView(val binding: CardCharacterBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {

        }

    }

}

@BindingAdapter("getCharacterImg")
fun setImg(imgView: ImageView, characterThumbnail: CharacterThumbnailInfo) {

    val imgPath = "${characterThumbnail.path}.${characterThumbnail.extension}"

    Glide.with(imgView.context)
        .load(imgPath)
        .fallback(R.drawable.ic_baseline_image_not_supported_24)
        .centerCrop()
        .into(imgView)

}

@BindingAdapter("getCharacterDescription")
fun setCharacterDescription(txtView: TextView, characterDescription: String) {

    if (characterDescription.isNullOrEmpty()) {
        txtView.text = txtView.resources.getString(R.string.card_character_no_description_in_API)
    } else {
        txtView.text = characterDescription
    }

}