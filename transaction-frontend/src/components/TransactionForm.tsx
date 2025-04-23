import { FC, useEffect } from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Button } from '@/components/ui/button'
import { toDateInputValue } from '@/lib/utils'

const schema = z.object({
  id: z.number().optional(),
  monto: z.preprocess(
    (val) => {
      if (typeof val === 'string') return parseFloat(val);
      return val;
    },
    z.number({
      required_error: 'El monto es obligatorio',
      invalid_type_error: 'El monto debe ser un número'
    }).positive({ message: 'El monto debe ser mayor a cero' })
  ),
  comercio: z.string({
    required_error: 'El comercio es obligatorio',
  }).min(1, 'El comercio es obligatorio'),
  usuario: z.string({
    required_error: 'El usuario es obligatorio',
  }).min(1, 'El usuario es obligatorio'),
  fecha: z.string().min(1, 'La fecha es obligatoria'),
})

type FormData = z.infer<typeof schema>

interface Props {
  defaultValues?: FormData
  onSubmit: (data: FormData) => void
}

export const TransactionForm: FC<Props> = ({ defaultValues, onSubmit }) => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm({
    resolver: zodResolver(schema),
    defaultValues,
  })

  useEffect(() => {
    reset({
      ...defaultValues,
      fecha: toDateInputValue(defaultValues?.fecha || new Date().toISOString()),
    })
  }, [defaultValues])


  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 pt-2">
      <div>
        <Label htmlFor="monto">Monto</Label>
        <Input id="monto" type='number' {...register('monto',{ valueAsNumber: true })} />
        {errors.monto && <p className="text-sm text-red-500">{errors.monto.message}</p>}
      </div>
      <div>
        <Label htmlFor="comercio">Comercio</Label>
        <Input id="comercio" {...register('comercio')} placeholder='Tenpo' />
        {errors.comercio && <p className="text-sm text-red-500">{errors.comercio.message}</p>}
      </div>
      <div>
        <Label htmlFor="usuario">Usuario</Label>
        <Input id="usuario" {...register('usuario')} placeholder='Cristian Gaviria'/>
        {errors.usuario && <p className="text-sm text-red-500">{errors.usuario.message}</p>}
      </div>
      <div>
        <Label htmlFor="fecha">Fecha</Label>
        <Input id="fecha" type="date" {...register('fecha')} />
        {errors.fecha && <p className="text-sm text-red-500">{errors.fecha.message}</p>}
      </div>
      <Button type="submit" className="w-full" disabled={isSubmitting}>
        {defaultValues ? 'Actualizar' : 'Guardar'}
      </Button>
    </form>
  )
}
