import 'package:cognitive_app/utils/helpers.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:cognitive_app/Services/keycloak_service.dart';
import 'package:cognitive_app/Services/auth_service.dart';
import 'package:cognitive_app/pages/Auth/login_screen.dart';
import 'package:cognitive_app/utils/colors.dart';

class ProfilPage extends StatelessWidget {
  final String accessToken;

  const ProfilPage({required this.accessToken, Key? key}) : super(key: key);

  Future<void> _signOut(BuildContext context) async {
    AuthService authService = AuthService();
    await authService.signOut();
    Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (context) => const LoginScreen()),
      (Route<dynamic> route) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    Map<String, dynamic>? userInfo;
    try {
      userInfo = KeycloakService.decodeAccessToken(accessToken);
    } catch (e) {
      // Handle invalid token
      return Scaffold(
        appBar: AppBar(
          title: const Text("Profile"),
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
        title: const Text("Profile"),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () => _signOut(context),
            tooltip: 'Sign Out',
          ),
        ],
        backgroundColor: primary,
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.symmetric(horizontal: 10.w),
        child: Column(
          children: [
            ListTile(
              title: Text(
                KeycloakService.getFullName(userInfo) ?? 'N/A',
                style: TextStyle(fontSize: 18.sp),
              ),
              subtitle: Text(
                "Member since ",
                style: TextStyle(fontSize: 12.sp),
              ),
              trailing: Container(
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(50.r),
                  border: Border.all(
                    width: 3.r,
                    color: lightPrimary,
                  ),
                ),
                child: GestureDetector(
                  onTap: () {},
                  child: CircleAvatar(
                    radius: 24.r,
                    child: Text(
                      userInfo['preferred_username'][0].toUpperCase(),
                      style: const TextStyle(fontSize: 24, color: Colors.white),
                    ),
                  ),
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 15.w, vertical: 10.h),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "personal info".toUpperCase(),
                    style: TextStyle(
                      fontSize: 14.sp,
                      color: primary.withOpacity(0.7),
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  ListTile(
                    leading: Icon(
                      Icons.mail_outline_outlined,
                      size: 25.sp,
                    ),
                    title: Text(
                      "Email",
                      style: TextStyle(fontSize: 14.sp),
                    ),
                    subtitle: Text(
                      KeycloakService.getEmail(userInfo) ?? 'N/A',
                      style: TextStyle(fontSize: 12.sp),
                    ),
                  ),
                  Text(
                    "general".toUpperCase(),
                    style: TextStyle(
                      fontSize: 14.sp,
                      color: primary.withOpacity(0.7),
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  ListTile(
                    leading: Icon(Icons.run_circle_outlined, size: 25.sp),
                    title: Text(
                      "Inactive",
                      style: TextStyle(fontSize: 14.sp),
                    ),
                    subtitle: Text(
                      "Set yourself as away",
                      style: TextStyle(fontSize: 12.sp),
                    ),
                    onTap: () {},
                  ),
                  ListTile(
                    leading: Icon(
                      Icons.cached,
                      size: 25.sp,
                    ),
                    title: Text(
                      "Clear caches",
                      style: TextStyle(fontSize: 14.sp),
                    ),
                    subtitle: Text(
                      "Cleaning caches from your device",
                      style: TextStyle(fontSize: 12.sp),
                    ),
                    onTap: () {},
                    trailing: Icon(
                      Icons.arrow_forward_ios,
                      size: 24.sp,
                    ),
                  ),
                  ListTile(
                    leading: Icon(Icons.logout, size: 25.sp),
                    title: Text(
                      "Logout",
                      style: TextStyle(fontSize: 14.sp),
                    ),
                    subtitle: Text(
                      "Signed in as ${KeycloakService.getFullName(userInfo) ?? 'N/A'}",
                      style: TextStyle(fontSize: 12.sp),
                    ),
                    onTap: () async {
                      final logout = await showDialog<bool>(
                        context: context,
                        barrierColor: Colors.black26,
                        builder: (context) {
                          return AlertDialog(
                            title: const Text("Sign Out?"),
                            content: const Text("Are you sure you want to log out of this application?"),
                            actions: [
                              TextButton(
                                onPressed: () => Navigator.of(context).pop(false),
                                child: const Text("Cancel"),
                              ),
                              TextButton(
                                onPressed: () => Navigator.of(context).pop(true),
                                child: const Text("Log Out"),
                              ),
                            ],
                          );
                        },
                      );
                      if (logout != true) return;
                      await _signOut(context);
                    },
                  ),
                ],
              ),
            ),
            SizedBox(height: 25.h),
            Transform.scale(
              scale: .7,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.map,
                    color: primary,
                    size: 60.sp,
                  ),
                  addVerticalSpace(120),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        "Cognitive Finder",
                        style: TextStyle(
                          fontSize: 20.sp,
                          fontWeight: FontWeight.bold,
                          fontStyle: FontStyle.italic,
                          color: Colors.grey.shade600,
                        ),
                      ),
                      SizedBox(height: 5.h),
                      Text(
                        "Protecting Lives, Enhancing Care",
                        style: TextStyle(
                          color: Colors.grey.shade800,
                          fontSize: 14.sp,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
            SizedBox(height: 25.h),
          ],
        ),
      ),
    );
  }
}