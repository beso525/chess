import { Component, computed, effect, ElementRef, Input, QueryList, ViewChild } from '@angular/core';
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
  @ViewChild('scrollDown') scrollContainer!: ElementRef<HTMLDivElement>;

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

  constructor() {
    effect(() => {
      const rows = this.notationRows();
      setTimeout(() => this.scrollToBottom(), 0)
    })
  }
  addMove(move: string) {
    this.boardReference.moveHistory.update(prev => [...prev, move])
  }

  private scrollToBottom(): void {
    if (this.scrollContainer) {
      const element = this.scrollContainer.nativeElement;
      element.scrollTop = element.scrollHeight;
    }
  }
}
