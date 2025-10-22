"use client";

import Link from "next/link";
import { Calendar, Inbox, Plus } from "lucide-react";
import { usePathname } from "next/navigation";
import SidebarCategory from "@/components/sidebar/sidebar-category";
import SidebarCreateCategory from "@/components/sidebar/sidebar-create-category";
import { useCategories } from "@/providers/categories-provider";
import SidebarWithoutCategory from "@/components/sidebar/sidebar-without-category";
import KeyboardShortcutHandler from "@/components/keyboard-shortcut-handler";

export function Sidebar() {
  const {categories} = useCategories()
  const pathname = usePathname();

  return <div className="w-96 bg-gradient-to-br from-slate-50 to-slate-100 border-r border-slate-200 h-screen overflow-y-auto">
    <div className="p-6">
      {/* Create Task Button */}
      <Link href="/tasks/new"
        className="w-full bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 text-white px-4 py-3 rounded-xl font-medium transition-all duration-200 flex items-center justify-center gap-2 shadow-lg hover:shadow-xl transform hover:scale-[1.02] active:scale-[0.98]"
      >
        <Plus size={20} />
        Создать задачу
      </Link>
      <KeyboardShortcutHandler />

      {/* Categories */}
      <div className="mt-8 space-y-2">
        {/* Today */}
        <Link href="/"
              className={`flex items-center gap-3 px-4 py-3 rounded-lg cursor-pointer transition-all duration-200 hover:bg-white hover:shadow-md ${pathname === '/' ? 'bg-white shadow-md border-l-4 border-blue-500' : ''}`}
        >
          <Calendar size={20} className="text-red-500" />
          <span className="font-medium text-slate-700">Today</span>
        </Link>

        {/* Inbox */}
        <Link href="/inbox"
              className={`flex items-center gap-3 px-4 py-3 rounded-lg cursor-pointer transition-all duration-200 hover:bg-white hover:shadow-md ${pathname === '/inbox' ? 'bg-white shadow-md border-l-4 border-blue-500' : ''}`}
        >
          <Inbox size={20} className="text-blue-500" />
          <span className="font-medium text-slate-700">Inbox</span>
        </Link>


        {/* User Categories */}
        <div className="pt-4">
          <div className="flex justify-between pr-2.5 mb-2">
            <h3 className="text-sm font-semibold text-slate-500 uppercase tracking-wider">Категории</h3>
            <SidebarCreateCategory />
          </div>
          <div>
            {categories.map(category => (
              <SidebarCategory key={category.id} category={category}/>
            ))}
            <SidebarWithoutCategory />
          </div>
        </div>
      </div>
    </div>
  </div>;
}