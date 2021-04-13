package tw.teng.practice.contact.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import tw.teng.practice.contact.R
import tw.teng.practice.contact.databinding.FragmentContactListBinding
import tw.teng.practice.contact.ui.MainViewModel


class ContactListFragment : Fragment(), ContactListAdapter.ContactListItemAdapterListener {


    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var navController: NavController
    private lateinit var binding: FragmentContactListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_contact_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.btnStarred.setOnClickListener {
            viewModel.selectDisplayState(DisplayState.STARRED.state)
        }
        binding.btnAll.setOnClickListener {
            viewModel.selectDisplayState(DisplayState.ALL.state)
        }
        binding.recyclerView.adapter = ContactListAdapter(this)
        viewModel.contactsListLiveData.observeForever {
            if (it.contacts != null) {
                (binding.recyclerView.adapter as ContactListAdapter).setData(it)

            }
        }

        viewModel.selectDisplayLivedata.observeForever {
            when (it) {
                DisplayState.STARRED.state -> {
                    binding.btnStarred.isSelected = true
                    binding.btnAll.isSelected = false
                }
                else -> {
                    binding.btnStarred.isSelected = false
                    binding.btnAll.isSelected = true
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