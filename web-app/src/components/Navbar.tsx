import React from "react";
import { Navbar, Typography } from "@material-tailwind/react";
import { ProfileMenu } from "./ProfileMenu";

export function DefaultNavbar() {
  const [isNavOpen, setIsNavOpen] = React.useState(false);

  const toggleIsNavOpen = () => setIsNavOpen((cur) => !cur);

  React.useEffect(() => {
    window.addEventListener(
      "resize",
      () => window.innerWidth >= 960 && setIsNavOpen(false)
    );
  }, []);

  return (
    <Navbar
      className="p-2 rounded-xl shadow-md bg-white z-20 "
      fullWidth
    >
      <div className="relative mx-8 flex items-center justify-between text-blue-gray-900">
        <Typography
          color="blue-gray"
          variant="h6"
          className="font-semibold"
        >
          Cognitive Finder
        </Typography>

        <ProfileMenu />
      </div>
    </Navbar>
  );
}
