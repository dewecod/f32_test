package com.example.f32.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.f32.R
import com.example.f32.databinding.ItemCategoryBinding
import com.example.f32.model.Category
import com.example.f32.ui.MainActivity
import com.example.f32.util.Util.Companion.requestOptions

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.PagerVH>() {
    private var mList: List<Category> = ArrayList()

    var onItemClick: ((Category) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerVH(binding)
    }

    override fun getItemCount() = mList.size
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.bind(mList[position])

    inner class PagerVH(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            if (mList.isNotEmpty()) {
                val activity: MainActivity = binding.root.context as MainActivity
                binding.root.setOnClickListener { onItemClick?.invoke(category) }

                binding.textCategory.text = category.title

                if (category.active) {
                    binding.cardCategory.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.primary))
                    binding.textCategory.setTextColor(ContextCompat.getColor(activity, R.color.primary))
                    binding.imageCategory.imageTintList = ContextCompat.getColorStateList(activity, R.color.white)
                } else {
                    binding.cardCategory.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.white))
                    binding.textCategory.setTextColor(ContextCompat.getColor(activity, R.color.black))
                    binding.imageCategory.imageTintList = ContextCompat.getColorStateList(activity, R.color.category_gray)
                }

                Glide.with(activity)
                    .load(category.picture)
                    .apply(requestOptions.priority(Priority.IMMEDIATE))
                    .into(binding.imageCategory)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Category>) {
        this.mList = list
        notifyDataSetChanged()
    }
}