<script lang="ts">
import { IconLineDashed, IconSquare, IconX } from '@tabler/icons-vue';

export default {
  props: {
    seconds: Number,
  },
  components: { IconSquare, IconLineDashed, IconX },
  data() {
    return {
      min: '' as string,
      sec: '' as string,
      overall: this.seconds as number,
    }
  },
  watch: {
    overall: {
      handler(value) {
        if (value > 0) {
          setTimeout(() => {
            this.overall--;
          }, 1000);
         this.sec = this.convertSeconds(value);
         this.min = this.convertMinutes(value);
        } else {
          this.$emit('done', value);
        }
      },
      immediate: true
    }
  },
  methods: {
    convertMinutes(e) {
      if (e < 60) {
        return e.toString().padStart(2, '0');
      }
      return Math.floor(e % 3600 / 60).toString().padStart(2, '0');
    },
    convertSeconds(e) {
      if (e < 60) {
        return e.toString().padStart(2, '0');
      }
      return Math.floor(e % 60).toString().padStart(2, '0');
    }
  },
  created() {
    this.min = this.convertMinutes(this.overall);
    this.sec = this.convertSeconds(this.overall);
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
        <div v-if="this.overall >= 60">
          <p class="text-6xl text-dusty-midnight-300"> {{ min }}:{{ sec }}</p>
        </div>
        <div v-if="this.overall < 60 && this.overall > 10">
          <p class="text-6xl text-dusty-midnight-300"> 00:{{ sec }} </p>
        </div>
        <div v-if="this.overall == 10">
          <p class="text-6xl text-red-600"> 00:{{ sec }} </p>
        </div>
        <div v-if="this.overall < 10">
          <p class="text-6xl text-red-600"> 00:0{{ sec }} </p>
        </div>
      </div>
      <div class="hourglass border-2 border-dusty-midnight-300 border-l-0 flex-none w-20">
        <div class="mt-0.5 animate-spin-slow">
          <img src="@/assets/images/hourglass2.png.webp" height="115px" width="70px" />
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
