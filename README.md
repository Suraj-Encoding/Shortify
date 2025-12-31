# 🕸️ Welcome To Shortify - Modern URL Shortener 🕸️

Shortify is a modern, full‑stack URL shortening service built with Go (backend), MongoDB (data store), Clerk (authentication), and Next.js (frontend). It's designed for rapid development and production readiness with clean APIs and a polished UI.

---

## Tech Stack

- **Backend:** Go
- **Database:** MongoDB
- **Auth:** Clerk
- **Frontend:** Next.js

---

## Key Features

- Shorten URLs into vanity links (username + slug)
- Fast redirect service for shortened links
- User management and authentication via Clerk
- MongoDB for reliable storage of users and links
- Next.js frontend with responsive UI and Clerk integration

---

## Project Structure (high level)

- `Server/` — Go backend, API handlers, app logic and MongoDB integration
- `Client/` — Next.js frontend, components, and Clerk auth routes
- `README.md` — This file

---

## Quick Start

Prerequisites:

- Go 1.20+ installed
- MongoDB accessible (local or hosted)
- Clerk account and API keys (for auth)

1. Clone the repo

```bash
mkdir Shortify
cd Shortify
git clone https://github.com/Suraj-Encoding/Shortify.git .
```

2. Backend: configure environment

- Copy or create `Server/.env` (or set env vars) with MongoDB URI, Clerk keys, and other settings used by `Server/env`.

3. Run the backend

```bash
cd Server
go mod tidy
go build main.go
./main
```

4. Frontend: install and run

```bash
cd Client
npm install
npm run build 
npm run dev
```

The frontend typically runs on `http://localhost:3000` and the backend on `http://localhost:3001` (configurable).

---

## Common Endpoints

- Redirect (public): `GET /{username}/{slug}` — redirects to the destination URL of the link
- API base: `/api/v1`
  - User webhook: `POST /api/v1/user/webhook` (Clerk)
  - Update username: `PUT /api/v1/user/username`
  - Links CRUD: under `/api/v1/link`

---

## Testing & Development Tips

- Use `curl -v http://localhost:3001/surajdalvi1/github` to test redirects.
- Run `go build main.go` to check for backend compile errors.
- Ensure Clerk webhooks point to the server's `/api/v1/user/webhook` during integration.

---

## Contributing

Contributions welcome — open issues or submit PRs. Follow these steps:

1. Fork the repo
2. Create a feature branch
3. Make changes and run `go build` and `npm run dev` locally
4. Open a PR with a clear description

---

## License

This project uses the license in the repository. Feel free to adapt as needed.

---

Enjoy Shortify — let me know if you want a README badge, deployment guide, or CI steps added.