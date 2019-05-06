package com.agzz.kineducatalog.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ActivityIndex(
    val data: ActivityData,
    val meta: ActivityMeta
)
@Entity
data class ActivityData(
        val activities: List<Activity>,
        @PrimaryKey
    val id: Int,
    val name: String,
    val type: String
)

@Entity
data class Activity(
    val active_milestones: Int,
    val activity_type: String,
    val age: Int,
    val age_group: String,
    val area_id: Int,
    val completed_milestones: Int,
    val domain_id: Int,
    @PrimaryKey
    val id: Int,
    val is_holiday: Boolean,
    val name: String,
    val purpose: String,
    val thumbnail: String
)

data class ActivityMeta(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)