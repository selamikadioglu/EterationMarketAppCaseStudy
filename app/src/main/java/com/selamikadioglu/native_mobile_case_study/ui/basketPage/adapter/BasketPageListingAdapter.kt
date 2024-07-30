package com.selamikadioglu.native_mobile_case_study.ui.basketPage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.selamikadioglu.native_mobile_case_study.databinding.RcBasketPageItemBinding
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.model.ProductResponseModel
import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel


class BasketPageListingAdapter(
    private val context: Context,
    private var orderList: List<Order>
) : RecyclerView.Adapter<BasketPageListingAdapter.BasketPageViewHolder>() {

    class BasketPageViewHolder(
        val basketPageItemBinding: RcBasketPageItemBinding,
        listener: onItemClickListener
    ) :
        RecyclerView.ViewHolder(basketPageItemBinding.root) {

        init {
            basketPageItemBinding.minusButton.setOnClickListener {
                listener.minusButtonClick(layoutPosition)
            }
            basketPageItemBinding.plusButton.setOnClickListener{
                listener.plusButtonClick(layoutPosition)
            }
            itemView.setOnClickListener {
                listener.onItemClick(layoutPosition)
            }
        }
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)

        fun plusButtonClick(position: Int)
        fun minusButtonClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasketPageViewHolder {
        return BasketPageViewHolder(
            RcBasketPageItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false),listener = mListener
        )
    }

    override fun onBindViewHolder(
        holder: BasketPageViewHolder,
        position: Int
    ) {
        holder.basketPageItemBinding.productName.text = orderList[position].name
        holder.basketPageItemBinding.productPrice.text = orderList[position].price
        holder.basketPageItemBinding.quantityButton.text = orderList[position].quantity.toString()
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
    fun  getItem(position: Int) : Order{
        return orderList[position]
    }
    override fun getItemCount(): Int {
        return orderList.size

    }

    /*fun setFavIcon(productId: String) {
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
    }*/
}