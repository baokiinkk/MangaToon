package com.baokiin.mangatoon.data.model

import com.baokiin.mangatoon.utils.SNSLoginType
import com.google.firebase.auth.FirebaseUser

 data class UserSNS (
     val snsLoginType: SNSLoginType? = null,
     val user: FirebaseUser? = null
)