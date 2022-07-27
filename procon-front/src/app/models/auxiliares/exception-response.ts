export interface HttpErrorResponse {
  error: Error;
  headers: {};
  message: string;
  name: string;
  ok: boolean;
  status: number;
  statusText: string;
  url: string;
}

interface Error {
  error: string;
  message: string;
  path: string;
  status: number;
  timestamp: string;
}
