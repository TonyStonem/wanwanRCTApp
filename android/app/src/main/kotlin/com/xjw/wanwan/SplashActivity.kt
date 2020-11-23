package com.xjw.wanwan

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xjw.base.constant.Path
import com.xjw.library.base.BaseActivity
import com.xjw.library.base.BaseContract
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by xjw on 2020/10/28 14:22
 */
@Route(path = Path.Splash)
class SplashActivity : BaseActivity() {

  override fun getLayoutResId(): Int = R.layout.activity_splash

  override fun injectPresenter(): BaseContract.Presenter<*>? {
    return null
  }

  private var isOpen = false

  override fun start() {
    startAnim(1)
    btn_splash_open_home.setOnClickListener {
      startAnim(200)
    }

    view_test.setOnClickListener {
      ARouter.getInstance().build(Path.Home).navigation()
      close()
    }
  }

  private fun startAnim(animDuration: Long) {
    view_test.pivotX = 0f
    view_test.pivotY = 0f
    val start = if (isOpen) 0f else 1f
    val end = if (isOpen) 1f else 0f
    val openAnim = ObjectAnimator.ofFloat(view_test, "scaleY", start, end).apply {
      duration = animDuration
      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
          super.onAnimationStart(animation)
          isOpen = !isOpen
        }
      })
    }
    openAnim.start()
  }

}