import { Component, OnInit, signal } from '@angular/core';
import { ChessApiService } from '../services/chess-api.service';

@Component({
  selector: 'app-board-component',
  imports: [],
  templateUrl: './board-component.html',
  styleUrl: './board-component.scss',
})
export class BoardComponent implements OnInit {
  squares = signal<(string | null)[][]>([]);
  selected: { row: number, col: number } | null = null;
  legalMoves = signal<{ toRow: number; toCol: number }[]>([]);

  constructor(private api: ChessApiService) { }

  ngOnInit(): void {
    console.log('init');
    this.api.getBoard().subscribe({
      next: res => {
        console.log('GOT BOARD', res);
        this.squares.set(res.squares);
        console.log('SQUARES SET', this.squares.length);

      },
      error: err => {
        console.error('failed to load board', err);
      }
    });
  }

  onSquareClick(row: number, col: number): void {
    if (this.selected) {
      console.log(this.selected);

      const isLegal = this.legalMoves().some(m => m.toRow == row && m.toCol == col);

      console.log(isLegal);

      if (isLegal) {
        const from = this.selected;
        this.selected = null;
        this.legalMoves.set([]);

        this.api.makeMove({
          fromRow: from.row,
          fromCol: from.col,
          toRow: row,
          toCol: col
        }).subscribe({
          next: res => this.squares.set(res.squares),
          error: err => console.error(err)
        })
      } else if (this.squares()[row][col]) {
        this.selectPiece(row, col);
      } else {
        this.selected = null;
        this.legalMoves.set([]);
      }
      return;
    }

    if (this.squares()[row][col]) {
      this.selectPiece(row, col);
    }
  }

  private selectPiece(row: number, col: number): void {
    this.selected = { row, col };
    this.legalMoves.set([]);

    this.api.getLegalMove(row, col).subscribe({
      next: moves => this.legalMoves.set(moves),
      error: err => console.error(err)
    })
  }

  isSelected(row: number, col: number): boolean {
    return this.selected?.row === row && this.selected?.col === col;
  }

  isLegalMove(row: number, col: number): boolean {
    return this.legalMoves().some(m => m.toRow == row && m.toCol == col);
  }
}
