package com.hos_dvk.easyphone.query

/**class CameraQuery {
    fun getPackageName(context: Context): String {

        var packageNameCamera = ""

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val pkgAppsList = context.packageManager.queryIntentActivities(mainIntent, 0)

        for (packageInfo in pkgAppsList) {
            val appPackageName: String =  packageInfo.activityInfo.packageName

            if (appPackageName.lowercase().endsWith("camera") || appPackageName.lowercase().endsWith("camera2")) {
                packageNameCamera = appPackageName
                break
            }
        }

        if (packageNameCamera == "") {
            val packages = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

            for (packageInfo in packages) {
                val appPackageName: String =  packageInfo.packageName
                if (appPackageName.lowercase().endsWith("camera") || appPackageName.lowercase().endsWith("camera2")) {
                    packageNameCamera = appPackageName
                    break
                }
            }
            if (packageNameCamera == "") {
                for (packageInfo in pkgAppsList) {
                    val appPackageName: String =  packageInfo.activityInfo.packageName

                    if (appPackageName.lowercase().contains("camera") && !appPackageName.lowercase().endsWith("service")) {
                        packageNameCamera = appPackageName
                        break
                    }
                }

                if (packageNameCamera == "") {
                    val packages =
                        context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

                    for (packageInfo in packages) {
                        val appPackageName: String = packageInfo.packageName
                        if (appPackageName.lowercase().contains("camera") && !appPackageName.lowercase().endsWith("service")) {
                            packageNameCamera = appPackageName
                            break
                        }
                    }
                }
            }
        }

        return packageNameCamera
    }
}**/