package com.to.aboomy.bannersample.util;

import com.to.aboomy.bannersample.R;

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

    public static int getRandomImage(){
        return IMAGES[getRandom()];
    }

    public static int getRandom() {
        return new Random().nextInt(IMAGES.length);
    }

}
