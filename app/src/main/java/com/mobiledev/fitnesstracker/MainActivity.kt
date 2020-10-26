package com.mobiledev.fitnesstracker

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.mobiledev.fitnesstracker.controllers.ExerciseController
import com.mobiledev.fitnesstracker.controllers.ModalController
import com.mobiledev.fitnesstracker.domain.ExerciseAdapter
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class ExerciseItem(
    var id: Int,
    val distance: Float,
    val timeSpent: Float,
    val FITNESSTYPE: FITNESSTYPE,
    val pace: Float
) : Parcelable

enum class FITNESSTYPE {
    WALKING, RUNNING
}

class MainActivity : AppCompatActivity() {
    private var exerciseListItems = mutableListOf<ExerciseItem>()
    private var exerciseController = ExerciseController(exerciseListItems)
    private var modalController = ModalController(this, exerciseController)
    private var exerciseAdapter =
        ExerciseAdapter(exerciseListItems, exerciseController, modalController)

    private var isTracking = false
    private val TAG = "Testing"
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var firstLocationTimeStamp: Pair<Location, Date>
    private lateinit var lastLocationTimeStamp: Pair<Location, Date>

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addExerciseButton = findViewById<Button>(R.id.addExerciseBtn)
        addExerciseButton.setOnClickListener {
            modalController.callCreateNewEntryForm(exerciseAdapter)
            exerciseAdapter.notifyDataSetChanged()
        }

        val trackExerciseButton = findViewById<Button>(R.id.trackerBtn)
        trackExerciseButton.setOnClickListener {
            getLastLocation(isTracking)
            if (isTracking) {
                trackExerciseButton.text = "Start Tracking"
            } else {
                trackExerciseButton.text = "Stop Tracking"
            }
            isTracking = !isTracking
        }

        var context = this
        findViewById<RecyclerView>(R.id.exerciseList).apply {
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(isTracking: Boolean) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnCompleteListener { taskLocation ->
                if (taskLocation.isSuccessful && taskLocation.result != null) {
                    val location = taskLocation.result
                    if (location != null && isTracking) {
                        lastLocationTimeStamp = Pair(location, Calendar.getInstance().time)
                        createDynamicRun(firstLocationTimeStamp, lastLocationTimeStamp)
                    }
                    if (location != null && !isTracking) {
                        firstLocationTimeStamp = Pair(location, Calendar.getInstance().time)
                    }
                    fusedLocationClient.flushLocations()
                } else {
                    Log.d(TAG, taskLocation.exception.toString())
                    showSnackbar(R.string.no_location_detected)
                }
            }
        }

    private fun showSnackbar(
        snackStrId: Int,
        actionStrId: Int = 0,
        listener: View.OnClickListener? = null
    ) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content), getString(snackStrId),
            BaseTransientBottomBar.LENGTH_INDEFINITE
        )
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(getString(actionStrId), listener)
        }
        snackbar.show()
    }

    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar(R.string.permission_rationale, android.R.string.ok, View.OnClickListener {
                startLocationPermissionRequest()
            })

        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> Log.i(TAG, "User interaction was cancelled.")

                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation(isTracking)

                else -> {
                    showSnackbar(R.string.permission_denied_explanation, R.string.settings
                    ) {
                        val intent = Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun createDynamicRun(
        firstLocation: Pair<Location, Date>,
        lastLocation: Pair<Location, Date>
    ) {
        val distance = firstLocation.first.distanceTo(lastLocation.first)
        val timeSpentMS = lastLocation.second.time - firstLocation.second.time
        val timeSpent = timeSpentMS / 1000

        val newItem = ExerciseItem(
            id = exerciseListItems.size,
            distance = distance,
            timeSpent = timeSpent.toFloat(),
            FITNESSTYPE = FITNESSTYPE.RUNNING,
            pace = distance / (timeSpent / 60),
        )

        exerciseController.addEntry(newItem)
        exerciseAdapter.notifyDataSetChanged()
    }
}