<script lang="ts">
export default {
  props: {
    oId: String,
    operator: String,
    oLength: Number,
  },
  data() {
    return {
      isActive: false
    }
  },
  computed: {
    currentLength() {
      return this.oLength
    }
  },
  watch: {
    oLength(newValue) {
      if (newValue === 3) {
        this.isActive = false;
      }
    }
  },
  methods: {
    toggleActive() {
      if (this.oLength !== 0)  // Use prop directly here
        this.isActive = !this.isActive;
    }
  }
}
</script>

<template>
  <div>
    <button id="${oId}" :class="isActive ? 'activeBtn' : ''" @click="[toggleActive(), $emit('click')]">
      <p class="text-5xl pl-2"> {{ operator }}</p>
    </button>
  </div>
</template>

<style scoped>
@import 'tailwindcss';

button {
  @apply h-20 w-20 bg-blue-300 border-t-blue-400 border-l-blue-400 border-[4px] text-lg font-semibold m-2 p-2;
}

button:hover {
  @apply text-blue-400;
}

button:active {
  @apply bg-white border-black border-b-blue-300 border-r-blue-300 text-blue-300 border-[4px];
}

.activeBtn {
  @apply bg-white border-black border-b-blue-300 border-r-blue-300 text-blue-300 border-[4px];
}
</style>
