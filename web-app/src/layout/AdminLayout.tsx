import React from 'react';
import { Outlet } from 'react-router-dom';
import { DefaultNavbar } from '../components/Navbar';
import { Sidebar } from '../components/Sidebar';

export interface IAdminLayoutProps {}

export function AdminLayout(props: IAdminLayoutProps) {
  return (
    <div className="flex min-h-screen py-4 px-4 bg-blue-gray-50 w-full h-screen gap-4">
      {/* Sidebar */}
      <Sidebar />
      
      {/* Main Content */}
      <main className="flex flex-col ml-[20rem] px-16 gap-4 w-full">
        <DefaultNavbar />
        <Outlet />
      </main>
    </div>
  );
}
