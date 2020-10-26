package com.xjw.wanwan

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.xjw.wanwan.diTest.view.TestDiActivity
import com.xjw.wanwan.rct.MainRCTActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private val onClickListener: View.OnClickListener by lazy {
    View.OnClickListener {
      when (it.id) {
        R.id.btn_main_open_rct_page -> {
          startActivity(Intent(this, MainRCTActivity::class.java))
        }
        R.id.btn_main_di_test -> {
          startActivity(Intent(this, TestDiActivity::class.java))
        }
      }
    }
  }

  private val requestCodeForPermission: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    text_main_app_id.text = BuildConfig.APPLICATION_ID
    checkDrawOverlaysPermission()
    arrayListOf<Int>(R.id.btn_main_open_rct_page, R.id.btn_main_di_test).forEach {
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