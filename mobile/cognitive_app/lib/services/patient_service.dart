import 'package:cognitive_app/Model/token_auth.dart';
import 'package:cognitive_app/model/patient.dart';
import 'package:cognitive_app/model/token_auth.dart';
import 'package:cognitive_app/services/auth_service.dart';
import 'package:cognitive_app/services/keycloak_service.dart';
import 'package:cognitive_app/utils/keys.dart';
import 'package:hive/hive.dart';
import 'package:logger/logger.dart';
import 'package:dio/dio.dart';

class PatientService{
  final Dio dio = Dio();
  final Logger logger = Logger();
  final AuthService authService = AuthService();
  
  // Method to fetch a paginated list of Patient
  Future<List<Patient>> getPatientsPage(String accessToken) async {
    // Get the access token 
    try {
    final String uri = "$backendUrl/api/v1/patients";
    logger.i("token2: $accessToken");

    // Send POST request with JSON body
    final response = await dio.get(
      uri,
      options: Options(headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $accessToken',
      }),
    );
    
    logger.i("Response: ${response.data}");
    if (response.statusCode == 200) {
      // Extract the list of demandes from the 'content' field
      List<dynamic> content = response.data['content'];
      return content.map((item) => Patient.fromJson(item)).toList();
    } else {
      throw Exception("Failed to fetch patients: ${response.data}");
    }
  } catch (error) {
    logger.e("An error occurred: $error");
    throw Exception("An unexpected error occurred: $error");
  }
  }
  
  // Future<Patient> getPatientById(String id) async {
  //   // Get patient by id from the API
  // }
  
  // Future<Patient> addPatient(Patient patient) async {
  //   // Add patient to the API
  // }
  
  // Future<Patient> updatePatient(Patient patient) async {
  //   // Update patient in the API
  // }
  
  // Future<void> deletePatient(String id) async {
  //   // Delete patient from the API
  // }
}