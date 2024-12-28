import React, { createContext, useContext, ReactNode, useState, useEffect } from 'react';
import Keycloak from 'keycloak-js';

interface KeycloakContextType {
  keycloak: Keycloak | null;
  authenticated: boolean;
}

const KeycloakContext = createContext<KeycloakContextType | null>(null);

export const useKeycloak = () => useContext(KeycloakContext);

export const KeycloakProvider = ({ keycloak, children }: { keycloak: Keycloak, children: ReactNode }) => {
  const [authenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    keycloak.onAuthSuccess = () => {
      setAuthenticated(true);
    };
    keycloak.onAuthError = () => {
      setAuthenticated(false);
    };
    keycloak.onTokenExpired = () => {
      keycloak.updateToken(30).then(refreshed => {
        if (refreshed) {
          console.log("Token refreshed");
        } else {
          console.warn("Token not refreshed, valid for " + Math.round(keycloak.tokenParsed!.exp! + keycloak.timeSkew! - new Date().getTime() / 1000) + " seconds");
        }
      }).catch(() => {
        console.error("Failed to refresh token");
      });
    };
  }, [keycloak]);

  return (
    <KeycloakContext.Provider value={{ keycloak, authenticated }}>
      {children}
    </KeycloakContext.Provider>
  );
};