<script lang="ts">
import { LocationQuery, LocationQueryValue } from 'vue-router'
import { Card } from '../components/types/card'
import { getNewCard, solve, getAllCardIDs, getCardById } from '../services/gameApi'

export default {
  data() {
    return {
      starting: false,                   // starting timer
      idsInDB: [] as number[],           // card ids found in backend; used to track if user saw any before
      card: null as unknown as Card,     // current card to solve
      loading: false,                    // card loading during a round
      operators: [] as string[],         // operators available in game
      usedCards: [] as string[],         // indexes of cards used in game
      operation: [] as string[],         // user operation by click of buttons
      endGame: false,                    // when all cards are exhausted or timer runs out
      errors: [] as string[],            // errors caught while playing the game
      correct: false,                    // array to catch a correct solution
      gameTimer: 0 as number,            // timer for game
      solved: 0 as number,               // number of cards solved when time runs out
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
        console.error('Error getting card: ', error)
        this.loading = false
      }
    },
    // push value to operation array
    async handleGameClick(id: string, value: string) {
      try {
        // Handle first operation element
        if (this.operation.length === 0 && Number.isInteger(Number.parseInt(value))) {
          this.operation.push(value);
          this.usedCards.push(id);
          return;
        }

        // Handle second operation element
        if (this.operation.length === 1 && !Number.isInteger(Number.parseInt(value))
          && this.operators.includes(value)) {
          this.operation.push(value);
          return;
        }

        // Handle third operation element
        if (this.operation.length === 2 && Number.isInteger(Number.parseInt(value))) {
          this.operation.push(value);
          this.usedCards.push(id);
          await this.handleOperation();
          return;
        }

        // Error handling
        const errorMessage = this.getErrorMessage(this.operation.length, value);
        this.errors.push(errorMessage);
        setTimeout(() => {
          this.errors = [];
        }, 3000);

      } catch (error: any) {
        this.errors.push(error.message);
        setTimeout(() => {
          this.errors = [];
        }, 3000);
        this.loading = false;
      }
    },
    // Helper method for error messages
    getErrorMessage(operationLength: number, value: string): string {
      switch (operationLength) {
        case 0:
          return 'WARNING: first click must be a card!';
        case 1:
          return 'WARNING: second click must be an operator!';
        default:
          return 'WARNING: invalid operation sequence!';
      }
    },
    // do operation (triggered in handleGameClick)
    async handleOperation() {
      // get new number from operation
      const newNumber: number = await solve(this.card, this.operation)

      // assign new number to array using index of last card in usedCards array
      if (newNumber || newNumber == 0) {
        const lastUsedCardIndex = this.usedCards.pop()
        console.log(lastUsedCardIndex);
        const idx: number = Number(lastUsedCardIndex)
        if (newNumber == 0) {
          this.card.cardNumbers[idx] = newNumber.toString()
        } else {
          this.card.cardNumbers[idx] = newNumber
        }
        console.log('After change: ' + this.card.cardNumbers[idx]);

        // remove other card from card array
        const remainingCardIdx = this.usedCards.pop();
        console.log(remainingCardIdx);
        const idxNumber = Number(remainingCardIdx);
        delete this.card.cardNumbers[idxNumber];
      }

      // empty usedCard array
      // this.usedCards = []
      // clear operation
      this.operation = []
    },
    // resetting game cards when last card in set is not 24
    async resetCard(id: number) {
      const response = await getCardById(id);
      this.card = response;
      const message = 'WARNING: didn\'t get 24, cards were reset.';
      this.errors.push(message);
      setTimeout(() => {
        this.errors = [];
      }, 3000);
    },
    // auto reset game variables and show a new card
    newCard() {
      setTimeout(() => {
        this.operation = []
        this.handleShowCard()
      }, 200)
    },
    // setting game starting timer
    startingTimerDone(value: number) {
      if (value == 0) {
        this.starting = false
      }
    },
    // setting game timer to true when the timer is done to 0
    gameTimerDone(value: number) {
      if (value == 0) {
        this.endGame = true
      }
    },
    saveTime() {
      const query: LocationQuery = this.$route.query;
      const gameTime: string | Object = query.gameTime!.valueOf();
      this.gameTimer = Number.parseInt(gameTime.toString());
    }
  },
  watch: {
    card: {
      handler(newValue: Card) {
        if (newValue) {
          // array still includes 4 elements, so to get
          // the correct response the array is converted to a
          // string and filtered of its commas to get the remaining values
          const conversion = newValue!.cardNumbers
            .filter((num: number | string) => num !== undefined)
            .join(',');

          if (conversion === '24') {
            delete this.idsInDB[newValue.cardId]
            if (this.idsInDB.some((item) => typeof item === 'number')) {
              this.newCard();
              this.solved++;
              this.correct = true;
              setTimeout(() => {
                this.correct = false;
              }, 3000);
            } else {
              this.endGame = true;
            }
          } else if (!conversion.includes(',') && conversion !== '24') {
            this.resetCard(newValue!.cardId);
          }
        }
      },
      deep: true,
      immediate: true,
    },
  },
  // grabbing all the card ids from the backend before loading app
  created() {
    this.errors = [];
    this.correct = false;
    this.starting = true;
    const validOperators: string[] = ['+', '-', 'x', '/'];
    this.operators = validOperators;
    Promise.all([
      getNewCard().then((card: Card) => {
        this.card = card;
      }),
      getAllCardIDs().then((ids: number[]) => {
        this.idsInDB = ids;
      }),
    ])
    this.saveTime();
    this.endGame = false;
  },
}
</script>
<template>
  <div v-if="starting">
    <StartingTimer @done="startingTimerDone($event)" />
  </div>
  <div v-else="starting">
    <div>
      <div class="text-dusty-midnight-300 text-4xl" v-if="endGame">
        <p>Game Over</p>
        <p v-if="solved !== 1">Congratulations! You solved {{ solved }} sets!</p>
        <p v-if="solved == 1">Congratulations! You solved 1 set!</p>
      </div>
      <NavBar />
      <div class="block mr-auto ml-auto mb-4 text-3xl text-green-600" v-show="correct && !endGame">
        <p v-if="solved !== 1">Correct! You have solved {{ solved }} sets so far!</p>
        <p v-if="solved == 1">Correct! You have solved 1 set so far!</p>
      </div>
      <div class="block mr-auto ml-auto mb-4 text-3xl text-dusty-midnight-300" v-show="errors.length > 0">
        {{ errors[0] }}
      </div>
      <div class="rounded-xl grid grid-cols-2 gap-8 mt-5" v-if="!endGame">
        <div class="border-4 border-dusty-midnight-300">
          <div class="border-b-2 border-dusty-midnight-300">
            <TitleBar title="Complete Court" />
          </div>
          <div class="flex flex-col max-h-[450px]">
            <img src="../assets/images/gameboard_background.png" height="600" width="450" class="h-auto max-w-full" />
            <div v-if="card && !loading">
              <section class="flex flex-row relative bottom-40 left-6">
                <div v-if="card.cardNumbers[0]" class="absolute">
                  <CardButton cardId="0" :cardNumber="card.cardNumbers[0]" :operationLength="operation.length"
                    @click="handleGameClick('0', card.cardNumbers[0].toString())" />
                </div>
                <div v-if="card.cardNumbers[1]" class="absolute left-24">
                  <CardButton cardId="1" :cardNumber="card.cardNumbers[1]" :operationLength="operation.length"
                    @click="handleGameClick('1', card.cardNumbers[1].toString())" />
                </div>
                <div v-if="card.cardNumbers[2]" class="absolute left-48">
                  <CardButton cardId="2" :cardNumber="card.cardNumbers[2]" :operationLength="operation.length"
                    @click="handleGameClick('2', card.cardNumbers[2].toString())" />
                </div>
                <div v-if="card.cardNumbers[3]" class="absolute left-72">
                  <CardButton cardId="3" :cardNumber="card.cardNumbers[3]" :operationLength="operation.length"
                    @click="handleGameClick('3', card.cardNumbers[3].toString())" />
                </div>
              </section>
            </div>
          </div>
        </div>
        <div>
          <GameTimer :seconds="gameTimer" @done="gameTimerDone($event)" />
          <div class="operators border-4 border-dusty-midnight-300 max-w-fit">
            <TitleBar title="Operators" />
            <div class="grid grid-cols-4 gap-2 border-t-2 border-t-dusty-midnight-300 p-3">
              <OperatorButton oId="addOperator" :oLength="operation.length" operator="+"
                @click="handleGameClick('addOperator', '+')" />
              <OperatorButton oId="subOperator" :oLength="operation.length" operator="-"
                @click="handleGameClick('subOperator', '-')" />
              <OperatorButton oId="mulOperator" :oLength="operation.length" operator="x"
                @click="handleGameClick('mulOperator', 'x')" />
              <OperatorButton oId="divOperator" :oLength="operation.length" operator="/"
                @click="handleGameClick('divOperator', '/')" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
