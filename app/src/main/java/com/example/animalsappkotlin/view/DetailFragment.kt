package com.example.animalsappkotlin.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.animalsappkotlin.R
import com.example.animalsappkotlin.model.Animals
import com.example.animalsappkotlin.util.getProgressDrawable
import com.example.animalsappkotlin.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    var animal : Animals ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        context?.let {
            animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }

        animalDiet.text = animal?.diet
        animalName.text = animal?.name
        animalLifeSpan.text = animal?.lifeSpan
        animalLocation.text = animal?.location

        animal?.imageUrl?.let {
            setBackgroundColor(it)
        }

    }

    private fun setBackgroundColor(url : String)
    {

        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    Palette.from(resource)
                        .generate(){ palette ->

                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            animalLayout.setBackgroundColor(intColor)
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }
}