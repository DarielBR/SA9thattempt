package com.example.sa9thattempt

import android.content.Intent
import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the [RecyclerView] in [MainActivity]
 */
class SensorAdapter(private val sensorList:List<Sensor>) :
    RecyclerView.Adapter<SensorAdapter.SensorViewHolder>(){

    class SensorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return SensorViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return sensorList.size
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val item = sensorList[position]
        holder.button.text = item.name.toString()
        /**
         * Creating a click listener for the button in the SensorViewHolder
         * the selected item will be passed-on in the bundle extras to the detail activity
         */
        holder.button.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context,DetailActivity::class.java)
                .putExtra(DetailActivity.SELECTED,holder.button.text.toString())
            context.startActivity(intent)
        }
    }

}