import { useState } from 'react'
import Box from '../components/box/Box'
import Input from '../components/input/Input'
import Layout from '../components/Layout/Layout'
import classes from './ResetPassword.module.scss'
import Button from '../components/button/Button'
import { Link, useNavigate } from 'react-router-dom'
import Seperator from '../components/seperator/Seperator'
import ResetPasswordEmailForm from './ResetPasswordEmailForm'

const ResetPassword = () => {
    const navigate = useNavigate()
    const [emailSent, setEmailSent] = useState<boolean>(false)

    return (
        <Layout className={classes.root}>
            <Box>
                <h1>Reset Password</h1>
                {!emailSent ? (
                    <ResetPasswordEmailForm setEmailSent={setEmailSent} />
                ) : (
                    <form>
                        <Input type="email" id="email" label="Email" />
                        <p>
                            sdfa'll send a verification code to this email or
                            phone number if it matches an existing LinkedIn
                            account.
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
                        <Link to="/request-password-reset">
                            Forgot password?
                        </Link>
                    </form>
                )}
            </Box>
        </Layout>
    )
}

export default ResetPassword
