import { Movimento } from "./auxiliares/movimento";
import { Consumidor } from "./consumidor";
import { Fornecedor } from "./fornecedor";
import { Usuario } from "./usuario";

export interface Processo {
  id: number;
  tipo: string;
  autos: string;
  data: string;
  consumidores: Consumidor[];
  representantes: Consumidor[];
  fornecedor: Fornecedor[];
  movimentacao: Movimento[];
  relato: string;
  situacao: string;
  atendente: Usuario;
}
