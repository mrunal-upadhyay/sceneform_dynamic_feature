# Use Sceneform as a Dynamic Feature Module

In order to reduce app size and deliver sceneform dependencies to limited set of devices and users who want to use it. This is an attempt to bundle sceneform as a seperate on demand dynamic module using Android feature delivery which is delivered through App Bundles.

The application consists of primary app module and sceneform_dynamic_module. When the user clicks on Start Sceneform Activity, app attempts to download the sceneform dependency and launch an AR fragment. 

**Steps to load app bundle and test apk locally:**
***Step1:*** Download bundletool from here. Rename the jar to `bundletool-all.jar`
***Step2:*** Open terminal and change current working directory to the one containing the above jar. Run the below command: 
```alias bundletool='java -jar bundletool-all.jar'``` 
***Step 3:*** Generate App Bundle 
```./gradlew :app:bundleDebug```
***Step 4:*** Run bundletool to generate apks
```bundletool build-apks --local-testing --bundle app-debug.aab --output app-debug.apks```
***Step 5:*** Connect device and install apks
```bundletool install-apks --apks app-debug.apks```


**Currently, getting below error when trying to launch the Sceneform Activity:**

```
Invalid ID 0x00000000.2023-04-11 15:56:02.798  9148-9148  AndroidRuntime        
com.acedev.dynamicfeature.debug      
D  Shutting down VM2023-04-11   E  FATAL EXCEPTION: main
Process: com.acedev.dynamicfeature.debug, PID: 9148                                                                                                   
android.view.InflateException: Binary XML file line #6 in 
com.acedev.dynamicfeature.debug.sceneform_dynamic_module:layout/sceneform_ux_fragment_layout: 
Binary XML file line #6 in com.acedev.dynamicfeature.debug.sceneform_dynamic_module:layout/sceneform_ux_fragment_layout: 
Error inflating class 
com.google.ar.sceneform.ArSceneView
Caused by: android.view.InflateException: Binary XML file line #6 in 
com.acedev.dynamicfeature.debug.sceneform_dynamic_module:layout/sceneform_ux_fragment_layout: 
Error inflating class com.google.ar.sceneform.ArSceneView
Caused by: 
java.lang.reflect.InvocationTargetException
   at java.lang.reflect.Constructor.newInstance0(Native Method)
   at java.lang.reflect.Constructor.newInstance(Constructor.java:343 undefined)
   at android.view.LayoutInflater.createView(LayoutInflater.java:858 undefined)
   at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:1010 undefined)
   at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:965 undefined)
   at android.view.LayoutInflater.rInflate(LayoutInflater.java:1127 undefined)
   at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:1088 undefined)
   at android.view.LayoutInflater.inflate(LayoutInflater.java:686 undefined)
   at android.view.LayoutInflater.inflate(LayoutInflater.java:538 undefined)
   at com.google.ar.sceneform.ux.BaseArFragment.onCreateView(BaseArFragment.java:184 undefined)
   at androidx.fragment.app.Fragment.performCreateView(Fragment.java:2963 undefined)
   at androidx.fragment.app.FragmentStateManager.createView(FragmentStateManager.java:518 undefined)
   at androidx.fragment.app.FragmentStateManager.moveToExpectedState(FragmentStateManager.java:282 undefined)
   at androidx.fragment.app.FragmentStore.moveToExpectedState(FragmentStore.java:112 undefined)
   at androidx.fragment.app.FragmentManager.moveToState(FragmentManager.java:1647 undefined)
   at androidx.fragment.app.FragmentManager.dispatchStateChange(FragmentManager.java:3128 undefined)
   at androidx.fragment.app.FragmentManager.dispatchActivityCreated(FragmentManager.java:3072 undefined)
   at androidx.fragment.app.FragmentController.dispatchActivityCreated(FragmentController.java:251 undefined)
   at androidx.fragment.app.FragmentActivity.onStart(FragmentActivity.java:502 undefined)
   at androidx.appcompat.app.AppCompatActivity.onStart(AppCompatActivity.java:251 undefined)
   at android.app.Instrumentation.callActivityOnStart(Instrumentation.java:1543 undefined)
   at android.app.Activity.performStart(Activity.java:8366 undefined)
   at android.app.ActivityThread.handleStartActivity(ActivityThread.java:3666 undefined)
   at android.app.servertransaction.TransactionExecutor.performLifecycleSequence(TransactionExecutor.java:224 undefined)
   at android.app.servertransaction.TransactionExecutor.cycleToPath(TransactionExecutor.java:204 undefined)
   at android.app.servertransaction.TransactionExecutor.executeLifecycleState(TransactionExecutor.java:176 undefined)
   at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:97 undefined)
   at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2303 undefined)
   at android.os.Handler.dispatchMessage(Handler.java:106 undefined)
   at android.os.Looper.loopOnce(Looper.java:201 undefined)
   at android.os.Looper.loop(Looper.java:288 undefined)
   at android.app.ActivityThread.main(ActivityThread.java:7884 undefined)
   at java.lang.reflect.Method.invoke(Native Method)
   at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548 undefined)
   at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:936 undefined)                                                                                                    
Caused by: android.content.res.Resources$NotFoundException: Unable to find resource ID #0x0
   at android.content.res.ResourcesImpl.getResourceTypeName(ResourcesImpl.java:281 undefined)
   at android.content.res.Resources.getResourceTypeName(Resources.java:2319 undefined)
   at com.google.ar.sceneform.utilities.LoadHelper.fromResource(LoadHelper.java:90 undefined)
   at com.google.ar.sceneform.rendering.Texture$Builder.setSource(Texture.java:153 undefined)
   at com.google.ar.sceneform.rendering.PlaneRenderer.loadPlaneMaterial(PlaneRenderer.java:339 undefined)
   at com.google.ar.sceneform.rendering.PlaneRenderer.<init>(PlaneRenderer.java:84 undefined)
   at com.google.ar.sceneform.ArSceneView.initializeAr(ArSceneView.java:561 undefined)
 ```
