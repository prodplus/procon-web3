import { UsuarioDto } from "./usuario-dto";

export interface LogDto {
  data: string;
  usuario: UsuarioDto;
  log: string;
  tipo: string;
}
