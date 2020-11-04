package com.xjw.wanwan

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

  override fun start() {
    drawStatusBarColor(R.color.transparent)
    btn_splash_open_home.setOnClickListener {
      ARouter.getInstance().build(Path.Home).navigation()
      close()
    }
  }

}