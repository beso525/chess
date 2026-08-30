import { Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class AudioService {
  private sounds: Record<string, HTMLAudioElement> = {};

  constructor() {
    this.sounds['move'] = new Audio('sounds/move.mp3');
    this.sounds['capture'] = new Audio('sounds/capture.mp3');
    this.sounds['check'] = new Audio('sounds/check.mp3');
    this.sounds['game-end'] = new Audio('sounds/game-end.mp3');
    this.sounds['castle'] = new Audio('sounds/castle.mp3');
    this.sounds['promote'] = new Audio('sounds/promote.mp3');
  }

  play(sound: string): void {
    const audio = this.sounds[sound];
    if (audio) {
      audio.currentTime = 0;
      audio.play().catch(err => console.error('Audio Error: ', err))
    }
  }
}