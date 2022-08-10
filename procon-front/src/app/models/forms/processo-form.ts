export class ProcessoForm {
  id: number | null;
  tipo: string;
  autos: string | null;
  data: string;
  consumidores: number[];
  representantes: number[];
  fornecedores: number[];
  relato: string;
  atendente: number;

  constructor(id: number | null, tipo: string, autos: string | null,
      data: string, consumidores: number[], representantes: number[],
      fornecedores: number[], relato: string, atendente: number) {
    this.id = id;
    this.tipo = tipo;
    this.autos = autos;
    this.data = data;
    this.consumidores = consumidores;
    this.representantes = representantes;
    this.fornecedores = fornecedores;
    this.relato = relato;
    this.atendente = atendente;
  }
}
