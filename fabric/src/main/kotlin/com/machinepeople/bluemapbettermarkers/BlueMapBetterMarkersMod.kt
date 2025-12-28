package com.machinepeople.bluemapbettermarkers

import com.machinepeople.bluemapbettermarkers.core.MarkerUpdates
import de.bluecolored.bluemap.api.BlueMapAPI
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import java.util.*
import java.util.function.Consumer

class BlueMapBetterMarkersMod : ModInitializer {

    companion object {
        const val MOD_ID = "bluemap-better-markers"
        val LOGGER = LoggerFactory.getLogger(MOD_ID)
    }

    private var timer: Timer? = null

    override fun onInitialize() {
        LOGGER.info("Initializing BlueMap Better Markers")

        BlueMapAPI.onEnable(Consumer { api ->
            LOGGER.info("BlueMap API enabled, starting marker update task...")
            
            val task = object : TimerTask() {
                override fun run() {
                    try {
                        for (map in api.maps) {
                            for ((setId, markerSet) in map.markerSets) {
                                for ((markerId, marker) in markerSet.markers) {
                                    MarkerUpdates.updateMarkerDetail(marker)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        LOGGER.error("Error updating markers", e)
                    }
                }
            }
            
            timer = Timer("BetterMarkersUpdateTask", true)
            timer?.scheduleAtFixedRate(task, 0, 5000) // Update every 5 seconds
        })

        BlueMapAPI.onDisable(Consumer {
            LOGGER.info("BlueMap API disabled, stopping marker update task...")
            timer?.cancel()
            timer = null
        })
    }
}

