// import { useKeycloak } from '@react-keycloak/web';
// import { Navigate } from 'react-router-dom';

// interface PrivateRouteProps {
//     children: React.ReactNode;
//     roles?: string[];
// }

// const PrivateRoute = ({ children, roles }: PrivateRouteProps) => {
//   const { keycloak } = useKeycloak();

//   if (!keycloak.authenticated) {
//     keycloak.login();
//     return <div>Redirecting to login...</div>;
//   }

//   if (roles && !roles.some((role) => keycloak.hasRealmRole(role))) {
//     return <Navigate to="/unauthorized" />;
//   }

//   return children;
// };

// export default PrivateRoute;
