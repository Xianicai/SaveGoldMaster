package com.savegoldmaster.home.model.bean;

import com.savegoldmaster.base.BaseBean;

public class RecyclerGoldBean extends BaseBean {

    /**
     * content : {"weight":17167.192900000002}
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
         * weight : 17167.192900000002
         */

        private double weight;

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }
}
