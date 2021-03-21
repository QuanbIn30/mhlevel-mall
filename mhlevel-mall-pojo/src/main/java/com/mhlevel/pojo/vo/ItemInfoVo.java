package com.mhlevel.pojo.vo;

import com.mhlevel.pojo.Items;
import com.mhlevel.pojo.ItemsImg;
import com.mhlevel.pojo.ItemsParam;
import com.mhlevel.pojo.ItemsSpec;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-21
 */
public class ItemInfoVo {

    private Items items;

    private List<ItemsImg> itemsImgList;

    private List<ItemsSpec> itemsSpecList;

    private ItemsParam itemsParam;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<ItemsImg> getItemsImgList() {
        return itemsImgList;
    }

    public void setItemsImgList(List<ItemsImg> itemsImgList) {
        this.itemsImgList = itemsImgList;
    }

    public List<ItemsSpec> getItemsSpecList() {
        return itemsSpecList;
    }

    public void setItemsSpecList(List<ItemsSpec> itemsSpecList) {
        this.itemsSpecList = itemsSpecList;
    }

    public ItemsParam getItemsParam() {
        return itemsParam;
    }

    public void setItemsParam(ItemsParam itemsParam) {
        this.itemsParam = itemsParam;
    }
}
