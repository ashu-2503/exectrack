import type { DashboardResponse } from "../types/dashboard.types";
import DayGrid from "./DayGrid";

interface Props {
  task: DashboardResponse;
}

function TaskRow({ task }: Props) {
  return (
    <div className="flex items-center bg-gray-800 p-3 rounded-lg">

      {/* LEFT - Task Name */}
      <div className="w-40 font-semibold">
        {task.taskName}
      </div>

      {/* CENTER - Grid */}
      <div className="flex-1 overflow-x-auto">
        <DayGrid days={task.dailyStatus} />
      </div>

      {/* RIGHT - Stats */}
      <div className="w-40 text-right text-sm">
        <div>{task.completionPercentage.toFixed(0)}%</div>
        <div>{task.completedDays}/{task.totalDays}</div>
        <div>🔥 {task.longestStreak}</div>
      </div>
    </div>
  );
}

export default TaskRow;