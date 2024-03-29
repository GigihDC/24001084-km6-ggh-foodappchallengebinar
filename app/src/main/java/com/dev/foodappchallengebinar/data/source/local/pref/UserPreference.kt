package com.dev.foodappchallengebinar.data.source.local.pref

import android.content.Context
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.MenuAdapter
import com.dev.foodappchallengebinar.utils.SharedPreferenceUtils
import com.dev.foodappchallengebinar.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun isUsingDarkMode(): Boolean
    fun setUsingDarkMode(isUsingDarkMode: Boolean)
    fun getListMode(): Int
    fun setListMode(listMode: Int)
}

class UserPreferenceImpl(private val context: Context) : UserPreference {

    private val pref = SharedPreferenceUtils.createPreference(context, PREF_NAME)

    override fun isUsingDarkMode(): Boolean = pref.getBoolean(KEY_IS_USING_DARK_MODE, false)

    override fun setUsingDarkMode(isUsingDarkMode: Boolean) {
        pref[KEY_IS_USING_DARK_MODE] = isUsingDarkMode
    }

    override fun getListMode(): Int = pref.getInt(KEY_LIST_MODE, MenuAdapter.MODE_GRID)

    override fun setListMode(listMode: Int) {
        pref[KEY_LIST_MODE] = listMode
    }

    companion object {
        const val PREF_NAME = "foodapp-pref"
        const val KEY_IS_USING_DARK_MODE = "KEY_IS_USING_DARK_MODE"
        const val KEY_LIST_MODE = "KEY_LIST_MODE"
    }
}
