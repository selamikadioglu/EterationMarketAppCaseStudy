package com.selamikadioglu.native_mobile_case_study.ui.filterPage.adapter

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selamikadioglu.native_mobile_case_study.databinding.RcBrandItemBinding
import com.selamikadioglu.native_mobile_case_study.model.CheckBoxItem


class BrandAdapter(
    private val context: Context,
    private var brandList: List<CheckBoxItem>
) : RecyclerView.Adapter<BrandAdapter.BrandListViewHolder>() {

    class BrandListViewHolder(
        val brandItemBinding: RcBrandItemBinding,
        listener: onItemClickListener
    ) :
        RecyclerView.ViewHolder(brandItemBinding.root) {

        init {

            itemView.setOnClickListener {
                listener.onItemClick(layoutPosition)
            }
        }
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandListViewHolder {
        return BrandListViewHolder(
            RcBrandItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), listener = mListener
        )
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    override fun onBindViewHolder(
        holder: BrandListViewHolder,
        position: Int
    ) {
        holder.brandItemBinding.checkBox.text = brandList[position].name
        holder.brandItemBinding.checkBox.isChecked = brandList[position].checked
    }

}
