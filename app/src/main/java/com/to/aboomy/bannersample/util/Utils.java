package com.to.aboomy.bannersample.util;

import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * auth aboom
 * date 2019-12-26
 */
public class Utils {
    private static final int[] IMAGES = {
            R.mipmap.image1,
            R.mipmap.image2,
            R.mipmap.image3,
            R.mipmap.image4,
            R.mipmap.image5,
    };

    public static List<Integer> getImage(int count) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(IMAGES[i]);
        }
        return list;
    }

    public static int getRandomImage() {
        return IMAGES[new Random().nextInt(IMAGES.length)];
    }


    /**
     * -----------------------------------------------------------------------
     */

    private static final String[] URLS = {
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2860421298,3956393162&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=163638141,898531478&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1028426622,4209712325&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1462142898,440466184&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3210855908,3095539181&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2080505558,2205047574&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2894881224,342594760&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2894881224,342594760&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3162346827,2000964752&fm=26&gp=0.jpg"
    };

    public static String getRandom() {
        return URLS[new Random().nextInt(URLS.length)];
    }

    public static List<String> getData(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandom());
        }
        return list;
    }










    public static List<VideoBean> getVideoList() {
        List<VideoBean> videoList = new ArrayList<>();
        videoList.add(new VideoBean(
                "你欠缺的也许并不是能力",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/b/a/c36e048e284c459686133e66a79e2eba.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2016/06/22/SBP8G92E3_hd.mp4"));

        videoList.add(new VideoBean(
                "坚持与放弃",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/0/4/e4c8836bfe154d76a808da38d0733304.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2015/08/27/SB13F5AGJ_sd.mp4"));

        videoList.add(new VideoBean(
                "不想从被子里出来",
                "http://open-image.nosdn.127.net/57baaaeaad4e4fda8bdaceafdb9d45c2.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2018/01/12/SD70VQJ74_sd.mp4"));

        videoList.add(new VideoBean(
                "不耐烦的中国人?",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/e/9/ac655948c705413b8a63a7aaefd4cde9.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2017/05/31/SCKR8V6E9_hd.mp4"));

        videoList.add(new VideoBean(
                "神奇的珊瑚",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/e/4/75bc6c5227314e63bbfd5d9f0c5c28e4.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2016/01/11/SBC46Q9DV_hd.mp4"));

        videoList.add(new VideoBean(
                "怎样经营你的人脉",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2018/3/b/c/9d451a2da3cf42b0a049ba3e249222bc.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2018/04/19/SDEQS1GO6_hd.mp4"));

        videoList.add(new VideoBean(
                "怎么才能不畏将来",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2018/1/c/8/1aec3637270f465faae52713a7c191c8.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2018/01/25/SD82Q0AQE_hd.mp4"));

        videoList.add(new VideoBean(
                "音乐和艺术如何改变世界",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2017/12/2/8/f30dd5f2f09c405c98e7eb6c06c89928.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2017/12/04/SD3SUEFFQ_hd.mp4"));

        return videoList;
    }



}
