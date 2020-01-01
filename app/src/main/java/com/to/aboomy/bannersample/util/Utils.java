package com.to.aboomy.bannersample.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * auth aboom
 * date 2019-12-26
 */
public class Utils {

    private static final String[] URLS = {
            "http://img5.imgtn.bdimg.com/it/u=276131620,3000139441&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3534759995,3613929034&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1506632564,2980082929&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2133257459,2103097271&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2975979162,3218013441&fm=26&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2849768792,1404089788&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=343743878,3804103777&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1875309042,500962011&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4238546330,3195362340&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1974510195,116291621&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=4189682572,4119487013&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1210911314,1867897320&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1299782562,2908748759&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=4104594815,3335181457&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1689047991,1656592824&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3133382899,2012591850&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1675850086,4034187200&fm=26&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1920803651,3235300619&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3021762161,2246446132&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2605165407,1234035421&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3539084977,1865112840&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1301065023,3462660787&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3343297221,224868939&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2305981512,2625270308&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3284858771,3534795421&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3142289829,63606089&fm=15&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2234461382,2285958805&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1082215124,1790186681&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2586323878,4267842114&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=432673539,832595310&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1403755695,3296737054&fm=15&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4261713652,4274801293&fm=15&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3148954939,3238754177&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1840912551,3120068487&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3915808603,1817200595&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1825501382,2987630063&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1971015771,3493830767&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=545102745,2368846584&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2761197207,1334590443&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3923822977,3958712703&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2757075225,359744642&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=783876970,1366818293&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1528081624,1130428080&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=740851866,1042794795&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1113122074,2846395022&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1100832054,3371623074&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2582888397,2291058569&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=119941966,571068300&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3778448021,1647731767&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3770070369,1161970080&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3046594921,4183816252&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3297027497,2148965799&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=358148953,1349188010&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3643993953,2455730845&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1434438981,2552167254&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3933664680,3988845807&fm=26&gp=0.jpg"

    };

    public static String getRandom() {
        return URLS[getRandomNum()];
    }


    public static int getRandomNum() {
        return new Random().nextInt(URLS.length);
    }

    public static List<String> getData(int size){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandom());
        }
        return list;
    }
}
