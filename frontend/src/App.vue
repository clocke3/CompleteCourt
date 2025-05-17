<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
      <nav>
        <RouterLink to="/" />
        <RouterLink to="/game" />
      </nav>
  </header>
  <RouterView />
</template>

<style>
</style>
