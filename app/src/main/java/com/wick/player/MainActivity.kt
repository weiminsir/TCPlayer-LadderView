package com.wick.player

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.tencent.liteav.demo.play.SuperPlayerConst
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig
import com.tencent.liteav.demo.play.SuperPlayerModel
import com.tencent.liteav.demo.play.SuperPlayerView
import com.tencent.liteav.demo.play.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN


class MainActivity : AppCompatActivity(), SuperPlayerView.PlayerViewCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        initView()
        window.decorView.systemUiVisibility = 0
        setNavigationStatusColor(Color.BLACK)
//        StatusBarUtils.setStatusBarColor(this, Color.BLACK)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            window.statusBarColor = Color.BLACK
////        }
        addKeyBoardListener()
//        addNatiavtorListener()
    }

    fun isPortrait() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT



    fun addNatiavtorListener() {
        val decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                Log.d("WickPlayer", "状态栏显示")
                // TODO: The system bars are visible. Make any desired
                // adjustments to your UI, such as showing the action bar or
                // other navigational controls.
//              StatusBarUtils.setFullscreen()
            } else {
                Log.d("WickPlayer", "状态栏隐藏")
                // TODO: The system bars are NOT visible. Make any desired
                // adjustments to your UI, such as hiding the action bar or
                // other navigational controls.
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            val permissions = ArrayList<String>()
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA)
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (permissions.size != 0) {
                ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 100)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        super_player_view?.onPause()
    }

    fun setPlayerModel() {
        val superPlayerModel = SuperPlayerModel()
//            superPlayerModel.placeholderImage = anchor?.user?.avatar
        superPlayerModel.videoURL = "http://5815.liveplay.myqcloud.com/live/5815_89aad37e06ff11e892905cb9018cf0d4.flv"
        superPlayerModel.title = "1234556"

        super_player_view.playWithMode(superPlayerModel)
    }


    private fun initView() {
        super_player_view.setPlayerViewCallback(this)
        initSuperVodGlobalSetting()
        setPlayerModel()
    }


    override fun onResume() {
        super.onResume()
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = 0
        setNavigationStatusColor(Color.BLACK)


        if (super_player_view.playState == SuperPlayerConst.PLAYSTATE_PLAY) {
            super_player_view.onResume()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        super_player_view?.apply {
            release()
            resetPlayer()
        }
    }


    override fun hideViews() {
//        isFullScreen = true
        Log.d("WickPlayer", "hideViews")
//        val config = this.resources.configuration.orientation
//        if (config == Configuration.ORIENTATION_LANDSCAPE) {
//            StatusBarUtils.setTranslucentStatusBarLand(this)
//        }
//        StatusBarUtils.setFullscreen(this, false, true)
//        setNavigationStatusColor(Color.TRANSPARENT)


    }


    override fun showViews() {
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        isFullScreen = false

    }

    override fun onQuit(playMode: Int) {
        if (playMode == SuperPlayerConst.PLAYMODE_WINDOW) {
            super_player_view.resetPlayer()
            finish()
        }
    }


    /**
     * 初始化超级播放器全局配置
     */
    private fun initSuperVodGlobalSetting() {
        val prefs = SuperPlayerGlobalConfig.getInstance()
        // 开启悬浮窗播放
        prefs.enableFloatWindow = false
        // 播放器默认缓存个数
        prefs.maxCacheItem = 5
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if (newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            window.decorView.systemUiVisibility = 0
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            setNavigationStatusColor(Color.BLACK)
//            StatusBarUtils.setStatusBarColor(this, Color.BLACK)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                window.statusBarColor = Color.BLACK
//            }
        } else {
//            StatusBarUtils.setTranslucentStatusBarLand(this)
            StatusBarUtils.setFullscreen(this, false, true)
            setNavigationStatusColor(Color.TRANSPARENT)
        }

    }

    fun setNavigationStatusColor(color: Int) {
        //VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.navigationBarColor = color
            window.statusBarColor = color
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.i("WickPlayer", "hasFocus=" + hasFocus)
        val config = this.resources.configuration.orientation
        if (config == Configuration.ORIENTATION_LANDSCAPE && hasFocus) {
            runOnUiThread {
                StatusBarUtils.setFullscreen(this, false, true)
                setNavigationStatusColor(Color.TRANSPARENT)
            }
        }

    }

    fun hideStatusBar(place: String) {
        Log.i("test", "hideStatusBar - $place")
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        decorView.systemUiVisibility = uiOptions

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    var isSoftKeboardShow = false
    var hideNavigationBar = false
    fun addKeyBoardListener() {
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            //获取当前屏幕内容的高度
            val screenHeight = window.decorView.height
            //获取View可见区域的bottom
            val rect = Rect()
            //DecorView即为activity的顶级view
            window.decorView.getWindowVisibleDisplayFrame(rect)
            //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
            //选取screenHeight*2/3进行判断
            if (screenHeight * 2 / 3 > rect.bottom) {
                if (!isSoftKeboardShow) {
                    isSoftKeboardShow = true
                    hideNavigationBar = true
                    Log.d("WickPlayer", "键盘显示")
//                    YtxLog.d(TAG, "222222: ${StatusBarUtil.getSceenHeight(this, true)}")
                }
            } else {
//                Log.d("WickPlayer", "键盘隐藏")
                if (isSoftKeboardShow) {
                    isSoftKeboardShow = false
                }
                if (hideNavigationBar&&!isPortrait()) {
                    StatusBarUtils.setFullscreen(this, false, true)
                    hideNavigationBar = false
                }
            }
        }
    }

}
