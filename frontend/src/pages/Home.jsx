// src/pages/Home.jsx
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function Home() {
  const [people, setPeople] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/people")
      .then((res) => res.json())
      .then((data) => setPeople(data))
      .catch((err) => console.error("Failed to load people", err));
  }, []);

  return (
    <div>
      <h2 className="text-2xl font-semibold mb-4">People</h2>
      <ul className="grid gap-4">
        {people.map((person) => (
          <li
            key={person.id}
            className="bg-white p-4 rounded shadow hover:bg-gray-50 transition"
          >
            <Link to={`/people/${person.id}`} className="text-lg font-medium text-blue-600 hover:underline">
              {person.name}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Home;
