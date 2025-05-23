<script lang="ts">
import { Card } from '../components/types/card'
import { getNewCard, solve, getAllCardIDs, getCardById } from '../services/gameApi'
import { IconX } from '@tabler/icons-vue';

export default {
  components: {
    IconX,
  },
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
      errors: [] as string[],
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

      } catch (error) {
        this.errors.push(errorMessage);
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
      const newNumber = await solve(this.card, this.operation)
      console.log(newNumber);

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
  },
  watch: {
    // errors: {
    //   handler(newError: string) {
    //     if (newError) {

    //     }
    //   }
    // }
    card: {
      handler(newValue: Card) {
        if (newValue) {
          // array still includes 4 elements, so to get
          // the correct response the array is converted to a
          // string and filtered of its commas to get the remaining values
          const conversion = newValue!.cardNumbers
            .filter((num: number) => num !== undefined)
            .join(',')

          if (conversion === '24') {
            delete this.idsInDB[newValue.cardId]
            if (this.idsInDB.some((item) => typeof item === 'number')) {
              this.newCard()
            } else {
              this.endGame = true
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
      <div class="endGame bg-dusty-midnight-300 text-white rounded-xl p-2" v-if="endGame">
        <p>Game Over</p>
      </div>
      <NavBar />
      <div class="errors absolute top-1/12 left-[490px] text-3xl text-dusty-midnight-300" v-show="errors.length > 0">
        {{  errors[0] }}
      </div>
      <div class="absolute top-[218px] left-[306px] z-1 w-2xl" v-if="!endGame">
        <img src="../assets/images/gameboard_background.png" class="relative w-[58.9%]" />
      </div>
      <div class="gameBoard rounded-xl grid grid-cols-2 gap-8" v-if="!endGame">
        <div class="h-[99%] w-[400px] border-2 border-dusty-midnight-300">
          <div class="border-b-2 border-dusty-midnight-300">
            <TitleBar title="Complete Court" />
          </div>
          <div v-if="card && !loading" class="cardDisplay h-[400px]">
            <section class="cards flex flex-row absolute top-[460px] right-[720px] z-4">
              <div v-if="card.cardNumbers[0]" class="absolute top-0 right-7">
                <CardButton cardId="0" :cardNumber="card.cardNumbers[0]" :operationLength="operation.length"
                  @click.stop="handleGameClick('0', card.cardNumbers[0].toString())" />
              </div>
              <div v-if="card.cardNumbers[1]" class="absolute top-0 right-[115px]">
                <CardButton cardId="1" :cardNumber="card.cardNumbers[1]" :operationLength="operation.length"
                  @click.stop="handleGameClick('1', card.cardNumbers[1].toString())" />
              </div>
              <div v-if="card.cardNumbers[2]" class="absolute top-0 right-[202px]">
                <CardButton cardId="2" :cardNumber="card.cardNumbers[2]" :operationLength="operation.length"
                  @click.stop="handleGameClick('2', card.cardNumbers[2].toString())" />
              </div>
              <div v-if="card.cardNumbers[3]" class="absolute top-0 right-[289px]">
                <CardButton cardId="3" :cardNumber="card.cardNumbers[3]" :operationLength="operation.length"
                  @click.stop="handleGameClick('3', card.cardNumbers[3].toString())" />
              </div>
            </section>
          </div>
        </div>
        <div class="rightSide z-10">
          <GameTimer @done="gameTimerDone($event)" />
          <div class="operators border-2 border-dusty-midnight-300">
            <TitleBar title="Operators" />
            <div class="grid grid-flow-col auto-cols-max border-t-2 border-t-dusty-midnight-300 p-1.5">
              <OperatorButton oId="addOperator" :oLength="operation.length" operator="+"
                @click.stop="handleGameClick('addOperator', '+')" />
              <OperatorButton oId="subOperator" :oLength="operation.length" operator="-"
                @click.stop="handleGameClick('subOperator', '-')" />
              <OperatorButton oId="mulOperator" :oLength="operation.length" operator="x"
                @click.stop="handleGameClick('mulOperator', 'x')" />
              <OperatorButton oId="divOperator" :oLength="operation.length" operator="/"
                @click.stop="handleGameClick('divOperator', '/')" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
