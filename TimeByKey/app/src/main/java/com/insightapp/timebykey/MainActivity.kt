package com.insightapp.timebykey

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.insightapp.timebykey.entity.TimeByKey
import com.insightapp.timebykey.ui.MainViewModel
import com.insightapp.timebykey.ui.TimeAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var calIni = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val adapter = this?.let {
            TimeAdapter(
                it,
                object :
                    TimeAdapter.ItemSelectedListener {
                    override fun onItemSelectedDel(item: TimeByKey) {
                        val alertDialog = AlertDialog.Builder(this@MainActivity, 0)
                        alertDialog.setMessage("Confirma cancelar este time?")
                        alertDialog.setPositiveButton("Sim") { dialog, which ->
                            viewModel.cancel(item.id)
                        }
                        alertDialog.setNegativeButton("Não", null)

                        alertDialog.show()
                    }

                    override fun onItemSelectedDateEnd(item: TimeByKey) {
                        val mDialogView =
                            LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog, null)
                        val mBuilder = AlertDialog.Builder(this@MainActivity).setView(mDialogView)
                        mDialogView.key_time.setText(item.key_time)
                        val mAlertDialog = mBuilder!!.show()
                        mDialogView.btn_save.setOnClickListener {
                            calIni.set(
                                mDialogView.init_ano.text.toString().toInt(),
                                mDialogView.ini_mes.text.toString().toInt(),
                                mDialogView.ini_dia.text.toString().toInt(),
                                mDialogView.ini_horas.text.toString().toInt(),
                                mDialogView.ini_minutos.text.toString().toInt()
                            )
                            item.fim = calIni.time
                            viewModel.update(item)
                            mAlertDialog.dismiss()
                        }
                    }

                    override fun onItemSelectedDateStart(item: TimeByKey) {
                        val mDialogView =
                            LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog, null)
                        val mBuilder = AlertDialog.Builder(this@MainActivity).setView(mDialogView)
                        mDialogView.key_time.setText(item.key_time)
                        val mAlertDialog = mBuilder!!.show()
                        mDialogView.btn_save.setOnClickListener {
                            calIni.set(
                                mDialogView.init_ano.text.toString().toInt(),
                                mDialogView.ini_mes.text.toString().toInt(),
                                mDialogView.ini_dia.text.toString().toInt(),
                                mDialogView.ini_horas.text.toString().toInt(),
                                mDialogView.ini_minutos.text.toString().toInt()
                            )
                            item.inicio = calIni.time
                            viewModel.update(item)
                            mAlertDialog.dismiss()
                        }
                    }
                })
        }

        this.recyView.adapter = adapter
        this.recyView.layoutManager = LinearLayoutManager(this)

        viewModel.allTimeByKey.observe(this, Observer { time ->
            time?.let {
                adapter!!.setTimeByKey(time)
            }
        })

        this.btn_add_date.setOnClickListener { view ->
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
                val key = mDialogView.key_time.text.toString()
                val time = TimeByKey(0, key, calIni.time, null, "A")
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
            R.id.action_settings -> {

                val mDialogView = LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog, null)
                val mBuilder = AlertDialog.Builder(this@MainActivity).setView(mDialogView)
                mDialogView.ini_horas.visibility = View.GONE;
                mDialogView.ini_minutos.visibility = View.GONE;
                mDialogView.ini_dia.visibility = View.GONE;
                mDialogView.ini_mes.visibility = View.GONE;
                mDialogView.init_ano.visibility = View.GONE;
                mDialogView.btn_save.setText("Informe key")
                mBuilder!!.show()


                mDialogView.btn_save.setOnClickListener {
                    GlobalScope.launch(Dispatchers.Main) {
                        val string = viewModel.getHoursByKey(mDialogView.key_time.text.toString())
                        Toast
                            .makeText(
                                this@MainActivity,
                                string,
                                Toast.LENGTH_LONG)
                            .show()
                    }

                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}


