export async function rewrites() {
  return [
    {
      source: '/api/:path*',
      destination: '/',
    },
  ];
}