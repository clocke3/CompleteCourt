<script lang="ts">
import { IconLineDashed, IconSquare, IconX } from '@tabler/icons-vue';

export default {
  components: { IconSquare, IconLineDashed, IconX },
  data() {
    return {
      seconds: 60,
    }
  },
  watch: {
    seconds: {
      handler(value) {
        if (value > 0) {
          setTimeout(() => {
            this.seconds--;
          }, 1000);
        } else {
          this.$emit('done', value);
        }
      },
      immediate: true
    }
  }
}
</script>

<template>
  <div class="flex flex-col mb-3">
    <div class="border-2 border-b-0 border-dusty-midnight-300 w-[290px]">
      <TitleBar title="Timer" />
    </div>
    <div class="flex flex-row mb-3">
      <div class="border-2 border-dusty-midnight-300 h-20 w-[210px] pl-2 pt-2">
        <div v-if="seconds == 60">
          <p class="text-6xl text-dusty-midnight-300"> 01:00 </p>
        </div>
        <div v-if="seconds < 60 && seconds > 10">
          <p class="text-6xl text-dusty-midnight-300"> 00:{{ seconds }} </p>
        </div>
        <div v-if="seconds == 10">
          <p class="text-6xl text-red-600"> 00:{{ seconds }} </p>
        </div>
        <div v-if="seconds < 10">
          <p class="text-6xl text-red-600"> 00:0{{ seconds }} </p>
        </div>
      </div>
      <div class="hourglass border-2 border-dusty-midnight-300 border-l-0 flex-none w-20">
        <div class="mt-0.5 animate-spin-slow">
          <img src="@/assets/images/hourglass2.png.webp" height="115px" width="70px"/>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
@import 'tailwindcss';

@theme {
  --animate-spin-slow: spin-slow 2s infinite;
  @keyframes spin-slow {
    50% {
      transform: rotate(90deg);
    }
    100% {
      transform: rotate(180deg);
    }
  }
}
</style>
