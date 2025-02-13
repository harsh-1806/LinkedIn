import { createContext, useContext, useEffect, useState } from 'react'
import { Navigate, Outlet, useLocation, useNavigate } from 'react-router-dom'
import Loader from '../../../components/Loader'

interface User {
    id: string
    email: string
    isVerified: boolean
}

interface ApiResponse {
    message: string
    status: string
    success: boolean
}

interface AuthContextType {
    user: User | null
    login: (email: string, password: string) => Promise<void>
    signup: (data: { email: string; password: string }) => Promise<any>
    verifyEmail: (token: string) => Promise<ApiResponse>
    logout: () => void
}

export const AuthContext = createContext<AuthContextType | null>(null)

export function useAuthentication() {
    return useContext(AuthContext)
}

const apiUrl: string = import.meta.env.VITE_API_URL
const userEndpoint: string = apiUrl + '/api/v1/auth/user'

const request = async (path: string, options: RequestInit) => {
    const endpoint = `${apiUrl}${path}`
    const headers = {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
        ...options.headers,
    }
    try {
        const response = await fetch(endpoint, { ...options, headers })
        if (!response.ok) {
            const errorData = await response.json()
            throw new Error(errorData.message || 'An error occurred')
        }
        return await response.json()
    } catch (error) {
        throw new Error(
            error instanceof Error ? error.message : 'An error occurred'
        )
    }
}

const AuthContextProvider = () => {
    const [user, setUser] = useState<User | null>(null)
    const [isLoading, setIsLoading] = useState<boolean>(true)
    const location = useLocation()

    const isAuthPage = [
        '/login',
        '/signup',
        '/request-password-reset',
        '/verify-email',
    ].includes(location.pathname)

    const login = async (email: string, password: string) => {
        const data = await request('/api/v1/auth/login', {
            method: 'POST',
            body: JSON.stringify({ email, password }),
        })
        // Save the token to localStorage
        localStorage.setItem('token', data.token)
        return data
    }

    const signup = async (data: { email: string; password: string }) => {
        const response = await request('/api/v1/auth/register', {
            method: 'POST',
            body: JSON.stringify(data),
        })

        localStorage.setItem('token', response.token)
        return data
    }

    const resetPassword = async (email: string) => {
        return request('/reset-password', {
            method: 'POST',
            body: JSON.stringify({ email }),
        })
    }

    const verifyEmail = async (token: string) => {
        return request(`/api/v1/auth/validate-email?token=${token}`, {
            method: 'PUT',
            headers: {},
        })
    }

    const logout = async () => {
        localStorage.removeItem('token')
        setUser(null)
    }

    useEffect(() => {
        if (user) return

        fetchUser()
    }, [user, location.pathname])

    const fetchUser = async () => {
        try {
            const response = await fetch(userEndpoint, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
            })

            if (!response.ok) {
                throw new Error('Authentication Failed')
            }

            const user = await response.json()

            console.log(user.isVerified)

            setUser(user)
        } catch (e) {
            console.error(e)
        } finally {
            setIsLoading(false)
        }
    }

    if (isLoading) {
        return <Loader />
    }

    if (!isLoading && !user && !isAuthPage) {
        return <Navigate to={'/login'} />
    }

    if (user && user.isVerified && isAuthPage) {
        return <Navigate to={'/'} />
    }

    return (
        <AuthContext.Provider
            value={{ user, login, verifyEmail, signup, logout }}
        >
            {user && !user.isVerified ? (
                <Navigate to={'/verify-email'} />
            ) : null}
            <Outlet />
        </AuthContext.Provider>
    )
}

export default AuthContextProvider
