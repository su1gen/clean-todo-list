"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {CategoryWithTodos} from "@/types";
import {notFound, useParams} from "next/navigation";
import apiFront from "@/lib/api-front";
import {useEffect, useState} from "react";
import Loader from "@/components/ui/loader";

export default function CategoryTasks() {
    const [categoryWithTodos, setCategoryWithTodos] = useState<CategoryWithTodos | null>(null)
    const [loading, setLoading] = useState(true)

    const { id } = useParams<{id: string}>();
    const categoryId = Number.parseInt(id);

    if (Number.isNaN(categoryId)) {
        notFound()
    }

    const fetchCategoryWithTasks = async (): Promise<CategoryWithTodos> => {
        const response = await apiFront.get(routes.todos.categoryTodos(categoryId))
        return response.data
    }

    useEffect(() => {
        fetchCategoryWithTasks()
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
      title={categoryWithTodos?.title || "Категория"}
      description={categoryWithTodos?.title ? `Задачи категории ${categoryWithTodos.title} без статуса` : "Задачи категории без статуса"}
    />
}