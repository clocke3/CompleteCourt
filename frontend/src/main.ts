import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import cors from 'cors';
import StartingTimer from './views/StartingTimer.vue';

const app = createApp(App)

app.component('StartingTimer', StartingTimer)

app.use(cors)
app.use(router)

app.mount('#app')
