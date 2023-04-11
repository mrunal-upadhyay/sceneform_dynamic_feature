package com.acedev.dynamicfeature

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

private const val TAG = "DynamicFeatures"
private const val PACKAGE_NAME = "com.acedev.dynamicfeature.registration"
private const val REGISTRATION_SAMPLE_CLASSNAME = "$PACKAGE_NAME.RegistrationActivity"
private const val CONFIRMATION_REQUEST_CODE = 1

class MainActivity : BaseSplitActivity() {

    /** Listener used to handle changes in state for install requests. */
    private val listener = SplitInstallStateUpdatedListener { state ->
        val multiInstall = state.moduleNames().size > 1

        val names = state.moduleNames().joinToString(" - ")

        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                //  In order to see this, the application has to be uploaded to the Play Store.
                toastAndLog("downloading $names")
            }
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                /*
                  This may occur when attempting to download a sufficiently large module.

                  In order to see this, the application has to be uploaded to the Play Store.
                  Then features can be requested until the confirmation path is triggered.
                 */
                manager.startConfirmationDialogForResult(state, this, CONFIRMATION_REQUEST_CODE)
            }
            SplitInstallSessionStatus.INSTALLED -> {
                    onSuccessfulLoad(names, launch = !multiInstall)
            }

            SplitInstallSessionStatus.INSTALLING -> toastAndLog("installing $names")
            SplitInstallSessionStatus.FAILED -> {
                toastAndLog("Error: {state.errorCode()} for module {state.moduleName}"+state.errorCode()+
                    state.moduleNames())
            }
        }
    }

    /** This is needed to handle the result of the manager.startConfirmationDialogForResult
    request that can be made from SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION
    in the listener above. */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CONFIRMATION_REQUEST_CODE) {
            // Handle the user's decision. For example, if the user selects "Cancel",
            // you may want to disable certain functionality that depends on the module.
            if (resultCode == Activity.RESULT_CANCELED) {
                toastAndLog("User Cancelled")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private val clickListener by lazy {
        View.OnClickListener {
            when (it.id) {
                R.id.uiStartReg -> loadAndLaunchModule(moduleSceneform)
            }
        }
    }

    private fun toastAndLog(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        Log.d(TAG, text)
    }

    private lateinit var manager : SplitInstallManager

    private val moduleSceneform by lazy { getString(R.string.module_sceneform_dynamic_module) }
    private val moduleFragment by lazy { getString(R.string.module_fragment_module) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = SplitInstallManagerFactory.create(this)
//      loadModule(moduleRegistration)
        setupClickListener()
    }
    private fun setupClickListener() {
        setClickListener(R.id.uiStartReg, clickListener)
    }
    private fun setClickListener(id: Int, listener: View.OnClickListener) {
        findViewById<View>(id).setOnClickListener(listener)
    }

    override fun onResume() {
        // Listener can be registered even without directly triggering a download.
        manager.registerListener(listener)
        super.onResume()
    }

    override fun onPause() {
        // Make sure to dispose of the listener once it's no longer needed.
        manager.unregisterListener(listener)
        super.onPause()
    }

    private fun loadModule(name: String) {
        //updateProgressMessage(getString(R.string.loading_module, name))
        toastAndLog("Loading Module:$name")
        // Skip loading if the module already is installed. Perform success action directly.
        if (manager.installedModules.contains(name)) {
            toastAndLog("Already Installed")
            //updateProgressMessage(getString(R.string.already_installed))
            onSuccessfulLoad(name, launch = false)
            return
        }

        // Create request to install a feature module by name.
        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        // Load and install the requested feature module.
        manager.startInstall(request)
        toastAndLog("Starting install for$name")
        //updateProgressMessage(getString(R.string.starting_install_for, name))
    }

    private fun loadAndLaunchModule(name: String) {
        //updateProgressMessage(getString(R.string.loading_module, name))
        toastAndLog("Loading Module:$name")
        // Skip loading if the module already is installed. Perform success action directly.
        if (manager.installedModules.contains(name)) {
            toastAndLog("Already Installed")
            //updateProgressMessage(getString(R.string.already_installed))
            onSuccessfulLoad(name, launch = true)
            return
        }

        // Create request to install a feature module by name.
        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        // Load and install the requested feature module.
        manager.startInstall(request)
        toastAndLog("Starting install for"+name)
        //updateProgressMessage(getString(R.string.starting_install_for, name))
    }

    private fun onSuccessfulLoad(moduleName: String, launch: Boolean) {
        if (launch) {
            when (moduleName) {
                moduleSceneform -> launchActivity(REGISTRATION_SAMPLE_CLASSNAME)
                moduleFragment -> showFragment()
            }
        }

//        displayButtons()
    }

    /** Launch an activity by its class name. */
    private fun launchActivity(className: String) {
        val intent = Intent().setClassName(BuildConfig.APPLICATION_ID, className)
        startActivity(intent)
    }

    private fun showFragment(){
        //        val fragment = Class.forName("com.example.dynamicfeature.fragment_module.MainFragment").newInstance()
//        val fragment = Class.forName("com.example.dynamicfeature.fragment_module.ArFragment").newInstance()
//        val fragment = MainArFragment()

//        if (fragment is Fragment) {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.fragmentHolder, fragment, "dynamic_fragment")
//                .addToBackStack("dynamic_fragment")
//                .commit()
//        }
    }
}
