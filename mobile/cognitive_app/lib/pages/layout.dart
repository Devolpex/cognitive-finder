import 'package:cognitive_app/pages/flottes/movements_page.dart';
import 'package:cognitive_app/pages/notifications/notifications_page.dart';
import 'package:cognitive_app/pages/profile/profil_page.dart';
import 'package:cognitive_app/utils/colors.dart';
import 'package:cognitive_app/Services/auth_service.dart';
import 'package:convex_bottom_bar/convex_bottom_bar.dart';
import 'package:flutter/material.dart';

class LayoutPage extends StatefulWidget {
  const LayoutPage({super.key});

  @override
  State<LayoutPage> createState() => _LayoutPageState();
}

class _LayoutPageState extends State<LayoutPage> with SingleTickerProviderStateMixin {
  late List<Widget> pages;
  int currentPage = 0;
  String? accessToken;

  @override
  void initState() {
    super.initState();
    _initPages();
    _fetchToken();
  }

  void _initPages() {
    pages = [
      MovementsPage(),
      // const DevicesPage(),
      // ReportsPage(onExploreMore: () {
      //   setState(() {
      //     currentPage = 0; // Navigate back to MovementsPage
      //   });
      // }),
      ProfilPage(accessToken: accessToken ?? ''),
      ProfilPage(accessToken: accessToken ?? ''),
      // MaintenancePage(),
      NotificationsPage(),
      ProfilPage(accessToken: accessToken ?? ''),
      // ReservationPage(),
    ];
    print("Pages initialized with ${pages.length} items");
  }

  Future<void> _fetchToken() async {
    AuthService authService = AuthService();
    var token = await authService.getAuthToken();
    setState(() {
      accessToken = token?.accessToken;
      _initPages(); // Reinitialize pages with the fetched token
    });
  }

  @override
  Widget build(BuildContext context) {
    final List<TabItem> allTabs = const [
      TabItem(icon: Icons.location_on, title: 'Flottes'),
      TabItem(icon: Icons.person_add, title: 'Patients'),
      TabItem(icon: Icons.history, title: 'Historique'),
      TabItem(icon: Icons.notifications, title: 'Notifications'),
      TabItem(icon: Icons.person, title: 'Profile'),
    ];

    return Scaffold(
      backgroundColor: primary,
      body: SafeArea(
        child: IndexedStack(
          index: currentPage,
          children: pages,
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: currentPage,
        onTap: (index) {
          setState(() {
            currentPage = index;
          });
        },
        items: allTabs.map((tab) {
          return BottomNavigationBarItem(
            icon: Icon(tab.icon),
            label: tab.title,
          );
        }).toList(),
        selectedItemColor: Colors.white,
        unselectedItemColor: Colors.grey,
        backgroundColor: primary,
        type: BottomNavigationBarType.fixed,
      ),
    );
  }
}