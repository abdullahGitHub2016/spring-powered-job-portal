import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css' // Assuming your Tailwind CSS is here

const app = createApp(App)

app.use(router)

app.mount('#app')