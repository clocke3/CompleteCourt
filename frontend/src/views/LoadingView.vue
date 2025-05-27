<script lang="ts">
import { LocationQuery, useRoute } from 'vue-router';
import { computed } from 'vue'
export default {
  data() {
    return {
      percentage: 0,
      timer: 0 as number,
      timerText: '' as string,
    }
  },
  methods: {
    chooseRandomNumber() {
      return Math.floor(Math.random() * 21) + 1;
    },
    saveTime(){
      const query: LocationQuery = this.$route.query;
      const time: string = query.time!.toString()
      this.timer = Number.parseInt(time);
      if (this.timer == 60) {
        this.timerText = '1'
      } else if (this.timer == 120) {
        this.timerText = '2'
      } else if (this.timer == 300) {
        this.timerText = '5'
      }
    }
  },
  watch: {
    percentage: {
      handler(value) {
        if (value < 100) {
          setTimeout(() => {
            const rand = this.chooseRandomNumber();
            this.percentage += rand;
          }, 1000);
        } else {
          this.$router.push({ path: '/game', query: { gameTime: this.timer } })
        }
      },
      immediate: true
    }
  },
  created() {
    this.saveTime()
  }
}
</script>

<template>
  <div>
    <div class="h-28 w-96 border-2 border-dusty-midnight-300">
      <div class="bar border-b-2 border-b-dusty-midnight-300 bg-dusty-midnight-300" v-b>
        <span class="text-white">Entering {{ timerText }} Minute Game...</span>
      </div>
      <div class="loadingBarArea flex flex-row ml-3">
        <div class="loadingBar h-7 w-64 inset-shadow-xs inset-shadow-gray-500 ml-8 mt-7">
          <div class="progressBar h-full bg-dusty-midnight-300 origin-left-right" style="opacity: 70%;"></div>
        </div>
        <span class="percentage text-dusty-midnight-300 ml-3 mt-7.5"> {{ percentage }}%</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.progressBar {
  animation: progress 1s infinite linear;
}

@keyframes progress {
  0% {
    width: 0;
  }

  100% {
    width: 100%;
  }
}
</style>
