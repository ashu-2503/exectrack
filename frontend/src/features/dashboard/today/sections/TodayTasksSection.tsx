import type { DashboardResponse } from "../../types/dashboard.types";
import TaskRow from "../components/TaskRow";

interface Props {
  data: DashboardResponse[];
}

function TodayTasksSection({ data }: Props) {
  return (
    <div className="card">

      <h3 className="text-sm font-semibold mb-3">
        Today's Tasks
      </h3>

      <div className="space-y-3">
        {data.map((task) => (
          <TaskRow key={task.taskId} task={task} />
        ))}
      </div>

    </div>
  );
}

export default TodayTasksSection;