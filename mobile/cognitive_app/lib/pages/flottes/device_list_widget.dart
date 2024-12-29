// import 'dart:ui'; // Import for blurring
// import 'package:flutter/material.dart';
// import 'package:cognitive_app/utils/colors.dart';
// import 'package:latlong2/latlong.dart';

// class DeviceListWidget extends StatelessWidget {
//   // final DevicesController devicesController;
//   // final PositionController positionController;
//   final String searchQuery;
//   final Function(LatLng) onDeviceTap;

//   const DeviceListWidget({
//     super.key,
//     // required this.devicesController,
//     // required this.positionController,
//     required this.searchQuery,
//     required this.onDeviceTap,
//   });

//   // Helper method to determine the status based on the position data
//   // Widget _getStatus(Position_model position) {
//   //   if (position.ignition == true && (position.speed ?? 0) > 0) {
//   //     return const Row(
//   //       children: [
//   //         Icon(Icons.directions_car, size: 16, color: Colors.green),
//   //         SizedBox(width: 5),
//   //         Text(
//   //           'En Marche',
//   //           style: TextStyle(fontSize: 14, color: Colors.green),
//   //         ),
//   //       ],
//   //     );
//   //   } else if (position.ignition == true && (position.speed ?? 0) == 0) {
//   //     return const Row(
//   //       children: [
//   //         Icon(Icons.directions_car, size: 16, color: Colors.orange),
//   //         SizedBox(width: 5),
//   //         Text(
//   //           'En Ralenti',
//   //           style: TextStyle(fontSize: 14, color: Colors.orange),
//   //         ),
//   //       ],
//   //     );
//   //   } else {
//   //     return const Row(
//   //       children: [
//   //         Icon(Icons.local_parking_sharp, size: 16, color: primary),
//   //         SizedBox(width: 5),
//   //         Text(
//   //           'En Parking',
//   //           style: TextStyle(fontSize: 14, color: primary),
//   //         ),
//   //       ],
//   //     );
//   //   }
//   // }

//   // Helper method to check if the device is expired
//   bool _isDeviceExpired(DateTime? expirationTime) {
//     if (expirationTime != null) {
//       return DateTime.now().isAfter(expirationTime);
//     }
//     return false;
//   }

//   // Helper method to format how long the device has been expired
//   String _getExpiredDuration(DateTime? expirationTime) {
//     if (expirationTime != null) {
//       final duration = DateTime.now().difference(expirationTime);
//       return '${duration.inDays}j ${duration.inHours % 24}h ${duration.inMinutes % 60}m';
//     }
//     return 'Unknown';
//   }

//   @override
//   Widget build(BuildContext context) {
//     // final filteredDevices = devicesController.Devices.where((device) {
//     //   return device.name!.toLowerCase().contains(searchQuery.toLowerCase());
//     // }).toList();

//     return ListView.builder(
//       // itemCount: filteredDevices.length,
//       itemBuilder: (context, index) {
//         // final device = filteredDevices[index];
//         // final position = positionController.getPositionByDeviceId(device.id!);

//         if (position == null) return const SizedBox.shrink();

//         bool isExpired = _isDeviceExpired(device.expirationTime);

//         return Stack(
//           children: [
//             // Main content of the list tile
//             ListTile(
//               leading: Image.asset(
//                 MapUtils.getCategoryImagePath(device.category),
//                 width: 40,
//                 height: 40,
//               ),
//               title: Text(device.name ?? 'Unknown Device'),
//               subtitle: Column(
//                 crossAxisAlignment: CrossAxisAlignment.start,
//                 children: [
//                   Text('${position.speed!.toStringAsFixed(2)} Km/h'),
//                   // _getStatus(position),
//                   Text(position.getDisplayAddress()),
//                 ],
//               ),
//               onTap: () {
//                 if (!isExpired) {
//                   onDeviceTap(LatLng(position.latitude!, position.longitude!));
//                 }
//               },
//             ),
//             // Blur effect for expired devices, keeping only the icon and name unblurred
//             if (isExpired)
//               Positioned.fill(
//                 child: ClipRRect(
//                   borderRadius: BorderRadius.circular(4),
//                   child: BackdropFilter(
//                     filter: ImageFilter.blur(sigmaX: 10, sigmaY: 10),
//                     child: Container(
//                       alignment: Alignment.centerLeft,
//                       padding: const EdgeInsets.all(10),
//                       color: Colors.black.withOpacity(1), // This controls the blur and overlay background
//                       child: Row(
//                         children: [
//                           // Icon container with transparent background
//                           Container(
//                             width: 40,
//                             height: 40,
//                             decoration: BoxDecoration(
//                               color: Colors.transparent, // Make background transparent
//                               borderRadius: BorderRadius.circular(10),
//                             ),
//                             // child: Image.asset(
//                             //   // MapUtils.getCategoryImagePath(device.category),
//                             //   fit: BoxFit.cover,
//                             // ),
//                           ),
//                           const SizedBox(width: 10),
//                           Column(
//                             crossAxisAlignment: CrossAxisAlignment.start,
//                             children: [
//                               Text(
//                                 device.name ?? "No Data",
//                                 style: const TextStyle(
//                                   color: Colors.white,
//                                   fontSize: 16,
//                                   fontWeight: FontWeight.bold,
//                                 ),
//                               ),
//                               const SizedBox(height: 5),
//                               Text(
//                                 'Expirer depuis ${_getExpiredDuration(device.expirationTime)}',
//                                 style: const TextStyle(
//                                   fontSize: 14,
//                                   color: Colors.red,
//                                   fontWeight: FontWeight.bold,
//                                 ),
//                               ),
//                             ],
//                           ),
//                         ],
//                       ),
//                     ),
//                   ),
//                 ),
//               ),
//           ],
//         );
//       },
//     );
//   }
// }
