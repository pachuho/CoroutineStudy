package com.pachuho.coroutinestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addJobLaunch()
    }

    private fun addJobLaunch(){
        CoroutineScope(Dispatchers.Main).launch{
            val deferredInt = async {
                val result = 2
                println(result)
                result
            }

            val value = deferredInt.await()
            println(value)
        }
    }
}