const BASE_URL = "http://localhost:8080";

export const apiClient = async <T>(
  url: string,
  options?: RequestInit
): Promise<T> => {
  const res = await fetch(BASE_URL + url, {
    headers: {
      "Content-Type": "application/json",
    },
    ...options,
  });

  if (!res.ok) {
    throw new Error("API Error");
  }

  return res.json();
};