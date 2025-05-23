<script lang="ts">
  export default {
    data() {
      return {
        percentage: 0,
      }
    },
    methods: {
      chooseRandomNumber() {
        return Math.floor(Math.random() * 21)  + 1;
      }
    },
    watch: {
    percentage: {
      handler(value) {
        if (value < 100) {
          setTimeout(() => {
            const rand = this.chooseRandomNumber();
            this.percentage+=rand;
          }, 1000);
        } else {
          this.$router.push('/game');
        }
      },
      immediate: true
    }
  }
  }
</script>

<template>
 <div>
  <div class="h-28 w-96 border-2 rounded-lg border-dusty-midnight-300">
    <div class="bar border-b-2 border-b-dusty-midnight-300 bg-dusty-midnight-300">
      <span class="text-white">Entering Game...</span>
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
  0% {width: 0;}
  100% {width: 100%;}
}

</style>
