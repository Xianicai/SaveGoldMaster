package com.savegoldmaster.home.model.bean;

import com.savegoldmaster.base.BaseBean;

import java.util.List;

public class BannerBean extends BaseBean {

    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    @Override
    public boolean isSuccess() {
        return getCode() == 100;
    }

    public static class ContentBean {
        /**
         * adStatus : 0
         * createBy : admin
         * createTime : 2018-05-09 11:29:58
         * deleted : 0
         * hrefUrl : http://test.activity.au32.cn/lottery
         * id : ff808081633fdd9a016342f2cc0b000b
         * imgUrl : https://au32-cjt-p-test.oss-cn-beijing.aliyuncs.com/20189/19-145448-1ywtCC.png
         * name : 中秋抽奖活动
         * position : 1
         * sortNo : 1
         * updateBy : platform
         * updateTime : 2018-09-19 19:51:06
         */

        private int adStatus;
        private String createBy;
        private String createTime;
        private int deleted;
        private String hrefUrl;
        private String id;
        private String imgUrl;
        private String name;
        private int position;
        private int sortNo;
        private String updateBy;
        private String updateTime;

        public int getAdStatus() {
            return adStatus;
        }

        public void setAdStatus(int adStatus) {
            this.adStatus = adStatus;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getHrefUrl() {
            return hrefUrl;
        }

        public void setHrefUrl(String hrefUrl) {
            this.hrefUrl = hrefUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
