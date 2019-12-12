package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //    private String       url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557327833941&di=4be9015403fd966f1a32711ade360ea4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201710%2F01%2F20171001105830_wWQej.jpeg";
    private String url4 = "http://img5.imgtn.bdimg.com/it/u=3048559810,1005075937&fm=26&gp=0.jpg";
    private String url5 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";
    private String url6 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";
    private List<String> list;
    private Banner banner;

    private BannerAdapter bannerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
//        list.add(url2);
        list.add(url4);
//        list.add(url5);
        list.add(url6);

        banner = findViewById(R.id.banner);
        bannerAdpter = new BannerAdapter(list);
        IndicatorView qyIndicator = new IndicatorView(this)
                .setIndicatorColor(Color.BLACK)
                .setIndicatorInColor(Color.WHITE)
                .setGravity(Gravity.CENTER);
        banner.setIndicator(qyIndicator);


        banner.postDelayed(new Runnable() {
            @Override
            public void run() {
                banner.setAdapter(bannerAdpter);

            }
        }, 2000);


        List<Bean> list1 = new ArrayList<>();
        Bean bean = new Bean();
        bean.name = "1111";
        list1.add(bean);


        Bean bean1 = new Bean();
        bean1.name = "bean1";
        bean1.list = new ArrayList<>();
        list1.add(bean1);

        Bean bean2 = new Bean();
        bean2.name = "bean2";
        bean1.list.add(bean2);
        bean2.list = new ArrayList<>();


        Bean bean3 = new Bean();
        bean3.name = "bean3";
        bean2.list.add(bean3);

        Bean bean4 = new Bean();
        bean4.name = "bean4";
        bean2.list.add(bean4);


        f2(list1);

//        Log.e("aa" , jitemvoew.toString());

    }

    class Bean {
        public List<String> fu;
        public String name;
        public List<Bean> list;

        @Override
        public String toString() {
            return "Bean{" +
                    "fu=" + fu +
                    ", name='" + name + '\'' +
                    ", list=" + list +
                    '}';
        }
    }


    public List<Bean> jitemvoew = new ArrayList<>();

    public void fff(List<Bean> list, List<String> listStr) {
        for (Bean bean : list) {
            List<Bean> list1 = bean.list;
            if (list1 != null) {
                fff(list, listStr);
                listStr.add(bean.name);

            } else {
                jitemvoew.add(bean);
                return;
            }
            //赋值完
//            listStr.add();
            listStr.clear();
        }


        for (Bean bean : list) {

            List<Bean> list1 = bean.list;
            if (list.isEmpty()) {
                jitemvoew.add(bean);
                continue;
            }
            fff(list1, listStr);


            listStr.add(bean.name);

        }





    }

    public void f2(List<Bean> list) {
        for (Bean bean : list) {
            List<Bean> list1 = bean.list;
            if (list1 == null || list1.size() == 0) {
                bean.fu = new ArrayList<>();
                jitemvoew.add(bean);
                continue;
            }

            f2(list1);

            for (Bean bean1 : list1) {
                if(jitemvoew.contains(bean1)){
                    continue;
                }
                ArrayList<Bean> lists = new ArrayList<>();
                addParent(bean1,lists);
                for (Bean list2 : lists) {
                    list2.fu.add(bean1.name);
                }
            }
        }

    }



    private void addParent(Bean bean,List<Bean> lists){
        if(bean.fu != null){
            lists.add(bean);
            return ;
        }
        for (Bean bean1 : bean.list) {
            addParent(bean1,lists);
        }
    }
}
