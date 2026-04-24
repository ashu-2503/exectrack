import type { DashboardResponse } from "../../types/dashboard.types";

interface Props {
  data: DashboardResponse[];
}

function SummaryCards({ data }: Props) {
  // 🔹 Aggregate values
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

  return (
    <div className="grid grid-cols-4 gap-4">

      {/* Total Tasks */}
      <div className="card">
        <p className="text-secondary text-sm">Total Tasks</p>
        <h2 className="text-2xl font-semibold mt-1">
          {totalTasks}
        </h2>
      </div>

      {/* Completed */}
      <div className="card">
        <p className="text-secondary text-sm">Completed</p>
        <h2 className="text-2xl font-semibold mt-1 text-success">
          {completed}
        </h2>
      </div>

      {/* Pending */}
      <div className="card">
        <p className="text-secondary text-sm">Pending</p>
        <h2 className="text-2xl font-semibold mt-1 text-warning">
          {pending}
        </h2>
      </div>

      {/* Completion % */}
      <div className="card flex flex-col justify-between">
        <p className="text-secondary text-sm">Completion %</p>

        <div className="flex items-center justify-between mt-2">
          <h2 className="text-xl font-semibold text-primary">
            {avgCompletion.toFixed(0)}%
          </h2>

          {/* Simple circle indicator (placeholder for now) */}
          <div className="w-10 h-10 rounded-full border-4 border-primary flex items-center justify-center text-xs font-semibold">
            {avgCompletion.toFixed(0)}
          </div>
        </div>
      </div>
    </div>
  );
}

export default SummaryCards;