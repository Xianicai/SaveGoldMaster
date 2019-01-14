package com.savegoldmaster.home.model.bean;

import com.savegoldmaster.base.BaseBean;

import java.util.List;

public class UserOderBean extends BaseBean {

    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * amount : 2710.75
         * createTime : 2019-01-11 14:52:44
         * username : 157****7739
         */

        private double amount;
        private String createTime;
        private String username;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
