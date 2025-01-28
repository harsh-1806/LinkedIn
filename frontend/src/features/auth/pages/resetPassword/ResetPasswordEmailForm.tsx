import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Input from '../../components/Input/Input'
import Button from '../../components/Button/Button'
import classes from './ResetPassword.module.scss'

interface ResetPasswordEmailFormProps {
    setEmailSent: React.Dispatch<React.SetStateAction<boolean>>
}

const ResetPasswordEmailForm: React.FC<ResetPasswordEmailFormProps> = ({
    setEmailSent,
}) => {
    const [email, setEmail] = useState('')
    const [errorMessage, setErrorMessage] = useState('')
    const navigate = useNavigate()

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if (!email.trim()) {
            setErrorMessage('Email is required')
            return
        }
        setErrorMessage('')
        setEmailSent(true)
    }

    return (
        <form onSubmit={handleSubmit}>
            <Input
                type="email"
                id="email"
                label="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
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
