package bassem.udacity.session5.ui.real_estates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import bassem.udacity.session5.MarsApplication
import bassem.udacity.session5.R
import bassem.udacity.session5.data.model.Type
import bassem.udacity.session5.databinding.FragmentRealEstatesBinding

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

        addMenu()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.realEstates.observe(viewLifecycleOwner) {
                adapter.submitList(it)
//                Toast.makeText(context, getString(R.string.real_estates_found, it.size), Toast.LENGTH_SHORT).show()
            }

            recyclerviewRealEstates.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
                menuInflater.inflate(R.menu.menu_main, menu)

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.view_rent_menu -> viewModel.setType(Type.RENT)
                    R.id.view_buy_menu -> viewModel.setType(Type.BUY)
                    R.id.view_all_menu -> viewModel.setType(Type.ALL)
                    R.id.clear_menu -> viewModel.deleteAll()
                    R.id.refresh_menu -> viewModel.refreshRealEstates()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}