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
    categoryTodos: (id: number) => `/todos/category/${id}`,
    categoryTodosInProcess: (id: number) => `/todos/category/${id}?status=in-process`,
    categoryTodosFinished: (id: number) => `/todos/category/${id}?status=finished`,
    categoryTodosNotRelevant: (id: number) => `/todos/category/${id}?status=not-relevant`,
    category: (id: number) => `/categories/${id}`,

    statuses: "/todos/statuses",
    withoutCategory: `/todos/without-category`,
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