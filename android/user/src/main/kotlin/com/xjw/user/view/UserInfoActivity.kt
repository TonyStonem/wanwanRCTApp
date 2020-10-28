package com.xjw.user.view

import android.os.Handler
import com.alibaba.android.arouter.facade.annotation.Route
import com.xjw.base.constant.Path
import com.xjw.library.base.BaseActivity
import com.xjw.library.base.BaseContract
import com.xjw.user.R

/**
 * Created by xjw on 2020/10/27 16:27
 */
@Route(path = Path.UserInfo)
class UserInfoActivity : BaseActivity() {

  private val handler: Handler by lazy {
    Handler(mainLooper) {
      true
    }
  }

  override fun getLayoutResId(): Int = R.layout.activity_user_info

  override fun injectPresenter(): BaseContract.Presenter<*>? {
    return null
  }

}