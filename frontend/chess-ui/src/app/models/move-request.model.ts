export interface MoveRequest {
  fromRow: number;
  toRow: number;
  fromCol: number;
  toCol: number;
}

export interface LegalMoveResponse {
  toRow: number;
  toCol: number;
}