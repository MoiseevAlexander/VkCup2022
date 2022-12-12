package vk.cup.android.presentation.utils

import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun View.startAnimate(@AnimRes animRes: Int) {
    this.startAnimation(AnimationUtils.loadAnimation(this.context, animRes))
}
