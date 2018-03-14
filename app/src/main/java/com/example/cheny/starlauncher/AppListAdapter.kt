package com.example.cheny.starlauncher

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

/**
 * Created by cheny on 18.3.14.
 * To replace the ownadapter class. That is to ugly...
 */

@Suppress("UNREACHABLE_CODE")
public class AppListAdapter(var apps: List<ResolveInfo>, val context: Context, val pm: PackageManager, private var db: DBManager) : BaseAdapter() {
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val v = if (convertView != null) {
            Toast.makeText(context, "it is not null", Toast.LENGTH_LONG).show()
            convertView
        } else {
            View.inflate(context, R.layout.list_item, null)
        }

        v.findViewById<ImageView>(R.id.app_icon).setImageDrawable(pm.getApplicationIcon(apps[position].activityInfo.packageName))
        v.findViewById<TextView>(R.id.app_title).text = apps[position].activityInfo.loadLabel(pm)
        v.findViewById<TextView>(R.id.app_gesture).text = db.scanGesture(apps[position].activityInfo.name)

        return v
    }

    override fun getItem(position: Int): Any {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return apps[position]
    }

    override fun getItemId(position: Int): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return position.toLong()
    }

    override fun getCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return apps.size
    }


}
