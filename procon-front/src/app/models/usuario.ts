import { Perfil } from "./perfil";

export class Usuario {
  id: number | null;
  nome: string;
  email: string;
  password: string;
  perfil: Perfil;
  ativo: boolean;

  constructor(id: number | null, nome: string, email: string, password: string,
      perfil: Perfil, ativo: boolean) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.password = password;
    this.perfil = perfil;
    this.ativo = ativo;
  }
}
