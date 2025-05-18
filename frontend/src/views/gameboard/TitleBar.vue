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
  <div class="h-10 w-[400px] border-2 p-1.5">
    <p class="float-left text-xl"> {{ title }}</p>
    <div class="flex flex-row float-right">
      <IconSquare />
      <IconLineDashed class="relative top-2"/>
      <IconX />
    </div>
  </div>
</template>
