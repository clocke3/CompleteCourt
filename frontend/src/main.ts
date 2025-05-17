import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import cors from 'cors';

const app = createApp(App)

app.use(cors)
app.use(router)

app.mount('#app')
