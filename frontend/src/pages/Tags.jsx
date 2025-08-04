// src/pages/Tags.jsx
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function Tags() {
  const [tags, setTags] = useState([]);

  useEffect(() => {
  fetch("http://localhost:8080/api/tags")
      .then((res) => res.json())
      .then((data) => {
      console.log("Fetched tags:", data);  // ← Add this line
      setTags(data);
      })
      .catch((err) => console.error("Failed to load tags", err));
  }, []);
  console.log("Rendering tags:", tags);
  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Tags</h1>

      {tags.length === 0 ? (
        <p className="text-gray-500">No tags found.</p>
      ) : (
        <ul className="space-y-6">
          {tags.map(tag => (
            <li key={tag.id}>
              <h2 className="text-xl font-semibold">{tag.label}</h2>
              {tag.people && tag.people.length > 0 ? (
                <ul className="ml-6 list-disc">
                  {tag.people.map(person => (
                    <li key={person.id}>
                      <Link to={`/people/${person.id}`} className="text-blue-600 hover:underline">
                        {person.name}
                      </Link>
                    </li>
                  ))}
                </ul>
              ) : (
                <p className="ml-4 text-sm text-gray-500">No people assigned.</p>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Tags;
