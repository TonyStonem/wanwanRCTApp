package com.xjw.library.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by xjw on 2020/10/26 16:07
 */
class App : Application() {

  companion object {

    private lateinit var app: Application

    fun obtainApp(): Application = app
  }

  override fun onCreate() {
    super.onCreate()
    app = this

    initialize()
  }

  private fun initialize() {
    ARouter.openLog()
    ARouter.openDebug()

    ARouter.init(this)
  }

}