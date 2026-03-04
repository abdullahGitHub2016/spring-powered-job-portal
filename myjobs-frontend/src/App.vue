<template>
  <div id="app">
    <Navbar />
    
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import Navbar from './components/Navbar.vue';
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap');

body {
  font-family: 'Inter', sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f4f7f9;
  color: #333;
}

.main-content {
  min-height: calc(100vh - 70px);
}
</style>