package com.example.cheny.starlauncher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setlauncher.*

class setlauncher : AppCompatActivity() {
    private var mApps = listOf<ResolveInfo>()
//    private val data = ownDataBase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setlauncher)

        val db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null)

        loadApp()

//        lv.adapter = ownadapt(mApps,this,packageManager, db)


        // On touch event
        lv.setOnItemClickListener { parent, view, position, id ->
            finish()
//            target.component = ComponentName(ia[id.toInt()].applicationInfo.packageName,ia[id.toInt()].applicationInfo.name)
//            val res = db.rawQuery("SELECT status FROM person WHERE name = '${mApps[id.toInt()].activityInfo.name}'", null)
//            if (res.count != 0) {
//                res.moveToFirst()
//                if (res.getString(0) == "enable") {
//                    db.execSQL("UPDATE person SET status = 'disable' WHERE name = '${mApps[id.toInt()].activityInfo.name}'")
//                } else {
//                    db.execSQL("UPDATE person SET status = 'enable' WHERE name = '${mApps[id.toInt()].activityInfo.name}'")
//                }
//            } else {
//                db.execSQL("INSERT INTO person (package, name, status) VALUES ('${mApps[id.toInt()].activityInfo.packageName}','${mApps[id.toInt()].activityInfo.name}','setting');")
//            }
//
//            finish()

        }

        lv.setOnItemLongClickListener { parent, view, position, id ->
            val setges = Intent(Intent.ACTION_MAIN)
            setges.component = ComponentName("com.example.cheny.starlauncher","com.example.cheny.starlauncher.MainActivity")
            setges.putExtra("status", "setting")
            setges.putExtra("packagename", mApps[position].activityInfo.packageName)
            setges.putExtra("name", mApps[position].activityInfo.name)
            startActivity(setges)
            true
        }
        /*
//        var li = mutableListOf<HashMap<String,Any>>()
//        Log.v("a","on ${mApps.size}")
//        for (i in 1..(mApps.size - 1)) {
//            var si = HashMap<String, Any>()
//            si.put("icon",mApps[i].activityInfo.loadIcon(packageManager))
//            var iv = ImageView(this)
////            iv.scaleType = ImageView.ScaleType.FIT_CENTER
////            iv.layoutParams = ViewGroup.LayoutParams(128,128)
//            iv.setImageDrawable(mApps[i].activityInfo.loadIcon(packageManager))
////            si.put("icon",iv)
//            si.put("title",mApps[i].activityInfo.packageName)
//            si.put("add",mApps[i].activityInfo.name)
//            li.add(si)
//            Log.v("a","on $i   ${iv.id}  $iv")
//        }
//
//        Log.v("asd","fasde")
//        Log.v("asd","fasde${app_icon}")
//        var nl : IntArray = intArrayOf(app_icon,app_title,app_address)
//        Log.v("asd","fasde")
//        Log.v("asd","fasde")
//        var mad = SimpleAdapter(applicationContext,li,R.layout.list_item, arrayOf("icon","title","add"), nl)
//        lv.adapter = mad
//        lv.adapter = mad
*/
    }

    private fun loadApp() {
        val mainIntent = Intent(Intent.ACTION_MAIN)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        mApps = packageManager.queryIntentActivities(mainIntent,0)
    }
}
