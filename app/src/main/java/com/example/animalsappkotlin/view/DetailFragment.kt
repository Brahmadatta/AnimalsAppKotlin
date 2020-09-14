package com.example.animalsappkotlin.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.animalsappkotlin.R
import com.example.animalsappkotlin.databinding.FragmentDetailBinding
import com.example.animalsappkotlin.model.Animals
import com.example.animalsappkotlin.util.getProgressDrawable
import com.example.animalsappkotlin.util.loadImage

class DetailFragment : Fragment() {

    var animal : Animals ?= null

    private lateinit var dataBinding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        return dataBinding.root

        //return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        context?.let {
            dataBinding.animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }


        animal?.imageUrl?.let {
            setBackgroundColor(it)
        }

        dataBinding.animal = animal

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
                            dataBinding.animalLayout.setBackgroundColor(intColor)
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }
}