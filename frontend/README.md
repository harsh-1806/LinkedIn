# LinkedIn Clone — Frontend

A reverse-engineered LinkedIn frontend built with **React 18**, **TypeScript**, **Vite**, and **SCSS Modules**. This project recreates LinkedIn's core authentication flows and feed layout with a clean, feature-based architecture.

![React](https://img.shields.io/badge/React-18.3-61DAFB?logo=react&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.6-3178C6?logo=typescript&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-6.0-646CFF?logo=vite&logoColor=white)
![SCSS](https://img.shields.io/badge/SCSS-Modules-CC6699?logo=sass&logoColor=white)

---

## Features

- **Login** — Email/password sign-in with error handling and loading states
- **Signup** — Account registration with automatic redirect to email verification
- **Email Verification** — Token-based verification with resend functionality
- **Password Reset** — Multi-step flow: enter email → receive code → set new password
- **Feed Page** — Responsive 3-column grid layout (sidebar, main content, right panel)
- **Auth Guards** — Automatic redirects for unauthenticated users and unverified emails
- **JWT Authentication** — Bearer token stored in `localStorage`, sent with every API call
- **Loading Screen** — Branded loader with LinkedIn logo and animated progress bar

---

## Tech Stack

| Layer        | Technology                                           |
| ------------ | ---------------------------------------------------- |
| Framework    | [React 18](https://react.dev/) with SWC compiler     |
| Language     | [TypeScript](https://www.typescriptlang.org/)        |
| Build Tool   | [Vite 6](https://vite.dev/)                          |
| Routing      | [React Router v7](https://reactrouter.com/)          |
| Styling      | SCSS Modules (scoped, per-component)                 |
| Fonts        | Google Fonts — Poppins, Roboto                       |
| Package Mgr  | [Bun](https://bun.sh/)                               |

---

## Project Structure

```
frontend/
├── public/
│   ├── favicon.ico
│   ├── logo.svg                    # LinkedIn logo (light)
│   └── logo-dark.svg               # LinkedIn logo (dark)
├── src/
│   ├── main.tsx                    # App entry — router setup
│   ├── index.scss                  # Global styles & CSS variables
│   ├── vite-env.d.ts
│   ├── components/
│   │   ├── Loader.tsx              # Full-screen branded loader
│   │   └── loader.module.scss
│   └── features/
│       ├── auth/
│       │   ├── context/
│       │   │   └── AuthContextProvider.tsx   # Auth context, user state, API calls
│       │   ├── utils/
│       │   │   └── api.ts                   # Generic typed fetch wrapper
│       │   ├── components/
│       │   │   ├── Box/             # Card container (white box with shadow)
│       │   │   ├── Button/          # Primary & outline button variants
│       │   │   ├── Input/           # Labeled input field
│       │   │   ├── Layout/          # Auth page layout (header + footer)
│       │   │   └── Seperator/       # "Or" divider line
│       │   └── pages/
│       │       ├── login/           # Sign-in page
│       │       ├── signup/          # Registration page
│       │       ├── verifyEmail/     # Email verification page
│       │       └── resetPassword/   # Multi-step password reset
│       └── feed/
│           └── pages/
│               ├── Feed.tsx         # Main feed (3-column grid)
│               └── Feed.module.scss
├── .env                            # API URL config
├── index.html
├── package.json
├── vite.config.ts
├── tsconfig.json
├── tsconfig.app.json
└── tsconfig.node.json
```

---

## Getting Started

### Prerequisites

- [Node.js](https://nodejs.org/) v18+ or [Bun](https://bun.sh/)
- A running backend API server (default: `http://localhost:8085`)

### Installation

```bash
# Clone the repository
git clone <repo-url>
cd frontend

# Install dependencies
bun install
# or
npm install
```

### Environment Variables

Create a `.env` file in the project root:

```env
VITE_API_URL=http://localhost:8085
```

### Development

```bash
bun run dev
# or
npm run dev
```

The app will be available at `http://localhost:5173`.

### Build

```bash
bun run build
# or
npm run build
```

---

## API Endpoints

The frontend communicates with the following backend API routes:

| Method | Endpoint                                 | Description                     |
| ------ | ---------------------------------------- | ------------------------------- |
| POST   | `/api/v1/auth/login`                     | User login                      |
| POST   | `/api/v1/auth/register`                  | User registration               |
| GET    | `/api/v1/auth/user`                      | Get current authenticated user  |
| PUT    | `/api/v1/auth/validate-email?token=`     | Verify email with token         |
| GET    | `/api/v1/auth/send-verification-email`   | Resend verification email       |
| POST   | `/api/v1/auth/send-reset-password-email` | Send password reset email       |
| POST   | `/api/v1/auth/validate-reset-password`   | Reset password with token       |

---

## Routes

| Path                     | Page             | Auth Required |
| ------------------------ | ---------------- | ------------- |
| `/`                      | Feed             | ✅ Yes        |
| `/login`                 | Login            | ❌ No         |
| `/signup`                | Signup           | ❌ No         |
| `/verify-email`          | Email Verification | ❌ No       |
| `/request-password-reset`| Password Reset   | ❌ No         |

---

## Auth Flow

```
┌──────────┐     ┌──────────┐     ┌───────────────┐     ┌──────┐
│  Signup   │────▶│  Verify  │────▶│  Verified?    │────▶│ Feed │
└──────────┘     │  Email   │     │  Yes → Feed   │     └──────┘
                 └──────────┘     │  No → Verify  │
                                  └───────────────┘
┌──────────┐     ┌──────────┐
│  Login   │────▶│  Feed    │
└──────────┘     └──────────┘

┌──────────────────────┐     ┌────────────────┐     ┌──────────┐
│  Forgot Password     │────▶│  Enter Code +  │────▶│  Login   │
│  (Enter Email)       │     │  New Password  │     └──────────┘
└──────────────────────┘     └────────────────┘
```

---

## License

This project is for educational purposes only. LinkedIn is a trademark of LinkedIn Corporation.
