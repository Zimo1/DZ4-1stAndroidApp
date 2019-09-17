package ru.netology.kotlin.dz3_1standroidapp.dto

open class Post (
    val id: Long,
    val author: String,
    val content: String,
    val created: String, //дата создания поста
    var likedByMe: Boolean = false,
    var commentedByMe: Boolean = false,
    var sharedByMe: Boolean = false,
    var likesQty: Int = 0,
    var commentsQty: Int = 0,
    var sharesQty: Int = 0
)