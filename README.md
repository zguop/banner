# Android轮播控件

ViewPager无限轮播功能。可以自定义indicator，需自定义实现 **Indicator** 接口，内置了圆形的IndicatorView，支持三种动画切换。
无缝衔接[MagicIndicator](https://github.com/hackware1993/MagicIndicator)大神的Indicator，打造花样Indicator，集成使用请参考demo。
项目参考了[banner](https://github.com/youth5201314/banner)轮播思想。

* banner支持一屏三页
* 支持自定义Indicator
* 支持自定义view
* 支持数据刷新
* 解决下拉刷新等滑动冲突问题，如嵌套SwipeRefreshLayout
* 解决多次重复回调onPageSelected问题
* 更多优化请参考代码实现。

## 效果图

##### 基本使用的功能，请下载demo体验
![tu1](gif/tu1.jpg)

##### 使用了MagicIndicator
![效果示例](gif/tu2.gif)


## 使用步骤

#### Step 1.依赖banner
Gradle 
```groovy
dependencies{
    implementation 'com.to.aboomy:banner:3.0.4'  //最新版本
}
```
或者引用本地lib
```groovy
compile project(':banner')
```


#### Step 2.xml
```xml
    <com.to.aboomy.banner.Banner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="250dp"/>
```

#### Step 3.自定义HolderCreator
```java
//实现HolderCreator接口
public interface HolderCreator {
    View createView(final Context context,final int index, Object o);
}

//举个栗子
public class ImageHolderCreator implements HolderCreator {
    @Override
    public View createView(final Context context, final int index, Object o) {
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(iv).load(o).into(iv);
        //内部实现点击事件
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, index + "", Toast.LENGTH_LONG).show();
            }
        });
        return iv;
    }
}
```

#### Step 4.在页面中使用Banner

```java

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = findViewById(R.id.banner);
        //使用内置Indicator
        IndicatorView qyIndicator = new IndicatorView(this)
              .setIndicatorColor(Color.DKGRAY)
              .setIndicatorSelectorColor(Color.WHITE);
        banner.setIndicator(qyIndicator)
              .setHolderCreator(new ImageHolderCreator())
              .setPages(list);
    }
```

### 如何自定义Indicator
```java
   //实现Indicator接口
/**
 * 可以实现该接口，自定义Indicator 可参考内置的{@link IndicatorView}
 */
public interface Indicator extends ViewPager.OnPageChangeListener {

    /**
     * 当数据初始化完成时调用
     *
     * @param pagerCount pager数量
     */
    void initIndicatorCount(int pagerCount);

    /**
     * 返回一个View，添加到banner中
     */
    View getView();

    /**
     * banner是一个RelativeLayout，设置banner在RelativeLayout中的位置，可以是任何地方
     */
    RelativeLayout.LayoutParams getParams();
}

//举个栗子
public class IndicatorView extends View implements Indicator{
       
        @Override
        public void initIndicatorCount(int pagerCount) {
            this.pagerCount = pagerCount;
            setVisibility(pagerCount > 1 ? VISIBLE : GONE);
            requestLayout();
        }
    
        @Override
        public View getView() {
            return this;
        }
         /**
          * 控制Indicator在Banner中的位置，开发者自行实现
          */
        @Override
        public RelativeLayout.LayoutParams getParams() {
            if (params == null) {
                params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.bottomMargin = dip2px(10);
            }
            return params;
        }
        /**
          * banner切换时同步回调的三个方法
          */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            selectedPage = position;
            offset = positionOffset;
            invalidate();
        }
        
        @Override
        public void onPageSelected(int position) {
        }
        
        @Override
        public void onPageScrollStateChanged(int state) {
        }
}

```
### Banner提供的方法介绍，banner未提供任何自定义属性
 |方法名|描述
 |---|---|
 |setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer)|设置viewpager的自定义动画
 |setOuterPageChangeListener(ViewPager.OnPageChangeListener outerPageChangeListener)|设置viewpager的滑动监听
 |setAutoTurningTime(long autoTurningTime)|设置自动轮播时长
 |setPagerScrollDuration(int pagerScrollDuration)|设置viewpager的切换时长
 |setAutoPlay(boolean autoPlay)|设置是否自动轮播，大于1页可以轮播
 |setIndicator(Indicator indicator)|设置indicator
 |HolderCreator(HolderCreator holderCreator))|设置view创建接口
 |setPages(List<?> items)|加载数据，此方法时开始轮播的方法，请再最后调用
 |setPages(List<?> items, int startPosition)|重载方法，设置轮播的起始位置
 |boolean isAutoPlay()|是否无限轮播
 |int getCurrentPager()|获取viewPager位置
 |void startTurning()|开始轮播
 |void stopTurning()|停止轮播

### 内置IndicatorView使用方法介绍，一般都是通过new关键字创建，设置到banner中，因此也没有提供任何自定义属性
 |方法名|描述
 |---|---|
 |setIndicatorRadius(float indicatorRadius)|设置圆点半径|
 |setIndicatorSpacing(float indicatorSpacing)|设置圆点间距|
 |setIndicatorStyle(@IndicatorStyle int indicatorStyle)|设置圆点切换动画，内置三种切换动画，请参考demo|
 |setIndicatorColor(@ColorInt int indicatorColor)|设置默认的圆点颜色|
 |setIndicatorSelectorColor(@ColorInt int indicatorSelectorColor) |设置选中的圆点颜色|
 |setParams(RelativeLayout.LayoutParams params) |设置IndicatorView在banner中的位置，默认底部居中，距离底部20dp，请参考demo|
 
### 感谢
[banner](https://github.com/youth5201314/banner) banner的思想非常棒，参考banner实现


###总结
-
xiexie ni de guāng gù ！ 喜欢的朋友轻轻右上角赏个star，您的鼓励会给我持续更新的动力。


