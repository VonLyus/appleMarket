package com.example.appdevelopmentskills

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdevelopmentskills.databinding.ItemListBinding

class MainViewAdapter(val itemList: MutableList<MainItemModel>) : RecyclerView.Adapter<MainViewAdapter.Holder>(){


    //클릭 이벤트 설정
    interface ItemClick {
        fun onClick(view : View, position : Int)
        fun onLongClick(view : View, position: Int)

    }

    //아이템 클릭 이벤트를 처리하는 콜백을 설정
    var itemClick : ItemClick? = null

    fun updateItemThumbsCount(position: Int, thumbsCheck: Int, numOfThumbs: Int) {
        itemList[position].thumbsCheck = thumbsCheck
        itemList[position].numOfThumbs = numOfThumbs
        notifyItemChanged(position)
    }

    //Holder 객체를 만든다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
//각 아이템의 데이터를 해당 아이템 뷰에 바인딩하여 표시합니다. 또한 아이템 뷰 클릭 이벤트 처리를 설정합니다.
    override fun onBindViewHolder(holder: Holder, position: Int) {

    holder.itemView.setOnClickListener{
            itemClick?.onClick(it, position)
        }

    // 값을 기입해야하니깐 설정한 것
        holder.itemImg.setImageResource(itemList[position].img)
        holder.title.text = itemList[position].title
        holder.location.text = itemList[position].location
        holder.price.text = itemList[position].price.toString()
        holder.chatText.text = itemList[position].numOfChat.toString()
        holder.thumbsText.text = itemList[position].numOfThumbs.toString()

    //기본 값 설정
    if(itemList[position].thumbsCheck == 0){
        holder.thumbsImg.setImageResource(R.drawable.heart_blank)
    }
    else{
        holder.thumbsImg.setImageResource(R.drawable.heart)
    }

    // 롱클릭 이벤트 처리
    holder.itemView.setOnLongClickListener {
        itemClick?.onLongClick(it, position)
        true // true를 반환하여 이벤트가 소비되었음을 나타냄
    }

    holder.thumbsImg.setOnClickListener{

        val thumbsCount = itemList[position].numOfThumbs
        if (itemList[position].thumbsCheck == 0){
            itemList[position].thumbsCheck = 1
            itemList[position].numOfThumbs = thumbsCount + 1 // 예시로 이모티콘 클릭 시 숫자를 증가시킴
            holder.thumbsImg.setImageResource(R.drawable.heart)

        // 변경된 데이터를 RecyclerView에 알리고 UI 갱신
        notifyItemChanged(position)}
        else{
            itemList[position].thumbsCheck = 0
            itemList[position].numOfThumbs = thumbsCount - 1 // 예시로 이모티콘 클릭 시 숫자를 증가시킴
            holder.thumbsImg.setImageResource(R.drawable.heart_blank)

            // 변경된 데이터를 RecyclerView에 알리고 UI 갱신
            notifyItemChanged(position)}

    }

    }

    //아이템 아이디 반환
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class Holder(
        binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root){
        val itemImg = binding.itemImg
        val title = binding.title
        val location = binding.location
        val price = binding.price
        val chatText = binding.chatText
        var thumbsText = binding.thumbsText

        val thumbsImg = binding.thumbsImg
    }
}