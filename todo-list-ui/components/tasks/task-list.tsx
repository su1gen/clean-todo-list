import {TaskInList} from "@/types";
import TaskListItem from "@/components/tasks/task-list-item";

type TaskListProps = {
  tasks: TaskInList[];
}

export default function TaskList({tasks}: TaskListProps) {
  return <div className="grid gap-4">
    {tasks.map(task => (
      <TaskListItem key={task.id} task={task}/>
    ))}
  </div>
}