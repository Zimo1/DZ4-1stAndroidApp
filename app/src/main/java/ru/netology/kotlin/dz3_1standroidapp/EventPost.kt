package ru.netology.kotlin.dz3_1standroidapp

import ru.netology.kotlin.dz3_1standroidapp.dto.Post

class EventPost (
    id: Long,
    author: String,
    content: String,
    created: String,
    likedByMe: Boolean = false,
    commentedByMe: Boolean = false,
    sharedByMe: Boolean = false,
    likesQty: Int = 0,
    commentsQty: Int = 0,
    sharesQty: Int = 0,
    val address: String,
    val coordinates: Pair<Double, Double>
): Post(id, author, content, created, likedByMe, commentedByMe, sharedByMe, likesQty, commentsQty, sharesQty)