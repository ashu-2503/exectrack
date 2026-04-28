import { createBrowserRouter } from "react-router-dom";
import AppLayout from "../shared/components/layout/AppLayout";

import DashboardPage from "../features/dashboard/today/pages/DashboardPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <AppLayout>
        <DashboardPage />
      </AppLayout>
    ),
  },
  {
    path: "/calendar",
    element: <div>Calendar Page</div>,
  },
  {
    path: "/analytics",
    element: <div>Analytics Page</div>,
  },
  {
    path: "/task",
    element: <div>Create Task Page</div>,
  },
  {
    path: "/profile",
    element: <div>Profile Page</div>,
  },
]);

export default router;