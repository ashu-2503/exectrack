import { TaskStatus } from "../../../shared/types/enums";

export interface DayStatus {
  day: number;
  status: TaskStatus;
}

export interface DashboardResponse {
  taskId: number;
  taskName: string;
  dailyStatus: DayStatus[];
  completedDays: number;
  totalDays: number;
  completionPercentage: number;
  longestStreak: number;
}