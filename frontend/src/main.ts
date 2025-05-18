import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import cors from 'cors';
import StartingTimer from './views/timers/StartingTimer.vue';
import GameTimer from './views/timers/GameTimer.vue';
import TitleBar from './views/gameboard/TitleBar.vue';

const app = createApp(App)

app.component('StartingTimer', StartingTimer)
  .component('GameTimer', GameTimer)
  .component('TitleBar', TitleBar)

app.use(cors)
app.use(router)

app.mount('#app')
