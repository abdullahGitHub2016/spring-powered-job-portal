<template>
  <div class="min-h-screen flex items-center justify-center bg-[#F5F5F5] py-12">
    <div class="bg-white p-8 rounded shadow-md w-full max-w-md border border-[#d5d5d5]">
      <h2 class="text-2xl font-bold text-[#338033] mb-6 text-center">Create Account</h2>
      
      <form @submit.prevent="handleRegister" class="space-y-4">
        <div>
          <label class="block text-sm font-bold text-[#333333] mb-1">Email</label>
          <input v-model="form.email" type="email" required class="w-full p-2 border border-gray-300 rounded outline-none">
        </div>
        
        <div>
          <label class="block text-sm font-bold text-[#333333] mb-1">Password</label>
          <input v-model="form.password" type="password" required class="w-full p-2 border border-gray-300 rounded outline-none">
        </div>

        <div>
          <label class="block text-sm font-bold text-[#333333] mb-1">I am a:</label>
          <select v-model="form.role" class="w-full p-2 border border-gray-300 rounded bg-white outline-none">
            <option value="JOB_SEEKER">Job Seeker</option>
            <option value="EMPLOYER">Employer (Post Jobs)</option>
          </select>
        </div>

        <button type="submit" class="w-full bg-[#338033] hover:bg-[#2d6e2d] text-white font-bold py-2 rounded uppercase text-sm">
          Create Account
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();
const form = reactive({
  email: '',
  password: '',
  role: 'JOB_SEEKER'
});

const handleRegister = async () => {
  try {
    await axios.post('http://localhost:8080/api/auth/register', form);
    alert('Registration Successful! You can now login.');
    router.push('/login');
  } catch (error) {
    alert(error.response?.data || 'Registration failed.');
  }
};
</script>