<script setup lang="ts">
</script>

<template>
  <main>
    <div id="content">
      <div class="welcomeMessage text-dusty-midnight-300">
          <p class="text-3xl">Welcome To</p>
          <img src="../assets/images/app_logo.png" class="w-[33%] block ml-auto mr-auto"/>
          <p class="mt-3 text-lg">Can you get the number 24 from a set of cards?</p>
      </div>
      <div class="gameModes">
        <router-link :to="{ path: '/loading', query: { time: 60 }}" custom v-slot="{ navigate }">
          <button @click="navigate" role="link" class="bg-dusty-midnight-300 m-2 p-2 text-lg font-semibold rounded-xl text-white">
           Start 1 Minute Game
          </button>
        </router-link>
        <router-link :to="{ path: '/loading', query: { time: 120 }}" custom v-slot="{ navigate }">
          <button @click="navigate" role="link" class="bg-dusty-midnight-300 m-2 p-2 text-lg font-semibold rounded-xl text-white">
           Start 2 Minute Game
          </button>
        </router-link>
        <router-link :to="{ path: '/loading',  query: { time: 300 }}" custom v-slot="{ navigate }">
          <button @click="navigate" role="link" class="bg-dusty-midnight-300 m-2 p-2 text-lg font-semibold rounded-xl text-white">
           Start 5 Minute Game
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
