package com.example.f32.ui.product

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.f32.R
import com.example.f32.base.BaseFragment
import com.example.f32.databinding.FragmentProductBinding
import com.example.f32.databinding.ItemColorBinding
import com.example.f32.databinding.ItemSdBinding
import com.example.f32.net.Resource
import com.example.f32.net.response.ProductResponse
import com.example.f32.ui.MainViewModel
import com.example.f32.util.showToast
import com.example.f32.util.toGone
import com.example.f32.util.toVisible
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.abs


class ProductFragment : BaseFragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val adapterSlider by lazy { ImageAdapter() }
    var currentPage = 0
    var selectedColorIndex = 0
    var selectedSdIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        mainViewModel.loadProduct()
        binding.viewPager.adapter = adapterSlider
        currentPage = 0

        binding.cardBack.setOnClickListener {
            mFragmentNavigation.showBottomNavBar()
            mFragmentNavigation.navigateBack()
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.productResponse().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progress.toVisible()
                    }
                    is Resource.Success -> {
                        updateUI(it.data)
                        binding.progress.toGone()
                    }
                    is Resource.Error -> {
                        binding.progress.toGone()
                        showToast("No internet")
                    }
                    else -> {}
                }
            }
        }
    }

    private fun updateUI(data: ProductResponse) {
        // SECTION: Image slider
        if (data.images.isEmpty()) {
            binding.viewPager.toGone()
        } else {
            binding.viewPager.toVisible()
            binding.viewPager.clipToPadding = false
            binding.viewPager.clipChildren = false
            binding.viewPager.adapter = adapterSlider
            adapterSlider.setList(data.images)

            binding.viewPager.offscreenPageLimit = 3
            binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(20))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.20F
            }
            binding.viewPager.setPageTransformer(compositePageTransformer)
        }

        binding.textTitle.text = data.title
        binding.rating.rating = data.rating.toFloat()
        binding.textCpu.text = data.cPU
        binding.textCamera.text = data.camera
        binding.textSd.text = data.sd
        binding.textSsd.text = data.ssd

        // SECTION: Colors
        binding.layoutColor.removeAllViews()
        data.color.forEachIndexed { index, s ->
            val bindingItemColor = ItemColorBinding.inflate(LayoutInflater.from(requireContext()), binding.layoutColor, false)

            bindingItemColor.root.setCardBackgroundColor(Color.parseColor(s))

            bindingItemColor.root.setOnClickListener {
                for (i in 0 until binding.layoutColor.childCount) {
                    val view = binding.layoutColor.getChildAt(i)
                    val imageChecked = view.findViewById(R.id.image_checked) as ImageView
                    imageChecked.toGone()
                }
                bindingItemColor.imageChecked.toVisible()
            }

            if (selectedColorIndex == index) {
                bindingItemColor.imageChecked.toVisible()
            }
            binding.layoutColor.addView(bindingItemColor.root)
        }

        // SECTION: Sd
        binding.layoutSd.removeAllViews()
        data.color.forEachIndexed { index, s ->
            val bindingItemSd = ItemSdBinding.inflate(LayoutInflater.from(requireContext()), binding.layoutSd, false)

            bindingItemSd.textSd.text = s

            bindingItemSd.root.setOnClickListener {
                for (i in 0 until binding.layoutSd.childCount) {
                    val view = binding.layoutSd.getChildAt(i) as MaterialCardView
                    val textSd = view.findViewById(R.id.text_sd) as TextView

                    view.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                    textSd.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                }
                bindingItemSd.root.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                bindingItemSd.textSd.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }

            if (selectedSdIndex == index) {
                bindingItemSd.root.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                bindingItemSd.textSd.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }

            binding.layoutSd.addView(bindingItemSd.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}