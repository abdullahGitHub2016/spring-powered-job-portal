<template>
  <div class="min-h-screen bg-gray-50 p-6">
    <div class="max-w-6xl mx-auto">
      <div class="flex justify-between items-center mb-8">
        <h1 class="text-2xl font-bold text-gray-800">Received Applications</h1>
        <router-link to="/" class="text-blue-600 hover:underline">Back to Job Board</router-link>
      </div>

      <div v-if="loading" class="text-center py-10 italic text-gray-500">Loading applications...</div>

      <div v-else-if="applications.length === 0" class="bg-white p-10 rounded-lg text-center border shadow-sm">
        <p class="text-gray-400">No applications received yet.</p>
      </div>

      <div v-else class="bg-white rounded-lg shadow-sm border overflow-hidden">
        <table class="w-full text-left border-collapse">
          <thead class="bg-gray-50 text-gray-600 uppercase text-xs font-bold">
            <tr>
              <th class="px-6 py-4 border-b">Applicant Name</th>
              <th class="px-6 py-4 border-b">Applied For</th>
              <th class="px-6 py-4 border-b">Contact Info</th>
              <th class="px-6 py-4 border-b">Cover Letter</th>
              <th class="px-6 py-4 border-b">Date</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr v-for="app in applications" :key="app.id" class="hover:bg-blue-50/30 transition">
              <td class="px-6 py-4 font-semibold text-gray-800">{{ app.applicantName }}</td>
              <td class="px-6 py-4">
                <span class="text-[#004d99] font-medium">{{ app.jobTitle }}</span>
                <p class="text-[10px] text-gray-400">ID: {{ app.jobId }}</p>
              </td>
              <td class="px-6 py-4 text-sm">
                <div class="flex flex-col">
                  <span>📧 {{ app.applicantEmail }}</span>
                  <span>📞 {{ app.applicantPhone }}</span>
                </div>
              </td>
              <td class="px-6 py-4">
                <p class="text-xs text-gray-600 line-clamp-2 max-w-xs">{{ app.coverLetter }}</p>
              </td>
              <td class="px-6 py-4 text-xs text-gray-400 italic">
                {{ new Date(app.appliedAt).toLocaleDateString() }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const applications = ref([]);
const loading = ref(true);

const fetchApplications = async () => {
  try {
    const res = await fetch('http://localhost:8080/api/applications');
    if (res.ok) {
      applications.value = await res.json();
    }
  } catch (err) {
    console.error("Error fetching applications:", err);
  } finally {
    loading.value = false;
  }
};

onMounted(fetchApplications);
</script>