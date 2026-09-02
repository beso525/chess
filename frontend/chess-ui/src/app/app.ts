import { Component, Input } from '@angular/core';
import { BoardComponent } from './components/board-component/board-component';
import { PlayerLayout } from "./components/player-layout/player-layout";
import { NotationsComponent } from "./components/notations-component/notations-component";

@Component({
  selector: 'app-root',
  imports: [BoardComponent, PlayerLayout, NotationsComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
}
