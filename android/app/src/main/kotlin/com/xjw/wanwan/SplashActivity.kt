package com.xjw.wanwan

import android.os.Handler
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xjw.base.constant.Path
import com.xjw.library.base.BaseActivity
import com.xjw.library.base.BaseContract

/**
 * Created by xjw on 2020/10/28 14:22
 */
@Route(path = Path.Splash)
class SplashActivity : BaseActivity() {

  private val handler: Handler by lazy {
    Handler(mainLooper) {

      ARouter.getInstance().build(Path.Home).navigation()
      close()

      true
    }
  }

  override fun getLayoutResId(): Int = R.layout.activity_splash

  override fun injectPresenter(): BaseContract.Presenter<*>? {
    return null
  }

  override fun start() {
    handler.sendEmptyMessageDelayed(0, 1500)
  }

}