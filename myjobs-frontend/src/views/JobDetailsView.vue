<template>
  <div class="min-h-screen bg-gray-50 pb-20">
    <nav class="bg-white border-b py-4 px-6 mb-8 shadow-sm">
      <div class="max-w-5xl mx-auto">
        <router-link to="/" class="text-[#004d99] font-bold flex items-center hover:underline">
          ← Back to Search
        </router-link>
      </div>
    </nav>

    <div v-if="job" class="max-w-5xl mx-auto px-4">
      <!-- Header Section -->
      <div class="bg-white border rounded-lg p-8 md:p-12 mb-8 shadow-sm flex flex-col md:flex-row justify-between items-start gap-6">
        <div>
          <h1 class="text-3xl font-bold text-[#004d99] mb-2">{{ job.title }}</h1>
          <p class="text-xl text-gray-700 font-medium mb-4">{{ job.companyName }}</p>
          <div class="flex gap-6 text-gray-500 text-sm">
            <span>📍 {{ job.location }}</span>
            <span>💰 ৳ {{ job.salary?.toLocaleString() }}</span>
          </div>
        </div>
        <button @click="showModal = true" class="bg-green-600 hover:bg-green-700 text-white font-bold py-4 px-12 rounded-lg shadow-lg uppercase transition">
          Apply Now
        </button>
      </div>

      <!-- Description Section -->
      <div class="bg-white border rounded-lg p-8 md:p-12 shadow-sm">
        <h3 class="text-xl font-bold text-gray-800 border-b pb-4 mb-6 text-sm uppercase tracking-wide">Job Description</h3>
        <p class="text-gray-700 leading-relaxed whitespace-pre-line text-lg">{{ job.description }}</p>
      </div>

      <!-- --- APPLY MODAL CODE START --- -->
      <div v-if="showModal" class="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-xl max-w-lg w-full p-8 shadow-2xl relative">
          <!-- Close Button -->
          <button @click="showModal = false" class="absolute top-4 right-6 text-3xl text-gray-400 hover:text-gray-600 transition">&times;</button>
          
          <h2 class="text-2xl font-bold text-[#004d99] mb-1">Send Application</h2>
          <p class="text-gray-500 mb-8 border-b pb-4 text-sm">Applying for: {{ job.title }}</p>

          <form @submit.prevent="submitApp" class="space-y-4">
            <div>
              <label class="block text-xs font-bold text-gray-500 uppercase mb-1">Full Name</label>
              <input v-model="form.applicantName" placeholder="Enter your full name" required class="w-full p-4 border rounded-lg outline-[#004d99] bg-gray-50 focus:bg-white transition" />
            </div>
            
            <div>
              <label class="block text-xs font-bold text-gray-500 uppercase mb-1">Email Address</label>
              <input v-model="form.applicantEmail" type="email" placeholder="example@mail.com" required class="w-full p-4 border rounded-lg outline-[#004d99] bg-gray-50 focus:bg-white transition" />
            </div>

            <div>
              <label class="block text-xs font-bold text-gray-500 uppercase mb-1">Phone Number</label>
              <input v-model="form.applicantPhone" placeholder="01XXXXXXXXX" required class="w-full p-4 border rounded-lg outline-[#004d99] bg-gray-50 focus:bg-white transition" />
            </div>

            <div>
              <label class="block text-xs font-bold text-gray-500 uppercase mb-1">Short Cover Letter</label>
              <textarea v-model="form.coverLetter" placeholder="Briefly explain why you are a good fit..." rows="4" class="w-full p-4 border rounded-lg outline-[#004d99] bg-gray-50 focus:bg-white transition"></textarea>
            </div>
            
            <button :disabled="sending" type="submit" class="w-full bg-[#004d99] text-white font-bold py-4 rounded-lg hover:bg-blue-800 transition disabled:bg-gray-400 mt-4 shadow-md">
              {{ sending ? 'Submitting Application...' : 'Confirm & Submit' }}
            </button>
          </form>
        </div>
      </div>
      <!-- --- APPLY MODAL CODE END --- -->

      <!-- Success Toast -->
      <transition name="fade">
        <div v-if="success" class="fixed bottom-8 left-1/2 -translate-x-1/2 bg-green-600 text-white px-8 py-4 rounded-full shadow-2xl z-[60] flex items-center gap-2">
          <span>✅ Application sent successfully!</span>
        </div>
      </transition>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const job = ref(null);
const showModal = ref(false);
const sending = ref(false);
const success = ref(false);

const form = ref({ 
  applicantName: '', 
  applicantEmail: '', 
  applicantPhone: '', 
  coverLetter: '' 
});

const fetchJob = async () => {
  try {
    const res = await fetch(`http://localhost:8080/api/jobs/${route.params.id}`);
    if (res.ok) {
      job.value = await res.json();
    }
  } catch (err) {
    console.error("Error fetching job:", err);
  }
};

const submitApp = async () => {
  sending.value = true;
  try {
    const payload = { 
      ...form.value, 
      jobId: job.value.id, 
      jobTitle: job.value.title 
    };
    
    const res = await fetch('http://localhost:8080/api/applications', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (res.ok) {
      success.value = true;
      showModal.value = false;
      // Hide success message after 4 seconds
      setTimeout(() => success.value = false, 4000);
      // Reset the form fields
      form.value = { applicantName: '', applicantEmail: '', applicantPhone: '', coverLetter: '' };
    }
  } catch (err) { 
    alert("Submission failed. Please check if your backend is running."); 
  } finally {
    sending.value = false;
  }
};

onMounted(fetchJob);
</script>

<style scoped>
@import url('https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css');

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>