import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { BoardResponse } from "../models/board-state.model";
import { LegalMoveResponse, MoveRequest, PromotionRequest } from "../models/move-request.model";


@Injectable({ providedIn: 'root' })
export class ChessApiService {
  constructor(private http: HttpClient) { }
  private readonly BASE_URL = 'http://localhost:4200'

  getBoard(): Observable<BoardResponse> {
    return this.http.get<BoardResponse>(`${this.BASE_URL}/api/board`);
  }

  makeMove(move: MoveRequest): Observable<BoardResponse> {
    return this.http.post<BoardResponse>(`${this.BASE_URL}/api/move`, move);
  }

  getLegalMove(row: number, col: number): Observable<LegalMoveResponse[]> {
    return this.http.get<LegalMoveResponse[]>(`/api/legal-moves?row=${row}&col=${col}`);
  }

  resetBoard(): Observable<BoardResponse> {
    return this.http.post<BoardResponse>('/api/reset', {});
  }

  promotePawn(promote: PromotionRequest): Observable<BoardResponse> {
    return this.http.put<BoardResponse>('/api/swap', promote);
  }
}