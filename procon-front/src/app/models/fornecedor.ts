import { Endereco } from "./auxiliares/endereco";

export class Fornecedor {
  id: number | null;
  fantasia: string;
  razaoSocial: string | null;
  cnpj: string | null;
  email: string | null;
  endereco: Endereco | null;
  fones: string[];

  constructor(id: number | null, fantasia: string, razaoSocial: string | null,
      cnpj: string | null, email: string | null, endereco: Endereco | null,
      fones: string[]) {
    this.id = id;
    this.fantasia = fantasia;
    this.razaoSocial = razaoSocial;
    this.cnpj = cnpj;
    this.email = email;
    this.endereco = endereco;
    this.fones = fones;
  }
}
