export type Category = {
    id: number;
    title: string;
}

export type Status = {
    id: number;
    title: string;
}

export type TaskInList = {
    id: number,
    title: string,
    statusTitle: string,
    categoryTitle?: string,
}

export type Task = {
    id: number,
    title: string,
    description: string,
    categoryId?: number,
    categoryTitle?: string,
    status: Status,
    plannedAt: Date | null,
}