"use client"

import {CalendarIcon, Save} from "lucide-react"
import {Task} from "@/types";
import {Input} from "@/components/ui/input";
import {Label} from "@/components/ui/label";
import {Textarea} from "@/components/ui/textarea";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select";
import {routes} from "@/lib/routes";
import {toast} from "sonner";
import {useCategories} from "@/providers/categories-provider";
import {useStatuses} from "@/providers/statuses-provider";
import {Popover, PopoverClose, PopoverContent, PopoverTrigger} from "@/components/ui/popover";
import {Button} from "@/components/ui/button";
import {cn, formatDateForAPI} from "@/lib/utils";
import {format} from "date-fns";
import {Calendar} from "@/components/ui/calendar";
import {ru} from "date-fns/locale";
import {useRef} from "react";
import apiFront from "@/lib/api-front";

type TaskFormProps = {
  task?: Task;
}

type TaskFormState = {
  title: string;
  description: string;
  categoryId: string | null;
  statusId: string;
  plannedAt: Date | undefined;
}

export default function TaskForm({task}: TaskFormProps) {
  const {categories} = useCategories()
  const {statuses} = useStatuses()
  const popOverRef = useRef<HTMLButtonElement | null>(null);

  const {
    register,
    handleSubmit,
    reset,
    control,
    formState: {errors},
  } = useForm<TaskFormState>({
    defaultValues: {
      title: task?.title || "",
      description: task?.description || "",
      categoryId: task?.category?.id?.toString() || null,
      statusId: task?.status?.id?.toString() || "3",
      plannedAt: task?.plannedAt || undefined,
    },
  });

  const handleSaveTask: SubmitHandler<TaskFormState> = async (data: TaskFormState) => {
    if (!task) {
      await apiFront.post(routes.todos.store, data)
    } else {
      await apiFront.put(routes.todos.fullEdit(task.id), data)
    }

    toast.success("Task saved successfully.")
    if (!task) reset()
  }

  return <form onSubmit={handleSubmit(handleSaveTask)}
               className="bg-white border border-slate-200 rounded-xl p-8 shadow-lg">
    <div className="space-y-6">
      <div>
        <Label htmlFor="title" className="text-sm font-semibold text-slate-700 mb-3">Название задачи</Label>
        <Input
          type="text"
          id="title"
          className="w-full font-medium"
          placeholder="Введите название задачи..."
          {...register("title", {required: "Название обязательно", maxLength: {
            message: "Название не может быть длиннее 255 символов",
            value: 255,
            }})}
        />
        {errors.title && <p className="text-red-500">{errors.title.message}</p>}
      </div>

      <div>
        <Label htmlFor="description" className="text-sm font-semibold text-slate-700 mb-3">Описание
          задачи</Label>
        <Textarea
          id="description"
          className="w-full font-medium resize-none"
          placeholder="Опишите детали задачи..."
          {...register("description", {required: "Описание обязательно"})}
        />
        {errors.description && <p className="text-red-500">{errors.description.message}</p>}
      </div>

      <div>
        <Label htmlFor="categoryId" className="text-sm font-semibold text-slate-700 mb-3">Категория</Label>
        <Controller
          control={control}
          name="categoryId"
          render={({field}) => (
            <Select onValueChange={(v) => field.onChange(v === "none" ? null : v)} value={field.value ?? "none"}>
              <SelectTrigger className="w-[280px]">
                <SelectValue placeholder="Выберите категорию"/>
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="none">Без категории</SelectItem>
                {categories.map(category => (
                  <SelectItem key={category.id}
                              value={category.id.toString()}>{category.title}</SelectItem>
                ))}
              </SelectContent>
            </Select>
          )}
        />
      </div>

      { task && <div>
        <Label htmlFor="statusId" className="text-sm font-semibold text-slate-700 mb-3">Статус</Label>
        <Controller
          control={control}
          name="statusId"
          render={({field}) => (
            <Select onValueChange={field.onChange} value={field.value}>
              <SelectTrigger className="w-[280px]">
                <SelectValue placeholder="Выберите статус"/>
              </SelectTrigger>
              <SelectContent>
                {statuses.map(status => (
                  <SelectItem key={status.id} value={status.id.toString()}>{status.title}</SelectItem>
                ))}
              </SelectContent>
            </Select>
          )}
        />
      </div> }

      <div>
        <Label htmlFor="plannedAt" className="text-sm font-semibold text-slate-700 mb-3">День, на который запланировать задачу</Label>
        <Controller
          control={control}
          name="plannedAt"
          render={({field}) => (
            <Popover>
              <PopoverTrigger asChild>
                <Button
                  variant="outline"
                  className={cn(
                    "w-full sm:w-[280px] justify-start text-left font-normal",
                    !field.value && "text-muted-foreground"
                  )}
                >
                  <CalendarIcon className="mr-2 h-4 w-4"/>
                  {field.value ? format(field.value, "dd.MM.yyyy", {locale: ru}) : "Выбрать дату"}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0">
                <PopoverClose ref={popOverRef} />
                <Calendar
                  mode="single"
                  selected={field.value}
                  onSelect={(value) => {
                    field.onChange(formatDateForAPI(value));
                    popOverRef.current?.click();
                  }}
                  locale={ru}
                />
              </PopoverContent>
            </Popover>
          )}
        />
      </div>

      <div className="flex gap-4 pt-4">
        <button
          type="submit"
          className="flex-1 bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 text-white px-6 py-3 rounded-lg font-medium transition-all duration-200 flex items-center justify-center gap-2 shadow-lg hover:shadow-xl transform hover:scale-[1.02] active:scale-[0.98]">
          <Save size={20}/>
          Сохранить
        </button>
      </div>
    </div>
  </form>
}