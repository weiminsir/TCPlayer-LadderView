package com.wick.player

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_demo_activity.*


class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_activity)


        //1:3 进度比
        onUpdatePercent(100, 300)
    }

    override fun onResume() {
        super.onResume()
        lv_up_progress.postDelayed({
            startAnimation(lv_up_word)
        }, 200)
        startAnimation(lv_up_progress)
        ll_guess_percent?.postDelayed({
            startAnimation(lv_down_word)
        }, 700)
    }


    /**
     *
     * 更新涨跌百分比 //  优化定义View 里面
     *
     * */

    private fun onUpdatePercent(up_num: Int, down_num: Int) {
        ll_guess_percent.post {
            val totalWidth = cl_percent.width
            val upRate: Float = up_num.toFloat() / (up_num.toFloat() + down_num.toFloat())
            val lvUpParams = lv_up_progress.layoutParams as ViewGroup.LayoutParams
            lvUpParams.width = (totalWidth * upRate).toInt()
            lv_up_progress.layoutParams = lvUpParams
            cl_percent.invalidate()
            val upPercent = (upRate * 100 + 0.5).toInt()
            tv_up_num.text = upPercent.toString().plus("%")
            tv_down_num.text = (100 - upPercent).toString().plus("%")
        }
    }

    /**
     *
     * 看涨动画
     * */

    fun startAnimation(view: View) {
        if (view == null) return
        val animatorSet = AnimatorSet()//组合动画
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f)

        animatorSet.duration = 500
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.play(scaleX).with(scaleY)//两个动画同时开始
        animatorSet.start()
    }

}
