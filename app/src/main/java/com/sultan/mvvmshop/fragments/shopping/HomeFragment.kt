package com.sultan.mvvmshop.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sultan.mvvmshop.R
import com.sultan.mvvmshop.adapters.HomeViewpagerAdapter
import com.sultan.mvvmshop.databinding.FragmentHomeBinding
import com.sultan.mvvmshop.fragments.categories.*

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding :FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupBoardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )
        val viewpager2Adapter = HomeViewpagerAdapter(categoriesFragments,childFragmentManager,lifecycle)
        binding.viewPagerHome.adapter = viewpager2Adapter
        TabLayoutMediator(binding.tabLayout,binding.viewPagerHome){ tab, position->
            when(position){
                0-> tab.text = "Main"
                1-> tab.text = "Chair"
                2-> tab.text = "Cupboard"
                3-> tab.text = "Table"
                4-> tab.text = "Accessory"
                5-> tab.text = "Furniture"
            }
        }.attach()
    }
}