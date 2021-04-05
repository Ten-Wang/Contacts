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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.teng.practice.contact.R
import tw.teng.practice.contact.ui.MainViewModel


class ContactListFragment : Fragment(), ContactListAdapter.ContactListItemAdapterListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var btnStarred: AppCompatButton
    private lateinit var btnAll: AppCompatButton

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

        btnStarred = view.findViewById(R.id.btn_starred)
        btnStarred.setOnClickListener {
            viewModel.selectDisplayState(DisplayState.STARRED.state)
        }
        btnAll = view.findViewById(R.id.btn_all)
        btnAll.setOnClickListener {
            viewModel.selectDisplayState(DisplayState.ALL.state)
        }

        recyclerView.adapter = ContactListAdapter(this)
        viewModel.contactsListLiveData.observeForever {
            if (it.contacts != null) {
                (recyclerView.adapter as ContactListAdapter).setData(it)

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