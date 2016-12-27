package me.sweetll.evilhide.fragment

import android.content.ComponentName
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceFragment
import me.sweetll.evilhide.MainActivity

import me.sweetll.evilhide.R
import me.sweetll.evilhide.config.Settings

class SettingFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == Settings.KEY_PREF_INVISIBLE) {
            val selfInvisible = sharedPreferences.getBoolean(key, false)
            val p = activity.packageManager
            val componentName = ComponentName(activity, MainActivity::class.java)
            when (selfInvisible) {
                true -> handler.postDelayed({
                    p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0)
                }, 1000)
                false -> p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
            }
        }
    }
}
