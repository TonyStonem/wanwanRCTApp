package com.xjw.library.utils

import android.widget.Toast
import com.xjw.library.base.App

/**
 * Created by xjw on 2020/10/26 17:19
 */
object ToastUtils {

  private val toast: Toast by lazy { Toast.makeText(App.obtainApp(), "", Toast.LENGTH_SHORT) }

  fun show(string: String, duration: Int) {
    toast.setText(string)
    toast.duration = duration
    toast.show()
  }

  fun show(strResId: Int, duration: Int) {
    toast.setText(strResId)
    toast.duration = duration
    toast.show()
  }

  fun cancel() {
    toast.cancel()
  }

}