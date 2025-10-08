"use client";

import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {Label} from "@/components/ui/label";
import {useRouter} from "next/navigation";
import {SubmitHandler, useForm} from "react-hook-form";
import apiFront from "@/lib/api-front";
import {routes} from "@/lib/routes";

type LoginFormState = {
  email: string;
  password: string;
}

export default function LoginPage() {
  const router = useRouter()
  const {
    register,
    handleSubmit,
    formState: {errors},
  } = useForm<LoginFormState>({
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const handleLogin: SubmitHandler<LoginFormState> = async (data: LoginFormState) => {
    await apiFront.post(routes.user.login, data)
    router.push("/")
  }

  return (
    <form onSubmit={handleSubmit(handleLogin)}
      className="h-screen w-full flex items-center justify-center bg-gray-50">
      <div className="w-full max-w-sm bg-white shadow-md rounded-lg p-6 space-y-6">
        <h1 className="text-2xl font-bold text-center">Вход</h1>

        <div className="space-y-4">
          {/* Email */}
          <div className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input id="email"
                   type="email"
                   placeholder="you@example.com"
                   {...register("email", {required: "Email обязателен"})}
            />
          </div>

          {/* Пароль */}
          <div className="space-y-2">
            <Label htmlFor="password">Пароль</Label>
            <Input id="password"
                   type="password"
                   placeholder="••••••••"
                   {...register("password", {
                     required: "Пароль обязателен",
                   })}
            />
          </div>

          <Button type="submit" className="w-full">Войти</Button>
        </div>

        <p className="text-sm text-gray-500 text-center">
          Нет аккаунта?{" "}
          <a href="/register" className="text-blue-600 hover:underline">
            Зарегистрироваться
          </a>
        </p>
      </div>
    </form>
  );
}
