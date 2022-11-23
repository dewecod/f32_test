package com.example.f32.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.f32.R
import com.example.f32.base.BaseFragment
import com.example.f32.databinding.DialogFilterBinding
import com.example.f32.databinding.FragmentHomeBinding
import com.example.f32.net.Resource
import com.example.f32.net.response.IndexResponse
import com.example.f32.ui.MainViewModel
import com.example.f32.ui.product.ProductFragment
import com.example.f32.util.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val adapterSlider by lazy { SliderAdapter() }
    private val adapterCategory by lazy { CategoryAdapter() }
    private val adapterProduct by lazy { ProductAdapter() }

    var currentPage = 0
    private var categoryList = Util.categoryList

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRecyclerView()
        observe()
    }

    private fun initView() {
        mainViewModel.loadIndex()
        binding.viewPager.adapter = adapterSlider
        currentPage = 0

        binding.imageFilter.setOnClickListener {
            showBottomFilterDialog()
        }
    }

    private fun showBottomFilterDialog() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val dialogFilterBinding = DialogFilterBinding.inflate(LayoutInflater.from(requireContext()), binding.root, false)

        dialogFilterBinding.cardClose.setOnClickListener {
            dialog.dismiss()
        }

        dialogFilterBinding.cardDone.setOnClickListener {
            dialog.dismiss()
        }

        ArrayAdapter.createFromResource(requireContext(), R.array.brand_list, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dialogFilterBinding.spinnerBrand.adapter = adapter
            }
        ArrayAdapter.createFromResource(requireContext(), R.array.size_list, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dialogFilterBinding.spinnerSize.adapter = adapter
            }
        ArrayAdapter.createFromResource(requireContext(), R.array.price_list, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dialogFilterBinding.spinnerPrice.adapter = adapter
            }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogFilterBinding.root)
        dialog.show()
    }

    private fun initRecyclerView() {
        binding.recyclerProducts.adapter = adapterProduct
        binding.recyclerProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerProducts.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing_small)))
        binding.recyclerProducts.setHasFixedSize(true)

        binding.recyclerCategories.adapter = adapterCategory
        binding.recyclerCategories.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerCategories.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing_small)))
        binding.recyclerCategories.setHasFixedSize(true)

        adapterCategory.onItemClick = {
            categoryList.forEach { category -> category.active = false }
            categoryList.find { category -> category.title == it.title }?.active = true
            adapterCategory.setList(categoryList)
        }

        adapterProduct.onItemClick = {
            val productFragment = ProductFragment()
            mFragmentNavigation.navigateTo(productFragment)
            mFragmentNavigation.hideBottomNavBar()
        }

        adapterProduct.onFavoriteClick = { product ->
            // mainViewModel.insertOrRemoveFavorite(product)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.indexResponse().observe(viewLifecycleOwner) {
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

    private fun updateUI(data: IndexResponse) {
        // SECTION: Slider
        if (data.sliderList.isEmpty()) {
            binding.viewPager.toGone()
        } else {
            binding.viewPager.toVisible()
            binding.viewPager.clipToPadding = false
            binding.viewPager.clipChildren = false
            binding.viewPager.adapter = adapterSlider
            adapterSlider.setList(data.sliderList)

            binding.viewPager.offscreenPageLimit = 3
            binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(20))
            binding.viewPager.setPageTransformer(compositePageTransformer)
        }

        // SECTION: Categories
        if (categoryList.isNotEmpty()) {
            adapterCategory.setList(categoryList)
        }

        // SECTION: Home category products
        if (data.productList.isNotEmpty()) {
            adapterProduct.submitList(data.productList.toMutableList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}