# Android轮播控件
[ ![Download](https://api.bintray.com/packages/a13706649811/maven/banner/images/download.svg) ](https://bintray.com/a13706649811/maven/banner/_latestVersion)

ViewPager无限轮播功能。可以自定义indicator，需自定义实现 **Indicator** 接口，内置了圆形的IndicatorView，支持三种动画切换。
无缝衔接[MagicIndicator](https://github.com/hackware1993/MagicIndicator)大神的Indicator，打造花样Indicator，集成使用请参考Sample。

![logo](gif/logo.png)

* 支持一屏三页
* 支持魅族效果
* 支持自定义Indicator
* 支持自定义view
* 支持数据刷新
* 解决下拉刷新等滑动冲突问题，如嵌套SwipeRefreshLayout
* 解决多次重复回调onPageSelected问题
* 良好的代码封装，更多优化请参考代码实现。


## 效果图

**点击下载 [banner.apk](https://fir.im/r7le) 体验**

![qrcode](data:image/png;base64,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)

------

|描述|图片|
|---|---|
|**基本使用的功能，请下载apk体验更流畅**|![tu1](gif/tu1.png)|

|描述|普通样式|两边缩放|魅族样式|
|---|---|---|---|
|**一屏三页**|![https://img-blog.csdnimg.cn/20200102193308276.gif](https://img-blog.csdnimg.cn/20200102193308276.gif)|![https://img-blog.csdnimg.cn/20200102193350824.gif](https://img-blog.csdnimg.cn/20200102193350824.gif)|![https://img-blog.csdnimg.cn/20200102193414824.gif](https://img-blog.csdnimg.cn/20200102193414824.gif)|

|效果图|1|2|
|---|---|---|
|**Indicator查看simple代码** |![https://img-blog.csdnimg.cn/20200102194459852.gif](https://img-blog.csdnimg.cn/20200102194459852.gif)|![https://img-blog.csdnimg.cn/20200102194542140.gif](https://img-blog.csdnimg.cn/20200102194542140.gif)|
|![https://img-blog.csdnimg.cn/20200102194843393.gif](https://img-blog.csdnimg.cn/20200102194843393.gif)|![https://img-blog.csdnimg.cn/20200102195247747.gif](https://img-blog.csdnimg.cn/20200102195247747.gif)|![https://img-blog.csdnimg.cn/202001021953167.gif](https://img-blog.csdnimg.cn/202001021953167.gif)|

## 使用步骤

#### Step 1.依赖banner
Gradle 
```groovy
dependencies{
    implementation 'com.waitou:banner:3.0.4'  //最新版本
}
```
或者引用本地lib
```groovyImageUtil
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
    View createView(Context context,final int index, Object o);
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

### 简单设置一屏三页效果
```java

//设置左右页面露出来的宽度及item与item之间的宽度
.setPageMargin(UIUtil.dip2px(this, 20), UIUtil.dip2px(this, 10))
//内置ScaleInTransformer，设置切换缩放动画
.setPageTransformer(true, new ScaleInTransformer())
    
```

### 关于ViewPager切换动画

Sample中集成了以下两个ViewPager切换动画，请运行Sample查看动画效果，参考需要的ViewPagerTransform放到项目中，或者根据需求进行自定义。

[ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)

[MagicViewPager](https://github.com/hongyangAndroid/MagicViewPager)

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
 |setIndicator(Indicator indicator, boolean attachToRoot)|设置indicator
 |HolderCreator(HolderCreator holderCreator))|设置view创建接口
 |setPages(List<?> items)|加载数据，此方法时开始轮播的方法，请再最后调用
 |setPages(List<?> items, int startPosition)|重载方法，设置轮播的起始位置
 |isAutoPlay()|是否无限轮播
 |getCurrentPager()|获取viewPager位置
 |startTurning()|开始轮播
 |stopTurning()|停止轮播
 |setPageMargin(int multiWidth, int pageMargin)|设置一屏多页
 |setPageMargin(int leftWidth, int rightWidth, int pageMargin)|设置一屏多页,方法重载

### 内置IndicatorView使用方法介绍，没有提供任何自定义属性
 |方法名|描述
 |---|---|
 |setIndicatorRadius(float indicatorRadius)|设置圆点半径|
 |setIndicatorSpacing(float indicatorSpacing)|设置圆点间距|
 |setIndicatorStyle(@IndicatorStyle int indicatorStyle)|设置圆点切换动画，内置三种切换动画，请参考Sample|
 |setIndicatorColor(@ColorInt int indicatorColor)|设置默认的圆点颜色|
 |setIndicatorSelectorColor(@ColorInt int indicatorSelectorColor) |设置选中的圆点颜色|
 |setParams(RelativeLayout.LayoutParams params) |设置IndicatorView在banner中的位置，默认底部居中，距离底部20dp，请参考Sample|
 
### 感谢
项目参考了[banner](https://github.com/youth5201314/banner) count+2的轮播思想。

### 总结
-
xiexie ni de guāng gù ！ 喜欢的朋友轻轻右上角赏个star，您的鼓励会给我持续更新的动力。



