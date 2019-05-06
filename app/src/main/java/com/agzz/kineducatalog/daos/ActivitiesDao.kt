package com.agzz.kineducatalog.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.agzz.kineducatalog.entities.Activity

@Dao
interface ActivitiesDao {

    @Query("SELECT * FROM Activity")
    fun getAll(): List<Activity>

    @Insert(onConflict = REPLACE)
    fun insert(activity: Activity)

    @Insert(onConflict = REPLACE)
     fun insertAll(activities: List<Activity>)
}