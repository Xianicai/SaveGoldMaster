package com.savegoldmaster.common

class Urls {
    companion object {
        /**
         * Base地址
         */
        const val BASE_URL = "https://api-test.au32.cn"

        /**
         * 获取验证码
         */
        const val GET_PHONE_CODE =
            "$BASE_URL/v3/member/smsCaptcha"

        /**
         * 快捷登录
         */
        const val FASTER_LOGIN =
            "$BASE_URL/v3/member/quickLogin"
        /**
         * 账号登录
         */
        const val ACCOUNT_LOGIN =
            "$BASE_URL/v3/member/login"
        /**
         * 个人详情
         */
        const val GET_USER_DETAIL =
            "$BASE_URL/v3/member/queryMyProfil"
        /**
         * 获取首页banner
         */
        const val GET_BANNER_LIST =
            "$BASE_URL/v3/adPic/getLimit"
    }

}

