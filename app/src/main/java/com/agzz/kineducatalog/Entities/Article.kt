package com.agzz.kineducatalog.Entities

data class ArticleIndex(
    val data: ArticleData,
    val meta: ArticleMeta
)

data class ArticleMeta(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)

data class ArticleData(
    val articles: List<Article>
)

data class Article(
    val area_id: Int,
    val id: Int,
    val max_age: Int,
    val min_age: Int,
    val name: String,
    val picture: String,
    val short_description: String,
    val type: String
)