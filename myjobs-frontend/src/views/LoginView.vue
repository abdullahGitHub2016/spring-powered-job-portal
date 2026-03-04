<template>
    <div class="min-h-screen flex items-center justify-center bg-[#F5F5F5] font-sans">
        <div class="bg-white p-8 rounded shadow-md w-full max-w-md border border-[#d5d5d5]">
            <h2 class="text-2xl font-bold text-[#338033] mb-6 text-center">Login to JobPortal</h2>

            <form @submit.prevent="handleLogin" class="space-y-4">
                <div>
                    <label class="block text-sm font-bold text-[#333333] mb-1">Email Address</label>
                    <input v-model="email" type="email" required
                        class="w-full p-2 border border-gray-300 rounded focus:border-[#2F64A3] outline-none"
                        placeholder="example@mail.com">
                </div>

                <div>
                    <label class="block text-sm font-bold text-[#333333] mb-1">Password</label>
                    <input v-model="password" type="password" required
                        class="w-full p-2 border border-gray-300 rounded focus:border-[#2F64A3] outline-none"
                        placeholder="******">
                </div>

                <button type="submit"
                    class="w-full bg-black hover:bg-[#2F64A3] text-white font-bold py-2 rounded transition-colors uppercase text-sm">
                    Login
                </button>
            </form>

            <p class="mt-4 text-center text-sm text-[#656565]">
                Don't have an account? <router-link to="/register" class="text-[#2F64A3] font-bold">Register
                    here</router-link>
            </p>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import api from '@/api/axios'; // Ensure this points to your axios.js file
import { useRouter } from 'vue-router';

const email = ref('');
const password = ref('');
const router = useRouter();

const handleLogin = async () => {
  try {
    const response = await api.post('/auth/login', { 
      email: email.value, 
      password: password.value 
    });

    if (response.status === 200) {
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('role', response.data.role);

      // TRIGGER: Notify the rest of the app that auth state changed
      window.dispatchEvent(new Event('auth-changed'));

      router.push('/dashboard');
    }
  } catch (error) {
    console.error("Login Error:", error);
  }
};
</script>