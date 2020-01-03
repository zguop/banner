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

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.adapter.Test1ChildAdapter;
import com.to.aboomy.bannersample.bean.BannerBean;
import com.to.aboomy.bannersample.bean.TextBean;
import com.to.aboomy.bannersample.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2019-12-28
 */
public class Test1ChildFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test1_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.list);

        final Test1ChildAdapter adapter = new Test1ChildAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        List<MultiItemEntity> list = new ArrayList<>();


        BannerBean bannerBean = new BannerBean();
        bannerBean.urls = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            bannerBean.urls.add(Utils.getRandom());
        }
        list.add(bannerBean);

        for (int i = 0; i < 200; i++) {
            TextBean textBean = new TextBean();
            textBean.text = "--- " + i;
            list.add(textBean);
        }
        adapter.addData(list);


    }
}
