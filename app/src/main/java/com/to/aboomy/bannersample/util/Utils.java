package com.to.aboomy.bannersample.util;

import java.util.Random;

/**
 * auth aboom
 * date 2019-12-26
 */
public class Utils {

    private static final String[] URLS = {
            "http://img5.imgtn.bdimg.com/it/u=3048559810,1005075937&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3612267661,1332194385&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2076697566,2577708113&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2033203008,2694922586&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=276131620,3000139441&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3534759995,3613929034&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1506632564,2980082929&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2133257459,2103097271&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2975979162,3218013441&fm=26&gp=0.jpg"
    };

    public static String getRandom() {
        return URLS[getRandomNum()];
    }


    public static int getRandomNum() {
        return new Random().nextInt(URLS.length);
    }
}
