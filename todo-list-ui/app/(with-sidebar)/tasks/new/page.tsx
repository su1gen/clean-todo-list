import TaskFormPage from "@/components/tasks/task-form-page";

export default async function NewTask() {
    return <TaskFormPage title="Создать задачу"
                     description="Заполните информацию о новой задаче"
    />
}