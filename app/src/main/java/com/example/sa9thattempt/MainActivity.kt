package com.example.sa9thattempt

/**
 * In this project we will provide access to device's sensors data in a graphical manner.
 * To create charts to show the sensors event data we will be using Philipp Jahoda's lib MPAndroidChart at
 * https://github.com/PhilJay/MPAndroidChart
 */
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sa9thattempt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sensorManager : SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * We will use the bindings approach instead of the R.id.view model
         */
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager= LinearLayoutManager(this)
        /**
         * initializing sensorManager before calling [SensorAdapter] constructor
         * so we can pass-on the device's sensor list
         */
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        recyclerView.adapter = SensorAdapter(sensorManager.getSensorList(Sensor.TYPE_ALL))
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }
}