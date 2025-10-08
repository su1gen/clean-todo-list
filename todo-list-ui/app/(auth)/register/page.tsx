"use client";

import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {Label} from "@/components/ui/label";
import {SubmitHandler, useForm} from "react-hook-form";
import apiFront from "@/lib/api-front";
import {routes} from "@/lib/routes";
import {useRouter} from "next/navigation";
import {toast} from "sonner";

type RegisterFormState = {
  email: string;
  password: string;
}

export default function RegisterPage() {
  const router = useRouter()
  const {
    register,
    handleSubmit,
    formState: {errors},
  } = useForm<RegisterFormState>({
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const handleRegister: SubmitHandler<RegisterFormState> = async (data: RegisterFormState) => {
    const registerResponse = await apiFront.post(routes.user.register, data)
    toast.success("Вы успешно зарегистрировались")
    router.push("/login")
  }

  return (
    <form onSubmit={handleSubmit(handleRegister)}
          className="h-screen w-full flex items-center justify-center bg-gray-50">
      <div className="w-full max-w-sm bg-white shadow-md rounded-lg p-6 space-y-6">
        <h1 className="text-2xl font-bold text-center">Регистрация</h1>

        <div className="space-y-4">
          {/* Email */}
          <div className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input id="email"
                   type="email"
                   placeholder="you@example.com"
                   {...register("email", {
                     required: "Email обязателен", maxLength: {
                       message: "Email не может быть длинней 255 символов",
                       value: 255,
                     }
                   })}
            />
            {errors.email && <p className="text-red-500">{errors.email.message}</p>}
          </div>

          {/* Пароль */}
          <div className="space-y-2">
            <Label htmlFor="password">Пароль</Label>
            <Input id="password"
                   type="password"
                   placeholder="••••••••"
                   {...register("password", {
                     required: "Пароль обязателен",
                     maxLength: {
                       message: "Не больше 255 символов",
                       value: 255
                     },
                     minLength: {
                       message: "Не меньше 6 символов",
                       value: 6
                     },
                   })}
            />
            {errors.password && <p className="text-red-500">{errors.password.message}</p>}
          </div>

          <Button type="submit" className="w-full">Зарегистрироваться</Button>
        </div>

        <p className="text-sm text-gray-500 text-center">
          Уже есть аккаунт?{" "}
          <a href="/login" className="text-blue-600 hover:underline">
            Войти
          </a>
        </p>
      </div>
    </form>
  );
}
