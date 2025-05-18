<script lang="ts">
import { Card } from '../components/types/card'
import { getNewCard, solve, getAllCardIDs, getCardById } from '../services/gameApi'
export default {
  data() {
    return {
      starting: false, // tracking starting timer
      idsInDB: [] as number[], // card ids found in backend; used to track if user saw any before
      card: null as unknown as Card, // current card to solve
      loading: false, // tracking card loading
      operators: [] as string[], // tracking operators available in game
      usedCards: [] as string[], // tracking indexes of cards used in game
      operation: [] as string[], // tracking user operation by clicks of buttons
      endGame: false, // tracking when all cards are exhausted (will eventually be when time runs out)
    }
  },
  methods: {
    // show new card
    async handleShowCard() {
      try {
        let okay = false
        while (!okay) {
          let response: Card = {
            cardId: 0,
            cardNumbers: [],
          }
          if (this.idsInDB.length == 1) {
            response = await getCardById(this.idsInDB[0])
          } else if (this.idsInDB.length > 1) {
            response = await getNewCard()
          }
          if (this.idsInDB.some((id) => id === response?.cardId)) {
            this.card = response
            okay = true
          }
        }
      } catch (error) {
        console.error('Error getting card:', error)
        this.loading = false
      }
    },
    // push value to operation array
    async handleGameClick(id: string, value: string) {
      try {
        if (this.operation.length == 0 && Number.isInteger(Number.parseInt(value))) {
          this.operation.push(value)
          this.usedCards.push(id)
        } else if (this.operation.length == 1 && this.operators.includes(value)) {
          this.operation.push(value)
        } else if (this.operation.length == 2 && Number.isInteger(Number.parseInt(value))) {
          this.operation.push(value)
          this.usedCards.push(id)
          this.handleOperation()
        }
      } catch (error) {
        console.error('Error getting card:', error)
        alert(error)
        this.loading = false
      }
    },
    // do operation (triggered in handleGameClick)
    async handleOperation() {
      // get new number from operation
      console.log('We have an operation!')
      const newNumber = await solve(this.card, this.operation)

      // remove used cards from card array
      for (let card of this.usedCards) {
        const c = Number(card)
        delete this.card.cardNumbers[c]
      }

      // assign new number to array using index of last card in usedCards array
      const lastUsedCardIndex = this.usedCards.pop()
      const idx = Number(lastUsedCardIndex)
      this.card.cardNumbers[idx] = newNumber

      // empty usedCard array
      this.usedCards = []
      // clear operation
      this.operation = []

      console.log('Card array: ' + this.card.cardNumbers)
    },
    // auto reset game variables and show a new card
    newCard() {
      setTimeout(() => {
        this.operation = []
        this.handleShowCard()
      }, 200)
    },
    startingTimerDone(value: number) {
      if (value == 0) {
        this.starting = false
      }
    },
    gameTimerDone(value: number) {
      if (value == 0) {
        this.endGame = true
      }
    },
  },
  watch: {
    card: {
      handler(newValue: Card) {
        if (newValue?.cardNumbers?.includes(24)) {
          // array still includes 4 elements, so to get
          // the correct response the array is converted to a
          // string and filtered of its commas to get the remaining values
          const conversion = newValue.cardNumbers
            .filter((num: number) => num !== undefined)
            .join(',')

          if (conversion === '24') {
            delete this.idsInDB[newValue.cardId]
            console.log('New DB: ' + this.idsInDB)
            if (this.idsInDB.some((item) => typeof item === 'number')) {
              console.log('Still cards left')
              this.newCard()
            } else {
              this.endGame = true
            }
          }
        }
      },
      deep: true,
      immediate: true,
    },
  },
  // grabbing all the card ids from the backend before loading app
  created() {
    this.starting = true
    const validOperators: string[] = ['+', '-', 'x', '/']
    this.operators = validOperators

    Promise.all([
      getNewCard().then((card: Card) => {
        this.card = card
      }),
      getAllCardIDs().then((ids: number[]) => {
        this.idsInDB = ids
      }),
    ])
    this.endGame = false
  },
}
</script>
<template>
  <div v-if="starting">
    <StartingTimer @done="startingTimerDone($event)" />
  </div>
  <div v-else="starting">
    <div>
      <div class="endGame bg-blue-400 rounded-xl p-2" v-if="endGame">
        <p>Game Over</p>
      </div>
      <div class="nav absolute top-1 right-3">
        <router-link to="/" custom v-slot="{ navigate }">
          <button @click="navigate" role="link" class="homeButton">Home</button>
        </router-link>
      </div>
      <div class="gameBoard rounded-xl grid grid-cols-2 gap-8" v-if="!endGame">
        <div class="solitaire">
          <div v-if="card && !loading" class="cardDisplay">
            <section class="cards">
              <button class="card1" @click="handleGameClick('0', card.cardNumbers[0].toString())"
                v-if="card.cardNumbers[0]">
                {{ card.cardNumbers[0] }}
              </button>
              <button class="card2" @click="handleGameClick('1', card.cardNumbers[1].toString())"
                v-if="card.cardNumbers[1]">
                {{ card.cardNumbers[1] }}
              </button>
              <button class="card3" @click="handleGameClick('2', card.cardNumbers[2].toString())"
                v-if="card.cardNumbers[2]">
                {{ card.cardNumbers[2] }}
              </button>
              <button class="card4" @click="handleGameClick('3', card.cardNumbers[3].toString())"
                v-if="card.cardNumbers[3]">
                {{ card.cardNumbers[3] }}
              </button>
            </section>
          </div>
        </div>
        <div class="rightSide">
          <GameTimer @done="gameTimerDone($event)" />
          <div class="operators border-2">
            <TitleBar title="Operators" />
            <div class="grid grid-flow-col auto-cols-max border-t-2 p-1.5">
              <OperatorButton operator="+" @click="handleGameClick('addOperator', '+')"/>
              <OperatorButton operator="-" @click="handleGameClick('subOperator', '-')"/>
              <OperatorButton operator="x" @click="handleGameClick('mulOperator', 'x')"/>
              <OperatorButton operator="/" @click="handleGameClick('divOperator', '/')"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
