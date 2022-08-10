import { Usuario } from "./usuario";

export interface Log {
  id: number;
  data: string;
  usuario: Usuario;
  log: string;
  tipo: string;
}
