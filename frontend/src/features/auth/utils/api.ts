const BASE_URL = import.meta.env.VITE_API_URL

interface IRequestParams<T> {
    endpoint: string
    method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
    body?: BodyInit
    onSuccess: (data: T) => void
    onFailure: (error: string) => void
}

export const request = async <T>({
    endpoint,
    method = 'GET',
    body,
    onSuccess,
    onFailure,
}: IRequestParams<T>) => {
    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, {
            method,
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
            body,
        })

        if (!response.ok) {
            const { message } = await response.json()
            throw new Error(message)
        }

        const data: T = await response.json()

        onSuccess(data)
    } catch (error) {
        if (error instanceof Error) {
            onFailure(error.message)
        } else {
            onFailure('An error occurred. Please try again later.')
        }
    }
}
