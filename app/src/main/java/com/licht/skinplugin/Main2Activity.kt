package com.licht.skinplugin

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.licht.skinplugin.niebu.SkinManager
import com.licht.skinplugin.niebu.base.SkinControlBaseActivity
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File

class Main2Activity : SkinControlBaseActivity() {
    var  skinPath:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
         skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator + "rdSkin.tar";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                  var p = arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkSelfPermission(p[0]) == PackageManager.PERMISSION_DENIED){
                requestPermissions(p,100)
            }

        }

    }

    fun CC(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadSkin("100001",skinPath!!,R.color.colorAccent)
        }

    }

    fun CC2(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadSkin("100000",null,R.color.colorPrimaryDark)
        }
    }


}
