export function getMascaraFone(value: string): string {
  if (value) {
    if (value.charAt(0)) {
      if (value.charAt(0) === '1') return '000 00';
      if (value.charAt(0) === '4')
        if (value.charAt(1) && value.charAt(1) === '0') return '0000-0000';
    }
    if (value.charAt(1)) {
      if (value.charAt(1) === '8' && value.charAt(0) === '0')
        return '0000 000 0000';
    }
    if (value.charAt(2)) {
      if (value.charAt(2) === '9') return '(00) 00000-0000';
    }
    return '(00) 0000-0000';
  }
  return value;
}
