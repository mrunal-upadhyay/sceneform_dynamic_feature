# Use Sceneform as a Dynamic Feature Module

In order to reduce app size and deliver sceneform dependencies to limited set of devices and users who want to use it. This is an attempt to bundle sceneform as a seperate on demand dynamic module using Android feature delivery which is delivered through App Bundles.

The application consists of primary app module and sceneform_dynamic_module. When the user clicks on Start Sceneform Activity, app attempts to download the sceneform dependency and launch an AR fragment. 

Currently, getting below error when trying to launch the Sceneform Activity:
