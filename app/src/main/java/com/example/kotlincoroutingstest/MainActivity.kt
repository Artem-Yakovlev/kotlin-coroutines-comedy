package com.example.kotlincoroutingstest

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.MainThread
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val waterBase = WaterBase()
    private var size: Point = Point()
    private var currentPosition = 50
    private var currentDirectionIsRight = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display = windowManager.defaultDisplay
        display.getSize(size)

        button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                var resultMessage = ""
                withContext(Dispatchers.IO) {
                    resultMessage = waterBase.checkState()
                }
                text.text = resultMessage
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startAnimation()
    }

    private fun startAnimation() {
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                withContext(Dispatchers.IO) {
                    calculateNewPosition()
                }
                animatePosition(currentPosition)

            }
        }
    }

    private fun animatePosition(currentPosition: Int) {
        val param = funny_block.layoutParams as LinearLayout.LayoutParams
        param.setMargins(currentPosition - 50, 0, 0, 0)
        funny_block.layoutParams = param
    }

    private fun calculateNewPosition() {
        if (currentDirectionIsRight) {
            if (currentPosition + 2 < size.x) {
                currentPosition += 2
            } else {
                currentDirectionIsRight = false
            }
        } else {
            if (currentPosition - 2 > 50) {
                currentPosition -= 2
            } else {
                currentDirectionIsRight = true
            }
        }
    }
}
