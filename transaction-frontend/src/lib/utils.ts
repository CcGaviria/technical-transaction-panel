import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export function  toDateInputValue(date: string | Date) {
  const d = new Date(date);
  return d.toISOString().slice(0, 10);
};