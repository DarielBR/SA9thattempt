package com.example.sa9thattempt

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sa9thattempt.databinding.ActivityDetailBinding
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class DetailActivity : AppCompatActivity() {

    companion object{
        //const val SENSOR_LIST = "sensorList"
        const val SELECTED = "sensorSelected"


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Retrieving the selected sensor from the Main Activity
         */
        val sensorSelected = intent?.extras?.getString(SELECTED).toString()

        /**
         * initializing an instance of SensorManager to obtain a list od device's sensors
         * and find our selected sensor in it.
         */
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val sensor = deviceSensors.find{it.name == sensorSelected}
        /**
         * Writing some sensor info into a Text View
         */
        val nameTextView = binding.nameTextview
        val valueTextView = binding.valueTextview
        val sensorInfo = "name: ${sensor?.name.toString()} \n" +
                      "vendor: ${sensor?.vendor.toString()} \n" +
                      "resolution: ${sensor?.resolution.toString()} \n" +
                      "max range: ${sensor?.maximumRange.toString()} \n"
        nameTextView.text = sensorInfo
        /**
         * instancing the Line Chart, the LineData object and the DataSet
         */
        var counter = 0F
        val sensorChart = binding.sensorChart
        //var sensorData = sensorChart.data
        /**
         * We will create three DataSets to store the 3 readings from the sensor event data
         * DataSet for Value 1
         */
        val dataSetA = LineDataSet(null, "Value 1")
        dataSetA.axisDependency = YAxis.AxisDependency.LEFT
        dataSetA.color = Color.MAGENTA
        dataSetA.fillColor = Color.GRAY
        dataSetA.setDrawCircles(false)
        dataSetA.setDrawValues(false)
        dataSetA.lineWidth = 2f
        dataSetA.addEntry(Entry(counter,0f))
        /**
         * DataSet for Value 2
         */
        val dataSetB = LineDataSet(null, "Value 2")
        dataSetB.axisDependency = YAxis.AxisDependency.LEFT
        dataSetB.color = Color.CYAN
        dataSetB.fillColor = Color.TRANSPARENT
        dataSetB.setDrawCircles(false)
        dataSetB.setDrawValues(false)
        dataSetB.lineWidth = 2f
        dataSetB.addEntry(Entry(counter,0f))
        /**
         * DataSet for Value 3
         */
        val dataSetC = LineDataSet(null, "Value 3")
        dataSetC.axisDependency = YAxis.AxisDependency.LEFT
        dataSetC.color = Color.YELLOW
        dataSetC.fillColor = Color.TRANSPARENT
        dataSetC.setDrawCircles(false)
        dataSetC.setDrawValues(false)
        dataSetC.lineWidth = 2f
        dataSetC.addEntry(Entry(counter,0f))
        /**
         * Creating the Sensor Event Listener
         * The method "onAccuracyChange" will remain inert for the purposes of this exercise
         */
        val sensorEventListener = object:SensorEventListener{
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

            override fun onSensorChanged(event: SensorEvent?) {
                event?.let{
                    //storing event data
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    //showing sensor event information in text view
                    val valueInfo = "value1: ${event.values[0]} \n" +
                                    "value2: ${event.values[1]} \n" +
                                    "value3: ${event.values[2]} \n"

                    valueTextView.text = valueInfo
                    dataSetA.addEntry(Entry(counter,x))
                    dataSetB.addEntry(Entry(counter,y))
                    dataSetC.addEntry(Entry(counter,z))
                    counter++

                    val sensorData = LineData(dataSetA)
                    sensorData.addDataSet(dataSetB)
                    sensorData.addDataSet(dataSetC)

                    sensorChart.data = sensorData
                    sensorChart.setVisibleXRangeMaximum(60.0F)
                    sensorData.notifyDataChanged()

                    sensorChart.notifyDataSetChanged()
                    sensorChart.moveViewToX(counter)
                    sensorChart.invalidate()
                }
            }
        }
        //registering the even listener
        sensorManager.registerListener(sensorEventListener,sensor, SensorManager.SENSOR_DELAY_NORMAL)
        title = sensorSelected
    }
}