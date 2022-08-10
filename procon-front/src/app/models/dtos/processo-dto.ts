import { ConsumidorDto } from "./consumidor-dto";
import { FornecedorDto } from "./fornecedor-dto";
import { UsuarioDto } from "./usuario-dto";

export interface ProcessoDto {
  id: number;
  autos: string;
  tipo: string;
  data: string;
  consumidores: ConsumidorDto[];
  representantes: ConsumidorDto[];
  fornecedores: FornecedorDto[];
  situacao: string;
  atendente: UsuarioDto;
  ordem: number;
}
