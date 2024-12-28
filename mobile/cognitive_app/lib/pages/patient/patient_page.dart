import 'package:cognitive_app/model/patient.dart';
import 'package:cognitive_app/services/keycloak_service.dart';
import 'package:cognitive_app/utils/colors.dart';
import 'package:cognitive_app/utils/helpers.dart';
import 'package:flutter/material.dart';
import 'package:cognitive_app/services/patient_service.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class PatientPage extends StatelessWidget {
  final String accessToken;

  const PatientPage({required this.accessToken, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Map<String, dynamic>? userInfo;
    try {
      userInfo = KeycloakService.decodeAccessToken(accessToken);
    } catch (e) {
      // Handle invalid token
            // Handle invalid token
      return Scaffold(
        appBar: AppBar(
          title: const Text("Patient"),
          backgroundColor: Colors.deepPurple,
        ),
        body: Center(
          child: Text(
            "Invalid token. Please sign in again.",
            style: TextStyle(fontSize: 18.sp, color: Colors.red),
          ),
        ),
      );
    }
    return Scaffold(
      appBar: AppBar(
        title: const Text("Patient"),
        backgroundColor: primary,
        foregroundColor: Colors.white,
      ),
      body: FutureBuilder<List<Patient>>(
        future: PatientService().getPatientsPage(accessToken),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(
              child: Text(
                "An error occurred: ${snapshot.error}",
                style: TextStyle(color: Colors.red),
              ),
            );
          } else {
            final patients = snapshot.data ?? [];
            return SingleChildScrollView(
              padding: EdgeInsets.symmetric(horizontal: 10.w),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: patients.map((patient) => Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      "Name: ${patient.name}",
                      style: TextStyle(fontSize: 16.sp),
                    ),
                    addVerticalSpace(10),
                  ],
                )).toList(),
              ),
            );
          }
        }
      ),
    );
  }
}
