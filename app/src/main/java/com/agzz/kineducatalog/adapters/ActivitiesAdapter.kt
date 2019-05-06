package com.agzz.kineducatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agzz.kineducatalog.entities.Activity
import com.agzz.kineducatalog.R
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activities_list_row.view.*


class ActivitiesAdapter(private val glide: RequestManager) : RecyclerView.Adapter<ActivitiesViewHolder>(){

   var activitiesList:List<Activity> = emptyList()

    override fun getItemCount(): Int {
        return activitiesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.activities_list_row, parent, false)
        return ActivitiesViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val activity = activitiesList[position]
        holder.view.activity_item_name.text = activity.name
        holder.view.activity_item_purpose.text = activity.purpose
            glide.load(activity.thumbnail)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.view.activity_item_thumbnail)

    }

    fun setData(newData: List<Activity>) {
        this.activitiesList = newData
        notifyDataSetChanged()
    }
}
class ActivitiesViewHolder(val view: View): RecyclerView.ViewHolder(view)
