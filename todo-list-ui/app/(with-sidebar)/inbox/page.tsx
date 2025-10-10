"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {Task} from "@/types";
import apiFront from "@/lib/api-front";
import {useEffect, useState} from "react";
import Loader from "@/components/ui/loader";

const fetchInboxTasks = async (): Promise<Task[]> => {
  const response = await apiFront.get(routes.todos.inbox)
  return response.data
}

export default function Inbox() {
  const [tasks, setTasks] = useState<Task[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchInboxTasks()
      .then(response => {
        setTasks(response)
      })
      .finally(() => {
        setLoading(false)
      })
  }, [])


  if (loading) return <Loader/>

  return <TaskList
    tasks={tasks}
    title="Inbox"
    description="Входящие задачи"
  />
}