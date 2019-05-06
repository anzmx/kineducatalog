package com.agzz.kineducatalog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.agzz.kineducatalog.entities.Activity
import com.agzz.kineducatalog.entities.ActivityData

@Dao
interface ActivitiesDao {

    @Query("SELECT * FROM ActivityData")
    fun getActivityData(): LiveData<ActivityData>

    @Insert(onConflict = REPLACE)
    fun insertActivityData(activityData: ActivityData)

    @Query("SELECT * FROM Activity")
    fun getAll(): List<Activity>

    @Insert(onConflict = REPLACE)
    fun insert(activity: Activity)

    @Insert(onConflict = REPLACE)
    fun insertAll(activities: List<Activity>)
}