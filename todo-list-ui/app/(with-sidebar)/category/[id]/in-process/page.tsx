"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {CategoryWithTasks} from "@/types";
import {notFound, useParams} from "next/navigation";
import {useEffect, useState} from "react";
import apiFront from "@/lib/api-front";
import Loader from "@/components/ui/loader";


export default function InProcess() {
    const [categoryWithTodos, setCategoryWithTodos] = useState<CategoryWithTasks | null>(null)
    const [loading, setLoading] = useState(true)

    const { id } = useParams<{id: string}>();
    const categoryId = Number.parseInt(id);

    if (Number.isNaN(categoryId)) {
        notFound()
    }

    const fetchCategoryTasks = async (): Promise<CategoryWithTasks> => {
        const response = await apiFront.get(routes.todos.categoryTodosInProcess(categoryId))
        return response.data
    }

    useEffect(() => {
        fetchCategoryTasks()
          .then(response => {
              setCategoryWithTodos(response)
          })
          .finally(() => {
              setLoading(false)
          })
    }, [])


    if (loading) return <Loader/>

    return <TaskList
      tasks={categoryWithTodos?.todos || []}
      title={(categoryWithTodos?.title + " - " || "") + "In Process" }
      description={categoryWithTodos?.title ? `Задачи в работе категории ${categoryWithTodos.title}` : "Задачи в работе категории"}
    />
}
