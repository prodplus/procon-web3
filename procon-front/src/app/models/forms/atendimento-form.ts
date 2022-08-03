export class AtendimentoForm {
  id: number | null;
  data: string;
  consumidores: number[];
  fornecedores: number[];
  relato: string | null;
  atendente: number;

  constructor(id: number | null, data: string, consumidores: number[],
      fornecedores: number[], relato: string | null, atendente: number) {
    this.id = id;
    this.data = data;
    this.consumidores = consumidores;
    this.fornecedores = fornecedores;
    this.relato = relato;
    this.atendente = atendente;
  }
}
