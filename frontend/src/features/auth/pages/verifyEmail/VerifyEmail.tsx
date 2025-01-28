import { useState } from 'react'
import Box from '../../components/Box/Box'
import Input from '../../components/Input/Input'
import Layout from '../../components/Layout/Layout'
import classes from './VerifyEmail.module.scss'
import Button from '../../components/Button/Button'

const VerifyEmail = () => {
    const [errorMessage, setErrorMessage] = useState<string>('')
    const [message, setMessage] = useState<string>('')

    return (
        <Layout className={classes.root}>
            <Box>
                <h1>Verify your email</h1>
                <form>
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
                    <Button type="submit">Validate Email</Button>
                    <Button outline type="button">
                        Send Again
                    </Button>
                </form>
            </Box>
        </Layout>
    )
}

export default VerifyEmail
