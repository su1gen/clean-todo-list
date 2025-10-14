"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {CategoryWithTasks, Task} from "@/types";
import {notFound, useParams} from "next/navigation";
import {Suspense, useEffect, useState} from "react";
import apiFront from "@/lib/api-front";
import Loader from "@/components/ui/loader";

export default function NotRelevantTasks() {
    const [categoryWithTodos, setCategoryWithTodos] = useState<CategoryWithTasks | null>(null)
    const [loading, setLoading] = useState(true)

    const { id } = useParams<{id: string}>();
    const categoryId = Number.parseInt(id);

    if (Number.isNaN(categoryId)) {
        notFound()
    }

    const fetchCategoryTasks = async (): Promise<CategoryWithTasks> => {
        const response = await apiFront.get(routes.todos.categoryTodosNotRelevant(categoryId))
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
      title={(categoryWithTodos?.title + " - " || "") + "Not Relevant" }
      description={categoryWithTodos?.title ? `Не актуальные задачи категории ${categoryWithTodos.title}` : "Не актуальные задачи категории"}
    />
}
