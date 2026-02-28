<template>
  <div id="app">
    <!-- This tag is where HomeView.vue will be injected when you are on the "/" route -->
    <router-view />
  </div>
</template>

<script setup>
// No logic needed here for now; the router handles the switching
</script>

<style>
/* Global styles like font-family go here */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap');

body {
  font-family: 'Inter', sans-serif;
  margin: 0;
  padding: 0;
}
</style>