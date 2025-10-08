import {use} from "react"
import TasksEmpty from "@/components/tasks/tasks-empty";
import {Task} from "@/types";
import TaskListItem from "@/components/tasks/task-list-item";
import PageTitle from "@/components/ui/page-title";
import PageDescription from "@/components/ui/page-description";

type TaskListProps = {
  title: string;
  description: string;
  tasksPromise: Promise<Task[]>;
}

export default function TaskList({tasksPromise, title, description}: TaskListProps) {
  const tasks = use(tasksPromise)

  return <div className="p-8 animate-fadeIn">
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <PageTitle title={title}/>
        <PageDescription description={description}/>
      </div>

      {tasks.length === 0 && <TasksEmpty/>}
      {tasks.length > 0 && <div className="grid gap-4">
        {tasks.map(task => (
          <TaskListItem key={task.id} task={task}/>
        ))}
      </div>}
    </div>
  </div>
}