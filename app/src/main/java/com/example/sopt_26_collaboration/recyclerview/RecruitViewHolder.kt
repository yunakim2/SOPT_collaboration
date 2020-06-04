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
import com.example.sopt_26_collaboration.network.RequestToServer
import com.example.sopt_26_collaboration.network.customEnqueue
import com.example.sopt_semina_assignment.util.HorizontalItemDecorator
import com.example.sopt_semina_assignment.util.VerticalItemDecorator
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Integer.parseInt

class RecruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img_recruit = itemView.findViewById<ImageView>(R.id.img_recruit)
    val txt_recruit_field = itemView.findViewById<TextView>(R.id.txt_recruit_field)
    val txt_recruit_company = itemView.findViewById<TextView>(R.id.txt_recruit_company)
    val txt_recruit_location = itemView.findViewById<TextView>(R.id.txt_recruit_location)
    val txt_heart_count = itemView.findViewById<TextView>(R.id.txt_heart_count)
    val img_heart = itemView.findViewById<ImageView>(R.id.img_heart)

    fun bind(recruitData: RecruitData) {
        txt_recruit_field.text = recruitData.recruit_position
        txt_recruit_company.text = recruitData.company_name
        txt_recruit_location.text = recruitData.company_location
        txt_heart_count.text = recruitData.company_hearts.toString()
        Glide.with(itemView).load(recruitData.company_img).into(img_recruit)
        heart(recruitData)

    }

    fun heart(recruitData: RecruitData){
        val requestToServer = RequestToServer
        val btn_heart = itemView.findViewById<ConstraintLayout>(R.id.btn_heart)
        var clicked : Boolean = false
        var heartCount :Int

        btn_heart.setOnClickListener {
            //get으로 먼저 하트 갯수 받아오기
            requestToServer.service.requestRecruitInfo().customEnqueue(
                onError = { Log.d("requestRecruitInfo","올바르지 못한 요청")},
                onSuccess = {
                    if (it.success) { //body()가 null이 아니고, success가 true -> 성공
                        recruitData.company_hearts =
                            it.data.get(recruitData.company_idx-1).company_hearts
                    }
                }
            )

            //클릭 유무에 따라 하트버튼 채워짐, 하트갯수 조정
            if(!clicked) {
                img_heart.setImageResource(R.drawable.heartred_14px)
                heartCount = recruitData.company_hearts+1
                clicked = true
            }
            else{
                img_heart.setImageResource(R.drawable.heartgrey_14px)
                heartCount = recruitData.company_hearts-1
                clicked = false
            }

            requestToServer.service.requestHeartUpdate(
                   company_idx = recruitData.company_idx,
                   company_hearts = heartCount
               ).customEnqueue(
                   onError = { Log.d("requestHeartUpdate","올바르지 못한 요청")},
                   onSuccess = {
                       if(it.success){ //body()가 null이 아니고, success가 true -> 성공
                           requestToServer.service.requestRecruitInfo().customEnqueue(
                               onError = { Log.d("requestRecruitInfo","올바르지 못한 요청")},
                               onSuccess = {
                                   if (it.success) { //body()가 null이 아니고, success가 true -> 성공
                                       recruitData.company_hearts =
                                           it.data.get(recruitData.company_idx-1).company_hearts
                                       txt_heart_count.text = recruitData.company_hearts.toString()
                                   }
                               }
                           )
                       }
                   }
                )
        }
    }
}