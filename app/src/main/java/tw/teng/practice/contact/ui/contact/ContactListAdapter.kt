package tw.teng.practice.contact.ui.contact

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import tw.teng.practice.contact.R
import tw.teng.practice.contact.resource.network.model.APIContacts
import java.util.*

class ContactListAdapter internal constructor(
    private val mListener: ContactListItemAdapterListener
) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    internal var selectedPosition = -1
    private var itemList: ArrayList<APIContacts.Contacts>

    init {
        itemList = ArrayList<APIContacts.Contacts>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactViewHolder(
            inflater.inflate(R.layout.item_contact_list, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ContactViewHolder, position: Int) {
        viewHolder.bindData(itemList, position)
    }

    fun setData(it: APIContacts?) {
        itemList = it?.contacts as ArrayList<APIContacts.Contacts>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        private val imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        private val contentLayout: View = itemView.findViewById(R.id.item_user_content)
        private val imgIconStar: ImageView = itemView.findViewById(R.id.img_ic_star)
        private val imgStarred: ImageView = itemView.findViewById(R.id.img_starred)

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
            Glide.with(itemView.context)
                .load(item.pictureUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any, target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imgAvatar)

            contentLayout.setOnClickListener {
                selectedPosition = position
                mListener.onItemClick(position)
            }
            imgStarred.setOnClickListener {
                mListener.onStarredActionClick(position)
            }
        }
    }

    internal interface ContactListItemAdapterListener {
        fun onItemClick(position: Int)
        fun onStarredActionClick(position: Int)
    }

}
