<template>
  <div id="app">
    <Navbar />
    
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import Navbar from './components/Navbar.vue'; // Ensure this component exists
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap');

body {
  font-family: 'Inter', sans-serif; /* Matches industry standard font */
  margin: 0;
  background-color: #f4f7f9;
}
.main-content {
  padding: 20px;
}
</style>