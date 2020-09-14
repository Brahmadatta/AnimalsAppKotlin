package com.example.animalsappkotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.animalsappkotlin.R
import com.example.animalsappkotlin.databinding.ItemAnimalBinding
import com.example.animalsappkotlin.model.Animals
import com.example.animalsappkotlin.util.getProgressDrawable
import com.example.animalsappkotlin.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animals>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener{


    

    fun updateAnimalList(newAnimalList: List<Animals>)
    {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater,R.layout.item_animal,parent,false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {


        holder.view.animal = animalList[position]
        holder.view.listener = this

//        holder.view.animalLayout.setOnClickListener {
//            val action = ListFragmentDirections.actionDetail(animalList[position])
//            Navigation.findNavController(holder.view).navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    class AnimalViewHolder(var view : ItemAnimalBinding) : RecyclerView.ViewHolder(view.root)

    override fun onClick(view: View) {

        for (animal in animalList)
        {
            if (view.tag == animal.name)
            {
                val action = ListFragmentDirections.actionDetail(animal)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

}