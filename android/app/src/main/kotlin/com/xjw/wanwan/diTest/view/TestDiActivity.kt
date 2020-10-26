package com.xjw.wanwan.diTest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xjw.wanwan.R
import com.xjw.wanwan.diTest.di.DaggerTestDiComponent
import com.xjw.wanwan.diTest.presenter.TestDiPresenter
import javax.inject.Inject

/**
 * Created by xjw on 2020/10/26 14:11
 */
class TestDiActivity : AppCompatActivity() {

  @Inject
  lateinit var presenter: TestDiPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerTestDiComponent.builder().build().inject(this)
    setContentView(R.layout.activity_di_test)
    presenter.test()
  }

}