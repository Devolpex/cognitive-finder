import 'package:flutter_dotenv/flutter_dotenv.dart';

String googleApiKey = dotenv.env['GOOGLE_API_KEY']!;
String maptilerApiKey = dotenv.env['MAPTILER_API_KEY']!;
String keycloakBaseUrl = dotenv.env['KEYCLOAK_BASE_URL']!;
String backendUrl = dotenv.env['BACKEND_URL']!;
// String traccarBaseUrl = dotenv.env['TRACCAR_API_URL']!;
// String traccarWSUrl = dotenv.env['TRACCAR_WS_URL']!;
