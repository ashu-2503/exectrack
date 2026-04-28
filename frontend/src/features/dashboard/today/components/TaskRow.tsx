import type { DashboardResponse } from "../../types/dashboard.types";

interface Props {
  task: DashboardResponse;
}

function TaskRow({ task }: Props) {
  const getStatus = () => {
    if (task.completionPercentage === 100) return "DONE";
    if (task.completionPercentage > 0) return "PARTIAL";
    return "PENDING";
  };

  const status = getStatus();

  return (
    <div className="flex items-center justify-between px-4 py-2 bg-card rounded-xl border border-border">

      {/* LEFT */}
      <div className="flex items-center gap-3">

        {/* Icon */}
        <div className="w-9 h-9 rounded-xl bg-primary flex items-center justify-center text-white text-sm font-semibold">
          {task.taskName[0]}
        </div>

        <div className="flex flex-col">
          <span className="text-sm font-semibold leading-none">
            {task.taskName}
          </span>
          <span className="text-[11px] text-secondary mt-1">
            {task.completedDays} / {task.totalDays} Tasks
          </span>
        </div>

      </div>

      {/* RIGHT */}
      <div className="flex items-center gap-3">

        {/* Status */}
        <span
          className={`text-[10px] font-semibold px-2 py-[2px] rounded-full
            ${
              status === "DONE"
                ? "bg-green-100 text-green-600"
                : status === "PARTIAL"
                ? "bg-orange-100 text-orange-600"
                : "bg-red-100 text-red-600"
            }`}
        >
          {status}
        </span>

        {/* Controls */}
        <div className="flex items-center bg-hover rounded-md overflow-hidden">
          <button className="px-2 py-[2px] text-sm hover:bg-border">-</button>
          <button className="px-2 py-[2px] text-sm hover:bg-border">+</button>
        </div>

        {/* Log */}
        <button className="px-3 py-[3px] text-xs font-medium bg-hover rounded-md hover:bg-border">
          Log
        </button>

      </div>
    </div>
  );
}

export default TaskRow;