package com.agzz.kineducatalog.entities

data class ArticleDetail(
    val data: ArticleDetailData
)

data class ArticleDetailData(
    val article: ArticleX,
    val related_items: RelatedItems
)

data class ArticleX(
    val area_id: Int,
    val body: String,
    val faved: Boolean,
    val id: Int,
    val link: String,
    val picture: String,
    val title: String
)

data class RelatedItems(
    val activities: List<RelatedActivity>,
    val articles: List<RelatedArticle>
)

data class RelatedArticle(
    val area_id: Int,
    val faved: Boolean,
    val id: Int,
    val link: String,
    val picture: String,
    val short_description: String,
    val title: String
)

data class RelatedActivity(
    val activity_type: String,
    val age: Int,
    val age_group: String,
    val area_id: Int,
    val description: String,
    val faved: Boolean,
    val id: Int,
    val locked: Boolean,
    val name: String,
    val picture: String,
    val purpose: String,
    val shareable_thumbnail_url: String,
    val shareable_video_url: String,
    val video_id: String,
    val video_provider_thumbnails: VideoProviderThumbnails
)

data class VideoProviderThumbnails(
    val size1: String,
    val size2: String,
    val size3: String,
    val size4: String,
    val size5: String,
    val size6: String
)