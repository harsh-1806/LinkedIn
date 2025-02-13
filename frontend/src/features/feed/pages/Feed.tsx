import { useAuthentication } from '../../auth/context/AuthContextProvider'
import classes from './Feed.module.scss'

const Feed = () => {
    const authContext = useAuthentication()

    let user = null
    let logout = null

    if (authContext) {
        user = authContext.user
        logout = authContext.logout
    }

    return (
        <div className={classes.root}>
            <header className={classes.header}>
                <div>Hello {user?.email}</div>
                <span>|</span>
                <button onClick={logout}>Logout</button>
            </header>
            <main className={classes.content}>
                <div className={classes.left}></div>
                <div className={classes.center}>
                    <div className={classes.postings}></div>
                    <div className={classes.feed}></div>
                </div>
                <div className={classes.right}></div>
            </main>
        </div>
    )
}

export default Feed
