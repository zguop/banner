package com.to.aboomy.bannersample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.adapter.FragmentBannerAdapter;
import com.to.aboomy.bannersample.bean.BannerBean;
import com.to.aboomy.bannersample.bean.TextBean;
import com.to.aboomy.bannersample.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * auth aboom
 * date 2019-12-28
 */
public class ViewPagerFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentBannerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test1_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        adapter = new FragmentBannerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        loadData();

    }

    private void loadData() {
        List<MultiItemEntity> list = new ArrayList<>();
        BannerBean bannerBean = new BannerBean();
        bannerBean.urls = new ArrayList<>();
        int count = new Random().nextInt(4) + 1;
        for (int i = 0; i < count; i++) {
            bannerBean.urls.add(Utils.getRandomImage());
        }
        list.add(bannerBean);

        for (int i = 0; i < 200; i++) {
            TextBean textBean = new TextBean();
            textBean.text = "--- " + i;
            list.add(textBean);
        }
        adapter.replaceData(list);
        swipeRefreshLayout.setRefreshing(false);
    }
}
