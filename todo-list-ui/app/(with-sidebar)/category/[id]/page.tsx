"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {Task} from "@/types";
import {notFound, useParams} from "next/navigation";
import apiFront from "@/lib/api-front";
import {Suspense, useEffect, useState} from "react";
import Loader from "@/components/ui/loader";

export default function CategoryTasks() {
    const [tasksPromise, setTasksPromise] = useState<Promise<Task[]> | null>(null)
    const { id } = useParams<{id: string}>();
    const categoryId = Number.parseInt(id);

    if (Number.isNaN(categoryId)) {
        notFound()
    }

    useEffect(() => {
        const fetchCategoryTasks = async (): Promise<Task[]> => {
            const response = await apiFront.get(routes.todos.categoryTodos(categoryId))
            return response.data
        }

        setTasksPromise(fetchCategoryTasks)
    }, [categoryId])

    if (!tasksPromise) return null

    return <Suspense fallback={<Loader/>}>
        <TaskList
          tasksPromise={tasksPromise}
          title="Категория"
          description="Задачи категории без статуса"
        />
    </Suspense>
}