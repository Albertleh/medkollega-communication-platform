/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}"
  ],
  theme: {
    extend: {
      colors: {
        "jet-black": {
          50:  "#eff5f5",
          100: "#dfeceb",
          200: "#bfd9d7",
          300: "#9fc6c4",
          400: "#80b3b0",
          500: "#609f9c",
          600: "#4d807d",
          700: "#39605e",
          800: "#26403e",
          900: "#13201f",
          950: "#0d1616"
        }
      }
    }
  },
  plugins: [],
};
