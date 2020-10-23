package com.flywith24.adclose

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onResume() {
        super.onResume()
        val serviceName = "CustomAccessibilityService"
        if (!isAccessibilitySettingsOn(serviceName, this)) {
            openAccessibility(serviceName, this);
        }
    }

    /**
     * 该辅助功能开关是否打开了
     *
     * @param serviceName：指定辅助服务名字
     * @param context：上下文
     * @return
     */
    private fun isAccessibilitySettingsOn(serviceName: String, context: Context): Boolean {
        var accessibilityEnable = 0
        try {
            accessibilityEnable = Settings.Secure.getInt(context.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED, 0)
        } catch (e: Exception) {
            Log.e("TAG", "get accessibility enable failed, the err:" + e.message)
        }
        if (accessibilityEnable == 1) {
            val mStringColonSplitter = SimpleStringSplitter(':')
            val settingValue: String = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            mStringColonSplitter.setString(settingValue)
            while (mStringColonSplitter.hasNext()) {
                val accessibilityService = mStringColonSplitter.next()
                if (accessibilityService.equals("${context.packageName}/${serviceName}", ignoreCase = true)) {
                    Log.v("TAG", "We've found the correct setting - accessibility is switched on!")
                    return true
                }
            }
        } else {
            Log.d("TAG", "Accessibility service disable")
        }
        return false
    }

    /**
     * 跳转到系统设置页面开启辅助功能
     *
     * [serviceName] 指定辅助服务名字
     * [context] 上下文
     */
    private fun openAccessibility(serviceName: String, context: Context) {
        if (!isAccessibilitySettingsOn(serviceName, context)) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
    }
}