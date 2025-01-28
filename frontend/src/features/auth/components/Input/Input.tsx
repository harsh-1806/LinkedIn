import { InputHTMLAttributes } from 'react'
import classes from './Input.module.scss'

type InputProps = InputHTMLAttributes<HTMLInputElement> & {
    label: string;
}

const Input = ({label, ...otherProps} : InputProps) => {
  return (
    <div className= {classes.root}>
        <label htmlFor={otherProps.id}>
            {label}
        </label>
        <input {...otherProps} />
    </div>
  )
}

export default Input