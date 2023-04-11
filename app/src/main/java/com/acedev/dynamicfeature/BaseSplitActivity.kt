package com.acedev.dynamicfeature

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat

/**
 * This base activity unifies calls to attachBaseContext as described in:
 * https://developer.android.com/guide/app-bundle/playcore#invoke_splitcompat_at_runtime
 */
abstract class BaseSplitActivity : AppCompatActivity() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
        Log.i("App", "Attach context happened")
    }
}