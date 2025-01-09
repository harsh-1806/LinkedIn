import Layout from '../components/Layout/Layout'
import Box from '../components/box/Box'
import Input from '../components/input/Input'
import Button from '../components/button/Button'
import classes from './Login.module.scss'
import Seperator from '../components/seperator/Seperator'
import { Link } from 'react-router-dom'
import { useState } from 'react'

const Login = () => {
    const [errorMessage, setErrorMessage] = useState('')
    return (
        <Layout className={classes.root}>
            <Box>
                <h1>Sign In</h1>
                <p>Make the most of your professional life</p>

                <form>
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
