export class AtendimentoForm {
  id: number | null;
  data: string;
  consumidores: number[];
  fornecedores: number[];
  atendente: number;

  constructor(id: number | null, data: string, consumidores: number[],
      fornecedores: number[], atendente: number) {
    this.id = id;
    this.data = data;
    this.consumidores = consumidores;
    this.fornecedores = fornecedores;
    this.atendente = atendente;
  }
}
