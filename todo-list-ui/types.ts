export type Category = {
    id: number;
    title: string;
}

export type CategoryWithTodos = {
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
    categoryId: number,
    status: Status,
    plannedAt: Date | null,
}