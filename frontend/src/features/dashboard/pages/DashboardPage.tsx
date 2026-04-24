import { useApiQuery } from "../../../shared/hooks/useApiQuery";
import { fetchMonthlyDashboard } from "../api/dashboard.api";
import SummaryCards from "../today/sections/SummaryCards";

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
          Dashboard (Today)
        </h1>
        <p className="text-secondary text-sm">
          Wednesday, 22 Apr 2026
        </p>
      </div>

      {/* Summary Cards */}
      <SummaryCards data={data || []} />

    </div>
  );
}

export default DashboardPage;