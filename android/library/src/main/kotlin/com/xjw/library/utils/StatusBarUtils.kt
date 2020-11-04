package com.xjw.library.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.xjw.library.R
import java.lang.reflect.InvocationTargetException

object StatusBarUtils {

  const val webPageStatusBarName = "status_bar_height"

  /**
   * 测量状态栏的高度
   */
  fun measureStatusBarHeight(context: Context): Int {
    val resources = context.resources
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelOffset(resId)
  }

  /**
   * 判断当前是否刘海屏
   */
  fun isNotch(context: Context): Boolean {
    val resources = context.resources
    val realStatusBarHeight = measureStatusBarHeight(context)
    val standardStatusBarHeight = resources.getDimensionPixelOffset(R.dimen.status_bar_height)
    return realStatusBarHeight > 0 && realStatusBarHeight > standardStatusBarHeight
  }

  fun fitNotchByPaddingTop(view: View, force: Boolean = false) {
    val statusBarHeight = measureStatusBarHeight(view.context)
    if (force || statusBarHeight == view.paddingTop) {
      val lp = view.layoutParams
      val viewHeight = lp.height
      lp.height = viewHeight + statusBarHeight
      view.layoutParams = lp
      view.setPadding(view.paddingStart, view.paddingTop + statusBarHeight, view.paddingEnd,
        view.paddingBottom)
      view.postInvalidate()
    }
  }

  fun fitNotchByAddHeight(view: View) {
    val lp = view.layoutParams
    val viewHeight = lp.height
    lp.height = viewHeight + measureStatusBarHeight(view.context)
    view.layoutParams = lp
    view.postInvalidate()
  }

  /**
   * 通过设置View 的高度适配刘海屏.
   * 需要View 的高度为@dimen/status_bar_height
   */
  fun fitNotchByHeight(view: View, force: Boolean = false) {
    val resources = view.context.resources
    val standardStatusBarHeight = resources.getDimensionPixelOffset(R.dimen.status_bar_height)
    val viewHeight = view.height
    if (force || viewHeight == standardStatusBarHeight) {
      val realStatusBarHeight = measureStatusBarHeight(view.context)
      val viewLayoutParams = view.layoutParams
      viewLayoutParams.height = realStatusBarHeight
      view.layoutParams = viewLayoutParams
      view.requestLayout()
    }
  }

  /**
   * 通过Activity/Fragment的ContentView适配刘海屏.
   * 需要ContentView有paddingTop属性.值为@dimen/status_bar_height,否则不起作用
   */
  fun fitNotch(contentView: View?) {
    contentView?.let {
      val resources = it.context.resources
      val currentStatusBarHeight = resources.getDimensionPixelOffset(R.dimen.status_bar_height)
      if (currentStatusBarHeight > 0 && it.paddingTop == currentStatusBarHeight) {
        val realStatusBarHeight = measureStatusBarHeight(it.context)
        if (realStatusBarHeight != currentStatusBarHeight) {
          it.setPadding(it.paddingLeft, realStatusBarHeight, it.paddingRight, it.paddingBottom)
        }
      }
    }
  }

  /**
   * 通过设置某View的marginTop适配刘海屏.
   * force参数为true的情况下.则忽略View的marginTop值.直接重置View的marginTop为状态栏高度
   * 否则需要给View设置marginTop为@dimen/status_bar_height
   */
  fun fitNotchByMargin(view: View?, force: Boolean = false) {
    view?.let {
      val resources = it.context.resources
      val currentStatusBarHeight = resources.getDimensionPixelOffset(R.dimen.status_bar_height)
      val viewLayoutParams = it.layoutParams as? ViewGroup.MarginLayoutParams
      if (force || (currentStatusBarHeight > 0 && viewLayoutParams?.topMargin == currentStatusBarHeight)) {
        val realStatusBarHeight = measureStatusBarHeight(it.context)
        if (realStatusBarHeight != currentStatusBarHeight) {
          viewLayoutParams?.topMargin = realStatusBarHeight
          it.layoutParams = viewLayoutParams
          it.requestLayout()
        }
      }
    }
  }

  /**
   * 判断颜色是否偏黑色
   *
   * @param color 颜色
   * @param level 级别
   * @return
   */
  fun isBlackColor(color: Int, level: Int): Boolean {
    val grey = toGrey(color)
    return grey < level
  }

  /**
   * 颜色转换成灰度值
   *
   * @param rgb 颜色
   * @return　灰度值
   */
  fun toGrey(rgb: Int): Int {
    val blue = rgb and 0x000000FF
    val green = rgb and 0x0000FF00 shr 8
    val red = rgb and 0x00FF0000 shr 16
    return red * 38 + green * 75 + blue * 15 shr 7
  }

  /**
   * 设置状态栏字体图标颜色
   *
   * @param activity 当前activity
   * @param color    颜色
   */
  fun setStatusBarDarkIcon(activity: Activity, color: Int) {
    val mSetStatusBarColorIcon = try {
      Activity::class.java.getMethod("setStatusBarDarkIcon", Int::class.javaPrimitiveType)
    } catch (e: Exception) {
      null
    }
    if (mSetStatusBarColorIcon != null) {
      try {
        mSetStatusBarColorIcon.invoke(activity, color)
      } catch (e: IllegalAccessException) {
        //e.printStackTrace()
      } catch (e: InvocationTargetException) {
        //e.printStackTrace()
      }
    } else {
      val whiteColor = isBlackColor(color, 50)
      val mStatusBarColorFiled = try {
        WindowManager.LayoutParams::class.java.getField("statusBarColor")
      } catch (e: Exception) {
        null
      }
      if (mStatusBarColorFiled != null) {
        setStatusBarDarkIcon(activity, whiteColor, whiteColor)
        setStatusBarDarkIcon(activity.window, color)
      } else {
        setStatusBarDarkIcon(activity, whiteColor)
      }
    }
  }

