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
      <div class="absolute left-0.5 top-1">
        <IconCloverFilled v-if="icon === 1" />
        <IconHeartFilled v-if="icon === 2" />
        <IconDiamondsFilled v-if="icon === 3" />
        <IconSpadeFilled v-if="icon === 4" />
      </div>
      <span class="text-4xl pl-1">
        {{ cardNumber }}
      </span>
      <div class="absolute right-0.5 bottom-1">
        <IconCloverFilled v-if="icon === 1" />
        <IconHeartFilled v-if="icon === 2" />
        <IconDiamondsFilled v-if="icon === 3" />
        <IconSpadeFilled v-if="icon === 4" />
      </div>
    </button>
  </div>
</template>

<style scoped>
@reference '../../assets/main.css';
@import "tailwindcss";

button {
  @apply bg-dusty-midnight-300 rounded-lg h-[100px] w-[70px] text-lg font-semibold m-2 p-2 text-white outline-4 outline-solid outline-dusty-midnight-300 outline-offset-2;
}

button:hover {
  @apply bg-white h-[109px] w-[74px] border-dusty-midnight-300 border-2 text-dusty-midnight-300 outline-0;
}

button:active {
  @apply bg-white border-dusty-midnight-300 border-2 text-dusty-midnight-300 outline-0;
}

.activeBtn {
  @apply bg-white border-dusty-midnight-300 border-2 text-dusty-midnight-300 outline-0;
}
</style>
