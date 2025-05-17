<script setup lang="ts">
</script>

<template>
  <main>
    <div id="content">
      <div class="welcomeMessage mb-3.5">
          <p class="text-l">Welcome To</p>
          <p class="text-2xl">Complete Court!</p>
          <p class="text-sm">Can you get 24 from a set of cards?</p>
      </div>
      <div class="gameModes">
        <router-link to="/game" custom v-slot="{ navigate }">
          <button @click="navigate" role="link" class="bg-blue-300">
           Start Solo Game
          </button>
        </router-link>
      </div>
    </div>
  </main>
</template>