  /**
   * 设置状态栏字体图标颜色(只限全屏非activity情况)
   *
   * @param window 当前窗口
   * @param color  颜色
   */
  fun setStatusBarDarkIcon(window: Window, color: Int) {
    try {
      setStatusBarColor(window, color)
      if (Build.VERSION.SDK_INT > 22) {
        setStatusBarDarkIcon(window.decorView, true)
      }
    } catch (e: Exception) {
      //e.printStackTrace()
    }
  }

  /**
   * 为Miui以及Meizu手机设置状态栏字体图标颜色
   *
   * @param activity 当前activity
   * @param dark     是否深色 true为深色 false 为白色
   */
  fun setStatusBarDarkIcon(activity: Activity, dark: Boolean) {
    val deviceManufacturerName = Build.MANUFACTURER.replace(" ", "")
    if (deviceManufacturerName.contains("xiaomi", true)) {
      setMiUiStatusBarDarkMode(activity, dark)
    } else if (deviceManufacturerName.contains("meizu", true)) {
      setStatusBarDarkIcon(activity, dark, true)
    }
  }

  private fun setMiUiStatusBarDarkMode(activity: Activity, darkMode: Boolean) {
    val clazz = activity.window.javaClass
    try {
      val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
      val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
      var darkModeFlag = field.getInt(layoutParams)
      val extraFlagField =
        clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
      extraFlagField.invoke(activity.window, if (darkMode) darkModeFlag else 0, darkModeFlag)
    } catch (e: Exception) {
      //e.printStackTrace()
    }
  }

  private fun changeMeizuFlag(winParams: WindowManager.LayoutParams, flagName: String,
    on: Boolean): Boolean {
    try {
      val f = winParams.javaClass.getDeclaredField(flagName)
      f.isAccessible = true
      val bits = f.getInt(winParams)
      val f2 = winParams.javaClass.getDeclaredField("meizuFlags")
      f2.isAccessible = true
      var meizuFlags = f2.getInt(winParams)
      val oldFlags = meizuFlags
      if (on) {
        meizuFlags = meizuFlags or bits
      } else {
        meizuFlags = meizuFlags and bits.inv()
      }
      if (oldFlags != meizuFlags) {
        f2.setInt(winParams, meizuFlags)
        return true
      }
    } catch (e: NoSuchFieldException) {
      //e.printStackTrace()
    } catch (e: IllegalAccessException) {
      //e.printStackTrace()
    } catch (e: IllegalArgumentException) {
      //e.printStackTrace()
    } catch (e: Throwable) {
      //e.printStackTrace()
    }

    return false
  }

  /**
   * 设置状态栏颜色
   *
   * @param view
   * @param dark
   */
  private fun setStatusBarDarkIcon(view: View, dark: Boolean) {
    val oldVis = view.systemUiVisibility
    var newVis = oldVis
    try {
      val field = View::class.java.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR")
      var systemUiFlagLightStatusBar = field.getInt(null)
      if (dark) {
        newVis = newVis or systemUiFlagLightStatusBar
      } else {
        newVis = newVis and systemUiFlagLightStatusBar.inv()
      }
      if (newVis != oldVis) {
        view.systemUiVisibility = newVis
      }
    } catch (ignore: Exception) {
    }
  }

  /**
   * 设置状态栏颜色
   *
   * @param window
   * @param color
   */
  private fun setStatusBarColor(window: Window, color: Int) {
    val winParams = window.attributes
    val mStatusBarColorFiled = try {
      WindowManager.LayoutParams::class.java.getField("statusBarColor")
    } catch (e: IllegalAccessException) {
      null
    }
    mStatusBarColorFiled?.let {
      val oldColor = it.getInt(winParams)
      if (oldColor != color) {
        it.set(winParams, color)
        window.attributes = winParams
      }
    }
  }

  /**
   * 设置状态栏字体图标颜色(只限全屏非activity情况)
   *
   * @param window 当前窗口
   * @param dark   是否深色 true为深色 false 为白色
   */
  fun setStatusBarDarkIcon(window: Window, dark: Boolean) {
    if (Build.VERSION.SDK_INT < 23) {
      changeMeizuFlag(window.attributes, "MEIZU_FLAG_DARK_STATUS_BAR_ICON", dark)
    } else {
      val decorView = window.decorView
      if (decorView != null) {
        setStatusBarDarkIcon(decorView, dark)
        setStatusBarColor(window, 0)
      }
    }
  }

  private fun setStatusBarDarkIcon(activity: Activity, dark: Boolean, flag: Boolean) {
    val mSetStatusBarDarkIcon = try {
      Activity::class.java.getMethod("setStatusBarDarkIcon", Boolean::class.javaPrimitiveType)
    } catch (e: Exception) {
      null
    }
    if (mSetStatusBarDarkIcon != null) {
      try {
        mSetStatusBarDarkIcon.invoke(activity, dark)
      } catch (e: IllegalAccessException) {
        //e.printStackTrace()
      } catch (e: InvocationTargetException) {
        //e.printStackTrace()
      }

    } else {
      if (flag) {
        setStatusBarDarkIcon(activity.window, dark)
      }
    }
  }
}