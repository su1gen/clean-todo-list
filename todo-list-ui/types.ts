export type Category = {
    id: number;
    title: string;
}

export type CategoryWithTasks = {
    title: string;
    todos: Task[]
}

export type Status = {
    id: number;
    title: string;
}

export type Task = {
    id: number,
    title: string,
    description: string,
    categoryId?: number,
    category?: Category,
    status: Status,
    plannedAt: Date | null,
}