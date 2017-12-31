package pyxis.uzuki.live.edgevolumecontrol.fragment

import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import pyxis.uzuki.live.edgevolumecontrol.R
import pyxis.uzuki.live.edgevolumecontrol.utils.findPreferenceObject
import pyxis.uzuki.live.richutilskt.utils.versionName


/**
 * EdgeVolumeControl
 * Class: ConfigureFragment
 * Created by Pyxis on 12/31/17.
 *
 *
 * Description:
 */
class ConfigureFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.volume_configure)

        init()
    }

    private fun init() {
        val mStreamSetting = findPreferenceObject<ListPreference>("stream_setting")
        mStreamSetting.setSummaryValue()
        mStreamSetting.setOnPreferenceChangeListener { _, newValue ->
            mStreamSetting.setSummaryValue(newValue as String)
            true
        }

        val version = findPreferenceObject<Preference>("version")
        version.summary = context.versionName()
    }

    private fun ListPreference.setSummaryValue(value: String = this.value) {
        val titles = resources.getStringArray(R.array.stream_setting_titles).toList()
        val values = resources.getStringArray(R.array.stream_setting_values).toList()
        val index = values.indexOf(value)
        this.summary = "Now you can set %s".format(titles[index])
    }

}
