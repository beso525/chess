export interface BoardResponse {
  squares: (string | null)[][];
  whiteTurn: boolean;
  pendingPromotion: boolean;
  promotionRow: number;
  promotionCol: number;
  wasCastling: boolean;
  inCheck: boolean;
  gameStatus: 'ONGOING' | 'CHECKMATE' | 'STALEMATE';
  whiteCaptures: string[];
  blackCaptures: string[];
  moveHistory: string[];
}