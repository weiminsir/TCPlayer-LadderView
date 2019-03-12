package com.tencent.liteav.demo.play.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/*** 状态栏工具类
 * Created by wuyang on 16/2/18.
 */
public class StatusBarUtils {

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusBarView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
            ViewGroup rootView = (ViewGroup) (content).getChildAt(content.getChildCount() - 1);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }


    public static void setStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 设置状态栏为全透明，只在4.4以上版本有效
     */
    public static void setTranslucentStatusBarPort(Activity activity) {

    }

    public static void setTranslucentStatusBarLand(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            final int visibilityG =
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                            // bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//weimin
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;//weimin

            window.getDecorView().setSystemUiVisibility(visibilityG);
//            window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//                @Override
//                public void onSystemUiVisibilityChange(int visibility) {
//                    if (visibilityG != visibility) {
//                        window.getDecorView().setSystemUiVisibility(visibilityG);
//                    }
//                }
//            });
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public static void hideNavKey(Context context) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = ((Activity) context).getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = ((Activity) context).getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void showNavKey(Context context, int systemUiVisibility) {//getWindow().getDecorView().getSystemUiVisibility() 传入0也可以
        ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }


    /**
     * 使状态栏透明
     * <p/>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

//    /**
//     * 为DrawerLayout 布局设置状态栏变色
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     * @param color        状态栏颜色值
//     */
//    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 生成一个状态栏大小的矩形
//            View statusBarView = createStatusBarView(activity, color);
//            // 添加 statusBarView 到布局中
//            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//            contentLayout.addView(statusBarView, 0);
//            // 内容布局不是 LinearLayout 时,设置padding top
//            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
//            }
//            // 设置属性
//            ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
//            drawerLayout.setFitsSystemWindows(false);
//            contentLayout.setFitsSystemWindows(false);
//            contentLayout.setClipToPadding(true);
//            drawer.setFitsSystemWindows(false);
//        }
//    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
//    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 设置状态栏透明
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 设置内容布局属性
//            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//            contentLayout.setFitsSystemWindows(true);
//            contentLayout.setClipToPadding(true);
//            // 设置抽屉布局属性
//            ViewGroup vg = (ViewGroup) drawerLayout.getChildAt(1);
//            vg.setFitsSystemWindows(false);
//            // 设置 DrawerLayout 属性
//            drawerLayout.setFitsSystemWindows(false);
//        }
//    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, int color) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            setTranslucent(activity);
        }
    }


    private void hideNavigationBar(Context context) {
        Activity activity = (Activity) context;
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showNavigationBar(Context context) {
        Activity activity = (Activity) context;
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void setStatusBarVisible(boolean show, Context context) {
        Activity activity = (Activity) context;
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    public static void setSystemUIVisible(Context context) {
        Activity activity = (Activity) context;

        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        uiFlags |= 0x00001000;
        activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }


    /**
     * 判断虚拟导航栏是否显示
     *
     * @param context 上下文对象
     * @param
     * @return true(显示虚拟导航栏)，false(不显示或不支持虚拟导航栏)
     */
    public static boolean checkNavigationBarShow(Context context) {

        Activity activity = (Activity) context;
        if (activity == null) {
            throw new IllegalArgumentException("activity instance can not be null");
        }
        Window window = activity.getWindow();
        boolean show;
        Display display = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);

        View decorView = window.getDecorView();
        Configuration conf = context.getResources().getConfiguration();
        if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
            View contentView = decorView.findViewById(android.R.id.content);
            show = (point.x != contentView.getWidth());
        } else {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            show = (rect.bottom != point.y);
        }
        return show;
    }


    public int getKeyBoardHeight(Context context) {
        View decorView = ((Activity) context).getWindow().getDecorView();
        int screenHeight = decorView.getHeight();
        Rect r = new Rect();
        //获取当前界面可视部分
        decorView.getWindowVisibleDisplayFrame(r);
        int heightDifference = screenHeight - r.bottom;
        Log.d("Keyboard Size", "Size: " + heightDifference);
        return heightDifference;
    }

    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    public static void setFullscreen(Context context,boolean isShowStatusBar, boolean isShowNavigationBar) {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (!isShowStatusBar) {
            uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (!isShowNavigationBar) {
            uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        View decorView = ((Activity) context).getWindow().getDecorView();

        decorView.setSystemUiVisibility(uiOptions);

        //隐藏标题栏
        //专门设置一下状态栏导航栏背景颜色为透明，凸显效果。
//        setNavigationStatusColor(Color.TRANSPARENT);
    }


}
