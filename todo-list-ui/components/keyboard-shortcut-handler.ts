"use client"

import {useRouter} from "next/navigation";
import {useEffect} from "react";

export default function KeyboardShortcutHandler() {
  const router = useRouter();

  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.altKey && event.key === 't') {
        event.preventDefault();

        router.push('/tasks/new');
      }
    };

    document.addEventListener('keydown', handleKeyDown);

    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [router]);

  return null;
}