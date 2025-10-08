type PageTitleProps = {
  title: string
}

export default function PageTitle({ title }: PageTitleProps) {
  return <h1 className="text-3xl font-bold text-slate-800 mb-2">{title}</h1>
}