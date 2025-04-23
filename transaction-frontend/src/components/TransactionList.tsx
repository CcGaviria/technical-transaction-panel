import { DataTable } from "@/components/ui_components/data-table";
import { Button } from "@/components/ui/button";
import { Transaction } from "@/features/transactions/types";
import { ColumnDef } from "@tanstack/react-table";
import { FC } from "react";
import { DataTableColumnHeader } from "./ui_components/DataTableColumnHeaderProps";

interface Props {
  data: Transaction[];
  onEdit: (tx: Transaction) => void;
  onDelete: (id: number) => void;
}

export const TransactionList: FC<Props> = ({ data, onEdit, onDelete }) => {
  const handleDelete = (id: number) => {
    if (window.confirm("¿Estás seguro de que quieres eliminar esta transacción?")) {
      onDelete(id);
    }
  };
  const columns: ColumnDef<Transaction, unknown>[] = [
    { header: "ID", accessorKey: "id" },
    {
      accessorKey: "monto",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Monto" />
      ),
    },
    
    { header: "Comercio", accessorKey: "comercio" },
    { accessorKey: "usuario", 
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Usuario" />
      ),
     },
    {
      header: 'Fecha',
      accessorKey: 'date',
      cell: ({ row }) => {
        const dateFormatted = new Date(row.original.fecha)
        return dateFormatted.toLocaleString('es-ES', {
          year:   'numeric',
          month:  '2-digit',
          day:    '2-digit',
          hour:   '2-digit',
          minute: '2-digit',
          hour12: false
        })
      },
    },
    {
      header: "Acciones",
      cell: ({ row }) => (
        <div className="flex gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => onEdit(row.original)}
          >
            Editar
          </Button>
          <Button
            variant="destructive"
            size="sm"
            onClick={() => handleDelete(row.original.id)}
          >
            Eliminar
          </Button>
        </div>
      ),
    },
  ];

  return <DataTable columns={columns} data={data} />;
};
