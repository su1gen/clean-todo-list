"use client"

import TaskList from "@/components/tasks/task-list";
import {routes} from "@/lib/routes";
import {TaskInList} from "@/types";
import apiFront from "@/lib/api-front";
import {useEffect, useState} from "react";
import Loader from "@/components/ui/loader";
import PageTitle from "@/components/ui/page-title";
import PageDescription from "@/components/ui/page-description";
import TasksEmpty from "@/components/tasks/tasks-empty";

const fetchInboxTasks = async (): Promise<TaskInList[]> => {
  const response = await apiFront.get(routes.todos.inbox)
  return response.data
}

export default function Inbox() {
  const [tasks, setTasks] = useState<TaskInList[]>([])
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

  return <div className="p-8 animate-fadeIn">
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <PageTitle title="Inbox"/>
        <PageDescription description="Входящие задачи"/>
      </div>

      {tasks.length === 0 && <TasksEmpty/>}
      {tasks.length > 0 && <TaskList tasks={tasks || []}/>}
    </div>
  </div>
}