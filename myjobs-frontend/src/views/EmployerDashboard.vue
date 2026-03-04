<template>
  <div class="min-h-screen bg-gray-50 p-6">
    <div class="max-w-6xl mx-auto">
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-800">Employer Dashboard</h1>
        <p class="text-gray-600">Welcome back! Here is what's happening with your job postings.</p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
        <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex items-center gap-4">
          <div class="p-3 bg-blue-100 text-blue-600 rounded-lg text-2xl">💼</div>
          <div>
            <p class="text-sm text-gray-500 font-medium">Active Jobs</p>
            <h3 class="text-2xl font-bold">{{ stats.jobCount }}</h3>
          </div>
        </div>
        
        <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex items-center gap-4">
          <div class="p-3 bg-green-100 text-green-600 rounded-lg text-2xl">📝</div>
          <div>
            <p class="text-sm text-gray-500 font-medium">Total Applications</p>
            <h3 class="text-2xl font-bold">{{ stats.appCount }}</h3>
          </div>
        </div>

        <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex items-center gap-4">
          <div class="p-3 bg-purple-100 text-purple-600 rounded-lg text-2xl">🚀</div>
          <div>
            <p class="text-sm text-gray-500 font-medium">New Applicants</p>
            <h3 class="text-2xl font-bold">Check Admin</h3>
          </div>
        </div>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div class="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-shadow">
          <h2 class="text-xl font-bold text-gray-800 mb-3">Manage Applications</h2>
          <p class="text-gray-600 mb-6">Review resumes, cover letters, and contact information for candidates who applied to your roles.</p>
          <router-link to="/admin/applications" class="inline-block bg-[#004d99] text-white px-6 py-3 rounded-lg font-bold hover:bg-blue-800 transition-colors">
            View All Applications →
          </router-link>
        </div>

        <div class="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-shadow">
          <h2 class="text-xl font-bold text-gray-800 mb-3">Post a New Position</h2>
          <p class="text-gray-600 mb-6">Reach thousands of qualified candidates by posting your open requirements on the board.</p>
          <button @click="alert('Post Job form coming soon!')" class="inline-block bg-green-600 text-white px-6 py-3 rounded-lg font-bold hover:bg-green-700 transition-colors">
            + Post a Job
          </button>
        </div>
      </div>

      <div v-if="loading" class="fixed inset-0 bg-white/60 flex items-center justify-center z-50">
        <div class="animate-spin rounded-full h-10 w-10 border-b-2 border-[#004d99]"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import api from '@/api/axios'; //

const loading = ref(true);
const stats = reactive({
  jobCount: 0,
  appCount: 0
});

const fetchDashboardData = async () => {
  try {
    loading.value = true;
    
    // Fetch Jobs Count
    const jobsRes = await api.get('/jobs');
    stats.jobCount = jobsRes.data.length;

    // Fetch Applications Count - Requires Token
    const token = localStorage.getItem('token');
    const appsRes = await fetch('http://localhost:8080/api/applications', {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    
    if (appsRes.ok) {
      const appsData = await appsRes.json();
      stats.appCount = appsData.length;
    }
  } catch (error) {
    console.error("Error loading dashboard stats:", error);
  } finally {
    loading.value = false;
  }
};

onMounted(fetchDashboardData);
</script>

<style scoped>
/* Tailwind fallback */
@import url('https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css');
</style>