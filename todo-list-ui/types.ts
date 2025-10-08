export type Category = {
    id: number;
    title: string;
}

export type Status = {
    id: number;
    title: string;
}

export type Task = {
    id: number,
    title: string,
    description: string,
    category: Category,
    status: Status,
    plannedAt: Date | null,
}