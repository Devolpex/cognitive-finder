import 'dart:convert';
import 'package:cognitive_app/Config/server_config.dart';
import 'package:cognitive_app/Model/token_auth.dart';
import 'package:hive/hive.dart';
import 'package:http/http.dart' as http;
import 'package:logger/logger.dart';

class AuthService {
  Logger logger = Logger();

  /// Login function to authenticate a user with their username and password.
  Future<Map<String, dynamic>> login(String username, String password) async {
    logger.i('Logging in with username: $username and password: $password');
    final url = Uri.parse(
        '$keycloakBaseUrl/realms/COG/protocol/openid-connect/token');

    try {
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: {
          'grant_type': 'password',
          'client_id': 'cog-mobile-app',
          'username': username,
          'scope': 'email openid',
          'password': password,
        },
      );

      if (response.statusCode == 200) {
        logger.i('Login Successful: ${response.body}');
        final data = json.decode(response.body);
        final authToken = AuthToken.fromJson(data);
        logger.w('Access Token: ${authToken.accessToken}');

        var box = await Hive.openBox<AuthToken>('authTokenBox');
        await box.put('authToken', authToken);
        // Return the AuthToken object as a structured Map
        return {
          "result": "success",
          "authToken": authToken,
        };
      } else {
        logger.e('Login Failed: ${response.body}');
        throw Exception('Failed to login: ${response.body}');
      }
    } catch (error) {
      logger.e('An error occurred: $error');
      throw Exception('An unexpected error occurred: $error');
    }
  }

    /// Register function to create a new user account.
  Future<Map<String, dynamic>> register(String email, String username, String password) async {
    logger.i('Registering with email: $email, username: $username');
    final url = Uri.parse(
        '$keycloakBaseUrl/realms/COG/protocol/openid-connect/registrations');

    try {
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: {
          'client_id': 'Flutter-Client',
          'email': email,
          'username': username,
          'password': password,
        },
      );

      if (response.statusCode == 200) {
        logger.i('Registration Successful: ${response.statusCode}');
        return login(username, password);
        
      } else {
        logger.e('Registration Failed: ${response.body}');
        return {
          "result": "error",
          "message": response.body,
        };
      }
    } catch (error) {
      logger.e('An error occurred: $error');
      return {
        "result": "error",
        "message": error,
      };
    }
  }

  Future<Map<String, dynamic>> refreshToken() async {
    final url = Uri.parse(
        '$keycloakBaseUrl/realms/COG/protocol/openid-connect/token');

    try {
      var box = Hive.box<AuthToken>('authTokenBox');
      AuthToken? authToken = box.get('authToken');
      if (authToken == null) {
        throw Exception('No AuthToken found in storage!');
      }
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: {
          'grant_type': 'refresh_token',
          'client_id': 'Flutter-Client',
          'refresh_token': authToken.refreshToken,
        },
      );

      if (response.statusCode == 200) {
        logger.i('Login Successful: ${response.body}');
        final data = json.decode(response.body);
        final authToken = AuthToken.fromJson(data);
        logger.w('Access Token: ${authToken.accessToken}');

        var box = await Hive.openBox<AuthToken>('authTokenBox');
        await box.put('authToken', authToken);
        // Return the AuthToken object as a structured Map
        return {
          "result": "success",
          "authToken": authToken,
        };
      } else {
        logger.e('Login Failed: ${response.body}');
        throw Exception('Failed to login: ${response.body}');
      }
    } catch (error) {
      logger.e('An error occurred: $error');
      throw Exception('An unexpected error occurred: $error');
    }
  }

    Future<Map<String, dynamic>> registerBySpring(
      String email, String username, String password) async {
    logger.i('Registering with email: $email, username: $username');
    final url = Uri.parse(
        '$ipAdress/public/register');

    try {
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'email': email,
          'username': username,
          'password': password,
        }),
      );

      if (response.statusCode == 200) {
        logger.i('Registration Successful: ${response.statusCode}');
        return login(username, password);
      } else {
        logger.e('Registration Failed: ${response.body}');
        return {
          "result": "error",
          "message": response.body,
        };
      }
    } catch (error) {
      logger.e('An error occurred: $error');
      return {
        "result": "error",
        "message": error,
      };
    }
  }

  Future<void> saveAuthToken(AuthToken authToken) async {
    var box = await Hive.openBox<AuthToken>('authTokenBox');
    await box.put('authToken', authToken);
  }

  Future<AuthToken?> getAuthToken() async {
    var box = Hive.box<AuthToken>('authTokenBox');
    return box.get('authToken');
  }
  
  Future<void> signOut() async {
    var box = await Hive.openBox<AuthToken>('authTokenBox');
    await box.clear();
  }

}
