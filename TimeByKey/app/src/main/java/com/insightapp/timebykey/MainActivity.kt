package com.insightapp.timebykey

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.insightapp.timebykey.entity.TimeByKey
import com.insightapp.timebykey.ui.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var calIni = Calendar.getInstance()
    private var calFim = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        val adapter = this?.let { TimeAdapter(it, object:TimeAdapter.ItemSelectedListener {
            override fun onItemSelectedDel(item: TimeByKey) {
                val alertDialog = AlertDialog.Builder(this@MainActivity, 0)
                alertDialog.setMessage("Confirma cancelar este time?")
                alertDialog.setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                    viewModel.cancel(item.id)
                })
                alertDialog.setNegativeButton("Não", null)

                alertDialog.show()
            }

            override fun onItemSelectedAlt(item: TimeByKey) {
                val mDialogView = LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog, null)
                val mBuilder = AlertDialog.Builder(this@MainActivity).setView(mDialogView)

                val  mAlertDialog = mBuilder!!.show()

                mDialogView.btn_save.setOnClickListener {

                    viewModel.update(item)
                    mAlertDialog.dismiss()
                }
            }
        })}

        this.recyView.adapter = adapter
        this.recyView.layoutManager = LinearLayoutManager(this)

        viewModel.allTimeByKey.observe(this, Observer { time ->
            time?.let {
                adapter!!.setTimeByKey(time)
            }
        })


        btn_ini.setOnClickListener { view ->
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
            val  mAlertDialog = mBuilder!!.show()

            mDialogView.btn_save.setOnClickListener {
                calIni.set(
                    mDialogView.init_ano.text.toString().toInt(),
                    mDialogView.ini_mes.text.toString().toInt(),
                    mDialogView.ini_dia.text.toString().toInt(),
                    mDialogView.ini_horas.text.toString().toInt(),
                    mDialogView.ini_minutos.text.toString().toInt())
                val time = TimeByKey(0, "", calIni.time, calFim.time, "A")
                viewModel.insert(time)
                mAlertDialog.dismiss()
            }
        }

        btn_fim.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
            val  mAlertDialog = mBuilder!!.show()

            mDialogView.btn_save.setOnClickListener {
                calIni.set(
                    mDialogView.init_ano.text.toString().toInt(),
                    mDialogView.ini_mes.text.toString().toInt(),
                    mDialogView.ini_dia.text.toString().toInt(),
                    mDialogView.ini_horas.text.toString().toInt(),
                    mDialogView.ini_minutos.text.toString().toInt())
                val time = TimeByKey(0, "", calIni.time, calFim.time, "A")
                viewModel.insert(time)
                mAlertDialog.dismiss()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
        var hourOfDay: Int? = null
        var minute: Int? = null

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            this.hourOfDay = hourOfDay
            this.minute = minute
        }
    }
}


