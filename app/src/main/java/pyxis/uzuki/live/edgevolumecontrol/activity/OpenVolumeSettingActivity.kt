package pyxis.uzuki.live.edgevolumecontrol.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity

/**
 * EdgeVolumeControl
 * Class: OpenVolumeSettingActivity
 * Created by Pyxis on 12/31/17.
 *
 * Description:
 */

class OpenVolumeSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivityForResult(Intent(Settings.ACTION_SOUND_SETTINGS), 0)
        finish()
    }
}