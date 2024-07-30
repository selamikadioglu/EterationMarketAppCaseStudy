package com.selamikadioglu.native_mobile_case_study.ui.favoritePage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.databinding.RcFavoriteItemBinding
import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import com.selamikadioglu.native_mobile_case_study.model.ProductResponseModel
import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel


class FavoritePageAdapter(
    private val context: Context,
    private var productList: List<FavoriteProduct>
) : RecyclerView.Adapter<FavoritePageAdapter.ProductListViewHolder>() {

    class ProductListViewHolder(
        val favoritePageItemBinding: RcFavoriteItemBinding,
        listener: onItemClickListener
    ) :
        RecyclerView.ViewHolder(favoritePageItemBinding.root) {

        init {
            favoritePageItemBinding.favoriteIcon.setOnClickListener {
                listener.favoriteClick(layoutPosition)
            }
        }
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun favoriteClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListViewHolder {
        return ProductListViewHolder(
            RcFavoriteItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false),listener = mListener
        )
    }

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int
    ) {
        holder.favoritePageItemBinding.productPriceTv.text = productList[position].price.toString()
        holder.favoritePageItemBinding.productNameTv.text = productList[position].name.toString()
        holder.favoritePageItemBinding.favoriteIcon.setImageResource(R.drawable.ic_selected_star)
        Glide.with(context).load(productList[position].image).fitCenter().into(holder.favoritePageItemBinding.itemImage)
    }

    override fun getItemCount(): Int {
        return productList.size

    }
    fun  getItem(position: Int) : FavoriteProduct{
        return productList[position]
    }

}