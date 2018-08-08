package com.example.cheny.starlauncher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.LinearLayout
import com.example.cheny.starlauncher.R.layout.app_list_item

class AppAdapter(var context: Context, var appList: ArrayList<AppInfo>, var start: (Intent) -> Unit) : RecyclerView.Adapter<ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(context).inflate(app_list_item, parent,  false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.icon.setImageDrawable(appList[position].drawable)
        holder.title.text = appList[position].title
        holder.gesture.text = appList[position].pkgname


        /**
         * Start which app was clicked
         */
        holder.item.setOnClickListener {
            val intent = Intent()
            intent.component = ComponentName(appList[position].pkgname, appList[position].name)
            start(intent)
        }

    }

    public fun updata() {
        notifyDataSetChanged()
    }
}

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var item = itemView
    var icon = itemView.findViewById<ImageView>(R.id.app_icon)
    var title = itemView.findViewById<TextView>(R.id.app_title)
    var gesture = itemView.findViewById<TextView>(R.id.app_gesture)
}