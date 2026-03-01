<template>
  <div class="min-h-screen bg-gray-50 font-sans text-gray-900">
    <!-- --- BDJOBS STYLE HEADER --- -->
    <header class="bg-[#004d99] text-white py-12 px-4 shadow-inner">
      <div class="max-w-5xl mx-auto text-center">
        <h1 class="text-3xl md:text-4xl font-bold mb-8 text-black">Search Your Dream Job</h1>

        <!-- SEARCH BOX -->
        <div class="bg-white rounded-md shadow-xl p-2 flex flex-col md:flex-row items-stretch gap-2 max-w-4xl mx-auto">
          <div class="flex-1 flex items-center px-3 border-b md:border-b-0 md:border-r border-gray-200">
            <span class="text-gray-400 mr-2">🔍</span>
            <input type="text" placeholder="Job Title, Skills, or Company"
              class="w-full py-3 outline-none text-gray-700" />
          </div>
          <button
            class="bg-[#f05a28] hover:bg-[#d44d20] text-black font-bold px-10 py-3 rounded transition-colors uppercase tracking-wide">
            Search
          </button>
        </div>
      </div>
    </header>

    <!-- --- JOB LISTING SECTION --- -->
    <main class="max-w-6xl mx-auto py-10 px-4">
      <div class="flex items-center justify-between mb-8">
        <h2 class="text-2xl font-bold text-gray-800 border-l-8 border-[#004d99] pl-4">
          Recent Jobs
        </h2>
        <span class="text-sm text-gray-500">{{ jobs.length }} jobs found</span>
      </div>

      <!-- Loading State -->
      <div v-if="loading" class="flex justify-center items-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-[#004d99]"></div>
      </div>

      <!-- Error State -->
      <div v-if="error" class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded mb-6">
        {{ error }}
      </div>

      <!-- Job Grid -->
      <div v-if="!loading && !error" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div v-for="job in jobs" :key="job.id"
          class="bg-white rounded border border-gray-200 p-5 hover:shadow-lg transition-all cursor-pointer group relative overflow-hidden flex flex-col h-full">
          <div
            class="absolute top-0 left-0 w-full h-1 bg-[#004d99] transform -translate-x-full group-hover:translate-x-0 transition-transform">
          </div>

          <div class="mb-4">
            <h3 class="text-[#004d99] font-bold text-lg group-hover:underline leading-tight mb-2">
              {{ job.title }}
            </h3>
            <p class="text-gray-800 font-semibold text-sm">
              {{ job.companyName }}
            </p>
          </div>

          <div class="space-y-2 mb-6 flex-grow text-sm text-gray-600">
            <div class="flex items-center">
              <span class="mr-2">📍</span> {{ job.location || 'Dhaka' }}
            </div>
            <div class="flex items-center font-medium">
              <span class="mr-2 text-green-600">৳</span>
              {{ job.salary ? job.salary.toLocaleString() + ' BDT' : 'Negotiable' }}
            </div>
          </div>

          <div class="pt-4 border-t flex items-center justify-between text-xs">
            <div class="flex items-center text-gray-400">
              Deadline: Open
            </div>
            <div class="text-blue-600 font-bold group-hover:translate-x-1 transition-transform">
              <router-link :to="'/job/' + job.id"
                class="text-blue-600 font-bold uppercase cursor-pointer flex items-center">
                View Details →
              </router-link>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="!loading && jobs.length === 0 && !error"
        class="text-center py-20 bg-white border border-dashed rounded-lg">
        <p class="text-gray-500">No jobs posted yet. Check back later!</p>
      </div>
    </main>

    <footer class="bg-gray-800 text-white py-10 text-center mt-20">
      <p class="opacity-50 text-sm">© 2026 Bdjobs Clone. Powered by Spring Boot & Vue 3.</p>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import api from '../services/api.js'; // Centralized API service for better maintainability

const jobs = ref([]);
const loading = ref(true);
const error = ref(null);

const fetchJobs = async () => {
  try {
    loading.value = true;
    const response = await api.get('/jobs'); // No hardcoded URL!
    jobs.value = response.data;
  } catch (err) {
    error.value = "Unable to connect to the Job Server.";
  } finally {
    loading.value = false;
  }
};

onMounted(fetchJobs);
</script>

<style scoped>
/* Fallback for Tailwind if the build step is missing */
@import url('https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css');

/* Custom scrollbar to look modern */
::-webkit-scrollbar {
  width: 8px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background: #004d99;
  border-radius: 10px;
}
</style>