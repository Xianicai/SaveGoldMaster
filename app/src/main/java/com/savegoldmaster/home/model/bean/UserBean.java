package com.savegoldmaster.home.model.bean;

import com.savegoldmaster.base.BaseBean;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

public class UserBean extends BaseBean {

    private long id;
    /**
     * content : {"cashBalance":0,"cashBalanceStr":"0.00","cashFrozenBalanceStr":"0","cashNoBalance":0,"cashNoBalanceStr":"0.0000","demandGoldBalance":0,"demandGoldBalanceStr":"0.0000","fixedGoldBalance":0,"fixedGoldBalanceStr":"0.0000","goldBalance":0,"goldBalanceStr":"0.0000","goldMoney":0,"goldMoneyStr":"0.0000","isHandheldIDphoto":0,"realnamed":0,"telephone":"17600567656","userName":"176****7656"}
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
         * cashBalance : 0.0
         * cashBalanceStr : 0.00
         * cashFrozenBalanceStr : 0
         * cashNoBalance : 0.0
         * cashNoBalanceStr : 0.0000
         * demandGoldBalance : 0.0
         * demandGoldBalanceStr : 0.0000
         * fixedGoldBalance : 0.0
         * fixedGoldBalanceStr : 0.0000
         * goldBalance : 0.0
         * goldBalanceStr : 0.0000
         * goldMoney : 0.0
         * goldMoneyStr : 0.0000
         * isHandheldIDphoto : 0
         * realnamed : 0
         * telephone : 17600567656
         * userName : 176****7656
         */

        private double cashBalance;
        private String cashBalanceStr;
        private String cashFrozenBalanceStr;
        private double cashNoBalance;
        private String cashNoBalanceStr;
        private double demandGoldBalance;
        private String demandGoldBalanceStr;
        private double fixedGoldBalance;
        private String fixedGoldBalanceStr;
        private double goldBalance;
        private String goldBalanceStr;
        private double goldMoney;
        private String goldMoneyStr;
        private int isHandheldIDphoto;
        private int realnamed;
        private String telephone;
        @Index
        private String userName;

        public double getCashBalance() {
            return cashBalance;
        }

        public void setCashBalance(double cashBalance) {
            this.cashBalance = cashBalance;
        }

        public String getCashBalanceStr() {
            return cashBalanceStr;
        }

        public void setCashBalanceStr(String cashBalanceStr) {
            this.cashBalanceStr = cashBalanceStr;
        }

        public String getCashFrozenBalanceStr() {
            return cashFrozenBalanceStr;
        }

        public void setCashFrozenBalanceStr(String cashFrozenBalanceStr) {
            this.cashFrozenBalanceStr = cashFrozenBalanceStr;
        }

        public double getCashNoBalance() {
            return cashNoBalance;
        }

        public void setCashNoBalance(double cashNoBalance) {
            this.cashNoBalance = cashNoBalance;
        }

        public String getCashNoBalanceStr() {
            return cashNoBalanceStr;
        }

        public void setCashNoBalanceStr(String cashNoBalanceStr) {
            this.cashNoBalanceStr = cashNoBalanceStr;
        }

        public double getDemandGoldBalance() {
            return demandGoldBalance;
        }

        public void setDemandGoldBalance(double demandGoldBalance) {
            this.demandGoldBalance = demandGoldBalance;
        }

        public String getDemandGoldBalanceStr() {
            return demandGoldBalanceStr;
        }

        public void setDemandGoldBalanceStr(String demandGoldBalanceStr) {
            this.demandGoldBalanceStr = demandGoldBalanceStr;
        }

        public double getFixedGoldBalance() {
            return fixedGoldBalance;
        }

        public void setFixedGoldBalance(double fixedGoldBalance) {
            this.fixedGoldBalance = fixedGoldBalance;
        }

        public String getFixedGoldBalanceStr() {
            return fixedGoldBalanceStr;
        }

        public void setFixedGoldBalanceStr(String fixedGoldBalanceStr) {
            this.fixedGoldBalanceStr = fixedGoldBalanceStr;
        }

        public double getGoldBalance() {
            return goldBalance;
        }

        public void setGoldBalance(double goldBalance) {
            this.goldBalance = goldBalance;
        }

        public String getGoldBalanceStr() {
            return goldBalanceStr;
        }

        public void setGoldBalanceStr(String goldBalanceStr) {
            this.goldBalanceStr = goldBalanceStr;
        }

        public double getGoldMoney() {
            return goldMoney;
        }

        public void setGoldMoney(double goldMoney) {
            this.goldMoney = goldMoney;
        }

        public String getGoldMoneyStr() {
            return goldMoneyStr;
        }

        public void setGoldMoneyStr(String goldMoneyStr) {
            this.goldMoneyStr = goldMoneyStr;
        }

        public int getIsHandheldIDphoto() {
            return isHandheldIDphoto;
        }

        public void setIsHandheldIDphoto(int isHandheldIDphoto) {
            this.isHandheldIDphoto = isHandheldIDphoto;
        }

        public int getRealnamed() {
            return realnamed;
        }

        public void setRealnamed(int realnamed) {
            this.realnamed = realnamed;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
