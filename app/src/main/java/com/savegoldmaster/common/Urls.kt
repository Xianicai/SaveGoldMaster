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
        const val GET_PHONE_CODE = "$BASE_URL/v3/member/smsCaptcha"

        /**
         * 快捷登录
         */
        const val FASTER_LOGIN = "$BASE_URL/v3/member/quickLogin"
        /**
         * 账号登录
         */
        const val ACCOUNT_LOGIN = "$BASE_URL/v3/member/login"
        /**
         * 个人详情
         */
        const val GET_USER_DETAIL = "$BASE_URL/v3/member/queryMyProfil"
        /**
         * 获取首页banner
         */
        const val GET_BANNER_LIST = "$BASE_URL/v3/adPic/getLimit"
        /**
         * 获取首页消息红点
         */
        const val GET_MESSAGE_TIPS = "$BASE_URL/v3/member/queryMessage"
        /**
         * 获取首页回收黄金总量
         */
        const val GET_RECYCLE_GOLD = "$BASE_URL/v3/recycleOrder/totalWeight"

        /**
         * 查询金价
         */
        const val GET_GOLD_PRICE = "$BASE_URL/v3/price/queryGoldPrice"

        /**
         * 获取资讯
         */
        const val GET_NEW_INFORMATION = "$BASE_URL/v3/content/queryNewsPager"

        /**
         * 获取附近店铺
         */
        const val GET_NEARBY_SHOP = "$BASE_URL/v3/recycleOrder/personal/store_list"

        /**
         * 获取通知
         */
        const val GET_NOTICE = "$BASE_URL/v3/member/queryPageMsg"

        /**
         * 首页黄金回收信息
         */
        const val GET_GOLD_NEW_ODER = "$BASE_URL/v3/recycleOrder/newOrder"
    }

}

