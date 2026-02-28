import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import JobDetailsView from '../views/JobDetailsView.vue'

/**
 * Route Definitions
 * /         -> Shows the BDJobs style home page with the job list
 * /job/:id  -> Shows the details for a specific job based on the ID from the backend
 */
const routes = [
  // Add this to your routes array in src/router/index.js
  {
    path: '/admin/applications',
    name: 'admin-applications',
    component: () => import('../views/AdminApplicationsView.vue')
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

export default router