package com.xjw.library.base

import android.content.Context

/**
 * Created by xjw on 2020/10/26 16:10
 */
interface BaseContract {

  interface View {

    fun getCtx(): Context

    fun close()

    fun showToast(string: String)

    fun showToast(strResId: Int)

  }

  interface Presenter<in V : View> {

    fun attachView(v : V)

    fun start()

    fun resume()

    fun pause()

    fun destroy()

  }

}