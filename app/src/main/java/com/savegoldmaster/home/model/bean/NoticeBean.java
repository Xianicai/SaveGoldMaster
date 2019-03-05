package com.savegoldmaster.home.model.bean;

import com.savegoldmaster.base.BaseBean;

public class NoticeBean extends BaseBean {

    /**
     * content : {"count":0,"id":"ff80808167f436d70167f436d7760000","title":"元旦快乐"}
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    @Override
    public boolean isSuccess() {
        return getCode() == 100;
    }

    public static class ContentBean {
        /**
         * count : 0
         * id : ff80808167f436d70167f436d7760000
         * title : 元旦快乐
         */

        private int count;
        private String id;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
