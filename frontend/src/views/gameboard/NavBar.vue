<script lang="ts">
export default {
  methods: {
    reloadPage() {
      window.location.reload();
    }
  }
}
</script>

<template>
  <div class="absolute top-1 right-3">
    <nav>
      <RouterLink to="/" class="mr-4">[ESC] Exit Game</RouterLink>
      <a class="cursor-pointer" @click="reloadPage">[R] Reset Game</a>
    </nav>
  </div>
</template>
