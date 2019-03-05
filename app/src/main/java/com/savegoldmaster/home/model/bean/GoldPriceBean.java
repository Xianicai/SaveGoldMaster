package com.savegoldmaster.home.model.bean;

import com.savegoldmaster.base.BaseBean;

public class GoldPriceBean extends BaseBean {

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

        private String goldPrice ="0.00";
        private double price = 0.00;
        private String priceTime;

        public String getGoldPrice() {
            return goldPrice;
        }

        public void setGoldPrice(String goldPrice) {
            this.goldPrice = goldPrice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPriceTime() {
            return priceTime;
        }

        public void setPriceTime(String priceTime) {
            this.priceTime = priceTime;
        }
    }
}
