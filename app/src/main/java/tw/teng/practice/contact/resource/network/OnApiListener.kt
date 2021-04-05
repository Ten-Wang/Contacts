package tw.teng.practice.contact.resource.network

interface OnApiListener<T> {
    fun onApiTaskSuccess(responseData: T)

    fun onApiTaskFailure(toString: String)
}
