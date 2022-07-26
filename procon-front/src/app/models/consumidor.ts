import { Endereco } from "./auxiliares/endereco";

export class Consumidor {
  id: number | null;
  tipo: string;
  denominacao: string;
  cadastro: string;
  email: string | null;
  endereco: Endereco;
  fones: string[];

  constructor(id: number | null, tipo: string, denominacao: string,
      cadastro: string, email: string | null, endereco: Endereco, fones: string[]) {
    this.id = id;
    this.tipo = tipo;
    this.denominacao = denominacao;
    this.cadastro = cadastro;
    this.email = email;
    this.endereco = endereco;
    this.fones = fones;
  }
}
