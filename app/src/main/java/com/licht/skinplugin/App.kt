package com.licht.skinplugin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.licht.skinplugin.niebu.SkinManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.initSkin(this)
    }
}