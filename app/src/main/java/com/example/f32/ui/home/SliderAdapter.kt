package com.example.f32.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.f32.databinding.ItemSliderBinding
import com.example.f32.model.Slider
import com.example.f32.ui.MainActivity
import com.example.f32.util.Util.Companion.requestOptions
import com.example.f32.util.toGone
import com.example.f32.util.toInvisible
import com.example.f32.util.toVisible

class SliderAdapter : RecyclerView.Adapter<SliderAdapter.PagerVH>() {
    private var mList: List<Slider> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerVH(binding)
    }

    override fun getItemCount() = mList.size
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.bind(mList[position])

    inner class PagerVH(private val binding: ItemSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(slider: Slider) {
            if (mList.isNotEmpty()) {
                val activity: MainActivity = binding.root.context as MainActivity

                binding.textTitle.text = slider.title
                binding.textSubtitle.text = slider.subtitle

                if (slider.isNew)
                    binding.cardNew.toVisible()
                else
                    binding.cardNew.toGone()

                if (slider.isBuy)
                    binding.buttonBuy.toVisible()
                else
                    binding.buttonBuy.toInvisible()

                Glide.with(activity)
                    .load(slider.picture)
                    .apply(requestOptions.priority(Priority.IMMEDIATE))
                    .into(binding.image)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Slider>) {
        this.mList = list
        notifyDataSetChanged()
    }
}