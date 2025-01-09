import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.scss'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Feed from './features/feed/pages/Feed'
import Login from './features/auth/login/Login'
import Signup from './features/auth/signup/Signup'
import ResetPassword from './features/auth/resetPassword/ResetPassword'
import VerifyEmail from './features/auth/verifyEmail/VerifyEmail'

const router = createBrowserRouter([
    {
        path: '/',
        element: <Feed />,
    },
    {
        path: '/login',
        element: <Login />,
    },
    {
        path: 'signup',
        element: <Signup />,
    },
    {
        path: '/request-password-reset',
        element: <ResetPassword />,
    },
    {
        path: '/verify-email',
        element: <VerifyEmail />,
    },
])

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>
)
