package com.alpharays.apps.studio.vpnalpha.view.activites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.SkuDetails
import com.google.android.gms.ads.MobileAds
import com.alpharays.apps.studio.vpnalpha.AppSettings
import com.alpharays.apps.studio.vpnalpha.billing.BillingClass
import com.alpharays.apps.studio.vpnalpha.databinding.ActivitySplashBinding
import com.onesignal.OneSignal


class SplashActivity : AppCompatActivity(), BillingClass.BillingErrorHandler,
    BillingClass.SkuDetailsListener {

    private var binding: ActivitySplashBinding ?= null
    private var billingClass: BillingClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initGoogleAds()
        initOneSignal()
        initBilling()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun initGoogleAds() {
        MobileAds.initialize(
            this
        ) { }
    }

    private fun initOneSignal() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(AppSettings.oneSignalId);
    }

    private fun initBilling() {
        billingClass = BillingClass(this@SplashActivity)
        billingClass!!.setmCallback(this, this);
        billingClass!!.startConnection();
    }

    private fun startMainActivity() {
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this@SplashActivity, ControllerActivity::class.java))
            finish()
        }, 2000)
    }

    override fun displayErrorMessage(message: String?) {
        when {
            message.equals("done") -> {
                AppSettings.isUserPaid =
                    billingClass!!.isSubscribedToSubscriptionItem(AppSettings.one_month_subscription_id) ||
                            billingClass!!.isSubscribedToSubscriptionItem(AppSettings.three_month_subscription_id) ||
                            billingClass!!.isSubscribedToSubscriptionItem(AppSettings.one_year_subscription_id)

                startMainActivity()
            }
            message.equals("error") -> {
                AppSettings.isUserPaid = false;
                startMainActivity()
            }
            else -> {
                AppSettings.isUserPaid = false;
                startMainActivity()
            }
        }
    }

    override fun subscriptionsDetailList(skuDetailsList: MutableList<SkuDetails>?) {
    }
}