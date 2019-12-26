package com.to.aboomy.bannersample.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * auth aboom
 * date 2019-12-26
 */
public class TextBean implements MultiItemEntity {

    public String text;

    @Override
    public int getItemType() {
        return 2;
    }
}
