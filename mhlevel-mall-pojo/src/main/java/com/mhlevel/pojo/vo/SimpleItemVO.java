package com.mhlevel.pojo.vo;

/**
 * 最新商品的简单数据类型
 * @author quanbin
 * @date 2021-03-20
 */
public class SimpleItemVO {

    private String itemId;

    private String itemName;

    private String itemUrl;

    public String getItemid() {
        return itemId;
    }

    public void setItemid(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
