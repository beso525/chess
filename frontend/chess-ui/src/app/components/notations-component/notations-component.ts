import { Component, computed, Input } from '@angular/core';
import { BoardComponent } from '../board-component/board-component';

interface MoveTurn {
  turn: number;
  white: string;
  black: string;
}

@Component({
  selector: 'app-notations-component',
  imports: [],
  templateUrl: './notations-component.html',
  styleUrl: './notations-component.scss',
})

export class NotationsComponent {
  @Input() boardReference!: BoardComponent;

  notationRows = computed<MoveTurn[]>(() => {
    const moves = this.boardReference.moveHistory();
    const rows: MoveTurn[] = [];

    for (let i = 0; i < moves.length; i += 2) {
      const whiteMoveIndex = moves.length - 1 - i;
      const blackMoveIndex = moves.length - 1 - (i + 1);
      rows.push({
        turn: Math.floor(i / 2) + 1,
        white: moves[whiteMoveIndex],
        black: blackMoveIndex >= 0 ? moves[blackMoveIndex] : ''
      })
    }
    return rows;
  })

  addMove(move: string) {
    this.boardReference.moveHistory.update(prev => [...prev, move])
  }
}
