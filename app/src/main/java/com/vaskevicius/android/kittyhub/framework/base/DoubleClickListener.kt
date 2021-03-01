package com.vaskevicius.android.kittyhub.framework.base

import android.os.Handler
import android.view.View
import java.util.*

abstract class DoubleClickListener : View.OnClickListener {

    companion object {
        private const val DOUBLE_CLICK_TIME: Long = 300
    }

    var lastClickTime: Long = 0
    var timer: Timer? = null

    override fun onClick(v: View?) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME) {
            timer?.cancel()
            timer?.purge()
            onDoubleClick(v)
        } else {
            var handler = Handler()
            var runnable = Runnable {
                onSingleClick(v)
            }
            var timerTask = object : TimerTask() {
                override fun run() {
                    handler.post(runnable)
                }
            }
            timer = Timer()
            timer!!.schedule(timerTask, DOUBLE_CLICK_TIME)
        }
        lastClickTime = clickTime
    }

    abstract fun onSingleClick(v: View?)
    abstract fun onDoubleClick(v: View?)

}