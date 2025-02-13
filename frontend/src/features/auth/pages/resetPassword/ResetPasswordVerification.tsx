import React, { useState } from 'react'
import Input from '../../components/Input/Input'
import Button from '../../components/Button/Button'
import { useNavigate } from 'react-router-dom'
import classes from './ResetPassword.module.scss'

const apiUrl = import.meta.env.VITE_API_URL as string

const request = async (path: string, options: RequestInit) => {
    const endpoint = `${apiUrl}${path}`
    const headers = {
        'Content-Type': 'application/json',
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

const ResetPasswordVerification = (props: { email: string }) => {
    const navigate = useNavigate()
    const [code, setCode] = useState('')
    const [password, setPassword] = useState('')
    const [errorMessage, setErrorMessage] = useState('')

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        if (!code.trim() || !password.trim()) {
            setErrorMessage('Both fields are required')
            return
        }

        try {
            request('/api/v1/auth/validate-reset-password', {
                method: 'POST',
                body: JSON.stringify({
                    email: props.email,
                    token: code,
                    newPassword: password,
                }),
            })

            setErrorMessage('')
            navigate('/login')
            console.log('Verification code and password submitted')
        } catch (error) {
            if (error instanceof Error) {
                setErrorMessage(error.message)
            } else {
                setErrorMessage('An error occurred. Please try again later.')
            }
        }
    }

    return (
        <form className={classes.root} onSubmit={handleSubmit}>
            <p>
                Enter the verification code we sent to your email and your new
                password.
            </p>
            {errorMessage && <p className={classes.error}>{errorMessage}</p>}
            <Input
                type="password"
                id="code"
                label="Verification code"
                value={code}
                onChange={(e) => setCode(e.target.value)}
            />
            <Input
                type="password"
                id="password"
                label="New Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <Button type="submit" id="submit">
                Reset Password
            </Button>
            <Button
                type="button"
                id="back"
                outline
                onClick={() => navigate('/request-reset-password')}
            >
                Back
            </Button>
        </form>
    )
}

export default ResetPasswordVerification
