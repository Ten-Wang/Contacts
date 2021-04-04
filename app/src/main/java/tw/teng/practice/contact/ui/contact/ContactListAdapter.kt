package tw.teng.practice.contact.ui.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.teng.practice.contact.R
import tw.teng.practice.contact.resource.network.model.APIContacts
import tw.teng.practice.contact.ui.customer.recycler.LoadMoreExecutor
import tw.teng.practice.contact.ui.customer.recycler.RecyclerAdapterBase
import java.util.*

class ContactListAdapter internal constructor(
    private val itemList: ArrayList<APIContacts.Contacts>,
    private val mListener: ContactListItemAdapterListener
) : RecyclerAdapterBase<APIContacts.Contacts>(itemList), LoadMoreExecutor {

    internal var selectedPosition = -1

    init {
        isLoadMoreEnable
    }

    override fun onCreateItemViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ContactViewHolder(
            inflater.inflate(R.layout.item_contact_list, parent, false)
        )
    }

    override fun onBindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val vh = viewHolder as ContactViewHolder
        vh.bindData(itemList, position)
    }


    private inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private val imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        private val contentLayout: View = itemView.findViewById(R.id.item_user_content)
        private val imgIconStar: ImageView = itemView.findViewById(R.id.img_ic_star)
        private val imgStarred: ImageView = itemView.findViewById(R.id.img_starred)

        init {
            contentLayout.setOnClickListener {
                selectedPosition = getItemPosition(this@ContactViewHolder)
                mListener.onItemClick(getItemPosition(this@ContactViewHolder))
            }
            imgStarred.setOnClickListener {
                mListener.onStarredActionClick(getItemPosition(this@ContactViewHolder))
            }
        }

        fun bindData(list: List<APIContacts.Contacts>, position: Int) {
            val item = list[position]
            if (item.starred) {
                imgIconStar.visibility = View.VISIBLE
                imgStarred.setImageResource(R.drawable.ic_action_unstar)
            } else {
                imgIconStar.visibility = View.INVISIBLE
                imgStarred.setImageResource(R.drawable.ic_action_star)
            }

            tvName.text = item.name
            tvEmail.text = item.email
//            Glide.with(itemView.context)
//                .load(item.pictureUrl)
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        progress.visibility = View.GONE
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        resource: Drawable,
//                        model: Any,
//                        target: Target<Drawable>?,
//                        dataSource: DataSource,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        progress.visibility = View.GONE
//                        return false
//                    }
//                })
//                .into(imgAvatar)
        }
    }

    internal interface ContactListItemAdapterListener {
        fun onItemClick(position: Int)
        fun onStarredActionClick(position: Int)
    }
}
