package com.example.cheny.starlauncher

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_app_list.*
import kotlinx.android.synthetic.main.content_app_list.*
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager


class AppList : AppCompatActivity() {

    private var appInfos = ArrayList<AppInfo>()
    private lateinit var adapter: AppAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        load()

        // Load adapter
        adapter = AppAdapter(this, appInfos,{intent -> startActivity(intent) })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        app_list_cont.layoutManager = layoutManager
        app_list_cont.adapter = adapter
    }

    /**
     * Load apps' info
     */
    fun load() {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val apps = packageManager.queryIntentActivities(mainIntent, 0)

        Tools.d(apps.size.toString())

        for (i:Int in apps.indices) {
            appInfos.add(AppInfo(apps[i].activityInfo.loadLabel(packageManager).toString(),
                    apps[i].activityInfo.packageName,
                    apps[i].activityInfo.name,
                    apps[i].loadIcon(packageManager)))
            Tools.d(appInfos[i].name)
        }
    }

}

