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
  <div class="h-10 p-1.5 bg-blue-300">
    <p class="float-left text-xl"> {{ title }}</p>
    <div class="flex flex-row float-right">
      <IconLineDashed class="relative top-2"/>
      <IconSquare />
      <IconX />
    </div>
  </div>
</template>
