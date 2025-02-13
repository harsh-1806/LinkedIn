import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Input from '../../components/Input/Input'
import Button from '../../components/Button/Button'
import classes from './ResetPassword.module.scss'

interface ResetPasswordEmailFormProps {
    setEmailSent: React.Dispatch<React.SetStateAction<boolean>>
    setEmail: React.Dispatch<React.SetStateAction<string>>
}

const apiUrl = import.meta.env.VITE_API_URL as string

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

const ResetPasswordEmailForm: React.FC<ResetPasswordEmailFormProps> = ({
    setEmailSent,
    setEmail,
}) => {
    const [errorMessage, setErrorMessage] = useState('')
    const navigate = useNavigate()
    const [userEmail, setUserEmail] = useState('')

    const handleSubmit = () => {
        const response = request('/api/v1/auth/send-reset-password-email', {
            method: 'POST',
            body: JSON.stringify({ email: userEmail }),
        })

        if (response !== null) {
            setErrorMessage('')
            setEmailSent(true)
        } else {
            setErrorMessage('An error occurred. Please try again later.')
        }

        setEmailSent(true)
    }

    return (
        <form onSubmit={handleSubmit}>
            <Input
                type="email"
                id="email"
                label="Email"
                value={userEmail}
                onChange={(e) => {
                    setUserEmail(e.target.value)
                    setEmail(e.target.value)
                }}
            />
            {errorMessage && <p className={classes.error}>{errorMessage}</p>}
            <p>
                We'll send a verification code to this email or phone number if
                it matches an existing LinkedIn account.
            </p>
            <Button type="submit" id="submit">
                Next
            </Button>
            <Button
                type="button"
                id="back"
                outline
                onClick={() => navigate('/login')}
            >
                Back
            </Button>
        </form>
    )
}

export default ResetPasswordEmailForm
