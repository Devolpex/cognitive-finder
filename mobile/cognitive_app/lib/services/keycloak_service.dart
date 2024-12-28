import 'package:jwt_decoder/jwt_decoder.dart';

class KeycloakService {
  static Map<String, dynamic> decodeAccessToken(String accessToken) {
    return JwtDecoder.decode(accessToken);
  }

  static String? getUserName(Map<String, dynamic> tokenPayload) {
    return tokenPayload['preferred_username'];
  }

  static String? getEmail(Map<String, dynamic> tokenPayload) {
    return tokenPayload['email'];
  }

  static String? getFullName(Map<String, dynamic> tokenPayload) {
    return "${tokenPayload['given_name']} ${tokenPayload['family_name']}";
  }

}