package tw.teng.practice.contact.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.teng.practice.contact.R
import tw.teng.practice.contact.resource.network.model.APIContacts
import tw.teng.practice.contact.ui.MainViewModel
import tw.teng.practice.contact.ui.contact.ContactListAdapter.ContactListItemAdapterListener
import java.util.*

class ContactListFragment : Fragment(), ContactListItemAdapterListener {

    private val viewModel: MainViewModel by activityViewModels()
    lateinit var navController: NavController
    lateinit var btnStarred: AppCompatButton
    lateinit var btnAll: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val viewHeader = LayoutInflater.from(context)
            .inflate(R.layout.header_contact_list, (view.parent as ViewGroup), false)
        btnStarred = viewHeader.findViewById(R.id.btn_starred)
        btnStarred.setOnClickListener {
            viewModel.selectDisplayState(DisplayState.STARRED.state)
        }
        btnAll = viewHeader.findViewById(R.id.btn_all)
        btnAll.setOnClickListener {
            viewModel.selectDisplayState(DisplayState.ALL.state)
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity, (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        recyclerView.adapter = ContactListAdapter(
            ArrayList<APIContacts.Contacts>(),
            this
        )
        (recyclerView.adapter as ContactListAdapter).setHeaderView(viewHeader)

        viewModel.contactsListLiveData.observeForever {
            if (it.contacts != null) {
                recyclerView.adapter = ContactListAdapter(
                    it.contacts as ArrayList<APIContacts.Contacts>,
                    this
                )
                (recyclerView.adapter as ContactListAdapter).setHeaderView(viewHeader)
                (recyclerView.adapter as ContactListAdapter).notifyDataSetChanged()
            }
        }

        viewModel.selectDisplayLivedata.observeForever {
            when (it) {
                DisplayState.STARRED.state -> {
                    btnStarred.isSelected = true
                    btnAll.isSelected = false
                }
                else -> {
                    btnStarred.isSelected = false
                    btnAll.isSelected = true
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        navController.navigate(R.id.action_contactListFragment_to_contactDetailFragment)
        viewModel.select(position)
    }

    override fun onStarredActionClick(position: Int) {
        viewModel.clickStarred(position)
    }
}