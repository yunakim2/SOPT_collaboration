package com.example.sopt_26_collaboration

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img_profile = itemView.findViewById<ImageView>(R.id.iv_profile)
    val tv_name = itemView.findViewById<TextView>(R.id.tv_name)
    val tv_company = itemView.findViewById<TextView>(R.id.tv_company)

    fun bind(recommendData: RecommendData) {
        Glide.with(itemView).load(recommendData.img_profile).into(img_profile)
        tv_name.text = recommendData.name
        tv_company.text = recommendData.company
    }
}