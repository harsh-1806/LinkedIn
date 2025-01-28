import { ButtonHTMLAttributes, ReactNode } from 'react'
import classes from './Button.module.scss'

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
    outline?: boolean
    children?: ReactNode
}

const Button = ({ outline, ...others }: ButtonProps) => {
    return (
        <button
            className={`${classes.root} ${outline ? classes.outline : ''}`}
            {...others}
        ></button>
    )
}

export default Button
