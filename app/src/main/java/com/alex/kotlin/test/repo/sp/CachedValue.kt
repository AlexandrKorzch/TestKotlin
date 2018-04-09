package com.alex.kotlin.test.repo.sp

import android.content.SharedPreferences


@Suppress("JAVA_CLASS_ON_COMPANION")
class CachedValue<T> {

    companion object {
        val lock =  Object()
        lateinit var sp: SharedPreferences
    }

    private var name: String
    private var value : T?
    private var defValue : T?
    private var type : Class<Any>
    private var loaded = false


    constructor(name: String, type : Class<Any>) : this(name, null, null, type) {}

    constructor(name: String, defValue : T, type : Class<Any>) : this(name, null, defValue, type) {}

    constructor(name: String, value : T?, defValue : T?, type : Class<Any>) {
        this.name = name
        this.value = value
        this.defValue = defValue
        this.type = type
        this.loaded = value != null
    }

    fun setValue(value: T) {
        synchronized (lock) {
            loaded = true
            this.value = value
            write(value)
        }
    }

    fun getValue(): T? {
        synchronized (lock) {
            if (!loaded) {
                value = load()
                loaded = true
            }
            return value
        }
    }

    fun getName():String {
        return name
    }

    private fun write(value : T) {

        val editor : SharedPreferences.Editor  = sp.edit()

        if (value is String) {

            editor.putString(name, value as String)

        } else if (value is Int) {

            editor.putInt(name, value as Int)

        } else if (value is Float) {

            editor.putFloat(name, value as Float)

        } else if (value is Long) {

            editor.putLong(name, value as Long)

        } else if (value is Boolean) {

            editor.putBoolean(name, value as Boolean)

        }

        editor.apply()
    }


    @SuppressWarnings("unchecked")
    private fun load() : T? {

        if (type.isAssignableFrom(String.javaClass)) {

            return sp.getString(name, defValue as String) as T

        } else if (type.isAssignableFrom(Int.javaClass)) {

            return sp.getInt(name, defValue as Int) as T

        } else if (type.isAssignableFrom(Float.javaClass)) {

            return sp.getFloat(name, defValue as Float) as T

        } else if (type.isAssignableFrom(Long.javaClass)) {

            return sp.getLong(name, defValue as Long) as T

        } else if (type.isAssignableFrom(Boolean::class.javaObjectType)) {

            return sp.getBoolean(name, defValue as Boolean) as T

        }

        return null
    }

    fun delete() {
        synchronized (lock) {
            sp.edit().remove(name).apply()
            clear()
        }
    }

    private fun clear() {
        synchronized (lock) {
            loaded = false
            this.value = null
        }
    }
}

