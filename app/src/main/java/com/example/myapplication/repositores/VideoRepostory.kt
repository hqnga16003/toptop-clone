package com.example.myapplication.repositores

import com.example.myapplication.R
import javax.inject.Inject

class VideoRepostory @Inject constructor() {
    private val videos = listOf(R.raw.test,R.raw.test2,R.raw.test3,R.raw.test4)

    fun getVideo() = videos.random()
}