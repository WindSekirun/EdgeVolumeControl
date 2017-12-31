package pyxis.uzuki.live.edgevolumecontrol

import android.content.Context
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailManager
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailProvider

/**
 * EdgeVolumeControl
 * Class: VolumePlusProvider
 * Created by Pyxis on 12/31/17.
 *
 * Description:
 */

class VolumePlusProvider : SlookCocktailProvider() {

    override fun onUpdate(context: Context?, cocktailManager: SlookCocktailManager?, cocktailIds: IntArray?) {
        panelUpdate(context, cocktailManager, cocktailIds);
    }

    fun panelUpdate(context: Context?, cocktailManager: SlookCocktailManager?, cocktailIds: IntArray?) {

    }
}