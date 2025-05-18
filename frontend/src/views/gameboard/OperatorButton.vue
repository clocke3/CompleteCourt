<script lang="ts">
export default {
  props: {
    operatorId: String,
    operator: String,
    operationLength: Number,
  },
  data() {
    return {
      operationLength: this.operationLength,
      operatorId: this.operatorId,
      isActive: false
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
    }
  },
}
</script>

<template>
  <div>
    <button id="${operatorId}" :class="isActive ? 'activeBtn' : ''" @click="[toggleActive(), $emit('click')]">
      <p class="text-5xl"> {{ operator }}</p>
    </button>
  </div>
</template>

<style scoped>
@import 'tailwindcss';

button {
  @apply h-20 w-20 bg-blue-300 border-t-blue-400 border-l-blue-400 border-[4px];
}

button:active {
  @apply bg-white border-black border-b-blue-300 border-r-blue-300 text-blue-300 border-[4px];
}

.activeBtn {
  @apply bg-white border-black border-b-blue-300 border-r-blue-300 text-blue-300 border-[4px];
}
</style>
