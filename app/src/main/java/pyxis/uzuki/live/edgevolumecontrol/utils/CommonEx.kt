package pyxis.uzuki.live.edgevolumecontrol.utils

import android.preference.Preference
import android.preference.PreferenceFragment

/**
 * EdgeVolumeControl
 * Class: CommonEx
 * Created by Pyxis on 12/31/17.
 *
 * Description:
 */

fun <T: Preference> PreferenceFragment.findPreferenceObject(key: String) = this.findPreference(key) as T