export const TaskStatus = {
  DONE: "DONE",
  PARTIAL: "PARTIAL",
  MISSED: "MISSED",
  PENDING: "PENDING",
  FUTURE: "FUTURE",
} as const;

export type TaskStatus = (typeof TaskStatus)[keyof typeof TaskStatus];


export const FrequencyType = {
  DAILY: "DAILY",
  WEEKLY: "WEEKLY",
  CUSTOM: "CUSTOM",
} as const;

export type FrequencyType =
  (typeof FrequencyType)[keyof typeof FrequencyType];