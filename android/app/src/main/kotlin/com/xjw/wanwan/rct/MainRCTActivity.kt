package com.xjw.wanwan.rct

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.shell.MainReactPackage
import com.xjw.base.constant.Path
import com.xjw.wanwan.BuildConfig

/**
 * Created by xjw on 2020/10/22 15:21
 */
@Route(path = Path.HomeRCT)
class MainRCTActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler {

  private val rctInstanceManager: ReactInstanceManager by lazy {
    ReactInstanceManager.builder()
      .setApplication(application)
      .setCurrentActivity(this)
      .setBundleAssetName("index.android.bundle")
      .setJSMainModulePath("index")
      .addPackage(MainReactPackage())
      .setUseDeveloperSupport(BuildConfig.DEBUG)
      .setInitialLifecycleState(LifecycleState.RESUMED)
      .build()
  }

  private val rctRoot: ReactRootView by lazy {
    ReactRootView(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    rctRoot.startReactApplication(rctInstanceManager, "wanwanRCTApp", null)
    setContentView(rctRoot)
  }

  override fun onResume() {
    super.onResume()
    rctInstanceManager.onHostResume(this)
  }

  override fun onPause() {
    super.onPause()
    rctInstanceManager.onHostPause(this)
  }

  override fun onDestroy() {
    rctInstanceManager.onHostDestroy(this)
    super.onDestroy()
  }

  override fun invokeDefaultOnBackPressed() {
    super.onBackPressed()
  }

  /*override fun onBackPressed() {
    rctInstanceManager.onBackPressed()
  }*/

  override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
    if (keyCode == KeyEvent.KEYCODE_MENU) {
      rctInstanceManager.showDevOptionsDialog()
      return true
    }
    return super.onKeyUp(keyCode, event)
  }

}