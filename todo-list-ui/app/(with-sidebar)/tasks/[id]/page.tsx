import TaskFormPage from "@/components/tasks/task-form-page";
import {notFound} from 'next/navigation';

export default async function TaskItem({params}: { params: { id: string } }) {
  const {id} = await params;
  const taskId = Number.parseInt(id);

  if (Number.isNaN(taskId)) {
    notFound()
  }

  return <TaskFormPage
    title="Просмотр задачи"
    description="Просмотр информации о новой задаче"
    taskId={taskId}
  />
}