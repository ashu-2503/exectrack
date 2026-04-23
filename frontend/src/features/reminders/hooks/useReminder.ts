import { useEffect, useRef } from "react";
import { apiClient } from "../../../services/apiClient";

interface Reminder {
  taskId: number;
  taskName: string;
  reminderInterval: number;
}

export const useReminder = () => {
  const lastTriggered = useRef<Record<number, number>>({});

  useEffect(() => {
    const interval = setInterval(async () => {
      const reminders: Reminder[] = await apiClient("/reminders");

      const now = Date.now();

      reminders.forEach((task) => {
        const last = lastTriggered.current[task.taskId] || 0;

        if (now - last >= task.reminderInterval * 60 * 1000) {
          alert(`Reminder: ${task.taskName}`);

          lastTriggered.current[task.taskId] = now;
        }
      });
    }, 30000); // poll every 30 sec

    return () => clearInterval(interval);
  }, []);
};