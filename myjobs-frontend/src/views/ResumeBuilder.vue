<template>
  <div class="max-w-4xl mx-auto p-6 bg-white shadow-lg rounded-lg">
    <h2 class="text-2xl font-bold mb-6">Build Your Online Resume</h2>

    <form @submit.prevent="saveResume" class="space-y-4">
      <div class="grid grid-cols-2 gap-4">
        <div>
          <label>Full Name</label>
          <input v-model="form.fullName" type="text" class="w-full border p-2 rounded" />
        </div>
        <div>
          <label>Professional Title</label>
          <input v-model="form.professionalTitle" type="text" class="w-full border p-2 rounded"
            placeholder="e.g. Java Developer" />
        </div>
      </div>

      <div>
        <label>Summary</label>
        <textarea v-model="form.summary" class="w-full border p-2 rounded" rows="4"></textarea>
      </div>

      <div>
        <label>Skills (Comma separated)</label>
        <input v-model="skillsInput" @blur="processSkills" type="text" class="w-full border p-2 rounded"
          placeholder="Java, Spring Boot, Vue.js" />
      </div>

      <div class="flex gap-4 mt-6">
        <button @click="saveResume" class="bg-blue-600 text-white px-6 py-2 rounded shadow hover:bg-blue-700">
          Save Resume
        </button>

        <button @click="downloadPdf" type="button"
          class="bg-gray-200 text-gray-800 px-6 py-2 rounded shadow hover:bg-gray-300">
          Download PDF
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import axios from 'axios';

const skillsInput = ref("");
// Assuming your form looks something like this:
const form = ref({
  fullName: '',
  professionalTitle: '',
  phone: '',
  summary: '',
  skills: [],
  experiences: []
});

const processSkills = () => {
  form.skills = skillsInput.value.split(',').map(s => s.trim());
};

const saveResume = async () => {
  try {
    // 2. Use form.value to access the data
    console.log("Sending data:", form.value);

    if (!form.value) {
      console.error("Form data is missing!");
      return;
    }

    const token = localStorage.getItem('token');

    const response = await axios.post('http://localhost:8080/api/resumes', form.value, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    console.log("Saved Resume ID:", response.data.id);
    console.log("Saved Skills Count:", response.data.skills.length);
    alert('Resume saved successfully!');

  } catch (error) {
    // This will now catch actual server errors, not ReferenceErrors
    console.error("Error saving resume:", error.response?.data || error.message);
  }
};

const isLoading = ref(false);

const downloadPdf = async () => {
  isLoading.value = true; // Start loading
  try {
    const token = localStorage.getItem('token');
    
    // We use axios with 'blob' responseType to handle binary PDF data
    const response = await axios.get('http://localhost:8080/api/resumes/download', {
      headers: { 
        'Authorization': `Bearer ${token}` 
      },
      responseType: 'blob', 
    });

    // 1. Create a URL for the PDF blob
    const url = window.URL.createObjectURL(new Blob([response.data]));
    
    // 2. Create a temporary 'a' tag to trigger the download
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'My_Resume.pdf'); // Filename for the user
    
    // 3. Append to body, click it, and remove it
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    
    // 4. Clean up the URL object
    window.URL.revokeObjectURL(url);

  } catch (error) {
    console.error("Download failed:", error);
    alert("Could not download PDF. Make sure your backend is running and you have saved your resume!");
  }
  finally {
    isLoading.value = false; // Stop loading regardless of success/fail
  }
};
</script>