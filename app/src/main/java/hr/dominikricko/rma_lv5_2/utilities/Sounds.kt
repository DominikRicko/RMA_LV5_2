package hr.dominikricko.rma_lv5_2.utilities

import android.media.AudioAttributes
import android.media.SoundPool
import hr.dominikricko.rma_lv5_2.ApplicationContext
import hr.dominikricko.rma_lv5_2.R

class Sounds private constructor() {

    private val soundpool: SoundPool
    private val markerSoundId: Int

    init {
        val soundpoolBuilder = SoundPool.Builder()
        val soundAttributesBuilder = AudioAttributes.Builder()

        soundAttributesBuilder.setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
        soundAttributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)

        soundpoolBuilder.setAudioAttributes(soundAttributesBuilder.build())
        soundpoolBuilder.setMaxStreams(1)
        soundpool = soundpoolBuilder.build()

        markerSoundId = soundpool.load(ApplicationContext.context, R.raw.click, 0)
    }

    companion object {
        private lateinit var instance: Sounds

        @JvmName("getInstance1")
        fun getInstance(): Sounds {
            if (!this::instance.isInitialized) instance = Sounds()
            return instance
        }
    }

    fun playMarkerSound() {
        soundpool.play(markerSoundId, 0.8f, 0.8f, 0, 0, 1.0f)
    }

}