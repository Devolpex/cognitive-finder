import React from "react";
import { useNavigate } from "react-router-dom";
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
  MapIcon,
} from "@heroicons/react/24/solid";
import { useKeycloak } from "../context/KeycloakProvider";

// Define a menuItems array with name, path, and icon
const menuItems = [
  {
    title: "Patients",
    icon: UserGroupIcon,
    path: "/patient",
  },
  {
    title: "Map",
    icon: MapIcon,
    path: "/map",
  },
  {
    title: "Log Out",
    icon: PowerIcon,
    path: "/logout", // You could keep this path as a placeholder, but we won't be navigating to it.
    isLogout: true, // Add a flag to identify the logout item
  },
];

export function Sidebar() {
  const navigate = useNavigate(); // Initialize the navigate function

  // Function to handle navigation
  const handleNavigation = (path: string) => {
    navigate(path); // Navigate to the specified path
  };
  const { logout } = useKeycloak(); // Get the keycloak object from the context

  // on Sign Out, clear the local storage, log out using Keycloak, and navigate
  const handleSignOut = () => {
    localStorage.clear();
    logout(); // Log out using Keycloak
    navigate("/"); // Navigate to the homepage or login page
  };

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
            onClick={() => {
              if (item.isLogout) {
                handleSignOut(); // Call handleSignOut for logout
              } else {
                handleNavigation(item.path); // Navigate normally for other items
              }
            }}
            className="cursor-pointer"
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
