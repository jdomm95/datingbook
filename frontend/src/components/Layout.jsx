import { Link, Outlet } from "react-router-dom";

export default function Layout() {
  return (
    <div className="min-h-screen bg-gray-100 text-gray-900">
      <header className="bg-white shadow p-4 mb-6">
        <nav className="container mx-auto flex justify-between items-center">
          <h1 className="text-xl font-bold">
            <Link to="/">DatingBook</Link>
          </h1>
          <ul className="flex space-x-4">
            <li><Link to="/" className="hover:text-blue-600">Home</Link></li>
            <li><Link to="/about" className="hover:text-blue-600">About</Link></li>
            <li><Link to="/tags" className="hover:underline">Tags</Link></li>
          </ul>
        </nav>
      </header>
      <main className="container mx-auto px-4">
        <Outlet />
      </main>
    </div>
  );
}
