// src/pages/About.jsx
export default function About() {
  return (
    <div style={{ padding: '2rem', maxWidth: '800px', margin: '0 auto' }}>
      <h1>About DatingBook</h1>
      <p>
        <strong>DatingBook</strong> is your personal relationship companion — a private place to track the details of the people you're dating, their kids, what you’ve done together, and what makes them special.
      </p>
      <p>
        Whether you're juggling casual dates or looking for something deeper, DatingBook helps you remember birthdays, personalities, favorite spots, and even custody schedules — all in one place.
      </p>
      <p>
        This app is built with ❤️ using Spring Boot (backend) and React (frontend). All your data stays private and local to you.
      </p>
      <p style={{ marginTop: '2rem', fontStyle: 'italic' }}>
        Created by Jeff — because remembering who's allergic to shrimp shouldn’t require a spreadsheet.
      </p>
    </div>
  );
}
