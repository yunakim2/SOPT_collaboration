package com.example.sopt_26_collaboration.recyclerview

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sopt_26_collaboration.CompanyData
import com.example.sopt_26_collaboration.R
import com.example.sopt_26_collaboration.data.RecruitData
import com.example.sopt_26_collaboration.data.ResponseRecruitData
import java.lang.Integer.parseInt

class RecruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img_recruit = itemView.findViewById<ImageView>(R.id.img_recruit)
    val txt_recruit_field = itemView.findViewById<TextView>(R.id.txt_recruit_field)
    val txt_recruit_company = itemView.findViewById<TextView>(R.id.txt_recruit_company)
    val txt_recruit_location = itemView.findViewById<TextView>(R.id.txt_recruit_location)

    fun bind(recruitData: RecruitData) {
        txt_recruit_field.text = recruitData.recruit_position
        txt_recruit_company.text = recruitData.company_name
        txt_recruit_location.text = recruitData.company_location
        Glide.with(itemView).load(recruitData.company_img).into(img_recruit)
        heart()
    }

    fun heart(){
        val btn_heart = itemView.findViewById<ConstraintLayout>(R.id.btn_heart)
        val img_heart = itemView.findViewById<ImageView>(R.id.img_heart)
        val txt_heart_count = itemView.findViewById<TextView>(R.id.txt_heart_count)
        var clicked : Boolean = false

        btn_heart.setOnClickListener {
           if(!clicked){
               img_heart.setImageResource(R.drawable.heartred_14px)
               txt_heart_count.text = (parseInt(txt_heart_count.text.toString()) + 1).toString()
               clicked = true
           }
            else{
               img_heart.setImageResource(R.drawable.heartgrey_14px)
               txt_heart_count.text = (parseInt(txt_heart_count.text.toString()) - 1).toString()
               clicked = false
           }
        }
    }
}