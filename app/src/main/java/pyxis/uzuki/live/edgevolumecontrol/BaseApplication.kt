package pyxis.uzuki.live.edgevolumecontrol

import android.app.Application
import pyxis.uzuki.live.nyancat.NyanCatGlobal
import pyxis.uzuki.live.nyancat.config.LoggerConfig
import pyxis.uzuki.live.nyancat.config.TriggerTiming

/**
 * EdgeVolumeControl
 * Class: BaseApplication
 * Created by Pyxis on 12/31/17.
 *
 * Description:
 */

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = LoggerConfig(packageName, BuildConfig.DEBUG, TriggerTiming.ALL)
        NyanCatGlobal.breed(config)
    }
}