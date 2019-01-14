package com.savegoldmaster.home.model.bean;

import com.savegoldmaster.base.BaseBean;

import java.util.List;

public class NearbyShopBean extends BaseBean {


    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * address : 北京市西城区北京市西城区宣武门外大街8号庄胜崇光百货F1
         * star : 91
         * distance : 1.6
         * name : 谢瑞麟(庄胜崇光店)
         * id : ff8080816686d0c301669b0c70fc778b
         * label : 1,2,3,4
         * url : https://au32-cjt-m-test.oss-cn-beijing.aliyuncs.com/common/20180905170735.png
         * status : 0
         * labels : ["回购","提金","换购","维保"]
         */

        private String address;
        private int star;
        private double distance;
        private String name;
        private String id;
        private String label;
        private String url;
        private int status;
        private List<String> labels;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }
    }
}
