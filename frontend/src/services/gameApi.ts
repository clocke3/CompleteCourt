import axios from 'axios'
import { Card } from '../components/types/card'
import { GameDTO } from '../components/types/gameDTO'

const api = axios.create({
  baseURL: 'http://localhost:8080',
})

export async function getNewCard() {
  let response = await api.get<Card>('/game/card')
  return response.data
}

export async function getCardById(id: number) {
  let response = await api.get<Card>(`/game/card/${id}`)
  return response.data
}


export async function getAllCardIDs() {
  let response = await api.get<number[]>('/game/idList')
  return response.data
}

export async function solve(c: Card, operation: string[]) {
  const gameDTO: GameDTO = {
    card: c,
    currentOperation: operation,
  }
  let response = await api.post('game/solve', gameDTO)
  return response.data
}
