package com.mobiledev.fitnesstracker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class LocationManager {
    private val debugTag = "MAIN ACTIVITY"
    private val requestPermissionRequestCode = 34

    @SuppressLint("MissingPermission")
    fun getLastLocation(fusedLocationClient: FusedLocationProviderClient, locationFunction: (m: Location) -> Unit) {
        fusedLocationClient.lastLocation.addOnCompleteListener { taskLocation ->
            if (taskLocation.isSuccessful && taskLocation.result != null) {
                val location = taskLocation.result
                if (location != null)
                    locationFunction(location)
            } else {
                Log.d(debugTag, taskLocation.exception.toString())
            }
        }
        Log.d(debugTag, "Listener added")
    }

    fun checkPermissions(activity: Activity) =
        ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    fun requestPermissions(activity: Activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            callSnackBar(
                R.string.permission_rationale,
                android.R.string.ok,
                { startLocationPermissionRequest(activity) },
                activity
            )
        } else {
            startLocationPermissionRequest(activity)
        }
    }

    private fun startLocationPermissionRequest(context: Activity) {
        ActivityCompat.requestPermissions(
            context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestPermissionRequestCode
        )
    }

    private fun callSnackBar(
        snackStrId: Int,
        actionStrId: Int = 0,
        listener: View.OnClickListener? = null,
        activity: Activity
    ) {
        val snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content), activity.getString(snackStrId),
            BaseTransientBottomBar.LENGTH_INDEFINITE
        )
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(activity.getString(actionStrId), listener)
        }
        snackbar.show()
    }
}