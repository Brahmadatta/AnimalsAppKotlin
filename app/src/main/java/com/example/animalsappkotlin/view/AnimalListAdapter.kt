package com.example.animalsappkotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalsappkotlin.R
import com.example.animalsappkotlin.model.Animals
import com.example.animalsappkotlin.util.getProgressDrawable
import com.example.animalsappkotlin.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animals>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {


    

    fun updateAnimalList(newAnimalList: List<Animals>)
    {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_animal,parent,false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {

        holder.view.animalName.text = animalList[position].name
        holder.view.animalImage.loadImage(animalList[position].imageUrl, getProgressDrawable(holder.view.context))
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    class AnimalViewHolder(var view : View) : RecyclerView.ViewHolder(view)

}