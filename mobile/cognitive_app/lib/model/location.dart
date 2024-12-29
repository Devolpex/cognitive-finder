class Location {
  final int? id;
  final LocationAttributes? attributes;
  final int? deviceId;
  final String? protocol;
  final DateTime? serverTime;
  final DateTime? deviceTime;
  final DateTime? fixTime;
  final bool? outdated;
  final bool? valid;
  final double? latitude;
  final double? longitude;
  final double? altitude;
  final double? speed;
  final double? course;
  final String? address;
  final double? accuracy;
  final dynamic network;
  final dynamic geofenceIds; // Could be a list if the server returns a list

  Location({
    this.id,
    this.attributes,
    this.deviceId,
    this.protocol,
    this.serverTime,
    this.deviceTime,
    this.fixTime,
    this.outdated,
    this.valid,
    this.latitude,
    this.longitude,
    this.altitude,
    this.speed,
    this.course,
    this.address,
    this.accuracy,
    this.network,
    this.geofenceIds,
  });

  factory Location.fromJson(Map<String, dynamic> json) {
    return Location(
      id: json['id'],
      attributes: json['attributes'] != null
          ? LocationAttributes.fromJson(json['attributes'])
          : null,
      deviceId: json['deviceId'],
      protocol: json['protocol'],
      serverTime: json['serverTime'] != null
          ? DateTime.parse(json['serverTime'])
          : null,
      deviceTime: json['deviceTime'] != null
          ? DateTime.parse(json['deviceTime'])
          : null,
      fixTime: json['fixTime'] != null
          ? DateTime.parse(json['fixTime'])
          : null,
      outdated: json['outdated'],
      valid: json['valid'],
      latitude: (json['latitude'] != null) ? json['latitude'].toDouble() : null,
      longitude: (json['longitude'] != null) ? json['longitude'].toDouble() : null,
      altitude: (json['altitude'] != null) ? json['altitude'].toDouble() : null,
      speed: (json['speed'] != null) ? json['speed'].toDouble() : null,
      course: (json['course'] != null) ? json['course'].toDouble() : null,
      address: json['address'],
      accuracy: (json['accuracy'] != null) ? json['accuracy'].toDouble() : null,
      network: json['network'],
      geofenceIds: json['geofenceIds'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'attributes': attributes?.toJson(),
      'deviceId': deviceId,
      'protocol': protocol,
      'serverTime': serverTime?.toIso8601String(),
      'deviceTime': deviceTime?.toIso8601String(),
      'fixTime': fixTime?.toIso8601String(),
      'outdated': outdated,
      'valid': valid,
      'latitude': latitude,
      'longitude': longitude,
      'altitude': altitude,
      'speed': speed,
      'course': course,
      'address': address,
      'accuracy': accuracy,
      'network': network,
      'geofenceIds': geofenceIds,
    };
  }
}

class LocationAttributes {
  final double? batteryLevel;
  final double? distance;
  final double? totalDistance;
  final bool? motion;

  LocationAttributes({
    this.batteryLevel,
    this.distance,
    this.totalDistance,
    this.motion,
  });

  factory LocationAttributes.fromJson(Map<String, dynamic> json) {
    return LocationAttributes(
      batteryLevel:
          (json['batteryLevel'] != null) ? json['batteryLevel'].toDouble() : null,
      distance: (json['distance'] != null) ? json['distance'].toDouble() : null,
      totalDistance: (json['totalDistance'] != null)
          ? json['totalDistance'].toDouble()
          : null,
      motion: json['motion'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'batteryLevel': batteryLevel,
      'distance': distance,
      'totalDistance': totalDistance,
      'motion': motion,
    };
  }
}