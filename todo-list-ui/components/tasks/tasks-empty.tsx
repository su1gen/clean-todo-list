import { Calendar } from "lucide-react";

export default function TasksEmpty() {
  return <div className="text-center py-16">
    <div className="w-24 h-24 bg-gradient-to-br from-blue-100 to-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
      <Calendar size={32} className="text-blue-500" />
    </div>
    <h3 className="text-xl font-semibold text-slate-600 mb-2">Пока нет задач</h3>
    <p className="text-slate-500">Создайте новую задачу, чтобы начать работу</p>
  </div>
}