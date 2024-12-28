import {
  Card,
  Typography,
  List,
  ListItem,
  ListItemPrefix,
  ListItemSuffix,
  Chip,
} from "@material-tailwind/react";
import {
  UserCircleIcon,
  PowerIcon,
  UserGroupIcon,
} from "@heroicons/react/24/solid";

// Define a menuItems array with name, path, and icon
const menuItems = [
  {
    title: "Patients",
    icon: UserGroupIcon,
    path: "/admin/patients",
  },

  {
    title: "Profile",
    icon: UserCircleIcon,
    path: "/profile",
  },

  {
    title: "Log Out",
    icon: PowerIcon,
    path: "/logout",
  },
];

export function Sidebar() {
  return (
    <Card
      className="h-[calc(100vh-2rem)] w-[20rem] max-w-[20rem] p-4 shadow-xl shadow-blue-gray-900/5"
      placeholder={undefined}
      onPointerEnterCapture={undefined}
      onPointerLeaveCapture={undefined}
    >
      <div className="mb-2 p-4">
        <Typography
          variant="h5"
          color="blue-gray"
          placeholder={undefined}
          onPointerEnterCapture={undefined}
          onPointerLeaveCapture={undefined}
        >
          Sidebar
        </Typography>
      </div>
      <List
        placeholder={undefined}
        onPointerEnterCapture={undefined}
        onPointerLeaveCapture={undefined}
      >
        {/* Map through menuItems to dynamically generate the sidebar menu */}
        {menuItems.map((item) => (
          <ListItem
            key={item.title}
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            <ListItemPrefix
              placeholder={undefined}
              onPointerEnterCapture={undefined}
              onPointerLeaveCapture={undefined}
            >
              <item.icon className="h-5 w-5" />
            </ListItemPrefix>
            {item.title}
            {item.badge && (
              <ListItemSuffix
                placeholder={undefined}
                onPointerEnterCapture={undefined}
                onPointerLeaveCapture={undefined}
              >
                <Chip
                  value={item.badge}
                  size="sm"
                  variant="ghost"
                  color="blue-gray"
                  className="rounded-full"
                />
              </ListItemSuffix>
            )}
          </ListItem>
        ))}
      </List>
    </Card>
  );
}
