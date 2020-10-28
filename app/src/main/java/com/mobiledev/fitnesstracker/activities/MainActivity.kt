package com.mobiledev.fitnesstracker.activities

import android.location.Location
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mobiledev.fitnesstracker.R
import com.mobiledev.fitnesstracker.controllers.ExerciseController
import com.mobiledev.fitnesstracker.controllers.ModalController
import com.mobiledev.fitnesstracker.controllers.ExerciseAdapter
import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.persistence.ExerciseType
import com.mobiledev.fitnesstracker.controllers.LocationManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var cachedLocationTimeStamp: Pair<Location, Date>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var exerciseListItems = mutableListOf<ExerciseItem>()
    private var exerciseController = ExerciseController(exerciseListItems)
    private var modalController = ModalController(this, exerciseController)
    private var exerciseAdapter = ExerciseAdapter(
        exerciseListItems,
        exerciseController,
        modalController
    )
    private var locationManager = LocationManager()
    private var isTracking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addExerciseBtn.setOnClickListener {
            modalController.callCreateNewEntryForm(exerciseAdapter)
            exerciseAdapter.notifyDataSetChanged()
        }

        trackerBtn.setOnClickListener {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            locationManager.getLastLocation(fusedLocationClient, ::updateLocation)
        }

        var context = this
        findViewById<RecyclerView>(R.id.exerciseList).apply {
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!locationManager.checkPermissions(this)) {
            locationManager.requestPermissions(this)
        }
    }

    private fun updateLocation(location: Location){
        if (isTracking) {
            trackerBtn.text = "Start Tracking"
            var currentLocationTimeStamp = Pair(location, Calendar.getInstance().time)
            createDynamicRun(cachedLocationTimeStamp, currentLocationTimeStamp)
        } else {
            cachedLocationTimeStamp = Pair(location, Calendar.getInstance().time)
            trackerBtn.text = "Stop Tracking"
        }
        isTracking = !isTracking
    }

    private fun createDynamicRun(
        firstLocation: Pair<Location, Date>,
        lastLocation: Pair<Location, Date>
    ) {
        val distance = firstLocation.first.distanceTo(lastLocation.first)
        var different = TimeUnit.MILLISECONDS.toSeconds(lastLocation.second.time - firstLocation.second.time)

        val newItem = ExerciseItem(
            id = exerciseListItems.size,
            distance = distance,
            timeSpent = different.toFloat(),
            ExerciseType = ExerciseType.RUNNING,
            pace = distance /  different.toFloat(),
        )

        exerciseController.addEntry(newItem)
        exerciseAdapter.notifyDataSetChanged()
    }
}