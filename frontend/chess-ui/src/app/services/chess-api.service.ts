import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { BoardResponse } from "../models/board-state.model";
import { MoveRequest } from "../models/move-request.model";


@Injectable({ providedIn: 'root' })
export class ChessApiService {
  constructor(private http: HttpClient) { }
  private readonly BASE_URL = 'http://localhost:8080'

  getBoard(): Observable<BoardResponse> {
    return this.http.get<BoardResponse>(`${this.BASE_URL}/api/board`);
  }

  makeMove(move: MoveRequest): Observable<BoardResponse> {
    return this.http.post<BoardResponse>(`${this.BASE_URL}/api/move`, move);
  }
}