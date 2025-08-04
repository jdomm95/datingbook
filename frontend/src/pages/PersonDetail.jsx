// src/pages/PersonDetail.jsx
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

function PersonDetail() {
  const { id } = useParams();
  const [person, setPerson] = useState(null);
  const [tags, setTags] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/api/people/${id}`)
      .then((res) => res.json())
      .then((data) => {
        setPerson(data);
        setTags(data.tags || []); 
      })
      .catch((err) => console.error("Failed to load person", err));
  }, [id]);

  if (!person) return <p>Loading...</p>;

  return (
    <div className="p-6 space-y-4">
      <h1 className="text-3xl font-bold">{person.name}</h1>
      <div className="text-gray-700">
        <p><strong>Age:</strong> {person.age ?? "Unknown"}</p>
        <p><strong>Area:</strong> {person.area ?? "Unknown"}</p>
      </div>

      <div className="mt-4">
        <h2 className="text-xl font-semibold mb-2">Tags</h2>
        {tags.length > 0 ? (
          <ul className="list-disc list-inside">
            {tags.map(tag => (
              <li key={tag.id}>{tag.label}</li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-500">No tags</p>
        )}
      </div>

      <div>
        <h2 className="text-xl font-semibold mt-4">Children:</h2>
        {person.children && person.children.length > 0 ? (
          <ul className="list-disc ml-6">
            {person.children.map(child => (
              <li key={child.id}>
                {child.name} (Age: {child.age ?? "?"})
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-sm text-gray-500">No children listed.</p>
        )}
      </div>
    </div>
  );
}

export default PersonDetail;
