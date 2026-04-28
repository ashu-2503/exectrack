/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "var(--primary)",
        "primary-light": "var(--primary-light)",

        success: "var(--success)",
        warning: "var(--warning)",
        danger: "var(--danger)",
        info: "var(--info)",

        border: "var(--border)",
        card: "var(--bg-card)",
        page: "var(--bg-page)",
        hover: "var(--bg-hover)",
      },
    },
  },
  plugins: [],
};