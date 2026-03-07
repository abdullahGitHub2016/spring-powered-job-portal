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
            <h3 class="text-2xl font-bold">{{ stats.totalUsers }}</h3>
          </div>
        </div>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div class="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-shadow">
          <h2 class="text-xl font-bold text-gray-800 mb-3">Manage Applications</h2>
          <p class="text-gray-600 mb-6">Review resumes, cover letters, and contact information for candidates who
            applied to your roles.</p>
          <router-link to="/admin/applications"
            class="inline-block bg-blue-600 text-white px-6 py-3 rounded-lg font-bold hover:bg-blue-800 transition-colors">
            View All Applications →
          </router-link>
        </div>

        <div class="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-shadow">
          <h2 class="text-xl font-bold text-gray-800 mb-3">Post a New Position</h2>
          <p class="text-gray-600 mb-6">Reach thousands of qualified candidates by posting your open requirements on the
            board.</p>
          <button @click="alert('Post Job form coming soon!')"
            class="inline-block bg-green-600 text-white px-6 py-3 rounded-lg font-bold hover:bg-green-700 transition-colors">
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
import api from '@/api/axios'; // Ensure your axios instance has the base URL set

const loading = ref(true);
const reportGenerating = ref(false);

// This object holds the data fetched by your CompletableFuture threads
const stats = reactive({
  totalUsers: 0,
  totalJobs: 0,
  totalApplications: 0
});

const fetchDashboardData = async () => {
  try {
    loading.value = true;
    
    // We call the specific multithreaded endpoint we created in Spring Boot
    // This is much faster as it runs count queries in parallel
    const response = await api.get('/admin/dashboard/stats');
    
    // Map the record/DTO fields to your local reactive stats object
    stats.totalUsers = response.data.totalUsers;
    stats.jobCount = response.data.totalJobs;
    stats.appCount = response.data.totalApplications;

    console.log("Dashboard stats loaded successfully:", response.data);
  } catch (error) {
    // If you see a 403 here, ensure the 'ROLE_' prefix fix is in your Java Filter
    console.error("Error loading dashboard stats:", error);
    
    // Optional: Set fallback values if the request fails
    stats.jobCount = 0;
    stats.appCount = 0;
  } finally {
    loading.value = false;
  }
};
// 2. Trigger Background PDF Generation
const generateReport = async (adminId) => {
  try {
    reportGenerating.value = true;
    
    // This triggers the @Async background thread in Spring Boot
    const response = await api.post(`/admin/dashboard/report/${adminId}`);
    
    // The server returns 202 Accepted immediately while the thread works
    alert(response.data); 
    
  } catch (error) {
    console.error("Report trigger failed:", error);
  } finally {
    reportGenerating.value = false;
  }
};

onMounted(() => {
  fetchDashboardData();
});
</script>

<style scoped>
/* Tailwind fallback */
@import url('https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css');
</style>