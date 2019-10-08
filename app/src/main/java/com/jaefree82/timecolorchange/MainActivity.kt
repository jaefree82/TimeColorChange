package com.jaefree82.timecolorchange

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        private const val RED_COLOR = "#ff7979"
        private const val BLUE_COLOR = "#4087ff"
    }

    private val disposables by lazy {
        CompositeDisposable()
    }

    private var evenColor = RED_COLOR
    private var oddColor = BLUE_COLOR

    private var currentTimestamp = -1L

    private var isEvenOdd = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        setTimeView()
    }

    private fun setTimeView() {
        currentTimestamp = System.currentTimeMillis()

        Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                Logger.i("interval: $it")
                currentTimestamp += 1000
                evenColor = if (isEvenOdd) RED_COLOR else BLUE_COLOR
                oddColor = if (isEvenOdd) BLUE_COLOR else RED_COLOR
                tvTime.apply {
                    text = currentTimestamp.toDateFormat()
                    setTextColor(Color.parseColor(
                        if (currentTimestamp.getTimeEvenOddNum() == 0) evenColor else oddColor)
                    )
                }
            }.apply { disposables.add(this) }

        btnChangeColor.setOnClickListener {
            isEvenOdd = !isEvenOdd
            Toast.makeText(this, isEvenOdd.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }
}
