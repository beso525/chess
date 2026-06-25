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
    if (!this.selected) {
      if (this.squares()[row][col]) {
        this.selected = { row, col };
      }
      return;
    }

    const from = this.selected;
    this.selected = null;

    if (from.row === row && from.col === col) return;

    this.api.makeMove({
      fromRow: from.row,
      fromCol: from.col,
      toRow: row,
      toCol: col
    }).subscribe(res => this.squares.set(res.squares));
  }

  isSelected(row: number, col: number): boolean {
    return this.selected?.row === row && this.selected?.col === col;
  }
}
