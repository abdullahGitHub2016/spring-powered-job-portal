import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import JobDetailsView from '../views/JobDetailsView.vue'
// ADD THESE TWO LINES:
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import ResumeBuilder from '../views/ResumeBuilder.vue'

/**
 * Route Definitions
 * /         -> Shows the BDJobs style home page with the job list
 * /job/:id  -> Shows the details for a specific job based on the ID from the backend
 */
const routes = [
  { path: '/login', component: LoginView },
  { path: '/register', component: RegisterView },
  {
    path: '/employer/dashboard',
    component: () => import('@/views/EmployerDashboard.vue'),
    meta: { requiresAuth: true, requiresEmployer: true }
  },
  {
    path: '/candidate/dashboard',
    component: () => import('@/views/CandidateDashboard.vue'),
    meta: { requiresAuth: true } // Standard users
  },
  // Default /dashboard redirector
  {
    path: '/dashboard',
    beforeEnter: (to, from, next) => {
      const role = localStorage.getItem('role');
      if (role === 'EMPLOYER') next('/employer/dashboard');
      else next('/candidate/dashboard');
    }
  },
  // Add this to your routes array in src/router/index.js
  // src/router/index.js
  {
    path: '/admin/applications',
    name: 'admin-applications',
    component: () => import('../views/AdminApplicationsView.vue'),
    // FIX: Add these meta tags so the guard knows to check for Auth and Employer role
    meta: { requiresAuth: true, requiresEmployer: true }
  },
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/job/:id',
    name: 'job-details',
    component: JobDetailsView,
    // Allows the :id to be passed as a prop to the component if needed
    props: true
  },
  // Catch-all 404 route (optional but recommended)
  {
    path: '/:pathMatch(.*)*',
    redirect: '/'
  },
  {
    path: '/resume-builder',
    name: 'ResumeBuilder',
    component: ResumeBuilder,
    meta: { requiresAuth: true } // Ensure only logged-in users see this
  }
]



const router = createRouter({
  // Uses the HTML5 History API for clean URLs (no # in the link)
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  // Automatically scroll to top when navigating to a new page
  scrollBehavior() {
    return { top: 0 }
  }
})

// Inside your router.beforeEach in index.js
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role'); // Matches key in LoginView.vue

  // 1. Prevent logged-in users from seeing Login/Register
  if (token && (to.path === '/login' || to.path === '/register')) {
    return next('/dashboard');
  }

  // 2. Protect Admin routes - Employer role required
  if (to.meta.requiresEmployer && role !== 'EMPLOYER') {
    return next('/dashboard');
  }
  
  // 3. General Auth protection
  if (to.meta.requiresAuth && !token) {
    return next('/login');
  }

  next();
});

export default router