package com.licht.skinplugin.niebu.core

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatViewInflater
import com.licht.skinplugin.niebu.views.SkinButtonView
import com.licht.skinplugin.niebu.views.SkinLinearLayout
import com.licht.skinplugin.niebu.views.SkinTextView

class SkinAppCompatViewInflater(var name: String,
                                var context: Context,
                                var attrs: AttributeSet) :AppCompatViewInflater(){


    fun  createSkinView():View?{
         when (name){
             "TextView" -> return SkinTextView(context,attrs);
             "Button"-> return SkinButtonView(context,attrs);
             "LinearLayout"-> return SkinLinearLayout(context,attrs)
         }

        return null
    }



}