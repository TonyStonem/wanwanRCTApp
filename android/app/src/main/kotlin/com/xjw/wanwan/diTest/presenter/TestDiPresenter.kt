package com.xjw.wanwan.diTest.presenter

import com.xjw.library.base.BasePresenter
import com.xjw.wanwan.diTest.contract.TestDiContract
import javax.inject.Inject

/**
 * Created by xjw on 2020/10/26 14:01
 */
class TestDiPresenter @Inject constructor() : BasePresenter<TestDiContract.View>(),
  TestDiContract.Presenter {

  private val strList: Array<String> by lazy { arrayOf("Hello", "Hi") }

  private var before = true

  override fun start() {
    test()
  }

  fun test() {
    println("TestPresenter start: test.")
  }

  override fun processSayAction() {
    println("TestPresenter processSayAction: view say!")
    view.sayHello(if (before) strList[0] else strList[1])
    before = !before
  }

}