package com.mobiledev.fitnesstracker

import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mobiledev.fitnesstracker.controllers.ExerciseController
import com.mobiledev.fitnesstracker.controllers.ModalController
import com.mobiledev.fitnesstracker.domain.ExerciseAdapter
import com.mobiledev.fitnesstracker.domain.LocationManager
import kotlinx.android.parcel.Parcelize
import java.util.*
import java.util.concurrent.TimeUnit

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
    private lateinit var trackExerciseButton: Button
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

        val addExerciseButton = findViewById<Button>(R.id.addExerciseBtn)
        addExerciseButton.setOnClickListener {
            modalController.callCreateNewEntryForm(exerciseAdapter)
            exerciseAdapter.notifyDataSetChanged()
        }

        trackExerciseButton = findViewById(R.id.trackerBtn)
        trackExerciseButton.setOnClickListener {
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
            trackExerciseButton.text = "Start Tracking"
            var currentLocationTimeStamp = Pair(location, Calendar.getInstance().time)
            createDynamicRun(cachedLocationTimeStamp, currentLocationTimeStamp)
        } else {
            cachedLocationTimeStamp = Pair(location, Calendar.getInstance().time)
            trackExerciseButton.text = "Stop Tracking"
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
            FITNESSTYPE = FITNESSTYPE.RUNNING,
            pace = distance /  different.toFloat(),
        )

        exerciseController.addEntry(newItem)
        exerciseAdapter.notifyDataSetChanged()
    }
}