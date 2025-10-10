"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {Task} from "@/types";
import {useEffect, useState} from "react";
import apiFront from "@/lib/api-front";
import Loader from "@/components/ui/loader";

export default function WithoutCategoryFinished() {
    const [tasks, setTasks] = useState<Task[]>([])
    const [loading, setLoading] = useState(true)

    const fetchCategoryTasks = async (): Promise<Task[]> => {
        const response = await apiFront.get(routes.todos.withoutCategoryFinished)
        return response.data
    }

    useEffect(() => {
        fetchCategoryTasks()
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
      title="Завершенные задачи"
      description="Завершенные задачи без категория"
    />
}