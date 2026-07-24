import { Component, OnInit, signal } from '@angular/core';
import { ChessApiService } from '../services/chess-api.service';

@Component({
  selector: 'app-board-component',
  imports: [],
  templateUrl: './board-component.html',
  styleUrl: './board-component.scss',
})
export class BoardComponent implements OnInit {
  selected: { row: number, col: number } | null = null;
  squares = signal<(string | null)[][]>([]);
  legalMoves = signal<{ toRow: number; toCol: number }[]>([]);
  whiteTurn = signal<boolean>(true);
  pendingPromotion = signal<boolean>(false);
  promotionRow = signal<number>(-1);
  promotionCol = signal<number>(-1);
  promotionColor = signal<string>('');
  activeModal = signal<boolean>(false);
  promotionPieces: string[] = ['Q', 'N', 'B', 'R'];

  constructor(private api: ChessApiService) { }

  ngOnInit(): void {
    this.api.getBoard().subscribe({
      next: res => {
        this.squares.set(res.squares)
        this.whiteTurn.set(res.whiteTurn)
      },
      error: err => console.error('failed to load board', err)
    });
  }
  onSquareClick(row: number, col: number): void {
    if (this.activeModal()) {
      return;
    }

    if (this.selected) {
      if (this.isLegalMove(row, col)) {
        const from = this.selected;
        this.selected = null;
        this.legalMoves.set([]);

        this.api.makeMove({
          fromRow: from.row,
          fromCol: from.col,
          toRow: row,
          toCol: col
        }).subscribe({
          next: res => {
            this.squares.set(res.squares)
            this.whiteTurn.set(res.whiteTurn)
            if (res.pendingPromotion) {
              console.log('promotion res:', res);  // ← what does this show?
              // console.log('pawn at:', res.row, res.col, this.squares()[res.row][res.col]);

              this.activeModal.set(true)
              this.promotionCol.set(res.promotionCol)
              this.promotionRow.set(res.promotionRow)
              const pawn = this.squares()[res.promotionRow][res.promotionCol];
              this.promotionColor.set(pawn!.charAt(0));
            }
          },
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
      const piece = this.squares()[row][col];
      const isWhitePiece = piece!.charAt(0) === 'w';
      if (isWhitePiece === this.whiteTurn()) {
        this.selectPiece(row, col);
      }
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
    return this.legalMoves().some(m => m.toRow === row && m.toCol === col);
  }

  resetBoard(): void {
    this.api.resetBoard().subscribe({
      next: res => {
        console.log(res, "reset board");
        this.squares.set(res.squares);
        this.whiteTurn.set(res.whiteTurn);
        this.selected = null;
        this.legalMoves.set([]);
        this.pendingPromotion.set(false)
        this.activeModal.set(false)
        this.promotionCol.set(-1)
        this.promotionRow.set(-1)
        this.promotionColor.set('')
      },
      error: err => console.log(err, "err"),
    })
  }

  promotePawn(piece: string): void {
    this.api.promotePawn({
      row: this.promotionRow(),
      col: this.promotionCol(),
      piece: piece,
    }).subscribe({
      next: res => {
        console.log(res)
        this.pendingPromotion.set(false)
        this.activeModal.set(false)
        this.promotionCol.set(-1)
        this.promotionRow.set(-1)
        this.promotionColor.set('')
        this.squares.set(res.squares)
        this.whiteTurn.set(res.whiteTurn)
        this.selected = null;
        this.legalMoves.set([]);
      },
      error: err => console.error(err)
    })
  }


}
