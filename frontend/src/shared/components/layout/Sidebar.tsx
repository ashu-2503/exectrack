import {
  LayoutDashboard,
  Calendar,
  BarChart2,
  Plus,
  User,
} from "lucide-react";
import { useNavigate, useLocation } from "react-router-dom";

const navItems = [
  { icon: LayoutDashboard, key: "dashboard", path: "/" },
  { icon: Calendar, key: "calendar", path: "/calendar" },
  { icon: Plus, key: "add", path: "/task" },
  { icon: BarChart2, key: "analytics", path: "/analytics" },
];

function Sidebar() {
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div className="w-16 h-screen bg-card border-r border-border flex flex-col items-center">

      {/* 🔹 TOP SECTION */}
      <div className="flex flex-col items-center pt-4">

        {/* Logo */}
        <div className="text-sm font-semibold text-primary mb-6">
          ExecTrack
        </div>

        {/* Navigation */}
        <div className="flex flex-col items-center gap-4">
          {navItems.map(({ icon: Icon, path }) => {
            const isActive = location.pathname === path;

            return (
              <div
                key={path}
                onClick={() => navigate(path)}
                className={`
                  w-10 h-10 flex items-center justify-center rounded-xl cursor-pointer transition
                  ${
                    isActive
                        ? "bg-primary text-white shadow-sm"
                        : "text-secondary hover:bg-hover"
                  }
                `}
              >
                <Icon size={18} />
              </div>
            );
          })}
        </div>

      </div>

      {/* 🔹 BOTTOM SECTION */}
      <div className="mt-auto mb-4">
        <div
          onClick={() => navigate("/profile")}
          className="w-10 h-10 flex items-center justify-center rounded-xl text-secondary hover:bg-hover cursor-pointer transition"
        >
          <User size={18} />
        </div>
      </div>

    </div>
  );
}

export default Sidebar;