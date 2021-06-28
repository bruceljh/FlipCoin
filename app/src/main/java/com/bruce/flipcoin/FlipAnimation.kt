package com.bruce.flipcoin

import android.graphics.Camera
import android.view.animation.Animation
import android.view.animation.Transformation


class FlipAnimation(
    private val fromDegrees: Float,
    private val toDegrees: Float,
    private val centerX: Float,
    private val centerY: Float,
    private val onFlip: () -> Unit
) : Animation() {

    private lateinit var mCamera: Camera
    private var currentPosition = 0

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        mCamera = Camera()
    }

    override fun applyTransformation(
        interpolatedTime: Float,
        t: Transformation
    ) {
        val degrees: Float = fromDegrees + (toDegrees - fromDegrees) * interpolatedTime
        val camera = mCamera
        val matrix = t.matrix
        camera.save()
        val i = degrees.toInt() / 90 % 2
        if (i == 1) {
            if (i != currentPosition) {
                currentPosition = i
                onFlip()
            }
        } else {
            currentPosition = 0
        }
        camera.rotateY(degrees)
        camera.getMatrix(matrix)
        camera.restore()
        matrix.preTranslate(-centerX, -centerY)
        matrix.postTranslate(centerX, centerY)
    }
}