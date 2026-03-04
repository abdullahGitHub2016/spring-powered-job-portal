<template>
  <nav class="navbar shadow-sm">
    <div class="container mx-auto flex justify-between items-center px-4 py-3">
      <router-link to="/" class="text-2xl font-bold text-[#004d99]">
        MyJobs<span>BD</span>
      </router-link>

      <div class="flex items-center gap-6">
        <router-link to="/" class="font-medium hover:text-[#004d99]">Home</router-link>

        <template v-if="auth.isLoggedIn">
          <router-link :to="dashboardPath" class="font-medium hover:text-[#004d99]">
            Dashboard
          </router-link>

          <router-link v-if="auth.role === 'EMPLOYER'" to="/admin/applications" class="font-medium text-[#004d99]">
            Admin
          </router-link>

          <div class="flex items-center gap-3 border-l pl-4">
            <span class="text-xs bg-gray-100 px-2 py-1 rounded uppercase font-bold text-gray-600">
              {{ auth.role?.replace('_', ' ') }}
            </span>
            <button @click="logout" class="text-red-500 font-bold text-sm">Sign Out</button>
          </div>
        </template>

        <template v-else>
          <router-link to="/login" class="font-medium">Login</router-link>
          <router-link to="/register" class="bg-[#f05a28] text-white px-4 py-2 rounded font-bold">
            Join Now
          </router-link>
        </template>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { reactive, computed, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// Sync with LocalStorage
const auth = reactive({
  isLoggedIn: !!localStorage.getItem('token'),
  role: localStorage.getItem('role')
});

// Computed property to determine destination
const dashboardPath = computed(() => {
  if (auth.role === 'EMPLOYER') {
    return '/employer/dashboard';
  }
  return '/candidate/dashboard';
});

const syncAuth = () => {
  auth.isLoggedIn = !!localStorage.getItem('token');
  auth.role = localStorage.getItem('role');
};

const logout = () => {
  localStorage.clear();
  syncAuth();
  router.push('/login');
};

onMounted(() => {
  window.addEventListener('auth-changed', syncAuth); // Listener for LoginView trigger
});

onUnmounted(() => {
  window.removeEventListener('auth-changed', syncAuth);
});
</script>