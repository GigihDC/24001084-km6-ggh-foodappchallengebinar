package com.dev.foodappchallengebinar.data.models

import com.google.firebase.auth.FirebaseUser

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val password: String,
)

fun FirebaseUser?.toUser() =
    this?.let {
        User(
            id = this.uid,
            fullName = this.displayName.orEmpty(),
            email = this.email.orEmpty(),
            password = this.phoneNumber.orEmpty(),
        )
    }
