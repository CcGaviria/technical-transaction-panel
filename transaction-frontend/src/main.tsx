import App from "./App";
import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import queryClient from "./react-query";
import { Toaster } from "@/components/ui/toaster";
import { QueryClientProvider } from "@tanstack/react-query";
import { ThemeProvider } from "./components/ui/theme-provider";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
    <ThemeProvider defaultTheme="light" storageKey="vite-ui-theme">
        <App />
        <Toaster />
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>
);
