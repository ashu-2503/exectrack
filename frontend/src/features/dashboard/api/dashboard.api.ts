import { apiClient } from "../../../services/apiClient";
import type { DashboardResponse } from "../types/dashboard.types";

export const fetchMonthlyDashboard = async (): Promise<
  DashboardResponse[]
> => {
  return apiClient(
    "/dashboard/month?year=2026&month=4"
  );
};