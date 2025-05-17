import { Card } from "./card";

export interface Game {
  time: Date;
  score: number;
  cards: Card[];
}
