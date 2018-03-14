package com.example.cheny.starlauncher

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
//import com.example.cheny.starlauncher.R.id.*

//import com.example.cheny.starlauncher.R.id.*

/**
 * Created by cheny on 17.12.27.
 * To adapt the app list
 */
class Ownadapt(private var apps: List<ResolveInfo>, context : Context, pM : PackageManager, private var db: SQLiteDatabase) : BaseAdapter() {
    private var mContext : Context = context
    private var pm : PackageManager = pM

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("ViewHolder", "InflateParams", "SetTextI18n", "Recycle")
    override fun getView(position: Int, cView: View?, parent: ViewGroup?): View {

        // Set the picture and the text
        val res = db.rawQuery("SELECT gesture FROM map WHERE name = '${apps[position].activityInfo.name}'", null)

        val convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,null)
        val icon = convertView.findViewById<ImageView>(R.id.app_icon)
        val title = convertView.findViewById<TextView>(R.id.app_title)
        val gesture = convertView.findViewById<TextView>(R.id.app_gesture)


        var a = apps[position].activityInfo.loadIcon(pm)

        icon.setImageDrawable(apps[position].activityInfo.loadIcon(pm))
//        title.text = apps[position].activityInfo.packageName
        title.text = apps[position].activityInfo.toString()
        title.text = apps[position].activityInfo.loadLabel(pm)


        if (res.count != 0) {
            res.moveToFirst()
            gesture.text = res.getString(0)
        } else {
            gesture.text = "NONE"
        }

        return convertView
    }

    override fun getItem(position: Int): Any {
        return apps[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return apps.size
    }
}