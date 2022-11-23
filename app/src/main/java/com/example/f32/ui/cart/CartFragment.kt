package com.example.f32.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f32.R
import com.example.f32.base.BaseFragment
import com.example.f32.databinding.FragmentCartBinding
import com.example.f32.net.Resource
import com.example.f32.net.response.CartResponse
import com.example.f32.ui.MainViewModel
import com.example.f32.util.SpacesItemDecoration
import com.example.f32.util.showToast
import com.example.f32.util.toGone
import com.example.f32.util.toVisible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CartFragment : BaseFragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val adapterCart by lazy { CartAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRecyclerView()
        observe()
    }

    private fun initView() {
        mainViewModel.loadCart()
    }

    private fun initRecyclerView() {
        binding.recyclerCart.adapter = adapterCart
        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerCart.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing_small)))
        binding.recyclerCart.setHasFixedSize(true)
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.cartResponse().observe(viewLifecycleOwner) {
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

    @SuppressLint("SetTextI18n")
    private fun updateUI(data: CartResponse) {
        // SECTION: Cart
        if (data.basketList.isNotEmpty()) {
            adapterCart.submitList(data.basketList.toMutableList())
        }

        binding.textDelivery.text = data.delivery
        binding.textTotal.text = "$" + data.total.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}