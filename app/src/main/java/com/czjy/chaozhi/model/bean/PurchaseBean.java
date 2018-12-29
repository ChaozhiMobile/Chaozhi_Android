package com.czjy.chaozhi.model.bean;

import java.util.List;

public class PurchaseBean {

    /**
     * is_purchase : 1
     * chat : [{"product_id":18,"product_name":"【行政管理专科】零基础特招班","chat_url":"http://kf-dev.chaozhiedu.com:88/index/index/home?visiter_id=36741&visiter_name=%E5%A4%A7%E4%BE%A0&avatar=&business_id=admin&groupid=0&cle_info=eyJuYW1lIjoiXHU1OTI3XHU0ZmEwIiwiY2xlX251bSI6MzY3NDEsInRlbCI6IjE4NjU3NDY3MTkwIiwiY3R5cGUiOiJcdTMwMTBcdTg4NGNcdTY1M2ZcdTdiYTFcdTc0MDZcdTRlMTNcdTc5ZDFcdTMwMTFcdTk2ZjZcdTU3ZmFcdTc4NDBcdTcyNzlcdTYyZGJcdTczZWQifQ%3D%3D"}]
     */

    private int is_purchase;
    private List<ChatBean> chat;

    public int getIs_purchase() {
        return is_purchase;
    }

    public void setIs_purchase(int is_purchase) {
        this.is_purchase = is_purchase;
    }

    public List<ChatBean> getChat() {
        return chat;
    }

    public void setChat(List<ChatBean> chat) {
        this.chat = chat;
    }

    public static class ChatBean {
        /**
         * product_id : 18
         * product_name : 【行政管理专科】零基础特招班
         * chat_url : http://kf-dev.chaozhiedu.com:88/index/index/home?visiter_id=36741&visiter_name=%E5%A4%A7%E4%BE%A0&avatar=&business_id=admin&groupid=0&cle_info=eyJuYW1lIjoiXHU1OTI3XHU0ZmEwIiwiY2xlX251bSI6MzY3NDEsInRlbCI6IjE4NjU3NDY3MTkwIiwiY3R5cGUiOiJcdTMwMTBcdTg4NGNcdTY1M2ZcdTdiYTFcdTc0MDZcdTRlMTNcdTc5ZDFcdTMwMTFcdTk2ZjZcdTU3ZmFcdTc4NDBcdTcyNzlcdTYyZGJcdTczZWQifQ%3D%3D
         */

        private int product_id;
        private String product_name;
        private String chat_url;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getChat_url() {
            return chat_url;
        }

        public void setChat_url(String chat_url) {
            this.chat_url = chat_url;
        }
    }
}
