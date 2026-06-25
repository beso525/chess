import { Component } from '@angular/core';
import { BoardComponent } from "./board-component/board-component";

@Component({
  selector: 'app-root',
  imports: [BoardComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App { }
