"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {Task} from "@/types";
import apiFront from "@/lib/api-front";
import {Suspense, useEffect, useState} from "react";
import Loader from "@/components/ui/loader";

const fetchInboxTasks = async (): Promise<Task[]> => {
  const response = await apiFront.get(routes.todos.inbox)
  return response.data
}

export default function Inbox() {
  const [tasksPromise, setTasksPromise] = useState<Promise<Task[]> | null>(null)

  useEffect(() => {
    setTasksPromise(fetchInboxTasks)
  }, [])

  if (!tasksPromise) return null

  return (
    <Suspense fallback={<Loader/>}>
      <TaskList
        tasksPromise={tasksPromise}
        title="Inbox"
        description="Входящие задачи"
      />
    </Suspense>
  )
}