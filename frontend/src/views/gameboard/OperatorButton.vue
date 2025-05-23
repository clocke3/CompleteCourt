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
      if (this.oLength !== 0)
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
@reference '../../assets/main.css';
@import "tailwindcss";

button {
  @apply h-20 w-20 bg-dusty-midnight-200 border-b-black border-r-black border-[4px] text-lg text-white font-semibold m-2 p-2;
}

button:hover {
  @apply bg-dusty-midnight-300 text-white;
}

button:active {
  @apply bg-white border-black border-b-dusty-midnight-100 border-r-dusty-midnight-100 text-dusty-midnight-200 border-[4px];
}

.activeBtn {
  @apply bg-white border-black border-b-dusty-midnight-100 border-r-dusty-midnight-100 text-dusty-midnight-200 border-[4px];
}
</style>
