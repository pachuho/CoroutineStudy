package com.pachuho.coroutinestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testAsync()
    }

    /**
     * async 예제 적용
     * 작업이 작은 경우엔 job2가 실행되기전에 끝나버려서 일부러 크게 둠
     */
    private fun testAsync(){
        val job3 = CoroutineScope(Dispatchers.IO).async {
            println("job3, start!")
            (1..10000).sortedByDescending { it }
            println("job3, end!")
        }

        val job1 = CoroutineScope(Dispatchers.Main).launch {
            println("job1, start!")

            val job3Result = job3.await()
            println("job1, end! $job3Result")
        }

        val job2 = CoroutineScope(Dispatchers.Main).launch {
            println("job2, start!")
            println("job2, end!")
        }
    }

    /**
     * Dispatcher 종류
     * Main: UI와 상호작용
     * IO: 네트워크 작업
     * Default: CPU를 많이 사용하는 작업
     */
    private fun addDispatcher(){
        CoroutineScope(Dispatchers.Main).launch { // 기본 Dispatcher 설정
            val gotDeferred: Deferred<Array<Int>> = async(Dispatchers.IO) {
                arrayOf(5, 4, 3, 2, 1)
            }

            val sortedDeferred: Deferred<List<Int>> = async(Dispatchers.Default) {
                val value = gotDeferred.await()
                value.sortedBy { it }
            }

            launch {
                val sortedArray = sortedDeferred.await()
                println("textViewSettingJob, value: $sortedArray")
            }
        }
    }

    /**
     * Dispatcher에 Coroutine 붙일때
     * launch: 결과 값 반환 X, 변환 타입 = Job
     * async: 결과 값 반환 O, 변환 타입 = Deferred<T>
     */
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