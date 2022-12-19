package com.programmsoft.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.programmsoft.chinesehoroscope.databinding.ItemZodiacCompatibilityBinding
import com.programmsoft.models.Zodiac
import com.programmsoft.utils.App

class CompatibilityDialogAdapter() :
    ListAdapter<Zodiac, CompatibilityDialogAdapter.ViewHolder>(MyDiffUtil()) {
    lateinit var itemClick: OnItemClickListener

    fun interface OnItemClickListener {
        fun onClick(id: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClick = listener
    }


    inner class ViewHolder(private var binding: ItemZodiacCompatibilityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun onBind(zodiac: Zodiac) {
            binding.horoscopeTv.text = zodiac.name
            var drawableStr = zodiac.img
            val imageId = App.instance.resources.getIdentifier(drawableStr!!.lowercase(),
                "drawable",
                App.instance.packageName)
            binding.horoscopeImg.setImageResource(imageId)
            binding.horoscopeView.setOnClickListener {
                itemClick.onClick(zodiac.id!!)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemZodiacCompatibilityBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Zodiac>() {
        override fun areItemsTheSame(
            oldItem: Zodiac,
            newItem: Zodiac,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Zodiac,
            newItem: Zodiac,
        ): Boolean {
            return when {
                oldItem.id != newItem.id -> {
                    false
                }
                else -> true
            }
        }

    }
}