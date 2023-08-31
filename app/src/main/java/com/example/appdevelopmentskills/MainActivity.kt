package com.example.appdevelopmentskills


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdevelopmentskills.databinding.MainActivityBinding

// Notification 채널 ID와 이름을 정의
private const val CHANNEL_ID = "my_channel"
private const val CHANNEL_NAME = "My Channel"

// 고유한 ID를 정의
private const val NOTIFICATION_ID = 1

class MainActivity : AppCompatActivity() {

    private lateinit var binding : MainActivityBinding
    private var settingPosition: Int = 0
    private val itemList = mutableListOf<MainItemModel>() // 전역 변수로 선언

    // adapter 연결 설정        thumbsCheck값을 넘긴다
    val adapter = MainViewAdapter(itemList)

    private val itemDetailResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val resultData = result.data
            val judgeThumbsCheck = resultData?.getParcelableExtra<MainItemModel>("selectedItem")

            //adapter에 알리기
            if (judgeThumbsCheck!!.thumbsCheck != itemList[settingPosition].thumbsCheck){
                if(judgeThumbsCheck!!.thumbsCheck == 0){
                    adapter.updateItemThumbsCount(settingPosition, judgeThumbsCheck!!.thumbsCheck, judgeThumbsCheck!!.numOfThumbs)
                }
                else{
                    adapter.updateItemThumbsCount(settingPosition, judgeThumbsCheck!!.thumbsCheck, judgeThumbsCheck!!.numOfThumbs)
                }
            }

            itemList[settingPosition].thumbsCheck = judgeThumbsCheck!!.thumbsCheck
            Log.d("thumbResult", itemList[settingPosition].thumbsCheck.toString())



        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemList.add(MainItemModel(R.drawable.sample1, getString(R.string.sample_title1),getString(R.string.sample_location1), getString(R.string.sample_description1),getString(R.string.sample_name1),getString(R.string.sample_price1),13,25,0))
        itemList.add(MainItemModel(R.drawable.sample2, getString(R.string.sample_title2),getString(R.string.sample_location2), getString(R.string.sample_description2),getString(R.string.sample_name2),getString(R.string.sample_price2),8,28,0))
        itemList.add(MainItemModel(R.drawable.sample3, getString(R.string.sample_title3),getString(R.string.sample_location3), getString(R.string.sample_description3),getString(R.string.sample_name3),getString(R.string.sample_price3),23,5,0))
        itemList.add(MainItemModel(R.drawable.sample4, getString(R.string.sample_title4),getString(R.string.sample_location4), getString(R.string.sample_description4),getString(R.string.sample_name4),getString(R.string.sample_price4),14,17,0))
        itemList.add(MainItemModel(R.drawable.sample5, getString(R.string.sample_title5),getString(R.string.sample_location5), getString(R.string.sample_description5),getString(R.string.sample_name5),getString(R.string.sample_price5),22,9,0))
        itemList.add(MainItemModel(R.drawable.sample6, getString(R.string.sample_title6),getString(R.string.sample_location6), getString(R.string.sample_description6),getString(R.string.sample_name6),getString(R.string.sample_price6),25,16,0))
        itemList.add(MainItemModel(R.drawable.sample7, getString(R.string.sample_title7),getString(R.string.sample_location7), getString(R.string.sample_description7),getString(R.string.sample_name7),getString(R.string.sample_price7),142,54,0))
        itemList.add(MainItemModel(R.drawable.sample8, getString(R.string.sample_title8),getString(R.string.sample_location8), getString(R.string.sample_description8),getString(R.string.sample_name8),getString(R.string.sample_price8),31,7,0))
        itemList.add(MainItemModel(R.drawable.sample9, getString(R.string.sample_title9),getString(R.string.sample_location9), getString(R.string.sample_description9),getString(R.string.sample_name9),getString(R.string.sample_price9),7,28,0))
        itemList.add(MainItemModel(R.drawable.sample10, getString(R.string.sample_title10),getString(R.string.sample_location10), getString(R.string.sample_description10),getString(R.string.sample_name10),getString(R.string.sample_price10),40,6,0))



        binding.recyclerviewItemSet.adapter = adapter
        // 리니어 형태로 아이템을 배치하는 레이아웃 매니저
        binding.recyclerviewItemSet.layoutManager = LinearLayoutManager(this)

        //클릭한 item을 name에 저장
        adapter.itemClick = object : MainViewAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val selectedItem = itemList[position]
                settingPosition = position
                val intent = Intent(this@MainActivity, ItemDetailPage::class.java)
                intent.putExtra("selectedItem", selectedItem) // Parcelable 객체 전달

                itemDetailResultLauncher.launch(intent)

            }

            override fun onLongClick(view: View, position: Int) {
                // 롱클릭 이벤트 처리
                itemList.removeAt(position) // 데이터 리스트에서 아이템 제거
                binding.recyclerviewItemSet.adapter?.notifyItemRemoved(position) // RecyclerView에 변경 사항 알림
                binding.recyclerviewItemSet.adapter?.notifyItemRangeChanged(position, itemList.size - position) // 변경된 위치 이후 아이템들의 위치도 업데이트
            }



        }

        binding.notificationBtn.setOnClickListener{
            val title = "새로운 알림"
            val message = "이것은 새로운 알림 메시지입니다."

            // Notification 생성 함수 호출
            createNotification(this, title, message)
        }
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)

            binding.recyclerviewItemSet.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        binding.arrowUp.visibility = View.VISIBLE
                        binding.arrowUp.startAnimation(fadeIn)
                    } else {
                        binding.arrowUp.startAnimation(fadeOut)
                        binding.arrowUp.visibility = View.GONE
                    }
                }
            })
        // 플로팅 버튼 클릭 시 스크롤 상단으로 이동
        binding.arrowUp.setOnClickListener {
            binding.recyclerviewItemSet.smoothScrollToPosition(0)
        }

        // 플로팅 버튼 클릭 효과 추가
        binding.arrowUp.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> binding.arrowUp.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent))
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> binding.arrowUp.clearColorFilter()
            }
            false
        }

    }
    fun createNotification(context: Context, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 안드로이드 8.0 (Oreo) 이상에서는 Notification 채널을 생성해야 함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.bell) // 사용자에게 표시될 아이콘
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Notification을 표시
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭 시 다이얼로그 띄우기
        showExitDialog()
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
            .setMessage("정말 종료하시겠습니까?")
            .setNegativeButton("취소", null) // 취소 버튼은 아무 동작도 수행하지 않음
            .setPositiveButton("확인") { dialog, which ->
                finish() // 앱 종료
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}