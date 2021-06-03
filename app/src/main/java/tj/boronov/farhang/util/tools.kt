package tj.boronov.farhang.util

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View

fun Context.vibratePhone(time: Long) {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(time)
    }
}

fun scale(view: View) {
    val scalex = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f)
    val scaley = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
    val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 360f)

    val anim: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
        view,
        scalex,
        scaley,
        rotation
    )
    anim.repeatCount = 1
    anim.repeatMode = ValueAnimator.REVERSE
    anim.duration = 500
    anim.start()
}