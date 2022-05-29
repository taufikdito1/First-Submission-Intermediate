package com.example.storyapp.helper

import android.content.Context
import com.example.storyapp.data.model.UserSession

class UserPreference (context: Context) {

    private val preferences = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)

    fun setUser(user: UserSession) {
        val edt = preferences.edit()
        edt.putString(NAME, user.name)
        edt.putString(TOKEN, user.token)
        edt.putString(USER_ID, user.userId)
        edt.putBoolean(LOGIN_STATE, user.isLogin)
        edt.apply()
    }

    fun logout() {
        val edt = preferences.edit()
        edt.remove(NAME)
        edt.remove(TOKEN)
        edt.putBoolean(LOGIN_STATE, false)
        edt.apply()
    }

    fun getUser(): UserSession {
        return UserSession(
            preferences.getString(NAME, "") ?: "",
            preferences.getString(TOKEN, "")?: "",
            preferences.getString(USER_ID, "")?: "",
            preferences.getBoolean(LOGIN_STATE, false)
        )
    }

    companion object{
        const val LOGIN = "login"
        const val NAME = "name"
        const val TOKEN = "token"
        const val USER_ID = "userId"
        const val LOGIN_STATE = "login_state"
    }
}