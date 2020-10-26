package com.xjw.wanwan.diTest.di

import com.xjw.library.di.scope.PerActivity
import com.xjw.wanwan.diTest.presenter.TestDiPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by xjw on 2020/10/26 14:16
 */
@Module
class TestDiPresenterMapper {

  @PerActivity
  @Provides
  fun provideTestPresenter(): TestDiPresenter{
    println("mapper create: TestDiPresenter")
    return TestDiPresenter()
  }

}