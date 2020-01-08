package com.to.aboomy.bannersample.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * auth aboom
 * date 2019-12-26
 */
public class BannerBean implements MultiItemEntity {

    public List<Integer> urls;

    @Override
    public int getItemType() {
        return 1;
    }
}
