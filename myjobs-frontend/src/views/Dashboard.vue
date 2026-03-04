<template>
  <div class="dashboard-container">
    <div v-if="userRole === 'JOB_SEEKER'">
      <h1>Candidate Dashboard</h1>
      <AppliedJobsList /> <RecommendedJobs /> </div>

    <div v-else-if="userRole === 'EMPLOYER'">
      <h1>Employer Panel</h1>
      <ActivePostings /> <ApplicantStats />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const userRole = ref('');

onMounted(() => {
  // Retrieve the role saved during handleLogin
  userRole.value = localStorage.getItem('role') || 'JOB_SEEKER';
});
</script>