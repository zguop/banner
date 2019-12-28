package com.to.aboomy.bannersample.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BasePagerFragmentAdapter extends FragmentPagerAdapter {
    public List<Fragment> fragmentList = new ArrayList<>();

    public BasePagerFragmentAdapter(FragmentManager fm, Fragment... fragment) {
        super(fm);
        Collections.addAll(fragmentList, fragment);
    }

    public BasePagerFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList.addAll(fragmentList);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
