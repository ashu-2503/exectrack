import { useQuery } from "@tanstack/react-query";

export const useApiQuery = <T>(
  key: string[],
  fn: () => Promise<T>
) => {
  return useQuery({
    queryKey: key,
    queryFn: fn,
  });
};