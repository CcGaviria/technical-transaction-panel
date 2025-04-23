import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { api } from '../api';
import { Transaction } from '../types';

export function useTransactions() {
  const qc = useQueryClient();
  const list = useQuery<Transaction[]>({
    queryKey: ['transactions'],
    queryFn: () => api.get<Transaction[]>('/transactions').then(r => r.data),
  });

  const add = useMutation({
    mutationFn: (tx: Omit<Transaction,'id'>) => api.post('/transactions', tx),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['transactions'] }),
  });

  const update = useMutation({
    mutationFn: (tx: Transaction) => api.put('/transactions', tx),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['transactions'] }),
  });

  const remove = useMutation({
    mutationFn: (id: number) => api.delete(`/transactions/${id}`),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['transactions'] }),
  });

  return { ...list, add, update, remove };
}