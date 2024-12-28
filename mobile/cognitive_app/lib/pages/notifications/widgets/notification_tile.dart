import 'package:cognitive_app/model/notifications.dart';
import 'package:cognitive_app/utils/colors.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
// import 'package:get/get.dart';

class NotificationTile extends StatefulWidget {
  const NotificationTile({
    super.key,
    required this.notification,
    required this.onDeleted,
    // required this.dbService,
  });

  final Notifications notification;
  final VoidCallback onDeleted;
  // final DBService dbService;

  @override
  State<NotificationTile> createState() => _NotificationTileState();
}

class _NotificationTileState extends State<NotificationTile> {
  bool isDeleting = false;
  bool isDeclining = false;

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(
        horizontal: 8.w,
        vertical: 10.h,
      ),
      decoration: BoxDecoration(
        color: white,
        borderRadius: BorderRadius.circular(10.r),
        boxShadow: [
          BoxShadow(
            color: lightPrimary.withOpacity(0.7),
            blurRadius: 10,
          ),
        ],
      ),
      child: Stack(
        children: [
          Positioned(
            top: -20,
            left: -20,
            child: CircleAvatar(
              radius: 25.sp,
              backgroundColor: primary,
              foregroundColor: white,
            ),
          ),
          Padding(
            padding: EdgeInsets.only(
              left: 18.w,
              top: 12.h,
              right: 12.w,
              bottom: 12.h,
            ),
            // child: Column(
            //   crossAxisAlignment: CrossAxisAlignment.start,
            //   children: [
            //     Text(
            //       "      ${timeago.format(widget.notification.createdAt)}",
            //       style: TextStyle(
            //         fontStyle: FontStyle.italic,
            //         color: Colors.grey.shade500,
            //       ),
            //     ),
            //     addVerticalSpace(5),
            //     Text(
            //       actionsStr[widget.notification.action] ?? "UNKNOWN ACTION",
            //       style: TextStyle(
            //         fontSize: 16.sp,
            //         fontWeight: FontWeight.w700,
            //         color: Colors.grey.shade800,
            //       ),
            //     ),
            //     addVerticalSpace(8),
            //     Text(widget.notification.message),
            //     addVerticalSpace(10),
            //     if (widget.notification.action == NotificationAction.join)
            //       const Row(
            //         mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    // children: [
                    //   ElevatedButton(
                    //     onPressed: isDeclining || isDeleting
                    //         ? null
                    //         : () async {
                    //             final wantDecline = await warnMethod(
                    //               context,
                    //               title: "Decline Movement",
                    //               subtitle:
                    //                   "Are you sure do you want to decline joining the movement?",
                    //               okButtonText: "Decline",
                    //             );
                    //             if (wantDecline != true) return;
                    //             setState(() {
                    //               isDeclining = true;
                    //             });
                    //             final left =
                    //                 await widget.dbService.leaveMovement(
                    //               widget.notification.data["movementId"],
                    //             );
                    //             setState(() {
                    //               isDeclining = left;
                    //             });
                    //             if (!left) return;
                    //             final notificitionDeleted =
                    //                 await widget.dbService.deleteNotification(
                    //               widget.notification.id,
                    //             );
                    //             setState(() {
                    //               isDeclining = notificitionDeleted;
                    //             });
                    //             if (notificitionDeleted) {
                    //               Get.put(MovementController()).getMovements();
                    //               widget.onDeleted();
                    //             }
                    //           },
                    //     style: ElevatedButton.styleFrom(
                    //       backgroundColor: Colors.red.shade700,
                    //       tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                    //       minimumSize: Size.zero,
                    //       disabledBackgroundColor: Colors.red.shade800,
                    //       padding: EdgeInsets.symmetric(
                    //         vertical: 2.5.h,
                    //         horizontal: 15.w,
                    //       ),
                    //     ),
                    //     child: Text(
                    //       isDeclining ? "Canceling.." : "Decline",
                    //       style: TextStyle(
                    //         fontSize: 12.sp,
                    //         color: white,
                    //       ),
                    //     ),
                    //   ),
                    // ],
            //       )
            //   ],
            // ),
          ),
          // Positioned(
          //   top: 0,
          //   right: 0,
          //   child: GestureDetector(
          //     onTap: isDeleting || isDeclining
          //         ? null
          //         : () async {
          //             final wantDelete = await warnMethod(
          //               context,
          //               title: "Clear Notification",
          //               subtitle:
          //                   "Are you sure do you clear this notification?",
          //               okButtonText: "Clear",
          //             );
          //             if (wantDelete != true) return;
          //             setState(() {
          //               isDeleting = true;
          //             });
          //             final res = await widget.dbService.deleteNotification(
          //               widget.notification.id!,
          //             );
          //             setState(() {
          //               isDeleting = false;
          //             });
          //             if (res) {
          //               widget.onDeleted();
          //             }
          //           },
          //     child: CircleAvatar(
          //       radius: 15.sp,
          //       backgroundColor: primary,
          //       foregroundColor: white,
          //       child: isDeleting
          //           ? LoadingAnimationWidget.hexagonDots(
          //               color: white,
          //               size: 17.sp,
          //             )
          //           : Icon(
          //               Icons.close,
          //               size: 22.sp,
          //             ),
          //     ),
          //   ),
          // ),
        ],
      ),
    );
  }
}
