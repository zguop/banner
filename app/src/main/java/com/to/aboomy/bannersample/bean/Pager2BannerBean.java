package com.to.aboomy.bannersample.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * auth aboom
 * date 2019-12-28
 */
public class Pager2BannerBean implements MultiItemEntity {

    public List<Object> urls;

    @Override
    public int getItemType() {
        return 3;
    }
}
