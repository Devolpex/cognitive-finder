import * as React from 'react';
import { Outlet } from 'react-router-dom';

export interface IAdminLayoutProps {
}

export function AdminLayout (props: IAdminLayoutProps) {
  return (
    <div>
      <Outlet />
    </div>
  );
}
