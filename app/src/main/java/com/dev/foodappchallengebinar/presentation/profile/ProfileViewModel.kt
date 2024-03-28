package com.dev.foodappchallengebinar.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.foodappchallengebinar.data.models.Profile

class ProfileViewModel : ViewModel() {

    val profileData = MutableLiveData(
        Profile(
            username = "Gigih Dwi Cahyo",
            email = "gigih.dc@gmail.com",
            phone = "085238920982",
            profileImg = "https://avatars.githubusercontent.com/u/21256595?v=4"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}