import Layout from '../../components/Layout/Layout'
import Box from '../../components/Box/Box'
import Input from '../../components/Input/Input'
import Button from '../../components/Button/Button'
import classes from './Signup.module.scss'
import Seperator from '../../components/Seperator/Seperator'
import { Link, useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { useAuthentication } from '../../context/AuthContextProvider'

const Signup = () => {
    const [errorMessage, setErrorMessage] = useState('')
    const [isLoading, setIsLoading] = useState(false)

    const authContext = useAuthentication()
    const navigate = useNavigate()

    const signup = authContext?.signup

    const handleSignup = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setErrorMessage('')
        setIsLoading(true)

        const email = e.currentTarget.email.value
        const password = e.currentTarget.password.value

        try {
            if (signup) {
                await signup({ email, password })

                setIsLoading(false)
                navigate('/verify-email')
            }
        } catch (error) {
            if (error instanceof Error) {
                console.log(error)
                setErrorMessage(error.message)
            } else {
                setErrorMessage('An error occurred')
            }
        }
    }

    return (
        <Layout className={classes.root}>
            <Box>
                <h1>Sign Up</h1>
                <p>Make the most of your professional life</p>

                <form onSubmit={handleSignup}>
                    <Input type="email" id="email" label="Email" />
                    <Input type="password" id="password" label="Password" />
                    {errorMessage && (
                        <p className={classes.error}>{errorMessage}</p>
                    )}
                    <p className={classes.disclaimer}>
                        By clicking Agree & Join or Continue, you agree to the
                        LinkedIn <a href="">User Agreement</a>,{' '}
                        <a href="">Privacy Policy</a>, and{' '}
                        <a href="">Cookie Policy</a>.
                    </p>
                    <Button type="submit" disabled={isLoading} id="submit">
                        Agree & Join
                    </Button>
                </form>
                <Seperator>Or</Seperator>
                <div className={classes.signin}>
                    Already on LinkedIn? <Link to="/login">Sign In</Link>
                </div>
            </Box>
        </Layout>
    )
}

export default Signup
