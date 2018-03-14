package com.example.cheny.starlauncher

import android.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
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
//    lateinit var db: ownDataBase
    lateinit var db: DBManager
    lateinit var closeFunction: () -> Unit
    lateinit var appFunction: (packagename:String, name:String) -> Unit
    lateinit var startApp: (packagename: String, name: String) -> Unit
    lateinit var beforeSetApp: () -> Unit

    public var catchPackage = ""
    public var catchName = ""


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

//        return super.onCreateView(inflater, container, savedInstanceState)
//        packageManager = savedInstanceState.getSerializable("pm").packageManager

        // Init the fragment
//        val datasenter :DataSenter = arguments?.getSerializable("pm") as DataSenter
//        packageManager = datasenter.packageManager
//        db = datasenter.database
//        val db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null)

//        loadApp()

//        show.findViewById<ListView>(app_list_view.id).adapter = ownadapt(mApps,this.activity,packageManager, db.db)
//        app_list_view.adapter = ownadapt(mApps,this.activity,packageManager, db.db)

//        return inflater!!.inflate(R.layout.app_list, container, false)

        // Return the view of the fragment
        return inflater!!.inflate(R.layout.app_list, container, false)
    }
//        app_list_view.adapter = ownadapt(mApps,this.activity,packageManager, db.db)

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        app_list_view.adapter = ownadapt(mApps,this.activity,packageManager, db.db)
//    }

    // Load app list
    fun loadAppList() {
        val mainIntent = Intent(Intent.ACTION_MAIN)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        mApps = packageManager.queryIntentActivities(mainIntent,0)
//        app_list_view.adapter = ownadapt(mApps,this.activity,packageManager, db.db)
//        app_list_view.adapter = ownadapt(mApps,context,packageManager, db.db)
//        app_list_view.adapter = ownadapt(mApps,context, packageManager, db.db)
        app_list_view.adapter = AppListAdapter(mApps,context, packageManager, db)
//        app_list_view.adapter = ownadapt(mApps,cont, packageManager, db.db)
//        when (command) {
//            "start" -> app_list_view.setOnItemClickListener { parent, view, position, id ->
//                closeFunction()
//                appFunction(mApps[id.toInt()].activityInfo.packageName, mApps[id.toInt()].activityInfo.name)
//            }
//        }
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