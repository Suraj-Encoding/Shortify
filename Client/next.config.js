/** @type {import('next').NextConfig} */
const nextConfig = {
  // # comment out for clerk middleware 
  // output: "export", 
  trailingSlash: true,
  images: {
    unoptimized: true
  },
  webpack: (config) => {
    config.resolve.alias.canvas = false;
    return config;
  }
};

module.exports = nextConfig;
