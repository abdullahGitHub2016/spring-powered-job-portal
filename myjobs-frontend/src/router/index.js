import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import JobDetailsView from '../views/JobDetailsView.vue'
// ADD THESE TWO LINES:
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';

/**
 * Route Definitions
 * /         -> Shows the BDJobs style home page with the job list
 * /job/:id  -> Shows the details for a specific job based on the ID from the backend
 */
const routes = [
  { path: '/login', component: LoginView },
  { path: '/register', component: RegisterView },
  {
    path: '/dashboard',
    component: () => import('@/views/Dashboard.vue'),
    meta: { requiresAuth: true } // Mark routes that need login
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
  // FIX: Change 'userRole' to 'role'
  const role = localStorage.getItem('role'); 

  // 1. Check if the route specifically requires an Employer
  if (to.meta.requiresEmployer) {
    if (role === 'EMPLOYER') {
      next(); // User is an employer, let them in
    } else {
      next('/dashboard'); // User is logged in but not an employer, kick to dashboard
    }
    return;
  }
  
  // 2. Standard Auth Check
  if (to.meta.requiresAuth && !token) {
    return next('/login');
  }

  next();
});

export default router