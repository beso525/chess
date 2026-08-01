export interface BoardResponse {
  squares: (string | null)[][];
  whiteTurn: boolean;
  pendingPromotion: boolean;
  promotionRow: number;
  promotionCol: number;
  inCheck: boolean;
}