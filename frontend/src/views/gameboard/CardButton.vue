<script lang="ts">
export default {
  props: {
    cardId: String,
    cardNumber: Number,
    operationLength: Number,
  },
  data() {
    return {
      cId: '' as string,
      cNumber: 0 as number,
      isActive: false,
      icon: 0 as number,
    }
  },
  computed: {
    currentLength() {
      return this.operationLength
    }
  },
  watch: {
    operationLength(newValue) {
      if (newValue === 2) {
        this.isActive = false;
      }
    }
  },
  methods: {
    toggleActive() {
      console.log(this.operationLength);
      if (this.operationLength == 0) {
        this.isActive = !this.isActive;
      }
    },
    randomizeIcon() {
      return Math.floor(Math.random() * 4 + 1);
    }
  },
  created() {
    this.cId = this.cardId!;
    this.cNumber = this.cardNumber!;
    this.icon = this.randomizeIcon();
  }
}
</script>

<template>
  <div>
    <button id="${cId}" :class="isActive ? 'activeBtn' : ''" @click="[toggleActive(), $emit('click')]">
        <img src="../../assets/images/cards/regular/card_clover_reg.png" v-show="!isActive && icon == 1" />
        <img src="../../assets/images/cards/active/card_clover_active.png" v-show="isActive && icon == 1" />
        <img src="../../assets/images/cards/regular/card_heart_reg.png" v-show="!isActive && icon == 2" />
        <img src="../../assets/images/cards/active/card_heart_active.png" v-show="isActive && icon == 2" />
        <img src="../../assets/images/cards/regular/card_diamond_reg.png" v-show="!isActive && icon == 3" />
        <img src="../../assets/images/cards/active/card_diamond_active.png" v-show="isActive && icon == 3" />
        <img src="../../assets/images/cards/regular/card_spade_reg.png" v-show="!isActive && icon == 4" />
        <img src="../../assets/images/cards/active/card_spade_active.png" v-show="isActive && icon == 4" />
       <div :class="cardNumber > 9 ? 'doubleDigits' : 'cardNumber'">
        {{ cardNumber }}
       </div>
    </button>
  </div>
</template>

<style scoped>
@reference '../../assets/main.css';
@import "tailwindcss";

.cardNumber {
  @apply relative bottom-[70px] left-0.5 text-4xl pl-1 text-white;
}

button {
  @apply bg-transparent h-[150px] w-[94px] text-lg font-semibold m-2 p-2;
}

button:active {
  @apply bg-transparent;
}

.activeBtn > .cardNumber {
  @apply text-dusty-midnight-300;
}

.doubleDigits {
  @apply relative bottom-[70px] left-0.5 text-4xl pl-1 text-white;
}

.activeBtn > .doubleDigits {
  @apply text-dusty-midnight-300;
}

</style>
