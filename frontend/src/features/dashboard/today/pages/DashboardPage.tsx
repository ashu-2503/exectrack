import { useApiQuery } from "../../../../shared/hooks/useApiQuery";
import { fetchMonthlyDashboard } from "../../api/dashboard.api";
import SummaryCards from "../sections/SummaryCards";
import TodayTasksSection from "../sections/TodayTasksSection";

function DashboardPage() {
  const { data, isLoading } = useApiQuery(
    ["dashboard"],
    fetchMonthlyDashboard
  );

  if (isLoading) return <div className="p-6">Loading...</div>;

  return (
    <div className="p-6 space-y-6">

      {/* Header */}
      <div>
        <h1 className="text-xl font-semibold">
          Wednesday, 22 Apr 2026
        </h1>
      </div>

      {/* Summary Cards */}
      <SummaryCards data={data || []} />
      <TodayTasksSection data={data || []} />

    </div>
  );
}

export default DashboardPage;