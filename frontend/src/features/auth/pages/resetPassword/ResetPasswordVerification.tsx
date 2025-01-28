import React, { useState } from 'react'
import Input from '../../components/Input/Input'
import Button from '../../components/Button/Button'
import { useNavigate } from 'react-router-dom'
import classes from './ResetPassword.module.scss'

const ResetPasswordVerification = () => {
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
        setErrorMessage('')
        console.log('Verification code and password submitted')
        // Proceed with the reset password logic
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
