package com.example.cheny.starlauncher

import android.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_list.*

/**
 * Created by cheny on 18.1.23.
 *
 */
class AppListFragment : Fragment() {
    private var mApps = listOf<ResolveInfo>()
    lateinit var packageManager: PackageManager
    lateinit var db: DBManager
    lateinit var closeFunction: () -> Unit
    lateinit var startApp: (packagename: String, name: String) -> Unit
    lateinit var beforeSetApp: () -> Unit

    var catchPackage = ""
    var catchName = ""


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.app_list, container, false)
    }


    /**
     * Load the app list.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun loadAppList() {
        val mainIntent = Intent(Intent.ACTION_MAIN)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        mApps = packageManager.queryIntentActivities(mainIntent,0)
        app_list_view.adapter = AppListAdapter(mApps,context, packageManager, db)
            app_list_view.setOnItemClickListener { parent, view, position, id ->
                closeFunction()
                startApp(mApps[id.toInt()].activityInfo.packageName, mApps[id.toInt()].activityInfo.name)
            }
        app_list_view.setOnItemLongClickListener { parent, view, position, id ->
            catchPackage = mApps[id.toInt()].activityInfo.packageName
            catchName =  mApps[id.toInt()].activityInfo.name
            beforeSetApp()
            true
        }
    }
}