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
      turn: false,
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
         if(value % 6 == 0){
          this.turn = !this.turn;
         }
        } else {
          this.$emit('done', value);
        }
      },
      immediate: true
    }
  },
  methods: {
    convertMinutes(e: number) {
      if (e < 60) {
        return e.toString().padStart(2, '0');
      }
      return Math.floor(e % 3600 / 60).toString().padStart(2, '0');
    },
    convertSeconds(e: number) {
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
        <div v-if="overall >= 60">
          <p class="text-6xl text-dusty-midnight-300"> {{ min }}:{{ sec }}</p>
        </div>
        <div v-if="overall < 60 && overall > 10">
          <p class="text-6xl text-dusty-midnight-300"> 00:{{ sec }} </p>
        </div>
        <div v-if="overall == 10">
          <p class="text-6xl text-red-600"> 00:{{ sec }} </p>
        </div>
        <div v-if="overall < 10">
          <p class="text-6xl text-red-600"> 00:{{ sec }} </p>
        </div>
      </div>
      <div class="hourglass border-2 border-dusty-midnight-300 border-l-0 flex-none w-20">
        <div class="glass">
          <img src="@/assets/images/timers/hourglass.png" height="120px" width="71px" v-show="overall % 6 == 0 && turn" />
          <img src="@/assets/images/timers/hourglass_2.png" height="120px" width="71px" v-show="overall % 6 == 1" />
          <img src="@/assets/images/timers/hourglass_3.png" height="120px" width="71px" v-show="overall % 6 == 2" />
          <img src="@/assets/images/timers/hourglass_4.png" height="120px" width="71px" v-show="overall % 6 == 3" />
          <img src="@/assets/images/timers/hourglass_5.png" height="120px" width="71px" v-show="overall % 6 == 4" />
          <img src="@/assets/images/timers/hourglass_6.png" height="120px" width="71px" v-show="overall % 6 == 5" />
          <img src="@/assets/images/timers/hourglass.png" class="rotate-180" height="120px" width="71px" v-show="overall % 6 == 0 && !turn" />
        </div>
      </div>
    </div>
  </div>
</template>

<style>
@import 'tailwindcss';
.glass {
  @apply mt-1 mr-1 rotate-180;
}
</style>
