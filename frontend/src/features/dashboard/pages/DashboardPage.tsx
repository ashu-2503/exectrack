import { useApiQuery } from "../../../shared/hooks/useApiQuery";
import { fetchMonthlyDashboard } from "../api/dashboard.api";
import TaskRow from "../components/TaskRow";

function DashboardPage() {
  const { data, isLoading } = useApiQuery(
    ["dashboard"],
    fetchMonthlyDashboard
  );

  if (isLoading) return <div className="p-6">Loading...</div>;

  return (
    <div className="p-6 space-y-4">
      <h1 className="text-2xl font-bold">Execution Dashboard</h1>

      <div className="space-y-3">
        {data?.map((task) => (
          <TaskRow key={task.taskId} task={task} />
        ))}
      </div>
    </div>
  );
}

export default DashboardPage;