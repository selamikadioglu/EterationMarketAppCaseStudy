package com.selamikadioglu.native_mobile_case_study.ui.filterPage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selamikadioglu.native_mobile_case_study.databinding.RcBrandItemBinding
import com.selamikadioglu.native_mobile_case_study.model.CheckBoxItem

class ModelAdapter(
    private val context: Context,
    private var modelList: List<CheckBoxItem>
) : RecyclerView.Adapter<ModelAdapter.ModelListViewHolder>() {

    class ModelListViewHolder(
        val modelItemBinding: RcBrandItemBinding,
        listener: onItemClickListener
    ) :
        RecyclerView.ViewHolder(modelItemBinding.root) {

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
    ): ModelListViewHolder {
        return ModelListViewHolder(
            RcBrandItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), listener = mListener
        )
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(
        holder: ModelListViewHolder,
        position: Int
    ) {
        holder.modelItemBinding.checkBox.text = modelList[position].name
        holder.modelItemBinding.checkBox.isChecked = modelList[position].checked
    }

}
