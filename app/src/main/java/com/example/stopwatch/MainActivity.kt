package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            //플레이 버튼
            isRunning = !isRunning

            if (isRunning) { // true
                start()
            } else { // false
                pause()
            }
        }

        lapButton.setOnClickListener {
            //랩 타임 버튼
            recordLapTime()
        }

        resetFab.setOnClickListener {
            //리셋 버튼
          reset()
        }
    }

    private fun reset(){
        timerTask?.cancel()

        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        secTextView.text="0"
        milliTextView.text = "00"

        lapLayout.removeAllViews()
        lap = 1

    }

    private fun pause() { // 타이머 일시정지
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel() // 실행중인 타이머가 있다면 취소한다.
    }

    private fun start() { //타이머 스타트
        fab.setImageResource(R.drawable.ic_baseline_pause_24) //일시정지 이미지로 바꿔줌.

        timerTask = timer(period = 10) {
            // period = 10 0.01초 , period = 1000 면 1초
            time++
            // 0.01초마다 변수를 증가시킴

            val hour = (time / 144000) % 24 // 1시간
            val min = (time / 6000) % 60 // 1분
            val sec = (time / 100) % 60 //1초
            val milli = time % 100 // 0.01 초
            runOnUiThread {
                // Ui 를 갱신 시킴.

                if (min < 10) { // 분
                    minTextView.text = "0$min"
                } else {
                    minTextView.text = "$min"
                }

                if (sec < 10) { // 초
                    secTextView.text = "0$sec"
                } else {
                    secTextView.text = "$sec"
                }

                if (milli < 10) {
                    milliTextView.text = "0$milli"
                } else {
                    milliTextView.text = "$milli"
                }

                //$ 를 붙여주면 변하는 값을 계속 덮어준다
                //ex) $를 붙여주면 기존에 1이라는 값이 잇을때 값이 2로변하면 2로 바꿔준다.

            }
        }
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap Lap : ${lapTime /100}.${lapTime % 100}"

        lapLayout.addView(textView,0)
        lap++

    }
}
