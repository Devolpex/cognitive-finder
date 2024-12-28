import 'package:cognitive_app/components/map_widget.dart';
import 'package:cognitive_app/utils/colors.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';
import 'package:get/get.dart';
import 'package:provider/provider.dart';
import 'device_list_widget.dart';

class MovementsPage extends StatefulWidget {
  const MovementsPage({super.key});

  @override
  State<MovementsPage> createState() => _MovementsPageState();
}

class _MovementsPageState extends State<MovementsPage> {
  final LatLng _center = const LatLng(31.6225, -7.9898);
  final MapController _mapController = MapController();
  MapStyle _currentMapStyle = MapStyle.GoogleRoadMap;
  double _currentZoom = 10.0;
  bool _showDeviceList = false;
  String _searchQuery = '';

  // late DevicesController devicesController;
  // late PositionController positionController;

  final List<Marker> _markers = [];

  @override
  void initState() {
    super.initState();
    // final websocketService = Provider.of<WebSocketService>(context, listen: false);
    // devicesController = Get.put(DevicesController(websocketService));
    // positionController = Get.put(PositionController(websocketService));

    // _fetchDevicePositions();
    // _setupWebSocketListener();
  }

  // Future<void> _fetchDevicePositions() async {
  //   await devicesController.getDevicesList();
  //   await positionController.getPositions();
  //   _generateMarkers();
  // }

  // void _setupWebSocketListener() {
  //   final websocketService = Provider.of<WebSocketService>(context, listen: false);
  //   WidgetsBinding.instance.addPostFrameCallback((_) {
  //     websocketService.connect();
  //     websocketService.addListener(() {
  //       setState(() {
  //         _generateMarkers();
  //       });
  //     });
  //   });
  // }

  // void _generateMarkers() {
  //   final devices = devicesController.Devices;
  //   final positions = positionController.positions;
  //   setState(() {
  //     _markers = devices.where((device) {
  //       return device.expirationTime == null || DateTime.now().isBefore(device.expirationTime!);
  //     }).map((device) {
  //       final position = positions.firstWhere((pos) => pos.deviceId == device.id, orElse: () => Position_model());

  //       final iconPath = _getMarkerImage(position);
  //       final shouldRotate = position.ignition == true && (position.speed ?? 0) > 0;
  //       final rotationAngle = shouldRotate ? (position.course ?? 0) * (3.14159 / 180) : 0.0;

  //       return Marker(
  //         point: LatLng(position.latitude ?? 0, position.longitude ?? 0),
  //         width: 80.sp,
  //         height: 80.sp,
  //         child: Column(
  //           children: [
  //             Transform.rotate(
  //               angle: rotationAngle,
  //               child: Image.asset(
  //                 iconPath,
  //                 fit: BoxFit.contain,
  //                 width: 50.sp,
  //                 height: 50.sp,
  //               ),
  //             ),
  //             Text(
  //               device.name ?? 'Unknown',
  //               style: TextStyle(
  //                 fontSize: 12.sp,
  //                 color: Colors.black,
  //                 fontWeight: FontWeight.bold,
  //               ),
  //               overflow: TextOverflow.ellipsis,
  //             ),
  //           ],
  //         ),
  //       );
  //     }).toList();
  //   });
  // }

  // String _getMarkerImage(Position_model position) {
  //   if (position.ignition == true && (position.speed ?? 0) > 0) {
  //     return 'assets/images/en-marche-marker.png';
  //   } else if (position.ignition == true && (position.speed ?? 0) == 0) {
  //     return 'assets/images/en-ralenti-marker.png';
  //   } else {
  //     return 'assets/images/en-parking-marker.png';
  //   }
  // }

  void _toggleDeviceList() {
    setState(() {
      _showDeviceList = !_showDeviceList;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Flottes en mouvement"),
        backgroundColor: primary,
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            icon: const Icon(Icons.map),
            onPressed: () {
              setState(() {
                _currentMapStyle = _currentMapStyle == MapStyle.GoogleRoadMap
                    ? MapStyle.GoogleHybrid
                    : MapStyle.GoogleRoadMap;
              });
              print("Change Map Type to $_currentMapStyle");
            },
          ),
        ],
      ),
      body: Stack(
        children: [
          MapWidget(
            center: _center,
            currentZoom: _currentZoom,
            mapController: _mapController,
            markers: _markers,
            onZoomChanged: (newZoom) {
              setState(() {
                _currentZoom = newZoom;
              });
            },
            currentMapStyle: _currentMapStyle, polylines: [],
          ),
          Positioned(
            top: 10,
            left: 10,
            child: FloatingActionButton(
              backgroundColor: primary,
              onPressed: _toggleDeviceList,
              child: Icon(
                _showDeviceList ? Icons.list : Icons.list_outlined,
                color: Colors.white,
                size: 30.sp,
              ),
            ),
          ),
          if (_showDeviceList)
            Positioned(
              top: 60,
              left: 10,
              right: 10,
              bottom: 10,
              child: Container(
                padding: EdgeInsets.all(10.w),
                decoration: BoxDecoration(
                  color: Colors.white.withOpacity(0.9),
                  borderRadius: BorderRadius.circular(10.r),
                ),
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Expanded(
                          child: TextField(
                            onChanged: (value) {
                              setState(() {
                                _searchQuery = value;
                              });
                            },
                            decoration: const InputDecoration(
                              hintText: "Rechercher un véhicule ...",
                              prefixIcon: Icon(Icons.search),
                            ),
                          ),
                        ),
                        IconButton(
                          icon: const Icon(Icons.close, size: 45),
                          onPressed: _toggleDeviceList, // Close the device list
                          color: Colors.red,
                        ),
                      ],
                    ),
                    SizedBox(height: 10.h),
                    // Expanded(
                    //   child: DeviceListWidget(
                    //     // devicesController: devicesController,
                    //     // positionController: positionController,
                    //     searchQuery: _searchQuery,
                    //     onDeviceTap: (LatLng latLng) {
                    //       _mapController.move(latLng, 15.0);
                    //       _toggleDeviceList();
                    //     },
                    //   ),
                    // ),
                    SizedBox(height: 100.h),
                  ],
                ),
              ),
            ),
        ],
      ),
    );
  }
}
