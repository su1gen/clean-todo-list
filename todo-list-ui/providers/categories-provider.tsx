'use client';

import React, {createContext, useContext, useState, useEffect, ReactNode} from 'react';
import {Category} from "@/types";
import apiFront from "@/lib/api-front";
import {routes} from "@/lib/routes";

interface CategoriesContextType {
  categories: Category[]
  setCategories: React.Dispatch<React.SetStateAction<Category[]>>
  loading: boolean
  error: string | null
}

const CategoriesContext = createContext<CategoriesContextType | undefined>(undefined);

type CategoriesProviderProps = {
  children: ReactNode;
}

export function CategoriesProvider({children}: CategoriesProviderProps) {
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    async function fetchCategories() {
      try {
        setLoading(true);
        const response = await apiFront.get(routes.categories.all);
        setCategories(response.data);
        setError(null);
      } catch (err) {
        setError('Ошибка загрузки категорий');
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    fetchCategories();
  }, []);

  return (
    <CategoriesContext.Provider value={{ categories, setCategories, loading, error }}>
      {children}
    </CategoriesContext.Provider>
  );
}

export function useCategories() {
  const context = useContext(CategoriesContext);

  if (context === undefined) {
    throw new Error('useCategories должен использоваться внутри CategoriesProvider');
  }

  return context;
}