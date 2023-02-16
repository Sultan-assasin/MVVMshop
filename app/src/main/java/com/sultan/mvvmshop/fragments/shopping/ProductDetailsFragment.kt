package com.sultan.mvvmshop.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sultan.mvvmshop.R
import com.sultan.mvvmshop.adapters.ColorsAdapter
import com.sultan.mvvmshop.adapters.SizesAdapter
import com.sultan.mvvmshop.adapters.ViewPager2Images
import com.sultan.mvvmshop.data.CartProduct
import com.sultan.mvvmshop.databinding.FragmentProductDetailsBinding
import com.sultan.mvvmshop.util.Resource
import com.sultan.mvvmshop.util.hideBottomNavigation
import com.sultan.mvvmshop.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    private val args by navArgs<ProductDetailsFragmentArgs>() // product argument
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizeAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }
    private var selectedColor: Int? = null
    private var selectedSize: String? = null
    private val viewModel by viewModels<DetailsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigation()
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product //get product by navGraph

        setupSizeRv()
        setupColorsRv()
        setupViewpager()
        imageCloseFragment()

        colorsAdapter.onItemClick = {
            selectedColor = it
        }
        sizeAdapter.onItemClick = {
            selectedSize = it
        }

        binding.buttonAddToCard.setOnClickListener {
            // add if statement
            viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedColor, selectedSize))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.buttonAddToCard.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.buttonAddToCard.revertAnimation()
                        binding.buttonAddToCard.setBackgroundColor(resources.getColor(R.color.black))
                    }
                    is Resource.Error -> {
                        binding.buttonAddToCard.stopAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description

            if (product.colors.isNullOrEmpty()) {
                tvProductColors.visibility = View.INVISIBLE
            }
            if (product.sizes.isNullOrEmpty()) {
                tvProductSize.visibility = View.INVISIBLE
            }
        }

        viewPagerAdapter.differ.submitList(product.images)
        //colorsAdapter.differ.submitList(product.colors) we can't use this code below because all product don't have colors

        product.colors?.let { colorsAdapter.differ.submitList(it) } // if colors is not null
        product.sizes?.let { sizeAdapter.differ.submitList(it) }
    }

    private fun imageCloseFragment() {
        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupViewpager() {
        binding.apply {
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }

    private fun setupColorsRv() {
        binding.rvColors.apply {
            adapter = colorsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupSizeRv() {
        binding.rvSize.apply {
            adapter = sizeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}