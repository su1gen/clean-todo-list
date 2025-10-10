export const routes = {
  categories: {
    all: "/categories",
    store: "/categories",
  },
  todos: {
    all: "/todos",
    today: "/todos/today",
    inbox: "/todos/inbox",
    todo: (id: number) => `/todos/${id}`,

    categoryTodos: (id: number) => `/categories/todos/${id}?status=confirmed`,
    categoryTodosInProcess: (id: number) => `/categories/todos/${id}?status=in-process`,
    categoryTodosFinished: (id: number) => `/categories/todos/${id}?status=finished`,
    categoryTodosNotRelevant: (id: number) => `/categories/todos/${id}?status=not-relevant`,

    statuses: "/todos/statuses",
    withoutCategory: `/todos/without-category?status=confirmed`,
    withoutCategoryInProcess: `/todos/without-category?status=in-process`,
    withoutCategoryFinished: `/todos/without-category?status=finished`,
    withoutCategoryNotRelevant: `/todos/without-category?status=not-relevant`,

    expired: "/todos?status=expired",
    deleted: "/todos?status=deleted",
    store: "/todos",
    partEdit: (id: number) => `/todos/${id}`,
    fullEdit: (id: number) => `/todos/${id}`,
    delete: (id: number) => `/todos/${id}`,
  },

  user: {
    register: "/auth/register",
    login: "/auth/login",
  },

  export: {
    all: "/export",
    expired: "/export?status=expired",
    deleted: "/export?status=deleted",
  },
  import: {
    csv: "/import/csv"
  },
}