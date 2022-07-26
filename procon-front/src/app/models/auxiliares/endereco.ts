export class Endereco {
  cep: string;
  logradouro: string;
  numero: string;
  complemento: string;
  bairro: string;
  municipio: string;
  uf: string;

  constructor();
  constructor(cep: string, logradouro: string, numero: string, complemento: string,
    bairro: string, municipio: string, uf: string);
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.cep = '';
      this.logradouro = '';
      this.numero = '';
      this.complemento = '';
      this.bairro = '';
      this.municipio = '';
      this.uf = '';
    } else {
      this.cep = args[0];
      this.logradouro = args[1];
      this.numero = args[2];
      this.complemento = args[3];
      this.bairro = args[4];
      this.municipio = args[5];
      this.uf = args[6];
    }
  }
}
