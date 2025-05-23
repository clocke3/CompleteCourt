<script lang="ts">
import { IconSquare, IconLineDashed, IconX } from '@tabler/icons-vue';
export default {
  components: { IconSquare, IconLineDashed, IconX },
  props: {
    title: String,
  }
}
</script>

<template>
  <div class="h-10 p-1.5 bg-dusty-midnight-300">
    <p class="float-left text-xl text-white"> {{ title }}</p>
    <div class="flex flex-row float-right">
      <IconLineDashed class="relative top-2 text-white"/>
      <IconSquare class="text-white"/>
      <IconX class="text-white"/>
    </div>
  </div>
</template>
