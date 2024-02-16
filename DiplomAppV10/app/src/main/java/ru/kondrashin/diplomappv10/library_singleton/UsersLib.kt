package ru.kondrashin.diplomappv10.library_singleton

import android.content.Context

import ru.kondrashin.diplomappv10.data_class.User

class UsersLib private constructor(context: Context){
    var users = mutableListOf<User>()
    companion object {
        private var INSTANCE: UsersLib? = null
        fun get(context: Context): UsersLib {
            if (INSTANCE == null)
                INSTANCE =  UsersLib(context)
            return INSTANCE!!
        }

    }
    fun getAllUsers(): MutableList<User>{
        return users
    }



    fun getUser(userId: Int): User?{
        for (user in users) {
            if (user.id == userId){
                return user
            }
        }
        return null
    }
    fun addUser(user: User){
        users.add(user)
    }
}