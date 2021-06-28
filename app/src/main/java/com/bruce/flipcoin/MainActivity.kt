package com.bruce.flipcoin

import android.graphics.Camera
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.DecelerateInterpolator
import com.bruce.flipcoin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var animation: FlipAnimation? = null
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mCamera: Camera
    private var isHead = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mCamera = Camera()
        mBinding.coin.setOnClickListener {
            startFlip()
        }
    }

    private fun startFlip() {
        animation = FlipAnimation(
            0f,
            3600f,
            (mBinding.coin.width / 2).toFloat(),
            (mBinding.coin.height / 2).toFloat(),
            onFlip = {
                isHead = isHead.not()
                if (isHead)
                    mBinding.coin.setImageResource(R.mipmap.ic_coin_head)
                else
                    mBinding.coin.setImageResource(R.mipmap.ic_coin_flower)
            }
        ).apply {
            interpolator = DecelerateInterpolator(3.0f)
            duration = 8000
        }
        mBinding.coin.startAnimation(animation)
    }
}