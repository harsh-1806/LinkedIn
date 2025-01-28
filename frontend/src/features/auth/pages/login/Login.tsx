import Layout from '../../components/Layout/Layout'
import Box from '../../components/Box/Box'
import Input from '../../components/Input/Input'
import Button from '../../components/Button/Button'
import classes from './Login.module.scss'
import Seperator from '../../components/Seperator/Seperator'
import { Link } from 'react-router-dom'
import { FormEvent, useState } from 'react'
import { useAuthentication } from '../../context/AuthContextProvider'

const Login = () => {
    const [errorMessage, setErrorMessage] = useState('')

    const authContext = useAuthentication()

    if(!authContext) {
        throw new Error('Authentication context not found')
    }

    const { login } = authContext

    const handleLogin = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        const email = e.currentTarget.email.value;
        const password = e.currentTarget.password.value;

        try {
            await login(email, password)
        } catch (error) {
            if(error instanceof Error) {
                console.log(error)
                setErrorMessage(error.message)
            }
            else {
                setErrorMessage('An error occurred')
            }
        }
    }

    return (
        <Layout className={classes.root}>
            <Box>
                <h1>Sign In</h1>
                <p>Make the most of your professional life</p>

                <form onSubmit={handleLogin}>
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
                    <Button type="submit" id="submit">
                        Sign In
                    </Button>
                    <Link to="/request-password-reset">Forgot password?</Link>
                </form>
                <Seperator>Or</Seperator>
                <div className={classes.signup}>
                    New to LinkedIn? <Link to="/signup">Join Now</Link>
                </div>
            </Box>
        </Layout>
    )
}

export default Login
