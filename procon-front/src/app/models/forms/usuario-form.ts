export class UsuarioForm {
  id: number | null;
  nome: string;
  email: string;
  password: string;
  perfil: string;
  ativo: boolean;

  constructor(id: number | null, nome: string, email: string,
      password: string, perfil: string, ativo: boolean) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.password = password;
    this.perfil = perfil;
    this.ativo = ativo;
  }
}
