package tw.teng.practice.contact.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import tw.teng.practice.contact.R
import tw.teng.practice.contact.databinding.FragmentContactDetailBinding
import tw.teng.practice.contact.databinding.FragmentContactListBinding
import tw.teng.practice.contact.ui.MainViewModel


class ContactDetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var binding: FragmentContactDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_contact_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    navController.navigate(R.id.action_contactDetailFragment_to_contactListFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        viewModel.selected.observeForever {
            run {
                Glide.with(view)
                    .load(it.pictureUrl)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(view.findViewById(R.id.img_avatar))
                view.findViewById<TextView>(R.id.tv_name).text = it.name
                view.findViewById<TextView>(R.id.tv_company).text = it.company?.name
                view.findViewById<TextView>(R.id.tv_bs).text = it.company?.bs
                view.findViewById<TextView>(R.id.tv_email).text = it.email
            }
        }

       binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_contactDetailFragment_to_contactListFragment)
        }
    }
}