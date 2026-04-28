import type { DashboardResponse } from "../../types/dashboard.types";

interface Props {
  data: DashboardResponse[];
}

function SummaryCards({ data }: Props) {
  const totalTasks = data.length;

  const completed = data.filter(
    (t) => t.completionPercentage === 100
  ).length;

  const pending = totalTasks - completed;

  const avgCompletion =
    data.length === 0
      ? 0
      : data.reduce((acc, t) => acc + t.completionPercentage, 0) /
        data.length;

  const percentage = Math.round(avgCompletion);

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">

      {/* Total Tasks */}
      <div className="card shadow-sm">
        <p className="text-secondary text-xs font-medium">
          Total Tasks
        </p>
        <h2 className="text-2xl font-bold mt-1">
          {totalTasks}
        </h2>
      </div>

      {/* Completed */}
      <div className="card shadow-sm">
        <p className="text-secondary text-xs font-medium">
          Completed
        </p>
        <h2 className="text-2xl font-bold mt-1 text-success">
          {completed}
        </h2>
      </div>

      {/* Pending */}
      <div className="card shadow-sm">
        <p className="text-secondary text-xs font-medium">
          Pending
        </p>
        <h2 className="text-2xl font-bold mt-1 text-warning">
          {pending}
        </h2>
      </div>

      {/* Completion */}
      <div className="card shadow-sm">
        <p className="text-secondary text-xs font-medium">
          Completion
        </p>

        <div className="flex items-center justify-between mt-2">

          <h2 className="text-xl font-bold text-primary">
            {percentage}%
          </h2>

          {/* Proper Circular Progress */}
          <div className="relative w-10 h-10">
            <svg className="w-10 h-10 -rotate-90">
              <circle
                cx="20"
                cy="20"
                r="16"
                stroke="#E2E8F0"
                strokeWidth="3"
                fill="none"
              />
              <circle
                cx="20"
                cy="20"
                r="16"
                stroke="#6C5CE7"
                strokeWidth="3"
                fill="none"
                strokeDasharray={2 * Math.PI * 16}
                strokeDashoffset={
                  2 * Math.PI * 16 * (1 - percentage / 100)
                }
                strokeLinecap="round"
              />
            </svg>

            <div className="absolute inset-0 flex items-center justify-center text-[10px] font-semibold">
              {percentage}
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}

export default SummaryCards;