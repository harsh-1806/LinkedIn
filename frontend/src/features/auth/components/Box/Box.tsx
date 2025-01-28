import { ReactNode } from "react"
import classes from "./Box.module.scss"

const Box = ({children} : {children : ReactNode}) => {
  return <div className={classes.root}>{children}</div>
}

export default Box