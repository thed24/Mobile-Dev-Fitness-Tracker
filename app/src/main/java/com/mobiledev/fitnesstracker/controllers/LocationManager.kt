package com.mobiledev.fitnesstracker.controllers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.mobiledev.fitnesstracker.R

class LocationManager {
    private val debugTag = "MAIN ACTIVITY"
    private val requestPermissionRequestCode = 34
    private val requestInterval: Long = 10000
    private val fastestRequestInterval = requestInterval / 2
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    fun getLastLocation(
        fusedLocationClient: FusedLocationProviderClient,
        locationFunction: (m: Location) -> Unit
    ) {
        mLocationCallback = object : LocationCallback() {}
        createLocationRequest()
        fusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            callSnackBar({ startLocationPermissionRequest(activity) }, activity)
        } else {
            startLocationPermissionRequest(activity)
        }
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = requestInterval
        mLocationRequest.fastestInterval = fastestRequestInterval
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun startLocationPermissionRequest(context: Activity) {
        ActivityCompat.requestPermissions(
            context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestPermissionRequestCode
        )
    }

    private fun callSnackBar(
        listener: View.OnClickListener? = null,
        activity: Activity
    ) {
        val snackStrId = R.string.permission_rationale
        val actionStrId = android.R.string.ok

        val snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            activity.getString(snackStrId),
            BaseTransientBottomBar.LENGTH_INDEFINITE
        )
        if (listener != null) {
            snackbar.setAction(activity.getString(actionStrId), listener)
        }
        snackbar.show()
    }
}
