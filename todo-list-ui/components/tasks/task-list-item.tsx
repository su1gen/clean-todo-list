import Link from "next/link";
import { Task } from "@/types";
import { Eye } from "lucide-react";

type TaskListItem = {
  task: Task;
}

export default function TaskListItem({ task }: TaskListItem) {
  const getStatusClasses = () => {
    switch (task.status.title) {
      case 'In process':
        return 'bg-orange-100 text-orange-700';
      case 'Finished':
        return 'bg-green-100 text-green-700';
      case 'Not relevant':
        return 'bg-gray-100 text-gray-700';
      case 'Created':
        return 'bg-blue-100 text-blue-700';
      case 'Confirmed':
        return 'bg-purple-100 text-purple-700';
      default:
        return ''
    }
  }


  return <Link
    href={`/tasks/${task.id}`}
    className="bg-white border border-slate-200 rounded-xl p-6 hover:shadow-lg hover:border-slate-300 transition-all duration-200 cursor-pointer transform hover:scale-[1.01] group"
  >
    <div className="flex items-start justify-between">
      <div className="flex-1">
        <h3 className="font-semibold text-slate-800 group-hover:text-blue-600 transition-colors duration-200">{task.title}</h3>
        {task?.category?.title && <p className="text-slate-600 text-sm mt-1">{task.category.title}</p>}
      </div>
      <div className="flex items-center gap-2">
        {task?.status?.title && <span className={`px-3 py-1 rounded-full text-xs font-medium ${getStatusClasses()}`}>
          {task.status.title}
        </span>}
        <Eye size={16} className="text-slate-400 group-hover:text-blue-500 transition-colors duration-200" />
      </div>
    </div>
  </Link>
}