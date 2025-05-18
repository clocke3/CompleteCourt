<script lang="ts">
export default {
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
  created() {
    this.cId = this.cardId!;
    this.cNumber = this.cardNumber!;
  }
}
</script>

<template>
  <div>
    <button id="${cardId}" :class="isActive ? 'activeBtn' : ''" @click="[toggleActive(), $emit('click')]">
      <span class="text-4xl pl-1">
        {{ cardNumber }}
      </span>
    </button>
  </div>
</template>

<style scoped>
@import 'tailwindcss';

button {
  @apply bg-blue-500 rounded-lg h-[100px] w-[70px] text-white;
}

button:active {
  @apply bg-white border-blue-500 border-2 text-blue-500;
}

.activeBtn {
  @apply bg-white border-blue-500 border-2 text-blue-500;
}

</style>
