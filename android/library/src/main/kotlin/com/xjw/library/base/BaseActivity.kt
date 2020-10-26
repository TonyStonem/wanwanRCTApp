package com.xjw.library.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xjw.library.utils.ToastUtils

/**
 * Created by xjw on 2020/10/26 16:11
 */
abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

  protected lateinit var activity: AppCompatActivity

  private var basePresenter: BaseContract.Presenter<BaseContract.View>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    basePresenter = injectPresenter() as? BaseContract.Presenter<BaseContract.View>
    super.onCreate(savedInstanceState)
    activity = this
    setContentView(getLayoutResId())
    basePresenter?.attachView(this)
    start()
    basePresenter?.start()
  }

  protected open fun start() {

  }

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