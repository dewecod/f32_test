package com.example.f32.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.f32.databinding.ItemCartBinding
import com.example.f32.model.Basket
import com.example.f32.ui.MainActivity
import com.example.f32.util.Util.Companion.requestOptions

class CartAdapter : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {
    private var mList: MutableList<Basket> = mutableListOf()

    var onItemClick: ((Basket) -> Unit)? = null

    /****************************************************************************************************
     **  DiffUtil
     ****************************************************************************************************/
    private val mDiffer: AsyncListDiffer<Basket> = AsyncListDiffer(this, DiffUtilCallback)

    object DiffUtilCallback : DiffUtil.ItemCallback<Basket>() {
        override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = mDiffer.currentList.size
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(mDiffer.currentList[position])

    inner class ItemViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(basket: Basket) {
            if (mDiffer.currentList.isNotEmpty()) {
                val activity: MainActivity = binding.root.context as MainActivity
                binding.root.setOnClickListener { onItemClick?.invoke(basket) }
                binding.textTitle.text = basket.title
                binding.textPrice.text = "$" + basket.price.toString()

                Glide.with(activity)
                    .load(basket.images)
                    .apply(requestOptions.priority(Priority.HIGH))
                    .into(binding.imageProduct)
            }
        }
    }

    fun getList(): List<Basket> = mList

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(basketList: MutableList<Basket>) {
        mList.clear()
        mList.addAll(basketList)
        mDiffer.submitList(basketList)
        notifyDataSetChanged()
    }
}