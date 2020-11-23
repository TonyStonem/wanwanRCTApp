package com.xjw.library.model.bean

/**
 * Created by xjw on 2020/11/4 15:59
 */

data class User(
  val id: Int = 0,
  val userName: String = "",
  val nickName: String = "",
  val phoneNumber: String = "",
  val userPicture: String = "",
  val token: String = "", // 用户token
  val guestToken: String = "", // 游客token
)