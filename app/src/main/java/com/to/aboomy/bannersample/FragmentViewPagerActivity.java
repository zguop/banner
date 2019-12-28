package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import com.to.aboomy.bannersample.fragment.Test1Fragment;
import com.to.aboomy.bannersample.fragment.Test2Fragment;
import com.to.aboomy.bannersample.fragment.Test3Fragment;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

/**
 * auth aboom
 * date 2019-12-28
 */
public class FragmentViewPagerActivity extends AppCompatActivity {

    public static final int TAB_TEST1 = R.id.test1;
    public static final int TAB_TEST2 = R.id.test2;
    public static final int TAB_TEST3 = R.id.test3;

    private SparseArray<View> tabView = new SparseArray<>();
    private View selectedButton;
    private int currentTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        setContentView(R.layout.activity_fvp);
        binding();
        clickTab(tabView.get(TAB_TEST1));
    }


    private void clickTab(View view) {
        if (selectedButton != null) {
            selectedButton.setSelected(Boolean.FALSE);
        }
        selectedButton = view;
        selectedButton.setSelected(Boolean.TRUE);
        selectFragment(view.getId());
    }

    private void binding() {
        tabView.put(TAB_TEST1, findViewById(TAB_TEST1));
        tabView.put(TAB_TEST2, findViewById(TAB_TEST2));
        tabView.put(TAB_TEST3, findViewById(TAB_TEST3));
        for (int i = 0; i < tabView.size(); i++) {
            tabView.get(tabView.keyAt(i)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTab(v);
                }
            });
        }
    }

    private void selectFragment(int tag) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (tag == currentTag) {
            return;
        }
        Fragment currentFragment = supportFragmentManager.findFragmentByTag(String.valueOf(currentTag));
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        Fragment fragment = supportFragmentManager.findFragmentByTag(String.valueOf(tag));
        if (fragment == null) {
            transaction.add(R.id.main_content, getFragment(tag), String.valueOf(tag));
        } else {
            transaction.show(fragment);
        }
        currentTag = tag;
        transaction.commitAllowingStateLoss();
    }

    @NonNull
    private Fragment getFragment(int tag) {
        switch (tag) {
            case TAB_TEST1:
                return new Test1Fragment();
            case TAB_TEST2:
                return new Test2Fragment();
            case TAB_TEST3:
                return new Test3Fragment();
        }
        return new Test1Fragment();
    }
}
