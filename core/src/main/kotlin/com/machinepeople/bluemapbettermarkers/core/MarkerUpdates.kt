package com.machinepeople.bluemapbettermarkers.core

import de.bluecolored.bluemap.api.markers.HtmlMarker
import de.bluecolored.bluemap.api.markers.Marker
import de.bluecolored.bluemap.api.markers.POIMarker

object MarkerUpdates {

    fun updateMarkerDetail(marker: Marker) {
        val pos = marker.position
        val coordsString = String.format("<br><span style=\"font-size: 0.8em; color: #888;\">Coordinates: %d, %d, %d</span>", pos.x.toInt(), pos.y.toInt(), pos.z.toInt())
        
        if (marker is POIMarker) {
            val label = marker.label
            val detail = marker.detail
            
            if (detail == null) {
                marker.detail = label + coordsString
            } else if (!detail.contains("Coordinates:")) {
                marker.detail = detail + coordsString
            }
        } else if (marker is HtmlMarker) {
            val label = marker.label
            val detail = marker.html
            
            if (detail == null) {
                marker.html = label + coordsString
            } else if (!detail.contains("Coordinates:")) {
                marker.html = detail + coordsString
            }
        }
    }
}

