package com.example.f32.ui.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.f32.databinding.ItemImageBinding
import com.example.f32.ui.MainActivity
import com.example.f32.util.Util.Companion.requestOptions

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.PagerVH>() {
    private var mList: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerVH(binding)
    }

    override fun getItemCount() = mList.size
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.bind(mList[position])

    inner class PagerVH(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {
            if (mList.isNotEmpty()) {
                val activity: MainActivity = binding.root.context as MainActivity

                Glide.with(activity)
                    .load(image)
                    .apply(requestOptions.priority(Priority.IMMEDIATE))
                    .into(binding.image)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<String>) {
        this.mList = list
        notifyDataSetChanged()
    }
}