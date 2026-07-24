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

export interface PromotionRequest {
  row: number;
  col: number;
  piece: string;
}