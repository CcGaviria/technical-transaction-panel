import { TransactionForm } from "@/components/TransactionForm";
import { TransactionList } from "@/components/TransactionList";
import { Alert, AlertTitle, AlertDescription } from "@/components/ui/alert";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogOverlay,
  DialogPortal,
  DialogTitle,
} from "@/components/ui/dialog";
import { useTransactions } from "@/features/transactions/hooks/useTransactions";
import { Transaction } from "@/features/transactions/types";
import { AlertTriangle } from "lucide-react";
import { useState } from "react";
import { useToast } from "@/hooks/use-toast"

export default function Home() {
  const { data, isLoading, error, add, update, remove } = useTransactions();
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState<Transaction | undefined>(undefined);
  const { toast } = useToast()
  const handleAdd = () => {
    setEditing(undefined);
    setModalOpen(true);
  };

  const handleEdit = (tx: Transaction) => {
    setEditing(tx);
    setModalOpen(true);
  };

  const handleDelete = (id: number) => {
    remove.mutate(id, {
      onSuccess: () => toast({title: "Transacción eliminada", description: "La transacción fue eliminada con éxito", variant: "default"}),
      onError: () => toast({title: "Error", description: "Hubo un error al eliminar la transacción", variant: "destructive"}),
    });
  };

  const handleSubmit = (tx: any) => {
    const opts = {
      onSuccess: () => {
        toast({title: `Transacción ${editing ? "actualizada" : "creada"} con éxito`, variant: "default" });
        setModalOpen(false);
        setEditing(undefined);
      },
      onError: (error: any) => {
        setModalOpen(false);
        toast({
          variant: "destructive",
          title: "Oh! Error.",
          description: error.response.data.details,
        })
      },
    };
    const formattedDate = `${tx.fecha}T00:00:00`;
    tx.fecha = formattedDate;
    if (editing) {
      update.mutate(tx as Transaction, opts);
    } else {
      const { id, ...newTx } = tx;
      add.mutate(newTx, opts);
    }
  };

  return (
    <div className="min-h-screen bg-muted/40">
      <main className="container mx-auto px-4 space-y-10">
        <header className="flex flex-col sm:flex-row justify-between items-center gap-4">
          <div>
            <h1 className="text-3xl font-bold tracking-tight text-left">
              Gestión de Transacciones
            </h1>
            <p className="text-muted-foreground text-sm mt-1">
            <small className="font-bold">Panel de Cliente</small>  Visualiza, agrega y edita las transacciones de los Tenpistas.
            </p>
          </div>
          <Button onClick={handleAdd} className="self-start sm:self-center">
            Agregar Transacción
          </Button>
        </header>

        <section>
          {isLoading ? (
            <p className="text-muted-foreground">Cargando transacciones...</p>
          ) : error ? (
            <Alert variant="destructive">
              <AlertTriangle className="h-5 w-5 text-red-500" />
              <AlertTitle>Error</AlertTitle>
              <AlertDescription>
                {(error as Error).message ||
                  "No se pudieron obtener las transacciones."}
                  <br/>
                  {"response" in error && (error as any).response?.data?.details}
              </AlertDescription>
            </Alert>
          ) : data && data.length > 0 ? (
            <TransactionList
              data={data}
              onEdit={handleEdit}
              onDelete={handleDelete}
            />
          ) : (
            <div className="text-center text-muted-foreground mt-10">
              <p>No se encontraron transacciones.</p>
              <p>Haz clic en "Agregar Transacción" para crear una nueva.</p>
            </div>
          )}
        </section>
      </main>

      <Dialog open={modalOpen} onOpenChange={setModalOpen}>
        <DialogPortal>
          <DialogOverlay className="fixed inset-0 bg-black/50 backdrop-blur-sm" />

          <DialogContent
            className="fixed inset-1/4 sm:inset-auto sm:top-1/2 sm:left-1/2
                                     sm:transform sm:-translate-x-1/2 sm:-translate-y-1/2
                                     bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg
                                     w-[90%] max-w-md z-50"
          >
            <DialogHeader>
              <DialogTitle>
                {editing ? "Editar Transacción" : "Agregar Transacción"}
              </DialogTitle>
            </DialogHeader>

            <TransactionForm defaultValues={editing} onSubmit={handleSubmit} />
          </DialogContent>
        </DialogPortal>
      </Dialog>
    </div>
  );
}
