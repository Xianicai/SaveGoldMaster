package com.savegoldmaster.account

import com.savegoldmaster.base.BaseApplication
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.StringUtil

class UserUtil {
    companion object {
        fun isLogin(): Boolean {
            val preferencesHelper = SharedPreferencesHelper(BaseApplication.instance, "UserBean")
            val token = preferencesHelper.getSharedPreference("token", "").toString().trim()
            val userId = preferencesHelper.getSharedPreference("userId", "").toString().trim()
            return StringUtil.isNotEmpty(token) && StringUtil.isNotEmpty(userId)
        }
    }


}