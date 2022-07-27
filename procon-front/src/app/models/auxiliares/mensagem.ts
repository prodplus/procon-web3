export interface Mensagem {
  tipo: string;
  titulo: string;
  mensagem: string;
}

export function trataMensagem(tipo: string): string {
  return tipo === 'd' ? 'DESATIVAR'
    : tipo === 'a' ? 'ATIVAR'
    : tipo === 'e' ? 'EXCLUIR'
    : tipo === 'c' ? 'CANCELAR'
    : '';
}
