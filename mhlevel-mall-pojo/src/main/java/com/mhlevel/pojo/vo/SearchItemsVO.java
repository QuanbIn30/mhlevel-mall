package com.mhlevel.pojo.vo;

/**
 * 用于展示商品搜索结果的VO
 * @author quanbin
 * @date 2021-03-22
 */
public class SearchItemsVO {

    private String itemId;

    private String itemName;

    private String itemImgUrl;

    private Integer sellCount;

    private Integer price; //涉及到金额不是应该用BigDecimal类型吗？

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImgUrl() {
        return itemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl) {
        this.itemImgUrl = itemImgUrl;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
