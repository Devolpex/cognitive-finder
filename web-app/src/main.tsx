import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import { ThemeProvider } from "@material-tailwind/react";
import { KeycloakProvider } from "./context/KeycloakProvider";
import { RouterProvider } from "react-router-dom";
import Keycloak from "keycloak-js";
import { env } from "./config/env";
import { router } from "./routes";

// Keycloak initialization options
const initOptions = {
  url: env.keycloack.url,
  realm: env.keycloack.realm,
  clientId: env.keycloack.clientId,
};

const keycloak = new Keycloak(initOptions);

keycloak.init({
  onLoad: 'login-required',
  checkLoginIframe: true,
  pkceMethod: 'S256',
}).then(authenticated => {
  if (!authenticated) {
    window.location.reload();
  } else {
    console.info("Authenticated");
    console.log('Keycloak', keycloak);
    console.log('Access Token', keycloak.token);

    // Set token in HTTP client (axios, fetch, etc.)
    // httpClient.defaults.headers.common['Authorization'] = `Bearer ${keycloak.token}`;
    if (keycloak.token) {
      // localStorage.setItem("token", keycloak.token);
      localStorage.setItem("userId", keycloak.tokenParsed?.sub || "");
    }

    createRoot(document.getElementById("root")!).render(
      <StrictMode>
        <ThemeProvider>
          <KeycloakProvider keycloak={keycloak}>
            <RouterProvider router={router} />
          </KeycloakProvider>
        </ThemeProvider>
      </StrictMode>
    );
  }
}).catch(() => {
  console.error("Authentication Failed");
});