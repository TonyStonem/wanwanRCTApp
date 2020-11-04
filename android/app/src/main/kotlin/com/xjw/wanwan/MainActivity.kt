package com.xjw.wanwan

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xjw.base.constant.Path
import com.xjw.library.base.BaseActivity
import com.xjw.library.base.BaseContract
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = Path.Home)
class MainActivity : BaseActivity() {

  private val onClickListener: View.OnClickListener by lazy {
    View.OnClickListener {
      when (it.id) {
        R.id.btn_main_user_info -> {
          ARouter.getInstance().build(Path.UserInfo).navigation()
        }
        R.id.btn_main_open_rct_page -> {
          ARouter.getInstance().build(Path.HomeRCT).navigation()
        }
        R.id.btn_main_di_test -> {
          ARouter.getInstance().build(Path.TestDi).navigation()
        }
      }
    }
  }

  private val requestCodeForPermission: Int = 0

  override fun getLayoutResId(): Int = R.layout.activity_main

  override fun injectPresenter(): BaseContract.Presenter<*>? {
    return null
  }

  override fun start() {
    drawStatusBarColor(R.color.transparent)
    text_main_app_id.text = BuildConfig.APPLICATION_ID
    checkDrawOverlaysPermission()
    arrayListOf<Int>(R.id.btn_main_open_rct_page, R.id.btn_main_di_test,
      R.id.btn_main_user_info).forEach {
      findViewById<View>(it).setOnClickListener(onClickListener)
    }
  }

  /**
   * rct 需要悬浮窗权限来展示开发中的错误信息
   */
  private fun checkDrawOverlaysPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (!Settings.canDrawOverlays(this)) {
        startActivityForResult(
          Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")),
          requestCodeForPermission)
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == requestCodeForPermission) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (!Settings.canDrawOverlays(this)) {
          println(">> 获取悬浮窗权限失败")
        }
      }
    }
  }

}