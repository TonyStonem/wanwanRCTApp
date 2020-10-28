package com.xjw.wanwan.diTest.view

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.xjw.base.constant.Path
import com.xjw.library.base.BaseActivity
import com.xjw.library.base.BaseContract
import com.xjw.wanwan.R
import com.xjw.wanwan.diTest.contract.TestDiContract
import com.xjw.wanwan.diTest.di.DaggerTestDiComponent
import javax.inject.Inject

/**
 * Created by xjw on 2020/10/26 14:11
 */
@Route(path = Path.TestDi)
class TestDiActivity : BaseActivity(), TestDiContract.View {

  private val onClickListener: View.OnClickListener by lazy {
    View.OnClickListener {
      when (it.id) {
        R.id.btn_test_di_close -> {
          close()
        }
        R.id.btn_test_di_say -> {
          presenter.processSayAction()
        }
      }
    }
  }

  @Inject
  lateinit var presenter: TestDiContract.Presenter

  override fun getLayoutResId(): Int = R.layout.activity_di_test

  override fun injectPresenter(): BaseContract.Presenter<*>? {
    DaggerTestDiComponent.builder().build().inject(this)
    return presenter
  }

  override fun start() {
    arrayListOf<Int>(R.id.btn_test_di_say, R.id.btn_test_di_close).forEach {
      findViewById<View>(it).setOnClickListener(onClickListener)
    }
  }

  override fun sayHello(string: String) {
    showToast(string)
  }

}