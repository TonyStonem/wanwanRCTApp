package com.xjw.wanwan.rx

import com.alibaba.android.arouter.facade.annotation.Route
import com.xjw.base.constant.Path
import com.xjw.library.base.BaseActivity
import com.xjw.library.base.BaseContract
import com.xjw.wanwan.R

/**
 * Created by xjw on 2020/11/23 17:40
 */
@Route(path = Path.Rx)
class RxActivity : BaseActivity() {

  override fun getLayoutResId(): Int = R.layout.activity_rx

  override fun injectPresenter(): BaseContract.Presenter<*>? {
    return null
  }

  override fun start() {

  }

  private fun fun1() {
    
  }

}