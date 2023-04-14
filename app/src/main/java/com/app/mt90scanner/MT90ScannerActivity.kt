package com.app.mt90_scanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MT90ScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("MT90 Scanner")

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        binding.scannedtxt?.setOnClickListener(View.OnClickListener {
//            intent = Intent("nlscan.action.SCANNER_TRIG")
//            intent.putExtra("SCAN_TIMEOUT", 4) // SCAN_TIMEOUT value: int, 1-9; unit: second
//            intent.putExtra("SCAN_TYPE ", 1) // SCAN_TYPE: read one barcodes during a scan attempt
            val intent = Intent("nlscan.action.SCANNER_TRIG")
            intent.putExtra("SCAN_TIMEOUT", 4) // SCAN_TIMEOUT value: int, 1-9; unit: second.
            intent.putExtra("SCAN_TYPE ", 1) // SCAN_TYPE: read two barcodes during a scan attempt.
            ReadPart(intent)
        })


    }

    fun ReadPart(intent: Intent?) {
        sendBroadcast(intent)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent != null) {
                    val barcodePart = intent.getStringExtra("SCAN_BARCODE1")
                    val barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1)
                    val scanStatus = intent.getStringExtra("SCAN_STATE")
                    if ("ok" == scanStatus) {
                        if (barcodePart != null) {
                            binding.scannedtxt!!.text = barcodePart
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Barcode Not getting.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "not using a proper barcode...",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } else {
                    Toast.makeText(applicationContext, "Scan Failed.", Toast.LENGTH_SHORT).show()

                }
            }
        }, IntentFilter("nlscan.action.SCANNER_RESULT"))
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}

