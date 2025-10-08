import {StatusesProvider} from "@/providers/statuses-provider";
import {Sidebar} from "@/components/sidebar/sidebar";
import {CategoriesProvider} from "@/providers/categories-provider";

export default async function WithSidebarLayout({children}: Readonly<{
  children: React.ReactNode;
}>) {
  return <CategoriesProvider>
    <StatusesProvider>
      <Sidebar/>
      <main className="flex-1 overflow-auto">
        {children}
      </main>
    </StatusesProvider>
  </CategoriesProvider>
}