import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-player-layout',
  imports: [],
  templateUrl: './player-layout.html',
  styleUrl: './player-layout.scss',
})
export class PlayerLayout {
  @Input() playerName: string = "Player";
  @Input() isBottom: boolean = false;
}
