export class Movimento {
  data: string;
  de: string;
  para: string;
  averbacao: string | null;
  auxD: string | null;
  auxT: string | null;

  constructor(data: string, de: string, para: string, averbacao: string | null,
      auxD: string | null, auxT: string | null) {
    this.data = data;
    this.de = de;
    this.para = para;
    this.averbacao = averbacao;
    this.auxD = auxD;
    this.auxT = auxT;
  }
}
