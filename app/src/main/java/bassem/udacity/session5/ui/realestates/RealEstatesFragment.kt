package bassem.udacity.session5.ui.realestates

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import bassem.udacity.session5.MarsApplication
import bassem.udacity.session5.R
import bassem.udacity.session5.databinding.FragmentRealEstatesBinding
import bassem.udacity.session5.data.model.Type
import bassem.udacity.session5.ui.RealEstateViewModelFactory
import bassem.udacity.session5.ui.RealEstatesViewModel

class RealEstatesFragment : Fragment() {

    private val viewModel: RealEstatesViewModel by viewModels {
        RealEstateViewModelFactory((requireActivity().application as MarsApplication).repository)
    }

    private var _binding: FragmentRealEstatesBinding? = null
    private lateinit var adapter: RealEstateAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRealEstatesBinding.inflate(inflater, container, false)

        adapter = RealEstateAdapter()

        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.allRealEstates.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                Toast.makeText(context, getString(R.string.real_estates_found, it.size), Toast.LENGTH_SHORT).show()
            }

            recyclerviewRealEstates.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_rent_menu -> viewModel.setType(Type.RENT)
            R.id.view_buy_menu -> viewModel.setType(Type.BUY)
            R.id.view_all_menu -> viewModel.setType(Type.ALL)
            R.id.clear_menu -> viewModel.deleteAll()
            R.id.refresh_menu -> viewModel.refreshRealEstates()
        }

        return true
    }

}