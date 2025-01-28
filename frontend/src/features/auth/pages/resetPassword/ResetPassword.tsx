import { useState } from 'react'
import Box from '../../components/Box/Box'
import Layout from '../../components/Layout/Layout'
import classes from './ResetPassword.module.scss'
import ResetPasswordEmailForm from './ResetPasswordEmailForm'
import ResetPasswordVerification from './ResetPasswordVerification'

const ResetPassword = () => {
    const [emailSent, setEmailSent] = useState<boolean>(false)

    return (
        <Layout className={classes.root}>
            <Box>
                <h1>Reset Password</h1>
                {!emailSent ? (
                    <ResetPasswordEmailForm setEmailSent={setEmailSent} />
                ) : (
                    <ResetPasswordVerification />
                )}
            </Box>
        </Layout>
    )
}

export default ResetPassword
