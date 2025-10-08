"use client"

import PageTitle from "@/components/ui/page-title";
import PageDescription from "@/components/ui/page-description";
import TaskForm from "@/components/tasks/task-form";
import {useEffect, useState} from "react";
import {Task} from "@/types";
import Loader from "@/components/ui/loader";
import apiFront from "@/lib/api-front";
import {routes} from "@/lib/routes";

type TaskFormPageProps = {
  title: string;
  description: string;
  taskId?: number;
}

export default function TaskFormPage({ title, description, taskId }: TaskFormPageProps) {
  const [task, setTask] = useState<Task | undefined>(undefined)
  const [loading, setLoading] = useState(true)
  
  useEffect(() => {
    if (taskId !== undefined){
      const taskRequest = async () => {
        const response = await apiFront.get(routes.todos.todo(taskId))
        const task: Task = response.data
        setTask(task)
        setLoading(false)
      }
      taskRequest()
    } else {
      setLoading(false)
    }
  }, [taskId])
  
  if (loading) return <Loader />
  
  return <div className="p-8 animate-fadeIn">
    <div className="max-w-2xl mx-auto">
      <div className="mb-8">
        <PageTitle title={title}/>
        <PageDescription description={description}/>
      </div>
      <TaskForm task={task}/>
    </div>
  </div>
}