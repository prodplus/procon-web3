import { Consumidor } from "./consumidor";
import { Fornecedor } from "./fornecedor";
import { Usuario } from "./usuario";

export interface Atendimento {
  id: number | null;
  consumidores: Consumidor[];
  fornecedores: Fornecedor[];
  data: string;
  relato: string;
  atendente: Usuario;
}
