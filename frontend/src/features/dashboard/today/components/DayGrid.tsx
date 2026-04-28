import type { DayStatus } from "../../types/dashboard.types";
import { TaskStatus } from "../../../../shared/types/enums";

interface Props {
  days: DayStatus[];
}

function getColor(status: TaskStatus) {
  switch (status) {
    case TaskStatus.DONE:
      return "bg-green-500";

    case TaskStatus.PARTIAL:
      return "bg-yellow-400";

    case TaskStatus.MISSED:
      return "bg-red-500";

    case TaskStatus.PENDING:
      return "bg-blue-500";

    case TaskStatus.FUTURE:
      return "bg-gray-600";

    default:
      return "bg-gray-700";
  }
}

function DayGrid({ days }: Props) {
  return (
    <div className="flex gap-1">
      {days.map((day) => (
        <div
          key={day.day}
          className={`w-5 h-5 rounded ${getColor(day.status)}`}
          title={`Day ${day.day} - ${day.status}`}
        />
      ))}
    </div>
  );
}

export default DayGrid;