"use client"

import {Category} from "@/types";
import {usePathname} from "next/navigation";
import {Check, ChevronRight, Folder, Play, X} from "lucide-react";
import Link from "next/link";
import {useState} from "react";
import SidebarEditCategory from "@/components/sidebar/sidebar-edit-category";

type SidebarCategoryProps = {
  category: Category
}

export default function SidebarCategory({ category }: SidebarCategoryProps) {
  const [isCategoryOpened, setIsCategoryOpened] = useState(false);
  const pathname = usePathname();


  return <div className="mb-1">
    <div className={`flex gap-3 px-1 mb-1 rounded-lg cursor-pointer transition-all duration-300 hover:bg-white hover:shadow-md ${pathname === `/category/${category.id}` ? 'bg-white shadow-md border-l-4 border-purple-500' : ''}`}>
      <button
        onClick={(e) => {
          e.stopPropagation();
          setIsCategoryOpened(prev => !prev);
        }}
        className="p-3"
      >
        <ChevronRight size={16} className={`transition-transform duration-300 text-slate-400 ${isCategoryOpened && 'rotate-90'}`} />
      </button>
      <Link href={`/category/${category.id}`} className="flex items-center w-full py-3 pr-1">
        <Folder size={20} className="text-purple-500 mr-2" />
        <span className="font-medium text-slate-700 flex-1 break-all">{category.title}</span>
      </Link>
      <SidebarEditCategory category={category} />
    </div>

     {/*Subfolders */}
    <div className={`transition-all duration-300 ${isCategoryOpened ? 'max-h-96 opacity-100 pointer-events-auto' : 'max-h-0 opacity-0 pointer-events-none'}`}>
      <div className="ml-8 space-y-1">
        <Link href={`/category/${category.id}/in-process`}
              className={`flex items-center gap-3 px-4 py-2 rounded-lg cursor-pointer transition-all duration-200 hover:bg-white hover:shadow-sm ${pathname === `/category/${category.id}/in-process` ? 'bg-white shadow-sm border-l-4 border-orange-500' : ''}`}
        >
          <Play size={16} className={`text-orange-500`} />
          <span className="text-sm font-medium text-slate-600">In Process</span>
          <span className={`ml-auto bg-orange-100 text-orange-600 px-2 py-1 rounded-full text-xs font-medium`}>
              7
            </span>
        </Link>

        <Link href={`/category/${category.id}/finished`}
              className={`flex items-center gap-3 px-4 py-2 rounded-lg cursor-pointer transition-all duration-200 hover:bg-white hover:shadow-sm ${pathname === `/category/${category.id}/finished` ? 'bg-white shadow-sm border-l-4 border-green-500' : ''}`}
        >
          <Check size={16} className={`text-green-500`} />
          <span className="text-sm font-medium text-slate-600">Finished</span>
          <span className={`ml-auto bg-green-100 text-green-600 px-2 py-1 rounded-full text-xs font-medium`}>
              4
          </span>
        </Link>

        <Link href={`/category/${category.id}/not-relevant`}
              className={`flex items-center gap-3 px-4 py-2 rounded-lg cursor-pointer transition-all duration-200 hover:bg-white hover:shadow-sm ${pathname === `/category/${category.id}/not-relevant` ? 'bg-white shadow-sm border-l-4 border-gray-500' : ''}`}
        >
          <X size={16} className={`text-gray-500`} />
          <span className="text-sm font-medium text-slate-600">Not Relevant</span>
          <span className={`ml-auto bg-gray-100 text-gray-600 px-2 py-1 rounded-full text-xs font-medium`}>
              7
            </span>
        </Link>
      </div>
    </div>
  </div>
}