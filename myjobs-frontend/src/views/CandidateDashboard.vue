<template>
  <div class="min-h-screen bg-gray-50 p-6">
    <div class="max-w-6xl mx-auto">
      <header class="mb-8">
        <h1 class="text-3xl font-bold text-gray-800">My Applications</h1>
        <p class="text-gray-600">Track the status of your recent job applications.</p>
      </header>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
        <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <p class="text-sm text-gray-500 font-medium">Jobs Applied</p>
          <h3 class="text-2xl font-bold text-[#004d99]">{{ applicationCount }}</h3>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        <div v-if="loading" class="p-10 text-center italic text-gray-500">Loading applications...</div>
        
        <div v-else-if="applicationCount === 0" class="p-10 text-center">
          <div class="text-5xl mb-4">📂</div>
          <h3 class="text-lg font-bold text-gray-800">No applications yet</h3>
          <p class="text-gray-500 mb-6">You haven't applied to any jobs. Start your search today!</p>
          <router-link to="/" class="bg-[#004d99] text-white px-6 py-2 rounded-lg font-bold">
            Browse Jobs
          </router-link>
        </div>

        <table v-else class="w-full text-left border-collapse">
          <thead class="bg-gray-50 text-gray-600 text-xs font-bold uppercase">
            <tr>
              <th class="px-6 py-4">Job Title</th>
              <th class="px-6 py-4">Date Applied</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="app in userApplications" :key="app.id" class="border-t">
              <td class="px-6 py-4 font-medium">{{ app.jobTitle }}</td>
              <td class="px-6 py-4 text-sm text-gray-500">{{ new Date(app.appliedAt).toLocaleDateString() }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';

const userApplications = ref([]);
const loading = ref(true);
const applicationCount = computed(() => userApplications.value.length);

const fetchMyApplications = async () => {
  try {
    const token = localStorage.getItem('token');
    // For a real app, the backend should provide an endpoint like /api/my-applications
    // For now, we fetch all and filter by the current user's email if possible
    const res = await fetch('http://localhost:8080/api/applications', {
      headers: { 'Authorization': `Bearer ${token}` }
    });

    if (res.ok) {
      const allApps = await res.json();
      // Logic: Filter by the email address you used to log in
      // Note: You may need to store 'userEmail' in localStorage during Login
      const currentUserEmail = localStorage.getItem('userEmail'); 
      
      if (currentUserEmail) {
        userApplications.value = allApps.filter(a => a.applicantEmail === currentUserEmail);
      } else {
        // Fallback: if no email is stored, show all for demonstration
        userApplications.value = allApps;
      }
    }
  } catch (err) {
    console.error("Error:", err);
  } finally {
    loading.value = false;
  }
};

onMounted(fetchMyApplications);
</script>