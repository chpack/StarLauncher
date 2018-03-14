package com.example.cheny.starlauncher

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
class MainActivity : Activity() {

    private var touchStart: Pot = Pot()
    private var touchList = mutableListOf<Int>()
    private var lastArea = 1
    private var nowArea : Int = 0
    private lateinit var data : DBManager
    private var fragmentStatus = "none"
    private var appList = AppListFragment()
    private var status = "normal"
    private lateinit var icons : List<ImageView>
    private var iconsPos = Array(7,init = { Pot()})

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Init database of the map of gesture and app
         */
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
        icons = listOf<ImageView>(icon0, icon1, icon2, icon3, icon4, icon5, icon6)
    }


    /**
     * Deal with the touch event.
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> startTouch(event.x, event.y)
            MotionEvent.ACTION_MOVE -> changeTouch(event.x, event.y)
            MotionEvent.ACTION_UP -> endTouch()
        }
        return true
    }


    /**
     *  Deal with the back button.
     *
     */
    override fun onBackPressed() {
//            "setting" -> super.onBackPressed()
    }


    /**
     * How to deal with the touch event.
     * When user start touch,
     * 1. clean the touvh list.
     * 2. close the app list if it was opened.
     * 3. draw the touch leader.
     */
    private fun startTouch(x: Float, y: Float) {
        touchList.clear()
        lastArea = 0
        nowArea = 0

        if (fragmentStatus == "on") {
            closeFragmentAnim()
        }
        touchStart = Pot(x,y)
//        appList.loadAppList()
        replaceIcons(x, y)
        changeIcons()
        onTouchAnim(0)
    }


    /**
     * When user moving position
     * 1. get the touch position.
     * 2. add the touch area if it is different with last one.
     * 3. create a toast to show touch list.(it's for debug, may delete this future)
     */
    private fun changeTouch(x: Float, y: Float) {
        val now = Pot (x, y)
        nowArea = touchStart.inWhichArea(now)

        if (nowArea != lastArea && nowArea != 7) {
            lastArea = nowArea
            touchList.add(nowArea)
            changeIcons()
            onTouchAnim(nowArea)
        }
    }


    /**
     * When user stop position
     * 1. play the animator
     * 2. start apps or other activity.
     */
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


    /**
     * Start app by package name and name.
     * There are some bugs, should fix it future.
     */
    private fun startApp(packagename: String, name: String) {
//            val target = Intent(Intent.ACTION_MAIN)
//            target.component = ComponentName(packagename, name)
//            startActivity(target)
        val tar = packageManager.getLaunchIntentForPackage(packagename)
        startActivity(tar)
    }


    /**
     * Some thing should be done before setting.
     * Should be fixed future.
     */
    private fun beforeSetApp() {
        status = "setting"
        closeFragmentAnim()
    }


    /**
     * Start set app's gesture.
     */
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


    /**
     * The animators on the fragment.
     */
    private fun openFragmentAnim() {
        appList.loadAppList()
        fragmentStatus = "on"
        app_list_fragment.x = touchStart.x - app_list_fragment.width / 2
        app_list_fragment.y = touchStart.y - app_list_fragment.height / 2
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


    /**
     * Open a setting Fragment
     */
    private fun startSettingFragment(command: String) {
        openFragmentAnim()
    }


    /**
     * I forget why i create this....
     * May be one day I will remember it.
     */
    private fun listApps() {

    }


//    private fun

    /**
     * Animators when it start to show.
     * The animator when user start the screen
     */
    private fun onTouchAnim(s: Int) {
        val ani = AnimatorSet()

        for (i: Int in 0..6){
//            icons[i].alpha = 1.0f
            ani.playTogether(ObjectAnimator.ofFloat(icons[i], "alpha", 0f, 1f))
            ani.playTogether(ObjectAnimator.ofFloat(icons[i], "x", iconsPos[s].x, iconsPos[i].x))
            ani.playTogether(ObjectAnimator.ofFloat(icons[i], "y", iconsPos[s].y, iconsPos[i].y))
        }
        ani.duration = 200
        ani.start()
    }

    /**
     * The animator when user stop touch the screen
     */
    private fun outTouchAnim() {
        val ani = AnimatorSet()
        for (i: Int in 0..6){
            ani.playTogether(ObjectAnimator.ofFloat(icons[i], "alpha", 1f, 0f))
        }
        ani.duration = 200
        ani.start()
    }


    /**
     * Change the icon on every area.
     */
    private fun changeIcons() {
        Log.d("asdf", "${nowArea},    ${lastArea}")
        for (i: Int in 0..6) {

            val instruce =  if (i != nowArea) {
                data.search((touchList + i).toString())
            } else {
                data.search((touchList).toString())
            }

            when (instruce[0]) {
                "app" -> icons[i].setImageDrawable(packageManager.getApplicationIcon(instruce[2]))

                "launcher" -> when (instruce[1]) {
                    "list" -> icons[i].setImageResource(R.drawable.list)
                    "setting" -> icons[i].setImageResource(R.drawable.setting)
                }

                else -> {       // Usual means it is empty.
                    icons[i].setImageResource(R.drawable.empty)
                }
            }
        }
    }


    /**
     * Replace the icons to the right position.
     */
    private fun replaceIcons(x: Float, y: Float) {
        val iconPoses = floatArrayOf(0f, 0f, -1.7f, -1f, 0f, -2f, 1.7f, -1f, 1.7f, 1f, 0f, 2f, -1.7f, 1f)
        for (i: Int in 0..6) {
            iconsPos[i].x = x + iconPoses[i *2] * icons[i].width/1.5f - icons[i].width/2
            iconsPos[i].y = y + iconPoses[i *2 + 1] * icons[i].width/1.5f - icons[i].width/2
        }

    }
}

