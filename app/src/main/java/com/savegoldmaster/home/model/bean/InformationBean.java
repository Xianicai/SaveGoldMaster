package com.savegoldmaster.home.model.bean;

import com.savegoldmaster.base.BaseBean;

import java.util.List;

public class InformationBean extends BaseBean {

    /**
     * content : {"count":true,"endRow":2,"list":[{"category":"对外输出","createTime":"2019-01-07 14:51:18.0","id":"2c9380886818645401682714721f0020","imageUrl":"","title":"测试对外输出"},{"category":"对外输出","createTime":"2019-01-02 18:29:11.0","id":"2c938088680df3c201680e1c2206000c","imageUrl":"https://au32-cjt-p-test.oss-cn-beijing.aliyuncs.com/2019/01/02/5e32a7c4-88d3-4ac9-beb1-9418e280f6b0-k71a-81544524480_.pic.jpg","title":"如何克服困意？"}],"pageNum":1,"pageSize":2,"pages":89,"startRow":0,"total":178}
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

        private boolean count;
        private int endRow;
        private int pageNum;
        private int pageSize;
        private int pages;
        private int startRow;
        private int total;
        private List<ListBean> list;

        public boolean isCount() {
            return count;
        }

        public void setCount(boolean count) {
            this.count = count;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * category : 对外输出
             * createTime : 2019-01-07 14:51:18.0
             * id : 2c9380886818645401682714721f0020
             * imageUrl :
             * title : 测试对外输出
             */

            private String category;
            private String createTime;
            private String id;
            private String imageUrl;
            private String title;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
