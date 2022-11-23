package com.example.f32.ui.home

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.f32.databinding.ItemProductBinding
import com.example.f32.model.Product
import com.example.f32.ui.MainActivity
import com.example.f32.util.Util.Companion.requestOptions

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ItemViewHolder>() {
    private var mList: MutableList<Product> = mutableListOf()

    var onItemClick: ((Product) -> Unit)? = null
    var onFavoriteClick: ((Product) -> Unit)? = null

    /****************************************************************************************************
     **  DiffUtil
     ****************************************************************************************************/
    private val mDiffer: AsyncListDiffer<Product> = AsyncListDiffer(this, DiffUtilCallback)

    object DiffUtilCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = mDiffer.currentList.size
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(mDiffer.currentList[position])

    inner class ItemViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            if (mDiffer.currentList.isNotEmpty()) {
                val activity: MainActivity = binding.root.context as MainActivity
                binding.root.setOnClickListener { onItemClick?.invoke(product) }
                binding.textName.text = product.title
                binding.textDiscountPrice.text = "$" + product.discountPrice.toString()
                binding.textPrice.text = "$" + product.priceWithoutDiscount.toString()
                binding.textPrice.paintFlags = binding.textPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                binding.cardFavorite.setOnClickListener {
                    onFavoriteClick?.invoke(product)
                }

                Glide.with(activity)
                    .load(product.picture)
                    .apply(requestOptions.priority(Priority.HIGH))
                    .into(binding.imageProduct)
            }
        }
    }

    fun getList(): List<Product> = mList

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(productList: MutableList<Product>) {
        mList.clear()
        mList.addAll(productList)
        mDiffer.submitList(productList)
        notifyDataSetChanged()
    }
}