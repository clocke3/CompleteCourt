<script lang="ts">
export default {
  data() {
    return {
      timerCount: 3
    }
  },
  watch: {
    timerCount: {
      handler(value) {
        if (value > 0) {
          setTimeout(() => {
            this.timerCount--;
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
  <div>
    <p class="text-6xl text-pink-500"> {{ timerCount }} </p>
  </div>
</template>
