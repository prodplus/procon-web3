export class RespModal {
  confirmou: boolean;
  tipo: string;

  constructor(confirmou: boolean, tipo: string) {
    this.confirmou = confirmou;
    this.tipo = tipo;
  }
}
