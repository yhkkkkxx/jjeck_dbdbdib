package com.example.jjeck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    private val items = mutableListOf<ContentsModel>(

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    items.add(
        ContentsModel(
            "https://www.mangoplate.com/restaurants/EY0YvPb4NdCe",
        "https://mp-seoul-image-production-s3.mangoplate.com/47875_1614167337567063.jpg?fit=around|359:240&crop=359:240;*,*&output-format=jpg&output-quality=80",
            "만화쉔샤오룽빠오면식관"
        )
    )
        items.add(
            ContentsModel(
                "https://www.mangoplate.com/restaurants/WjvK9ngVzlAg",
                "https://mp-seoul-image-production-s3.mangoplate.com/619788_1517797046008188.jpg?fit=around|359:240&crop=359:240;*,*&output-format=jpg&output-quality=80",
                "성심당"
            )
        )


        items.add(
                ContentsModel(
                    "https://www.mangoplate.com/restaurants/NjGScx24nD",
                    "https://mp-seoul-image-production-s3.mangoplate.com/522207_1628495150107028.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                    "태평소국밥"
                )
                    )

        items.add(
            ContentsModel(
                "https://www.mangoplate.com/restaurants/jyBALjr9F5L0",
                "https://mp-seoul-image-production-s3.mangoplate.com/573545_1594446229880774.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "토미야"
            )
        )

        items.add(
            ContentsModel(
                "https://www.mangoplate.com/restaurants/KwNMtQdIxa",
                "https://mp-seoul-image-production-s3.mangoplate.com/37416/lipm4-fdtyckaq.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "어화"
            )
        )
        items.add(
            ContentsModel(
                "https://www.mangoplate.com/restaurants/PwCiUmXeCd1L",
                "https://mp-seoul-image-production-s3.mangoplate.com/395434/1835160_1613992711191_269122?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "Karamel"
            )
        )


        items.add(
            ContentsModel(
                "https://www.mangoplate.com/restaurants/ofzaGexs7vx6",
                "https://mp-seoul-image-production-s3.mangoplate.com/1388693_1573981100625724.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "음식이있는풍경"
            )
        )

        items.add(
            ContentsModel(
                "https://www.mangoplate.com/restaurants/xeKYUj_igz",
                "https://mp-seoul-image-production-s3.mangoplate.com/193024/1097442_1635663955213_29449?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "성심당케익부띠끄"
            )
        )
        items.add(
            ContentsModel(
                "https://www.mangoplate.com/restaurants/GoaJ4WEYsFjZ",
                "https://mp-seoul-image-production-s3.mangoplate.com/47875_1593171723249345.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "요우란"
            )
        )

        items.add(
            ContentsModel(
                "https://www.mangoplate.com/restaurants/_hDeEh9BewtV",
                "https://mp-seoul-image-production-s3.mangoplate.com/415017/1803393_1602819958217_16680?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "우츠"
            )
        )

        val recyclerView =findViewById<RecyclerView>(R.id.rv)
        val rvAdapter = RVAdapter(baseContext,items)
        recyclerView.adapter =  rvAdapter

        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(baseContext, ViewActivity::class.java)
                intent.putExtra("url",items[position].url)
                startActivity(intent)


                 }
        }
        recyclerView.layoutManager = GridLayoutManager(this,2)
    }
}