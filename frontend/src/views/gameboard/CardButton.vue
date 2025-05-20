<script lang="ts">
import { Icon, IconCloverFilled, IconDiamondsFilled, IconHeartFilled, IconSpadeFilled } from '@tabler/icons-vue';

export default {
  components: {
    IconHeartFilled, IconSpadeFilled, IconCloverFilled, IconDiamondsFilled,
  },
  props: {
    cardId: Number,
    cardNumber: Number,
    operationLength: Number,
  },
  data() {
    return {
      cId: 0 as number,
      cNumber: 0 as number,
      isActive: false,
      operationLength: this.operationLength,
      icon: 0 as number,
    }
  },
  watch: {
    operationLength(newValue) {
      if (newValue === 3) {
        this.isActive = false;
      }
    }
  },
  methods: {
    toggleActive() {
      this.isActive = !this.isActive;
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
    <button id="${cardId}" :class="isActive ? 'activeBtn relative' : 'relative'" @click="[toggleActive(), $emit('click')]">
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
@import 'tailwindcss';

button {
  @apply bg-blue-500 rounded-lg h-[100px] w-[70px] text-white outline-2 outline-solid outline-blue-600 outline-offset-2;
}

button:hover {
  @apply bg-white border-blue-500 border-2 text-blue-500;
}

button:active {
  @apply bg-white border-blue-500 border-2 text-blue-500;
}

.activeBtn {
  @apply bg-white border-blue-500 border-2 text-blue-500;
}

</style>
