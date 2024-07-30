package com.selamikadioglu.native_mobile_case_study.ui.homePage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.databinding.RcHomePageItemBinding
import com.selamikadioglu.native_mobile_case_study.model.ProductResponseModel
import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel


class HomePageListingAdapter(
    private val context: Context,
    private var productList: List<ProductUiModel>
) : RecyclerView.Adapter<HomePageListingAdapter.ProductListViewHolder>() {

    class ProductListViewHolder(
        val homePageItemBinding: RcHomePageItemBinding,
        listener: onItemClickListener
    ) :
        RecyclerView.ViewHolder(homePageItemBinding.root) {

        init {
            homePageItemBinding.favoriteIcon.setOnClickListener {
                listener.favoriteClick(layoutPosition)
            }
            homePageItemBinding.buyButton.setOnClickListener {
                listener.buyButtonClick(layoutPosition)
            }
            itemView.setOnClickListener {
                listener.onItemClick(layoutPosition)
            }
        }
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)

        fun favoriteClick(position: Int)
        fun buyButtonClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListViewHolder {
        return ProductListViewHolder(
            RcHomePageItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false),listener = mListener
        )
    }

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int
    ) {
        holder.homePageItemBinding.productPriceTv.text = productList[position].price.toString()
        holder.homePageItemBinding.productNameTv.text = productList[position].name.toString()
        holder.homePageItemBinding.favoriteIcon
        if (productList[position].isFavorite) {
            holder.homePageItemBinding.favoriteIcon.setImageResource(R.drawable.ic_selected_star)
        } else {
            holder.homePageItemBinding.favoriteIcon.setImageResource(R.drawable.ic_unselected_star)
        }
        Glide.with(context).load(productList[position].image).fitCenter().into(holder.homePageItemBinding.itemImage)
    }

    fun addAll(productList: ArrayList<ProductResponseModel>) {
        for (result in productList) {
            add(result)
        }
    }

    fun add(product: ProductResponseModel) {
    }

    fun clearData() {

    }
    fun  getItem(position: Int) : ProductUiModel{
        return productList[position]
    }
    override fun getItemCount(): Int {
        return productList.size

    }

    fun setFavIcon(productId: String) {
        productList.forEachIndexed { index, productUiModel ->
            if (productUiModel.id == productId){
                productList[index].isFavorite = true
            }
        }
        notifyDataSetChanged()
    }

    fun getSelectedItemFavorite(position: Int): Boolean {
        return productList[position].isFavorite
    }

    fun getSelectedItem(position: Int): String {
        return productList[position].id
    }

    fun changeFavoriteStatus(position: Int, favoriteStatus: Boolean){
        productList[position].isFavorite = !favoriteStatus
        notifyItemChanged(position)
    }
}