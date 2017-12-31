package pyxis.uzuki.live.edgevolumecontrol.activity

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pyxis.uzuki.live.edgevolumecontrol.fragment.ConfigureFragment

/**
 * EdgeVolumeControl
 * Class: ConfigureActivity
 * Created by Pyxis on 12/31/17.
 *
 * Description:
 */

class ConfigureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment: Fragment = ConfigureFragment()
        fragmentManager.beginTransaction().replace(android.R.id.content, fragment, null).commit()
    }
}