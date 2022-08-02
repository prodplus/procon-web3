import { ConsumidorDto } from "./consumidor-dto";
import { FornecedorDto } from "./fornecedor-dto";

export interface AtendimentoDto {
  id: number;
  consumidores: ConsumidorDto[];
  fornecedores: FornecedorDto[];
  data: string;
  atendente: number;
}
