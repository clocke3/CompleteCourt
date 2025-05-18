<script lang="ts">

export default {
  props: {
    operator: String,
  }
}
</script>

<template>
  <div>
    <button @click="$emit('click')">
      <p class="text-5xl pl-1.5"> {{ operator }}</p>
    </button>
  </div>
</template>

<style scoped>
@import 'tailwindcss';

button {
  @apply h-20 w-20 bg-blue-300 border-t-blue-400 border-l-blue-400 border-[4px];
}

button:active {
  @apply bg-white border-black border-b-blue-300 border-r-blue-300 text-blue-600 border-[4px];
}
</style>
