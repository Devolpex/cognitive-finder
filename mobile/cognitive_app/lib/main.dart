import 'package:cognitive_app/pages/auth/login_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:hive/hive.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:cognitive_app/Pages/profile/profil_page.dart';
import 'package:cognitive_app/Services/auth_service.dart';
import 'package:cognitive_app/Model/token_auth.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Hive.initFlutter();
  Hive.registerAdapter(AuthTokenAdapter());
  await Hive.openBox<AuthToken>('authTokenBox');

  AuthService authService = AuthService();
  var token = await authService.getAuthToken();
  String? accessToken = token?.accessToken;

  runApp(MyApp(accessToken: accessToken));
}

class MyApp extends StatelessWidget {
  final String? accessToken;

  const MyApp({super.key, this.accessToken});

  @override
  Widget build(BuildContext context) {
    return ScreenUtilInit(
      designSize: const Size(360, 690),
      builder: (context, child) {
        return MaterialApp(
          debugShowCheckedModeBanner: false,
          routes: {
            '/': (context) => const LoginScreen(),
            '/profile': (context) => ProfilPage(accessToken: accessToken ?? ''),
          },
        );
      },
    );
  }
}