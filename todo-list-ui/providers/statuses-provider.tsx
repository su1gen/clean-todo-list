'use client';

import React, {createContext, useContext, useState, useEffect, ReactNode} from 'react';
import { Status } from "@/types";
import apiFront from "@/lib/api-front";
import {routes} from "@/lib/routes";

interface StatusesContextType {
  statuses: Status[]
  setStatuses: React.Dispatch<React.SetStateAction<Status[]>>
  loading: boolean
  error: string | null
}

const StatusesContext = createContext<StatusesContextType | undefined>(undefined);

type StatusesProviderProps = {
  children: ReactNode;
}

export function StatusesProvider({children}: StatusesProviderProps) {
  const [statuses, setStatuses] = useState<Status[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    async function fetchStatuses() {
      try {
        setLoading(true);
        const response = await apiFront.get(routes.todos.statuses);
        setStatuses(response.data);
        setError(null);
      } catch (err) {
        setError('Ошибка загрузки категорий');
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    fetchStatuses();
  }, []);

  return (
    <StatusesContext.Provider value={{ statuses, setStatuses, loading, error }}>
      {children}
    </StatusesContext.Provider>
  );
}

export function useStatuses() {
  const context = useContext(StatusesContext);

  if (context === undefined) {
    throw new Error('useStatuses должен использоваться внутри StatusesProvider');
  }

  return context;
}