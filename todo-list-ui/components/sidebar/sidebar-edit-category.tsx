"use client"

import {Pencil, Plus} from "lucide-react";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {Label} from "@/components/ui/label";
import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {SubmitHandler, useForm} from "react-hook-form";
import {routes} from "@/lib/routes";
import {Category} from "@/types";
import {useState} from "react";
import {useCategories} from "@/providers/categories-provider";
import apiFront from "@/lib/api-front";

type CategoryFormState = {
  title: string;
}

type SidebarEditCategoryProps = {
  category: Category
}

export default function SidebarEditCategory({category}: SidebarEditCategoryProps) {
  const {setCategories} = useCategories()
  const [isModalOpen, setIsModalOpen] = useState(false);
  const {
    register,
    handleSubmit,
    formState: {errors},
  } = useForm<CategoryFormState>({
    defaultValues: {
      title: category.title,
    },
  });

  const handleSaveCategory: SubmitHandler<CategoryFormState> = async (data: CategoryFormState) => {
    await apiFront.put(routes.categories.edit(category.id), data)
    setCategories(prev => {
      return prev.map(item => {
        if (item.id === category.id){
          return {
            ...item,
            title: data.title
          }
        }
        return item
      })
    })
    setIsModalOpen(false);
  }

  return <Dialog open={isModalOpen} onOpenChange={setIsModalOpen}>
    <DialogTrigger asChild>
      <button
        className="p-3"
        title="Редактировать категорию"
      >
        <Pencil size={16} />
      </button>
    </DialogTrigger>
    <DialogContent className="sm:max-w-[425px]">
      <form onSubmit={handleSubmit(handleSaveCategory)}>
        <DialogHeader className="mb-3">
          <DialogTitle>Редактировать категорию</DialogTitle>
          <DialogDescription>
            Введите новое название категории
          </DialogDescription>
        </DialogHeader>
        <div className="grid gap-3 mb-3">
          <Label htmlFor="title">Название категории</Label>
          <Input
            id="title"
            type="text"
            placeholder="Введите название категории"
            {...register("title", {
              required: "Название обязательно", maxLength: {
                message: "Название не может быть длиннее 255 символов",
                value: 255,
              }
            })}
          />
          {errors.title && <p className="text-red-500">{errors.title.message}</p>}
        </div>
        <DialogFooter>
          <DialogClose asChild>
            <Button variant="outline" type="button">Cancel</Button>
          </DialogClose>
          <Button type="submit">Save</Button>
        </DialogFooter>
      </form>
    </DialogContent>
  </Dialog>
}