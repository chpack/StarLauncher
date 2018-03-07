package com.example.cheny.starlauncher

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
class MainActivity : Activity() {
    private var touchStart: Pot = Pot(540.0,960.0)
    private var touchList = mutableListOf<Int>()
    private var lastArea = 1
    private var nowArea : Int = 0
    private lateinit var data : DBManager
    private var fragmentStatus = "none"
    private var appList = AppListFragment()
    private var status = "normal"

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init database of the map of gesture and app
//        data = ownDataBase(openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null))
        data = DBManager(this)

        // Create the fragment of app list
        val tra = fragmentManager.beginTransaction()
        appList.db = data
        appList.packageManager = packageManager
        appList.closeFunction = {closeFragmentAnim()}
        appList.startApp =  { packagename, name -> startApp(packagename, name) }
        appList.beforeSetApp = {beforeSetApp()}
        tra.replace(app_list_fragment.id,appList)
        tra.commit()
        app_list_fragment.x = -1000f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> startTouch(event.x, event.y)
            MotionEvent.ACTION_MOVE -> changeTouch(event.x, event.y)
            MotionEvent.ACTION_UP -> endTouch()
        }
        return true
    }

    override fun onBackPressed() {
//            "setting" -> super.onBackPressed()
    }

    // How to deal with the touch event.
    // When user start touch
    private fun startTouch(x: Float, y: Float) {
        touchList.clear()
        lastArea = 0
        if (fragmentStatus == "on") {
            closeFragmentAnim()
        }
        onTouchAnim(x,y)
        touchStart = Pot(x.toDouble(),y.toDouble())
        appList.loadAppList()
    }
    // When user moving position
    private fun changeTouch(x: Float, y: Float) {
        val now = Pot (x.toDouble(), y.toDouble())
        nowArea = touchStart.inWhichArea(now)
        if (nowArea != lastArea && nowArea != 7) {
            lastArea = nowArea
            touchList.add(nowArea)
        }
        val toa = Toast.makeText(this, touchList.toString(), Toast.LENGTH_SHORT)
        toa.show()
    }
    // when user stop position
    private fun endTouch() {
        outTouchAnim()
        val instruce = data.search(touchList.toString())
        when (status) {

        "normal" ->  when (instruce[0]) {
            "app" -> startApp(instruce[2], instruce[3])
            "launcher" -> startSettingFragment(instruce[1])
        }
            "setting" -> setApp(touchList.toString())
        }
    }

    private fun startApp(packagename: String, name: String) {
//            val target = Intent(Intent.ACTION_MAIN)
//            target.component = ComponentName(packagename, name)
//            startActivity(target)
        val tar = packageManager.getLaunchIntentForPackage(packagename)
        startActivity(tar)
    }

    private fun beforeSetApp() {
        status = "setting"
        closeFragmentAnim()
    }
    private fun setApp(gesture: String) {
//        return data.updata(gesture, appList.catchPackage, appList.catchName, "")
        if (data.updata(gesture, appList.catchPackage, appList.catchName, "")) {
            status = "normal"
            openFragmentAnim()
            val toa = Toast.makeText(this, "Success!", Toast.LENGTH_SHORT)
            toa.show()
        }else {
            val toa = Toast.makeText(this, "This gesture has alread used, \nPlease try another.", Toast.LENGTH_SHORT)
            toa.show()
        }
    }
    private fun deleteApp(gesture: String) {

    }

    private fun openFragmentAnim() {
        fragmentStatus = "on"
        app_list_fragment.x = touch_point.x
        app_list_fragment.y = touch_point.y
//        val ani0 = ObjectAnimator.ofInt(app_list_fragment,"width",0,touch_point.width*2)
        val ani0 = ObjectAnimator.ofFloat(app_list_fragment,"scaleX",0f, 1f)
        val ani1 = ObjectAnimator.ofFloat(app_list_fragment,"scaleY",0f, 1f)
        ani0.duration = 200
        ani1.duration = 200
        val ans = AnimatorSet()
        ans.playTogether(ani0, ani1)
        ans.start()
    }

    private fun closeFragmentAnim() {
        fragmentStatus = "off"
        val ani0 = ObjectAnimator.ofFloat(app_list_fragment,"scaleX",1f, 0f)
        val ani1 = ObjectAnimator.ofFloat(app_list_fragment,"scaleY",1f, 0f)
        val ani2 = ObjectAnimator.ofFloat(app_list_fragment,"x",1f, -1000f)
        ani0.duration = 200
        ani1.duration = 200
        ani2.duration = 1
        val ans = AnimatorSet()
        ans.play(ani0).with(ani1).before(ani2)
        ans.start()
//        ans.playTogether(ani0, ani1).
//        AnimatorSet().play(ani2).after(ani0)
//        ans.start()

    }

    // Open a setting Fragment
    private fun startSettingFragment(command: String) {
        openFragmentAnim()
        when (command) {
//            "start" -> appList.status = command
        }

    }

    private fun listApps() {

    }


//    private fun

    // Animators when it start to show.
    // The animator when user start the screen
    private fun onTouchAnim(x:Float, y:Float) {
        touch_point.x = x - touch_point.width/2
        touch_point.y = y - touch_point.height/2
        touchStart = Pot(x.toDouble(), x.toDouble())
        val ani = ObjectAnimator.ofFloat(touch_point, "alpha", 0f,1f)
        ani.duration = 200
        ani.start()
    }

    // The animator when user stop touch the screen
    private fun outTouchAnim() {
        val ani = ObjectAnimator.ofFloat(touch_point, "alpha", 1f,0f)
        ani.duration = 200
        ani.start()
    }
}

