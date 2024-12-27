import 'package:hive/hive.dart';

part 'token_auth.g.dart';

@HiveType(typeId: 0)
class AuthToken extends HiveObject {
  @HiveField(0)
  final String accessToken;

  @HiveField(1)
  final int expiresIn;

  @HiveField(2)
  final int refreshExpiresIn;

  @HiveField(3)
  final String refreshToken;

  @HiveField(4)
  final String tokenType;

  @HiveField(5)
  final String idToken;

  @HiveField(6)
  final int notBeforePolicy;

  @HiveField(7)
  final String sessionState;

  @HiveField(8)
  final String scope;

  AuthToken({
    required this.accessToken,
    required this.expiresIn,
    required this.refreshExpiresIn,
    required this.refreshToken,
    required this.tokenType,
    required this.idToken,
    required this.notBeforePolicy,
    required this.sessionState,
    required this.scope,
  });

  factory AuthToken.fromJson(Map<String, dynamic> json) {
    return AuthToken(
      accessToken: json['access_token'],
      expiresIn: json['expires_in'],
      refreshExpiresIn: json['refresh_expires_in'],
      refreshToken: json['refresh_token'],
      tokenType: json['token_type'],
      idToken: json['id_token'],
      notBeforePolicy: json['not-before-policy'],
      sessionState: json['session_state'],
      scope: json['scope'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'access_token': accessToken,
      'expires_in': expiresIn,
      'refresh_expires_in': refreshExpiresIn,
      'refresh_token': refreshToken,
      'token_type': tokenType,
      'id_token': idToken,
      'not-before-policy': notBeforePolicy,
      'session_state': sessionState,
      'scope': scope,
    };
  }
}
