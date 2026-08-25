# Chess

A full stack chess engine built using Java (Spring Boot) and Angular.
I'm building it as a learning project to practice DSA, system design, and full-stack development.

## Tech Stack
- *Backend*: Java, Spring Boot, Maven
- *Frontend*: Angular, TypeScript, SCSS

## Features
### Completed
- [x] Chessboard rendered with initial board pieces.
- [x] Game state saved
- [x] Piece movement logic
- [x] Turn based movement
- [x] Button to reset board
- [x] Special rule sets
  - [x] Pawn promotion
  - [x] Castling
  - [x] En Passant

### In Progress
- [ ] Detecting and fixing bugs
- [ ] UI improvements
- [ ] Adding sounds

### Future Features
- [ ] Chess engine opponent 
  - [ ] With future tests to determine chess rating vs stockfish
- [ ] Player vs computer mode

## DSA concepts used

- *Arrays, 2D matrices*
- *ArrayList*
- *HashMaps*

## Learning Objective

I'm building this project in multiple phases

- [x] **Phase 1**: Board rendering, connecting backend with frontend
- [x] **Phase 2**: Click to move
- [x] **Phase 3**: Piece move generation and legal moves
- [x] **Phase 4**: Full rule set, turn based
- [ ] **Phase 5**: Improving UI and adding sounds to moves
- [ ] **Phase 6**: Adding notations and undo 
- [ ] **Phase 7**: Engine opponent

## Bugs found
- En passant rules sometimes go into effect if a pawn captures a piece that's behind an  enemy pawn, even if the timing passed, also it sometimes just does it on regular pieces?
- Castling can still occur even if the square immediately next to the king is seen by an enemy piece
- If it's one player's turn and they click on their pieces then immediately click on a piece from an enemy, it shows that its the enemy's turn. (it should show no moves) however, the backend prevents any incorrect turn moves.

## Bugs fixed
