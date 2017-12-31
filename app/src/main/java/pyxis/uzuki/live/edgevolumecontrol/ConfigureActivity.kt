package pyxis.uzuki.live.edgevolumecontrol

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.samsung.android.sdk.SsdkUnsupportedException
import com.samsung.android.sdk.look.Slook
import pyxis.uzuki.live.richutilskt.utils.toast

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

        val slook = Slook()
        try {
            slook.initialize(this)
        } catch (e: SsdkUnsupportedException) {
            showErrToast("Sorry, your device doesn't support Slook SDK")
            return
        }

        if (!slook.isFeatureEnabled(Slook.COCKTAIL_PANEL)) {
            showErrToast("Sorry, your device doesn't support Panel Mode")
            return
        }


    }

    private fun showErrToast(message: String) {
        toast(message)
        finish()
    }
}