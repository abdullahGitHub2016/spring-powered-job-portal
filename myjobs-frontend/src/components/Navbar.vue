<template>
  <nav class="navbar">
    <div class="nav-container">
      <div class="nav-brand">
        <router-link to="/" class="logo">MyJobs<span>BD</span></router-link>
      </div>

      <div class="nav-menu">
        <router-link to="/" class="nav-link">Home</router-link>
        
        <template v-if="authState.isLoggedIn">
          <router-link to="/dashboard" class="nav-link">Dashboard</router-link>
          <div class="auth-section">
            <span class="user-role">{{ authState.role?.replace('_', ' ') }}</span>
            <button @click="handleLogout" class="btn-logout">Sign Out</button>
          </div>
        </template>

        <template v-else>
          <router-link to="/login" class="nav-link">Login</router-link>
          <router-link to="/login" class="btn-post">Post a Job</router-link>
        </template>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { reactive, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const authState = reactive({
  isLoggedIn: !!localStorage.getItem('token'),
  role: localStorage.getItem('role')
});

const updateAuth = () => {
  authState.isLoggedIn = !!localStorage.getItem('token');
  authState.role = localStorage.getItem('role');
};

const handleLogout = () => {
  localStorage.clear();
  updateAuth();
  router.push('/login');
};

onMounted(() => {
  window.addEventListener('auth-changed', updateAuth);
});

onUnmounted(() => {
  window.removeEventListener('auth-changed', updateAuth);
});
</script>

<style scoped>
.navbar {
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0,0,0,0.08);
  padding: 0.75rem 0;
  position: sticky;
  top: 0;
  z-index: 1000;
}
.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 2rem;
}
.logo {
  font-size: 1.5rem;
  font-weight: 800;
  text-decoration: none;
  color: #333;
}
.logo span { color: #2672e7; }
.nav-menu { display: flex; align-items: center; gap: 1.5rem; }
.nav-link {
  text-decoration: none;
  color: #555;
  font-weight: 600;
  font-size: 0.9rem;
}
.nav-link:hover { color: #2672e7; }
.auth-section {
  display: flex;
  align-items: center;
  gap: 1rem;
  border-left: 1px solid #eee;
  padding-left: 1.5rem;
}
.user-role {
  font-size: 0.75rem;
  background: #eef4ff;
  color: #2672e7;
  padding: 4px 10px;
  border-radius: 4px;
  font-weight: bold;
}
.btn-logout {
  background: transparent;
  border: 1px solid #ff4d4f;
  color: #ff4d4f;
  padding: 6px 14px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
}
.btn-post {
  background: #2672e7;
  color: white !important;
  padding: 8px 18px;
  border-radius: 4px;
  text-decoration: none;
  font-weight: bold;
}
</style>