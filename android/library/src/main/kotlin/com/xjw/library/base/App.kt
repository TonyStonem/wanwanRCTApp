package com.xjw.library.base

import android.app.Application

/**
 * Created by xjw on 2020/10/26 16:07
 */
class App : Application() {

  companion object{
    private lateinit var app : Application

    fun obtainApp() : Application = app
  }

  override fun onCreate() {
    super.onCreate()
    app = this
  }

}