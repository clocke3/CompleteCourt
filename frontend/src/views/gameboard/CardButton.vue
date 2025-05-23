<script lang="ts">
import { Icon, IconCloverFilled, IconDiamondsFilled, IconHeartFilled, IconSpadeFilled } from '@tabler/icons-vue';

export default {
  components: {
    IconHeartFilled, IconSpadeFilled, IconCloverFilled, IconDiamondsFilled,
  },
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
    oLength(newValue) {
      if (newValue === 2) {
        this.isActive = false;
      }
    }
  },
  methods: {
    toggleActive() {
      console.log(this.operationLength);
      if (this.operationLength !== 2) {
        this.isActive = !this.isActive;
      }
    },
    randomizeIcon() {
      const icons = [IconHeartFilled, IconCloverFilled, IconDiamondsFilled, IconSpadeFilled];
      return Math.floor(Math.random() * icons.length + 1);
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
    <button id="${cId}" :class="isActive ? 'activeBtn relative' : 'relative'" @click="[toggleActive(), $emit('click')]">
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
  @apply absolute top-14 left-9 text-4xl pl-1 text-white z-40;
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
  @apply absolute top-14 left-6 text-4xl pl-1 text-white z-40;
}

.activeBtn > .doubleDigits {
  @apply text-dusty-midnight-300;
}

</style>
