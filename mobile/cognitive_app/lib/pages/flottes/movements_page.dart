import 'package:cognitive_app/components/map_widget.dart';
import 'package:cognitive_app/pages/flottes/patient_list_widget.dart';
import 'package:cognitive_app/services/patient_service.dart';
import 'package:cognitive_app/utils/colors.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';

class MovementsPage extends StatefulWidget {
  const MovementsPage({super.key});

  @override
  State<MovementsPage> createState() => _MovementsPageState();
}

class _MovementsPageState extends State<MovementsPage> {
  final LatLng _center = const LatLng(31.6225, -7.9898);
  final MapController _mapController = MapController();
  final PatientService _patientService = PatientService();
  List<Marker> _markers = [];
  double _currentZoom = 10.0;
  bool _showDeviceList = false;
  String _searchQuery = '';
  MapStyle _currentMapStyle = MapStyle.GoogleRoadMap;

  @override
  void initState() {
    super.initState();
    _fetchPatientsAndLocations();
  }

  // Fetch patients and locations, create markers and add them to the map
  Future<void> _fetchPatientsAndLocations() async {
    try {
      // Fetch patients
      final patients = await _patientService.getPatientsPage();

      // Fetch locations and create markers for each patient
      List<Marker> markers = [];
      for (var patient in patients) {
        try {
          final location = await _patientService.getLocationByPatientId(patient.id.toString());
          markers.add(_createMarkerForPatient(patient.name, location.latitude!, location.longitude!));
        } catch (e) {
          debugPrint("Error fetching location for patient ${patient.id}: $e");
        }
      }

      setState(() {
        _markers = markers;
      });
    } catch (e) {
      debugPrint("Error fetching patients or locations: $e");
    }
  }

  // Create a marker for a patient with its location
  Marker _createMarkerForPatient(String? patientId, double latitude, double longitude) {
    return Marker(
      point: LatLng(latitude, longitude),
      width: 80.sp,
      height: 80.sp,
      child: Builder(
        builder: (ctx) => Column(
          children: [
            Icon(
              Icons.person_pin_circle,
              color: Colors.red,
              size: 50.sp,
            ),
            Text(
              'ID: $patientId',
              style: TextStyle(fontSize: 12.sp, color: Colors.black),
            ),
          ],
        ),
      ),
    );
  }

  // Toggle the visibility of the device list
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
                // Toggle between map styles
                _currentMapStyle = _currentMapStyle == MapStyle.GoogleRoadMap
                    ? MapStyle.GoogleHybrid
                    : MapStyle.GoogleRoadMap;
              });
            },
          ),
        ],
      ),
      body: Stack(
        children: [
          // Map with markers
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
            currentMapStyle: MapStyle.GoogleRoadMap, // Set map style
            polylines: [],
          ),
          // Floating Action Button to toggle device list
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
          // Device List View
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
                    // Search Bar and Close Button
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
                              hintText: "Rechercher un patient ...",
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
                    Expanded(
                      child: PatientListWidget(searchQuery: _searchQuery),
                    ),
                    SizedBox(height: 5.h),
                  ],
                ),
              ),
            ),
        ],
      ),
    );
  }
}
