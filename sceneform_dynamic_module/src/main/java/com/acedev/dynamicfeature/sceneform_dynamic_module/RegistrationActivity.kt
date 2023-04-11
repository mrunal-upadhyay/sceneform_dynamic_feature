package com.acedev.dynamicfeature.sceneform_dynamic_module

import android.os.Bundle
import com.acedev.dynamicfeature.BaseSplitActivity
import com.acedev.dynamicfeature.registration.R

class RegistrationActivity : BaseSplitActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val fm = supportFragmentManager
//        val fragment = fm.findFragmentByTag("myFragmentTag1")
//        if (fragment == null) {
//            val ft = fm.beginTransaction()
//            val fragment = RegistrationArFragment()
//            ft.add(android.R.id.content, fragment, "myFragmentTag1")
//            ft.commit()
//        }
        setContentView(R.layout.reg_main_activity);
    }

}