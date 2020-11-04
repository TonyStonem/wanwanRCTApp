package com.xjw.library.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.xjw.library.R
import com.xjw.library.utils.StatusBarUtils
import com.xjw.library.utils.ToastUtils

/**
 * Created by xjw on 2020/10/26 16:11
 */
abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

  protected lateinit var activity: AppCompatActivity

  private var basePresenter: BaseContract.Presenter<BaseContract.View>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    basePresenter = injectPresenter() as? BaseContract.Presenter<BaseContract.View>
    layoutStatusBar()
    super.onCreate(savedInstanceState)
    activity = this
    // MIUI和Flyme系统状态栏颜色
    setStatusBarDarkMode()
    setContentView(getLayoutResId())
    /**刘海屏*/
    StatusBarUtils.fitNotch(findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
    setSupportToolbar()
    basePresenter?.attachView(this)
    start()
    basePresenter?.start()
  }

  protected open fun start() {

  }

  private fun setSupportToolbar() {
    val toolbar: View? = findViewById(R.id.toolbar)
    if (toolbar != null) {
      setSupportToolbar(toolbar as Toolbar)
    }
  }

  protected open fun setStatusBarDarkMode(darkMode: Boolean = true) {
    // API等级大于等于24(Android 7.0),不做操作
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return
    }
    StatusBarUtils.setStatusBarDarkIcon(this, darkMode)
  }

  protected open fun layoutStatusBar(statusBarDarkFont: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val lightFlag = if (statusBarDarkFont)
        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      else
        0
      window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or lightFlag)
      drawStatusBarColor()
    }
  }

  /**
   * 设置状态栏的颜色
   * @param c Android 6.0以上默认是白色.5.0和5.1默认自定义的颜色
   */
  protected open fun drawStatusBarColor(@ColorRes c: Int =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
      R.color.white
    else
      R.color.colorNavigationBar) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val window= this.window
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      window.statusBarColor = ContextCompat.getColor(this, c)
    }
  }

  private fun setSupportToolbar(toolbar: Toolbar) {
    findToolbar()?.let {
      if (it.navigationIcon == null) {
        it.setNavigationIcon(R.drawable.ic_action_arrow_back_black)
      }
    }
    setSupportActionBar(toolbar)
    val actionBar = supportActionBar
    actionBar?.setDisplayHomeAsUpEnabled(true)
    val textToolbarTitle: TextView? =
      findAppBar()?.findViewById<View>(R.id.text_title_in_toolbar) as? TextView
    val title = actionBar?.title ?: ""
    textToolbarTitle?.let {
      it.text = title
      //TODO it.enableMarquee()
    }
    actionBar?.title = ""
    findToolbar()?.setNavigationOnClickListener { onBackPressed() }
  }

  protected fun findAppBar(): AppBarLayout? = findViewById(R.id.appbar)

  protected fun findToolbar(): Toolbar? = findViewById(R.id.toolbar)

  protected fun findTitleText(): TextView? = findViewById(R.id.text_title_in_toolbar)

  protected abstract fun getLayoutResId(): Int

  protected abstract fun injectPresenter(): BaseContract.Presenter<*>?

  override fun getCtx(): Context = this

  override fun close() {
    finish()
  }

  override fun showToast(string: String) {
    ToastUtils.show(string, Toast.LENGTH_SHORT)
  }

  override fun showToast(strResId: Int) {
    ToastUtils.show(strResId, Toast.LENGTH_SHORT)
  }

  override fun onResume() {
    super.onResume()
    basePresenter?.resume()
  }

  override fun onPause() {
    super.onPause()
    basePresenter?.pause()
  }

  override fun onDestroy() {
    basePresenter?.destroy()
    super.onDestroy()
  }

}