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
  const [tasks, setTasks] = useState<Task[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchTodayTasks()
      .then(response => {
        setTasks(response)
      })
      .finally(() => {
        setLoading(false)
      })
  }, [])


  if (loading) return <Loader/>

  return (
    <TaskList
      tasks={tasks}
      title="Today"
      description="Задачи на сегодня"
    />
  )
}
