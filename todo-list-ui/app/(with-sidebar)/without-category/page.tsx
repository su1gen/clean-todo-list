"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {Task} from "@/types";
import {Suspense, useEffect, useState} from "react";
import apiFront from "@/lib/api-front";
import Loader from "@/components/ui/loader";

export default function WithoutCategory() {
  const [tasksPromise, setTasksPromise] = useState<Promise<Task[]> | null>(null)

  useEffect(() => {
    const fetchCategoryTasks = async (): Promise<Task[]> => {
      const response = await apiFront.get(routes.todos.withoutCategory)
      return response.data
    }

    setTasksPromise(fetchCategoryTasks)
  }, [])

  if (!tasksPromise) return null

  return <Suspense fallback={<Loader/>}>
    <TaskList
      tasksPromise={tasksPromise}
      title="Без категория"
      description="Задачи без категории и без статуса"
    />
  </Suspense>
}