export class Configuracao {
  descricaoProcon: string;
  nomeProcon: string;
  endereco: string;
  fones: string[];
  email: string;
  diretoria: string;

  constructor();
  constructor(descricaoProcon: string, nomeProcon: string, endereco: string,
      fones: string[], email: string, diretoria: string);
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.descricaoProcon = '';
      this.nomeProcon = '';
      this.endereco = '';
      this.fones = [];
      this.email = '';
      this.diretoria = '';
    } else {
      this.descricaoProcon = args[0];
      this.nomeProcon = args[1];
      this.endereco = args[2];
      this.fones = args[3];
      this.email = args[4];
      this.diretoria = args[5];
    }
  }
}
