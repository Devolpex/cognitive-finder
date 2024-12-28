import React from "react";
import {
  Typography,
  Button,
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Avatar,
} from "@material-tailwind/react";
import {
  ChevronDownIcon,
  Cog6ToothIcon,
  PowerIcon,
} from "@heroicons/react/24/solid";
// import { useGlobalContext } from "@/context/GlobalProvider"; // Assuming this is the same context
import { useNavigate } from "react-router-dom"; // Use react-router-dom for navigation

// Profile menu component
export function ProfileMenu() {
  const [isMenuOpen, setIsMenuOpen] = React.useState(false);

  const closeMenu = () => setIsMenuOpen(false);
  // const { removeRoleFromLocalStorage, removeTokenFromLocalStorage } = useGlobalContext();
  const navigate = useNavigate(); // Use useNavigate for navigation in React

  const onSignOut = () => {
    // removeRoleFromLocalStorage();
    // removeTokenFromLocalStorage();
    closeMenu();
    navigate("/login"); // Navigate to login page using react-router-dom
  };

  const onEditProfile = () => {
    closeMenu();
    console.log("Navigate to profile editing page"); // Replace with actual navigation or logic
    // You can navigate to the profile editing page if needed
    // navigate("/profile/edit"); // Uncomment to navigate to edit profile page
  };

  return (
    <Menu open={isMenuOpen} handler={setIsMenuOpen} placement="bottom-end">
      <MenuHandler>
        <Button
          variant="text"
          color="blue-gray"
          className="flex items-center gap-1 rounded-full py-0.5 pr-2 pl-0.5 lg:ml-auto"
        >
          <Avatar
            variant="circular"
            size="sm"
            alt="User Avatar"
            className="border border-gray-900 p-0.5"
            src="https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1480&q=80"
          />
          <ChevronDownIcon
            strokeWidth={2.5}
            className={`h-3 w-3 transition-transform ${
              isMenuOpen ? "rotate-180" : ""
            }`}
          />
        </Button>
      </MenuHandler>
      <MenuList className="p-1">
        <MenuItem
          onClick={onEditProfile}
          className="flex items-center gap-2 rounded hover:bg-blue-gray-100 focus:bg-blue-gray-100"
        >
          <Cog6ToothIcon className="h-4 w-4" strokeWidth={2} />
          <Typography
            as="span"
            variant="small"
            className="font-normal"
          >
            Edit Profile
          </Typography>
        </MenuItem>
        <MenuItem
          onClick={onSignOut}
          className="flex items-center gap-2 rounded hover:bg-red-500/10 focus:bg-red-500/10"
        >
          <PowerIcon className="h-4 w-4 text-red-500" strokeWidth={2} />
          <Typography
            as="span"
            variant="small"
            className="font-normal text-red-500"
          >
            Sign Out
          </Typography>
        </MenuItem>
      </MenuList>
    </Menu>
  );
}
