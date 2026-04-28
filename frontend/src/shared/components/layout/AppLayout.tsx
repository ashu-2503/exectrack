import Sidebar from "./Sidebar";

function AppLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex">

      <Sidebar />

      <main className="flex-1 bg-page min-h-screen">
        {children}
      </main>

    </div>
  );
}

export default AppLayout;