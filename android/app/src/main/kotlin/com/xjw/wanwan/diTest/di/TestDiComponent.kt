package com.xjw.wanwan.diTest.di

import com.xjw.library.di.scope.PerActivity
import com.xjw.library.di.scope.PerFragment
import com.xjw.wanwan.diTest.view.TestDiActivity
import dagger.Component

/**
 * Created by xjw on 2020/10/26 14:08
 */
@PerActivity
@PerFragment
@Component() //modules = [TestDiPresenterMapper::class]
interface TestDiComponent {

  fun inject(activity: TestDiActivity)

}