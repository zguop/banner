package com.to.aboomy.bannersample.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.FragmentActivity;

import com.to.aboomy.bannersample.R;


/**
 * auth aboom
 * date 2019-11-22
 */
public class InnerDialog extends Dialog {

    protected int animStyle;
    protected int width = WindowManager.LayoutParams.WRAP_CONTENT;//宽度
    protected int height = WindowManager.LayoutParams.WRAP_CONTENT;//高度
    protected int gravity = Gravity.CENTER;
    protected float dimAmount = 0.5f;
    protected long minShowTime;


    public InnerDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = width;
            attributes.height = height;
            attributes.gravity = gravity;
            attributes.dimAmount = dimAmount;
            if (animStyle == 0) {
                if (gravity == Gravity.BOTTOM) {
                    animStyle = R.style.dl_anim_slide_share_from_bottom;
                }
            }
            window.setWindowAnimations(animStyle);
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    /**
     * 按下返回键不可关闭 false
     */
    public InnerDialog setOutCancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    /**
     * 屏幕外不可关闭 false
     */
    public InnerDialog setOutTouchOutside(boolean canceledOnTouchOutside) {
        super.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return this;
    }

    /**
     * 设置view的对接
     *
     * @param gravity {@link Gravity}
     */
    public InnerDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * 设置view宽
     *
     * @param width WindowManager.LayoutParams.WRAP_CONTENT  默认
     *              WindowManager.LayoutParams.MATCH_PARENT 可以传入
     *              60 固定值
     */
    public InnerDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * 设置view高
     *
     * @param height WindowManager.LayoutParams.WRAP_CONTENT  默认
     *               WindowManager.LayoutParams.MATCH_PARENT 可以传入
     *               60 固定值
     */
    public InnerDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * 设置
     * 调节灰色背景透明度[0-1]
     */
    public InnerDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    /**
     * 设置动画
     *
     * @param animStyle style主题默认 {@link values/styles.xml:25}
     */
    public InnerDialog setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    /**
     * 设置dialog最少显示时间
     *
     * @param minShowTime 毫秒
     */
    public InnerDialog setMinShowTime(long minShowTime) {
        this.minShowTime = minShowTime;
        return this;
    }

    @Override
    public void show() {
        if (activityIsRunning(getContext())) {
            super.show();
        }
    }

    /**
     * 是否可以显示Dialog
     *
     * @param context context
     * @return true 可以展示，false不能展示
     */
    private boolean activityIsRunning(final Context context) {
        Activity activity = scanForActivity(context);
        if (activity.isFinishing()) {
            return false;
        }
        if (activity.isDestroyed()) {
            return false;
        }
        if (context instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) context;
            return !fragmentActivity.getSupportFragmentManager().isDestroyed();
        }
        return true;
    }

    /**
     * 返回activity
     */
    private Activity scanForActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }


}
