<template>
  <div class="min-h-screen bg-gray-50 pb-20">
    <!-- Navigation -->
    <nav class="bg-white border-b py-4 px-6 mb-8 shadow-sm sticky top-0 z-40">
      <div class="max-w-6xl mx-auto flex justify-between items-center">
        <router-link to="/" class="text-[#004d99] font-bold flex items-center hover:underline">
          <span class="mr-2">←</span> Back to Job Search
        </router-link>
        <div class="hidden md:block text-sm text-gray-500 font-medium">
          Job ID: {{ job?.id }}
        </div>
      </div>
    </nav>

    <div v-if="loading" class="flex flex-col items-center justify-center py-20">
      <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-[#004d99] mb-4"></div>
      <p class="text-gray-500 italic">Loading job details...</p>
    </div>

    <div v-else-if="job" class="max-w-6xl mx-auto px-4">
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        <!-- LEFT COLUMN: MAIN CONTENT -->
        <div class="lg:col-span-2 space-y-6">
          
          <!-- Header Card -->
          <div class="bg-white border rounded-lg p-6 md:p-8 shadow-sm">
            <h1 class="text-2xl md:text-4xl font-bold text-[#004d99] mb-2 leading-tight">{{ job.title }}</h1>
            <p class="text-xl text-gray-700 font-semibold mb-6 italic">{{ job.companyName }}</p>
            
            <div class="flex flex-wrap gap-4 text-sm">
              <span class="bg-blue-50 text-blue-700 px-3 py-1 rounded-full font-medium">📍 {{ job.location }}</span>
              <span class="bg-green-50 text-green-700 px-3 py-1 rounded-full font-medium">💰 ৳ {{ job.salary?.toLocaleString() }} / month</span>
              <span class="bg-gray-100 text-gray-600 px-3 py-1 rounded-full font-medium">🕒 Full Time</span>
            </div>
          </div>

          <!-- Detailed Info Sections -->
          <div class="bg-white border rounded-lg p-6 md:p-8 shadow-sm space-y-8">
            
            <!-- Job Context/Description -->
            <section>
              <h3 class="text-lg font-bold text-gray-800 border-b pb-2 mb-4 flex items-center">
                <span class="w-1.5 h-6 bg-[#004d99] mr-3 rounded"></span>
                Job Context / Description
              </h3>
              <p class="text-gray-700 leading-relaxed whitespace-pre-line">
                {{ job.description }}
              </p>
            </section>

            <!-- Static Requirements (Since these aren't in DB yet, we show placeholders) -->
            <section>
              <h3 class="text-lg font-bold text-gray-800 border-b pb-2 mb-4 flex items-center">
                <span class="w-1.5 h-6 bg-[#004d99] mr-3 rounded"></span>
                Requirements & Skills
              </h3>
              <ul class="list-disc ml-5 space-y-2 text-gray-700">
                <li>Minimum 2-3 years of relevant professional experience.</li>
                <li>Strong understanding of modern development workflows.</li>
                <li>Ability to work in a fast-paced team environment.</li>
                <li>Excellent problem-solving and communication skills.</li>
              </ul>
            </section>

            <!-- Education -->
            <section>
              <h3 class="text-lg font-bold text-gray-800 border-b pb-2 mb-4 flex items-center">
                <span class="w-1.5 h-6 bg-[#004d99] mr-3 rounded"></span>
                Educational Requirements
              </h3>
              <p class="text-gray-700">Bachelor degree in any discipline (CSE/EEE preferred for technical roles).</p>
            </section>
          </div>
        </div>

        <!-- RIGHT COLUMN: SIDEBAR SUMMARY -->
        <div class="lg:col-span-1">
          <div class="bg-white border rounded-lg shadow-sm sticky top-24 overflow-hidden">
            <div class="bg-gray-50 px-6 py-4 border-b">
              <h3 class="font-bold text-gray-800 uppercase tracking-wider text-sm">Job Summary</h3>
            </div>
            <div class="p-6 space-y-5">
              <div>
                <p class="text-xs text-gray-400 uppercase font-bold mb-1">Published On</p>
                <p class="text-sm font-semibold text-gray-700">Feb 28, 2026</p>
              </div>
              <div>
                <p class="text-xs text-gray-400 uppercase font-bold mb-1">Employment Status</p>
                <p class="text-sm font-semibold text-gray-700">Full-time</p>
              </div>
              <div>
                <p class="text-xs text-gray-400 uppercase font-bold mb-1">Experience</p>
                <p class="text-sm font-semibold text-gray-700">2 to 5 year(s)</p>
              </div>
              <div>
                <p class="text-xs text-gray-400 uppercase font-bold mb-1">Job Location</p>
                <p class="text-sm font-semibold text-gray-700">{{ job.location }}</p>
              </div>
              <div>
                <p class="text-xs text-gray-400 uppercase font-bold mb-1">Salary</p>
                <p class="text-sm font-semibold text-gray-700">৳ {{ job.salary?.toLocaleString() }} (Monthly)</p>
              </div>
              <div>
                <p class="text-xs text-gray-400 uppercase font-bold mb-1">Application Deadline</p>
                <p class="text-sm font-bold text-red-600">March 15, 2026</p>
              </div>

              <button @click="showModal = true" class="w-full bg-green-600 hover:bg-green-700 text-white font-bold py-4 rounded-lg shadow-md uppercase transition-all mt-4 flex items-center justify-center gap-2">
                Apply Now <span class="text-xl">🚀</span>
              </button>
            </div>
          </div>
        </div>

      </div>

      <!-- APPLY MODAL -->
      <div v-if="showModal" class="fixed inset-0 bg-black/70 backdrop-blur-sm flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-xl max-w-lg w-full p-8 shadow-2xl relative animate-in fade-in zoom-in duration-200">
          <button @click="showModal = false" class="absolute top-4 right-6 text-3xl text-gray-400 hover:text-gray-600">&times;</button>
          
          <h2 class="text-2xl font-bold text-[#004d99] mb-1">Online Application</h2>
          <p class="text-gray-500 mb-8 border-b pb-4 text-sm">Position: {{ job.title }}</p>

          <form @submit.prevent="submitApp" class="space-y-4">
            <div class="grid grid-cols-1 gap-4">
              <input v-model="form.applicantName" placeholder="Your Full Name" required class="w-full p-4 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition" />
              <input v-model="form.applicantEmail" type="email" placeholder="Email Address" required class="w-full p-4 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition" />
              <input v-model="form.applicantPhone" placeholder="Mobile Number (e.g. 017...)" required class="w-full p-4 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition" />
              <textarea v-model="form.coverLetter" placeholder="Expected Salary & Why we should hire you?" rows="4" class="w-full p-4 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition"></textarea>
            </div>
            
            <button :disabled="sending" type="submit" class="w-full bg-[#004d99] text-white font-bold py-4 rounded-lg hover:bg-blue-800 transition shadow-lg disabled:bg-gray-400">
              {{ sending ? 'Sending your details...' : 'Submit Application' }}
            </button>
          </form>
        </div>
      </div>

      <!-- Success Notification -->
      <div v-if="success" class="fixed bottom-10 left-1/2 -translate-x-1/2 bg-gray-900 text-white px-8 py-4 rounded-full shadow-2xl z-[60] flex items-center gap-3 animate-bounce">
        <span class="bg-green-500 rounded-full p-1 text-xs">✓</span>
        <span>Application sent successfully!</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const job = ref(null);
const loading = ref(true);
const showModal = ref(false);
const sending = ref(false);
const success = ref(false);

const form = ref({ applicantName: '', applicantEmail: '', applicantPhone: '', coverLetter: '' });

const fetchJob = async () => {
  try {
    const res = await fetch(`http://localhost:8080/api/jobs/${route.params.id}`);
    if (res.ok) {
      job.value = await res.json();
    }
  } catch (err) {
    console.error("Fetch Error:", err);
  } finally {
    loading.value = false;
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
      setTimeout(() => success.value = false, 4000);
      form.value = { applicantName: '', applicantEmail: '', applicantPhone: '', coverLetter: '' };
    }
  } catch (err) { 
    alert("Check if backend is running!"); 
  } finally {
    sending.value = false;
  }
};

onMounted(fetchJob);
</script>

<style scoped>
@import url('https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css');
</style>