import 'package:cognitive_app/components/icon_shadow_button.dart';
import 'package:cognitive_app/model/notifications.dart';
import 'package:cognitive_app/services/notification_service.dart';
import 'package:cognitive_app/utils/colors.dart';
import 'package:cognitive_app/utils/helpers.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import 'widgets/notification_tile.dart';

class NotificationsPage extends StatefulWidget {
  const NotificationsPage({super.key});

  @override
  State<NotificationsPage> createState() => _NotificationsPageState();
}

class _NotificationsPageState extends State<NotificationsPage> {
  // final dbService = DBService();
  final notificationService = NotificationService();

  List<Notifications> notifications = [];

  void _init() async {
    // final notis = await NotificationService().getNotifications();
    // if (notis == null || !mounted) return;
    // setState(() {
    //   notifications = notis;
    // });
  }

  @override
  void initState() {
    _init();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Notifications'),
        backgroundColor: primary,
        foregroundColor: Colors.white,
        actions: [
          PopupMenuButton<MenuItem>(
            onSelected: (item) => item.onClick(),
            itemBuilder: (context) => MenuItem.items.map((item) {
              return PopupMenuItem<MenuItem>(
                value: item,
                child: Row(
                  children: [
                    Icon(item.icon, color: Colors.black),
                    SizedBox(width: 8),
                    Text(item.title),
                  ],
                ),
              );
            }).toList(),
          ),
        ],
      ),
      body: notifications.isEmpty
          ? Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.notifications_off_outlined,
                    size: 80.sp,
                    color: lightPrimary,
                  ),
                  addVerticalSpace(10),
                  Text(
                    "You have no recent notifications",
                    style: TextStyle(fontSize: 15.sp),
                  ),
                  addVerticalSpace(10),
                  ElevatedButton(
                    onPressed: () => popPage(context),
                    style: ElevatedButton.styleFrom(backgroundColor: primary),
                    child: const Text("Go Back", style: TextStyle(color: white)),
                  ),
                  addVerticalSpace(100),
                ],
              ),
            )
          : SingleChildScrollView(
              padding: EdgeInsets.symmetric(horizontal: 20.w),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  addVerticalSpace(10),
                  Text(
                    "Notifications",
                    style: TextStyle(
                      fontSize: 20.sp,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  addVerticalSpace(10),
                  // const SectionTitle("Today"),
                  for (int i = 0; i < notifications.length; i++)
                    NotificationTile(
                      notification: notifications[i],
                      // dbService: dbService,
                      onDeleted: () {
                        setState(() {
                          notifications.remove(notifications[i]);
                        });
                      },
                    ),
                ],
              ),
            ),
    );
  }
}

class MenuItem {
  IconData icon;
  String title;
  VoidCallback onClick;

  MenuItem({
    required this.icon,
    required this.title,
    required this.onClick,
  });
  static List<MenuItem> items = [
    MenuItem(
      icon: Icons.visibility_outlined,
      title: "Mark all as read",
      onClick: () {},
    ),
    MenuItem(
      icon: Icons.visibility_off_outlined,
      title: "Mark all as unread",
      onClick: () {},
    ),
    MenuItem(
      icon: Icons.notifications_off,
      title: "Turn off",
      onClick: () {},
    ),
    MenuItem(
      icon: Icons.delete,
      title: "Clear All",
      onClick: () {},
    ),
  ];
}

class SectionTitle extends StatelessWidget {
  const SectionTitle(
    this.text, {
    super.key,
  });

  final String text;

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: TextStyle(
        color: primary.withOpacity(0.9),
        fontSize: 14.5.sp,
      ),
    );
  }
}
