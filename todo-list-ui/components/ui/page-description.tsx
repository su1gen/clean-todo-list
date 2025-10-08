type PageDescriptionProps = {
  description: string
}

export default function PageDescription({ description }: PageDescriptionProps) {
  return <p className="text-slate-600">{description}</p>
}