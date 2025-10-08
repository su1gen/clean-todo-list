import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"
import {toast} from "sonner";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export function formatDateForAPI (date?: Date) {
  if (!date) return undefined;
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}T00:00:00`;
}

export function showToastErrors (messages: string | string[] | undefined){
  if (typeof messages === 'string'){
    toast.error(messages);
  } else if (Array.isArray(messages)){
    messages.forEach((message: string) => {
      toast.error(message);
    })
  }
}
