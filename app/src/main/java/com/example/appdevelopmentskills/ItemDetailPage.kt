package com.example.appdevelopmentskills

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.appdevelopmentskills.databinding.ItemDetailPageActivityBinding
import com.example.appdevelopmentskills.databinding.MainActivityBinding
import com.google.android.material.snackbar.Snackbar

class ItemDetailPage : AppCompatActivity() {

    private lateinit var binding: ItemDetailPageActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ItemDetailPageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //이미지 색 변경
        binding.userImg.setColorFilter(Color.parseColor("#FFFFFF"))
        binding.back.setColorFilter(Color.parseColor("#FFFFFF"))

        //값 받아오기
        val selectedItem = intent.getParcelableExtra<MainItemModel>("selectedItem")
        Log.d("thumb", selectedItem!!.thumbsCheck.toString())

        binding.back.setOnClickListener{
            val resultIntent = Intent()
            resultIntent.putExtra("selectedItem", selectedItem)
            Log.d("thumb", selectedItem!!.thumbsCheck.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.userItemImg.setImageResource(selectedItem!!.img)
        binding.userName.text = selectedItem!!.name
        binding.userLocation.text = selectedItem!!.location
        binding.detailTitle.text = selectedItem!!.title
        binding.detailDescription.text = selectedItem!!.description
        binding.itemPrice.text = selectedItem!!.price

        if(selectedItem!!.thumbsCheck == 0){binding.Thumbs.setImageResource(R.drawable.heart_blank)}
        else{binding.Thumbs.setImageResource(R.drawable.heart)}

        binding.Thumbs.setOnClickListener {
            if (selectedItem!!.thumbsCheck == 1) {
                selectedItem!!.thumbsCheck = 0
                selectedItem!!.numOfThumbs = selectedItem!!.numOfThumbs - 1
                binding.Thumbs.setImageResource(R.drawable.heart_blank)
                Log.d("thumb", selectedItem!!.thumbsCheck.toString())
            }
            else{
                selectedItem!!.thumbsCheck = 1
                selectedItem!!.numOfThumbs = selectedItem!!.numOfThumbs + 1
                binding.Thumbs.setImageResource(R.drawable.heart)
                var messageSet = selectedItem!!.numOfThumbs
                Log.d("thumb", selectedItem!!.thumbsCheck.toString())
                val snackbarMessage = "좋아요 $messageSet 개가 선택되었습니다."
                Snackbar.make(binding.root, snackbarMessage, Snackbar.LENGTH_SHORT).show()

            }
        }
    }
}