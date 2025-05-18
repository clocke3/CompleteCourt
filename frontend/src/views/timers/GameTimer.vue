<script lang="ts">
import { IconLineDashed, IconSquare, IconX } from '@tabler/icons-vue';

export default {
  components: { IconSquare, IconLineDashed, IconX },
  data() {
    return {
      seconds: 60,
    }
  },
  watch: {
    seconds: {
      handler(value) {
        if (value > 0) {
          setTimeout(() => {
            this.seconds--;
          }, 1000);
        } else {
          this.$emit('done', value);
        }
      },
      immediate: true
    }
  }
}
</script>

<template>
  <div class="flex flex-col mb-3">
    <div class="border-2 border-b-0 w-[290px]">
      <TitleBar title="Timer" />
    </div>
    <div class="flex flex-row mb-3">
      <div class="border-2 h-20 w-[210px] pl-2 pt-2">
        <div v-if="seconds == 60">
          <p class="text-6xl text-black"> 01:00 </p>
        </div>
        <div v-if="seconds < 60 && seconds >= 10">
          <p class="text-6xl text-black"> 00:{{ seconds }} </p>
        </div>
        <div v-if="seconds < 10">
          <p class="text-6xl text-black"> 00:0{{ seconds }} </p>
        </div>
      </div>
      <div class="hourglass border-2 border-l-0 flex-none w-20">
        hourglass
      </div>
    </div>
  </div>
</template>
