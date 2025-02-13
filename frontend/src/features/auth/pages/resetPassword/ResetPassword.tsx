import { useState } from 'react'
import Box from '../../components/Box/Box'
import Layout from '../../components/Layout/Layout'
import classes from './ResetPassword.module.scss'
import ResetPasswordEmailForm from './ResetPasswordEmailForm'
import ResetPasswordVerification from './ResetPasswordVerification'

const ResetPassword = () => {
    const [emailSent, setEmailSent] = useState<boolean>(false)
    const [email, setEmail] = useState<string>('')

    return (
        <Layout className={classes.root}>
            <Box>
                <h1>Reset Password</h1>
                {!emailSent ? (
                    <ResetPasswordEmailForm
                        setEmail={setEmail}
                        setEmailSent={setEmailSent}
                    />
                ) : (
                    <ResetPasswordVerification email={email} />
                )}
            </Box>
        </Layout>
    )
}

export default ResetPassword
