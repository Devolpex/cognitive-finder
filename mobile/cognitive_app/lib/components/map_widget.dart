import 'package:cognitive_app/utils/keys.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';

enum MapStyle {
  GoogleRoadMap,
  GoogleHybrid,
}

class MapWidget extends StatelessWidget {
  final LatLng center;
  final double currentZoom;
  final MapController mapController;
  final List<Marker> markers;
  final List<Polyline> polylines; // Add this
  final Function(double) onZoomChanged;
  final MapStyle currentMapStyle;

  const MapWidget({
    super.key,
    required this.center,
    required this.currentZoom,
    required this.mapController,
    required this.markers,
    required this.polylines, // Include it in constructor
    required this.onZoomChanged,
    required this.currentMapStyle,
  });

  @override
  Widget build(BuildContext context) {
    return FlutterMap(
      mapController: mapController,
      options: MapOptions(
        initialCenter: center,
        initialZoom: currentZoom,
        minZoom: 3.0,
        maxZoom: 20.0,
        onPositionChanged: (position, hasGesture) {
          if (hasGesture) {
            onZoomChanged(position.zoom);
          }
        },
      ),
      children: [
        TileLayer(
          urlTemplate: styleToUrl(currentMapStyle),
          subdomains: const ['a', 'b', 'c'],
        ),
        MarkerLayer(
          markers: markers,
        ),
        PolylineLayer( // Add this layer to draw polylines
          polylines: polylines,
        ),
      ],
    );
  }

  String styleToUrl(MapStyle style) {
    switch (style) {
      case MapStyle.GoogleRoadMap:
        return 'https://mt0.google.com/vt/lyrs=m&x={x}&y={y}&z={z}&key=$googleApiKey';
      case MapStyle.GoogleHybrid:
        return 'https://mt0.google.com/vt/lyrs=y&x={x}&y={y}&z={z}&key=$googleApiKey';
      default:
        return 'https://mt0.google.com/vt/lyrs=m&x={x}&y={y}&z={z}&key=$googleApiKey';
    }
  }
}
