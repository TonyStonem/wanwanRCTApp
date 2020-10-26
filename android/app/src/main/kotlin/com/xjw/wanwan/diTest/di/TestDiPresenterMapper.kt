package com.xjw.wanwan.diTest.di

import com.xjw.library.di.scope.PerActivity
import com.xjw.wanwan.diTest.contract.TestDiContract
import com.xjw.wanwan.diTest.presenter.TestDiPresenter
import dagger.Binds
import dagger.Module

/**
 * Created by xjw on 2020/10/26 14:16
 */
@Module
abstract class TestDiPresenterMapper {

  @PerActivity
  @Binds
  abstract fun provideTestPresenter(presenter: TestDiPresenter): TestDiContract.Presenter

}