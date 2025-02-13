import { useState } from 'react'
import Box from '../../components/Box/Box'
import Input from '../../components/Input/Input'
import Layout from '../../components/Layout/Layout'
import classes from './VerifyEmail.module.scss'
import Button from '../../components/Button/Button'
import { useAuthentication } from '../../context/AuthContextProvider'
import { useNavigate } from 'react-router-dom'

const VerifyEmail = () => {
    const [errorMessage, setErrorMessage] = useState<string>('')
    const [message, setMessage] = useState<string>('')
    const [isLoading, setIsLoading] = useState<boolean>(false)

    const authContext = useAuthentication()

    const verifyEmail = authContext?.verifyEmail

    const navigate = useNavigate()

    const handleSendAgain = async () => {
        setErrorMessage('')
        try {
            const response = await fetch(
                `${
                    import.meta.env.VITE_API_URL
                }/api/v1/auth/send-verification-email`,
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem(
                            'token'
                        )}`,
                    },
                }
            )
            if (response.ok) {
                setErrorMessage('')
                setMessage('Email sent successfully')
            } else {
                const { message } = await response.json()
                setErrorMessage(message)
            }
        } catch (e) {
            console.log(e)
            setErrorMessage('Something went wrong, please try again.')
        } finally {
            setIsLoading(false)
        }
    }

    const handleVerifyEmail = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setMessage('')

        const code = (e.currentTarget.elements[0] as HTMLInputElement).value
        try {
            if (verifyEmail) {
                const response = await verifyEmail(code)

                if (response) {
                    setMessage('')

                    const { message } = response

                    setMessage(message)

                    navigate('/')
                } else {
                    throw new Error("Problem is authContext's verifyEmail")
                }
            } else {
                throw new Error('An error occurred')
            }
        } catch (error) {
            if (error instanceof Error) setErrorMessage(error.message)
            else setErrorMessage('An error occurred. Please try again later.')
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <Layout className={classes.root}>
            <Box>
                <h1>Verify your email</h1>
                <form onSubmit={handleVerifyEmail}>
                    <p>
                        Only one step left to complete your registration. Verify
                        your email address.
                    </p>
                    <Input
                        type="text"
                        label="Verification Code"
                        id="code"
                    ></Input>
                    {message && <p style={{ color: 'green' }}>{message}</p>}
                    {errorMessage && (
                        <p style={{ color: 'red' }}>{errorMessage}</p>
                    )}
                    <Button type="submit" disabled={isLoading}>
                        Validate Email
                    </Button>
                    <Button
                        outline
                        type="button"
                        onClick={() => handleSendAgain()}
                        disabled={isLoading}
                    >
                        {isLoading ? '...' : 'Send Again'}
                    </Button>
                </form>
            </Box>
        </Layout>
    )
}

export default VerifyEmail
