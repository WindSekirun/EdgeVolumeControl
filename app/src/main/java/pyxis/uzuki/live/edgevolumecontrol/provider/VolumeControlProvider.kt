package pyxis.uzuki.live.edgevolumecontrol.provider

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.widget.RemoteViews
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailManager
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailProvider
import pyxis.uzuki.live.edgevolumecontrol.Constants
import pyxis.uzuki.live.edgevolumecontrol.R
import pyxis.uzuki.live.nyancat.NyanCat
import pyxis.uzuki.live.richutilskt.utils.RPreference
import pyxis.uzuki.live.richutilskt.utils.audioManager
import pyxis.uzuki.live.richutilskt.utils.isEmpty


/**
 * EdgeVolumeControl
 * Class: VolumeControlProvider
 * Created by Pyxis on 12/31/17.
 *
 * Description:
 */

class VolumeControlProvider : SlookCocktailProvider() {
    private var mVolumeControlView: RemoteViews? = null

    /**
     * This method is called when the user adds the Edge Single Mode, Edge Single Plus Mode or the Edge Feeds
     * Mode. If necessary, it should perform the essential setup, such as defining event handlers for views and
     * starting a temporary Service.
     */
    override fun onUpdate(context: Context?, cocktailManager: SlookCocktailManager?, cocktailIds: IntArray?) {
        if (mVolumeControlView == null) {
            mVolumeControlView = createRemoteView(context ?: return)
        }

        cocktailManager?.updateCocktail(cocktailIds!![0], mVolumeControlView)
    }

    /**
     * This method is called for every broadcast and before each of the above callback methods.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val action = intent?.action ?: ""
        NyanCat.d("onReceive: $action")
        when (action) {
            Constants.ACTION_REMOTE_CLICK -> context?.performClickEvent(intent ?: return)
        }
    }

    private fun createRemoteView(context: Context): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.volume_control)
        remoteView.setOnClickPendingIntent(R.id.btnPlus, context.getClickIntent(R.id.btnPlus))
        remoteView.setOnClickPendingIntent(R.id.btnMinus, context.getClickIntent(R.id.btnMinus))
        remoteView.setOnClickPendingIntent(R.id.btnMute, context.getClickIntent(R.id.btnMute))
        remoteView.setOnClickPendingIntent(R.id.btnSetting, context.getClickIntent(R.id.btnSetting))
        return remoteView
    }

    private fun Context.getClickIntent(id: Int, key: Int = 0): PendingIntent {
        val clickIntent = Intent(Constants.ACTION_REMOTE_CLICK).apply {
            putExtra("id", id)
            putExtra("key", key)
        }

        return PendingIntent.getBroadcast(this, id, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun Context.performClickEvent(intent: Intent) {
        val id = intent.getIntExtra("id", -1)
        when (id) {
            R.id.btnPlus -> {
                changeVolume(1)
                updateUI()
            }

            R.id.btnMinus -> {
                changeVolume(-1)
                updateUI()
            }

            R.id.btnMute -> {
                changeVolume(isMute = true)
                updateUI()
            }

            R.id.btnSetting -> {
                sendBroadcast(Intent(Constants.ACTION_OPEN_VOLUME_SETTING))
            }
        }
    }

    private fun Context.changeVolume(changeValue: Int = 0, isMute: Boolean = false) {
        val original = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val changed = original + changeValue
        val lastBehavior = RPreference.getInstance(this).getString(Constants.KEY_LAST_BEHAVIOR)
        var nowBehavior = ""

        if (lastBehavior == Constants.LAST_BEHAVIOR_MUTE || audioManager.isStreamMute(AudioManager.STREAM_MUSIC)) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0)
        }

        if (isMute) {
            nowBehavior = Constants.LAST_BEHAVIOR_MUTE
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0)
        }

        if (changeValue < 0) {
            nowBehavior = Constants.LAST_BEHAVIOR_MINUS
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, changed, AudioManager.FLAG_SHOW_UI)
        }

        if (nowBehavior.isEmpty()) {
            nowBehavior = Constants.LAST_BEHAVIOR_PLUS
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, changed, AudioManager.FLAG_SHOW_UI)
        }

        RPreference.getInstance(this).put(Constants.KEY_LAST_BEHAVIOR to nowBehavior)
    }

    private fun Context.updateUI() {
        val lastBehavior = RPreference.getInstance(this).getString(Constants.KEY_LAST_BEHAVIOR)
        val cocktailIds = getCocktaiIds()
        if (mVolumeControlView == null) {
            mVolumeControlView = createRemoteView(this)
        }

        if (lastBehavior == Constants.LAST_BEHAVIOR_MUTE) {
            changeIcon(R.drawable.ic_volume_up)
        } else {
            changeIcon(R.drawable.ic_volume_off)
        }

        SlookCocktailManager.getInstance(this).updateCocktail(cocktailIds[0], mVolumeControlView)
    }

    private fun Context.getCocktaiIds() =
            SlookCocktailManager.getInstance(this).getCocktailIds(
                    ComponentName(this, VolumeControlProvider::class.java))

    private fun changeIcon(resId: Int, srcId: Int = R.id.btnMute) =
            mVolumeControlView?.setImageViewResource(srcId, resId)

    // unused methods

    /**
     * This method is called when the Edge Single Mode, Edge Single Plus Mode or the Edge Feeds Mode is
     * created for the first time. If you need to open a new database or perform setup, then this is a good place to
     * do it.
     */
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    /**
     * This method is called when the visibility of the Edge Single Mode, Edge Single Plus Mode or the Edge Feeds
     * Mode is changed.
     *
     * If the visibility is COCKTAIL_VISIBILITY_SHOW, the state of the Edge Single Mode, Edge Single Plus Mode or
     * the Edge Feeds Mode is shown. At that time, if you want to refresh your Edge Single Mode, Edge Single Plus
     * Mode or Edge Feeds Mode, you should call updateCocktail with a new RemoteViews. If you want the
     * callback method called, you have to define permitVisibilityChanged to true in the CocktailProviderInfo
     * (refer to section 3.3.2)
     */
    override fun onVisibilityChanged(context: Context?, cocktailId: Int, visibility: Int) {
        super.onVisibilityChanged(context, cocktailId, visibility)
    }

    /**
     * This method is called when the instance of your Edge Single Mode, Edge Single Plus Mode or Edge Feeds
     * Mode is deleted from the enabled list. This is where you should clean up any work done in
     * onEnabled(Context), such as delete a temporary database.
     */
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }
}