"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {TaskInList} from "@/types";
import {useEffect, useState} from "react";
import apiFront from "@/lib/api-front";
import Loader from "@/components/ui/loader";
import PageTitle from "@/components/ui/page-title";
import PageDescription from "@/components/ui/page-description";
import TasksEmpty from "@/components/tasks/tasks-empty";

export default function WithoutCategoryFinished() {
    const [tasks, setTasks] = useState<TaskInList[]>([])
    const [loading, setLoading] = useState(true)

    const fetchCategoryTasks = async (): Promise<TaskInList[]> => {
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

    return <div className="p-8 animate-fadeIn">
        <div className="max-w-4xl mx-auto">
            <div className="mb-8">
                <PageTitle title="Завершенные задачи"/>
                <PageDescription description="Завершенные задачи без категория"/>
            </div>

            {tasks.length === 0 && <TasksEmpty/>}
            {tasks.length > 0 && <TaskList tasks={tasks || []}/>}
        </div>
    </div>
}