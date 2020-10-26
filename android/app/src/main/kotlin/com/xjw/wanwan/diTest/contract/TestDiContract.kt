package com.xjw.wanwan.diTest.contract

import com.xjw.library.base.BaseContract

/**
 * Created by xjw on 2020/10/26 16:32
 */
interface TestDiContract {

  interface View : BaseContract.View{

    fun sayHello(string: String)

  }

  interface Presenter : BaseContract.Presenter<View>{

    fun processSayAction()

  }

}