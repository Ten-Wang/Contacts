package tw.teng.practice.contact.ui.customer.recycler

interface LoadMoreExecutor {
    fun setLoadMoreEnable(isEnable: Boolean)

    fun setLoadMoreListener(loadMoreListener: LoadMoreListener)

    interface LoadMoreListener {
        fun onLoadMore()
    }
}

