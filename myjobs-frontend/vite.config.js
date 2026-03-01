import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      // This is the magic line that fixes your error
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})