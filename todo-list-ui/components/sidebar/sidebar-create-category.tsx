"use client"

import {Plus} from "lucide-react";
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

export default function SidebarCreateCategory() {
  const {setCategories} = useCategories()
  const [isModalOpen, setIsModalOpen] = useState(false);
  const {
    register,
    handleSubmit,
    formState: {errors},
  } = useForm<CategoryFormState>({
    defaultValues: {
      title: "",
    },
  });

  const handleSaveCategory: SubmitHandler<CategoryFormState> = async (data: CategoryFormState) => {
    const response = await apiFront.post(routes.categories.store, data)
    const newCategory: Category = response.data
    setCategories(prev => {
      return [
        newCategory,
        ...prev,
      ]
    })
    setIsModalOpen(false);
  }

  return <Dialog open={isModalOpen} onOpenChange={setIsModalOpen}>
    <DialogTrigger asChild>
      <button
        className="p-1 text-slate-400 hover:text-slate-600 hover:bg-white rounded-md transition-all duration-200"
        title="Создать категорию"
      >
        <Plus size={16}/>
      </button>
    </DialogTrigger>
    <DialogContent className="sm:max-w-[425px]">
      <form onSubmit={handleSubmit(handleSaveCategory)}>
        <DialogHeader className="mb-3">
          <DialogTitle>Создать категорию</DialogTitle>
          <DialogDescription>
            Введите название новой категории
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