import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.scss'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Feed from './features/feed/pages/Feed'
import Login from './features/auth/pages/login/Login'
import Signup from './features/auth/pages/signup/Signup'
import ResetPassword from './features/auth/pages/resetPassword/ResetPassword'
import VerifyEmail from './features/auth/pages/verifyEmail/VerifyEmail'
import AuthContextProvider from './features/auth/context/AuthContextProvider'

const router = createBrowserRouter([
    {
        element: <AuthContextProvider />,
        children: [
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
        ],
    },
])

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>
)
