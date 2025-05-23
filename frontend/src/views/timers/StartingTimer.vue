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
    <span class="text-[300px] text-dusty-midnight-300"> {{ timerCount }} </span>
  </div>
</template>
