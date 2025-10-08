"use client"

import {useState, useEffect, Suspense} from "react"
import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {Task} from "@/types";
import apiFront from "@/lib/api-front";
import Loader from "@/components/ui/loader";

const fetchTodayTasks = async (): Promise<Task[]> => {
  const response = await apiFront.get(routes.todos.today)
  return response.data
}

export default function Today() {
  const [tasksPromise, setTasksPromise] = useState<Promise<Task[]> | null>(null)

  useEffect(() => {
    setTasksPromise(fetchTodayTasks)
  }, [])

  if (!tasksPromise) return null

  return (
    <Suspense fallback={<Loader/>}>
      <TaskList
        tasksPromise={tasksPromise}
        title="Today"
        description="Задачи на сегодня"
      />
    </Suspense>
  )
}
