<script lang="ts">
export default {
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
  <div>
    <div v-if="seconds == 60">
    <p class="text-6xl text-black"> 01:00 </p>
  </div>
  <div v-if="seconds < 60 && seconds >= 10">
    <p class="text-6xl text-black"> 00:{{ seconds }} </p>
  </div>
  <div v-if="seconds < 10">
    <p class="text-6xl text-black"> 00:0{{ seconds }} </p>
  </div>
  </div>
</template>
