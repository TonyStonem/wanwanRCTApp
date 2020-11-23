package com.xjw.wanwan

import com.alibaba.android.arouter.facade.annotation.Route
import com.xjw.base.constant.Path
import com.xjw.library.base.BaseActivity
import com.xjw.library.base.BaseContract

/**
 * Created by xjw on 2020/11/4 15:55
 */
@Route(path = Path.Login)
class LoginActivity : BaseActivity() {

  override fun getLayoutResId(): Int = R.layout.activity_login

  override fun injectPresenter(): BaseContract.Presenter<*>? {
    return null
  }

}