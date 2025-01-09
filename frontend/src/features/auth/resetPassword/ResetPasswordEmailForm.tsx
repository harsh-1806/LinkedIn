import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Input from '../components/input/Input'
import Button from '../components/button/Button'

interface ResetPasswordEmailFormProps {
    setEmailSent: React.Dispatch<React.SetStateAction<boolean>>
}

const ResetPasswordEmailForm: React.FC<ResetPasswordEmailFormProps> = ({
    setEmailSent,
}) => {
    const [errorMessage, setErrorMessage] = useState('')
    const navigate = useNavigate()

    return (
        <form>
            <Input type="email" id="email" label="Email" />
            <p>
                We'll send a verification code to this email or phone number if
                it matches an existing LinkedIn account.
            </p>
            <Button
                type="submit"
                id="submit"
                onClick={(e) => {
                    e.preventDefault()
                    setEmailSent(true)
                }}
            >
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
            <Link to="/request-password-reset">Forgot password?</Link>
        </form>
    )
}

export default ResetPasswordEmailForm
