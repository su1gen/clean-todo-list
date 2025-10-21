"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {TaskInList} from "@/types";
import {notFound, useParams} from "next/navigation";
import apiFront from "@/lib/api-front";
import {useEffect, useState} from "react";
import Loader from "@/components/ui/loader";
import {useCategories} from "@/providers/categories-provider";
import PageTitle from "@/components/ui/page-title";
import PageDescription from "@/components/ui/page-description";
import TasksEmpty from "@/components/tasks/tasks-empty";

export default function CategoryTasks() {
  const [tasks, setTasks] = useState<TaskInList[]>([])
  const {categories} = useCategories()
  const [loading, setLoading] = useState(true)

  const {id} = useParams<{ id: string }>();
  const categoryId = Number.parseInt(id);
  const category = categories.find(obj => obj.id === categoryId)

  if (Number.isNaN(categoryId)) {
    notFound()
  }

  const fetchCategoryWithTasks = async (): Promise<TaskInList[]> => {
    const response = await apiFront.get(routes.todos.categoryTodos(categoryId))
    return response.data
  }

  useEffect(() => {
    fetchCategoryWithTasks()
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
        <PageTitle title={category?.title || "Категория"}/>
        <PageDescription description={category?.title ? `Задачи категории ${category.title} без статуса` : "Задачи категории без статуса"}/>
      </div>

      {tasks.length === 0 && <TasksEmpty/>}
      {tasks.length > 0 && <TaskList tasks={tasks || []}/>}
    </div>
  </div>
}