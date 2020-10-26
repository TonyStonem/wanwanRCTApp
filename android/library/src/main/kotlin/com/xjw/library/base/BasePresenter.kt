package com.xjw.library.base

/**
 * Created by xjw on 2020/10/26 16:27
 */
abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

  protected lateinit var view: V

  override fun attachView(v: V) {
    this.view = v
  }

  override fun resume() {

  }

  override fun pause() {

  }

  override fun destroy() {

  }

}