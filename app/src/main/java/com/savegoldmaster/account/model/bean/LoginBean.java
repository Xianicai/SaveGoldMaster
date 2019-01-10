package com.savegoldmaster.account.model.bean;

import com.savegoldmaster.base.BaseBean;

public class LoginBean extends BaseBean {
    /**
     * content : {"token":"e36df360a3ea4aafb6405fa84f166b51","userId":"ff80808165f77df60165f9da906b003a"}
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * token : e36df360a3ea4aafb6405fa84f166b51
         * userId : ff80808165f77df60165f9da906b003a
         */

        private String token;
        private String userId;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
