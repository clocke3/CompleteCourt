<script setup lang="ts">
</script>

<template>
  <main>
    <div id="content">
      <div class="welcomeMessage text-dusty-midnight-300">
          <p class="text-3xl">Welcome To</p>
          <img src="../assets/images/app_logo.png" class="w-[33%] z-1 ml-[490px]"/>
          <p class="text-sm mt-3">Can you get the number 24 from a set of cards?</p>
      </div>
      <div class="gameModes">
        <router-link to="/loading" custom v-slot="{ navigate }">
          <button @click="navigate" role="link" class="bg-dusty-midnight-300 m-2 p-2 text-lg font-semibold rounded-xl text-white">
           Start Solo Game
          </button>
        </router-link>
      </div>
    </div>
  </main>
</template>

<style scoped>
@import 'tailwindcss';

.welcomeMessage {
  @apply font-bold pb-2;
  animation: fadeUp 3s;
}

@keyframes fadeUp {
  from {
    opacity: 0%;
    margin-top: 100px;
  }
  to {
    opacity: 100%;
    margin-top: 0px;
  }
}
</style>
