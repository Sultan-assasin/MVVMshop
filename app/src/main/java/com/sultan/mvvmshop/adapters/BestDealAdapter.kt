package com.sultan.mvvmshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sultan.mvvmshop.data.Product
import com.sultan.mvvmshop.databinding.BestDealsRvItemBinding

class BestDealAdapter : RecyclerView.Adapter<BestDealAdapter.BestDealAdapterViewHolder>() {
    inner class BestDealAdapterViewHolder(private val binding: BestDealsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView).load(product.images!![0]).into(imgBestDeal)
                product.offerPercentage?.let {
                    val remainingPricePercentage = 1f-it
                    val priceAfterOffer = remainingPricePercentage * product.price
                    tvNewPrice.text = "$ ${String.format("%.2f",priceAfterOffer)}"
                }
                tvOldPrice.text = "$ ${product.price}"
                tvDealProductName.text = product.name
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealAdapterViewHolder {
        return BestDealAdapterViewHolder(
            BestDealsRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestDealAdapterViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}