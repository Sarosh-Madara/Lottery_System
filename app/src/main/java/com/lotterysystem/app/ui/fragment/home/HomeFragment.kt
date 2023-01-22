package com.lotterysystem.app.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.lotterysystem.app.ui.activity.MainActivity
import com.lotterysystem.app.databinding.FragmentHomeBinding
import com.lotterysystem.app.ui.adapter.TicketsAdapter
import com.lotterysystem.app.ui.fragment.home.response.GetTicketsResponse
import com.lotterysystem.app.ui.fragment.home.viewmodel.HomeViewModel
import com.lotterysystem.app.utils.visible
import com.lotterysystem.app.base.BaseFragment
import com.lotterysystem.app.utils.gone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    /** viewModel for home
     * ... screen handling
     * ... states and store
     * ... its values
     */
    val viewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        subscribeCallbackFromSuccessStatus()

        getTicketsFromServer()


        return binding.root
    }


    /*  fetch tickets from server
    *... using viewmodel
    * */
    private fun getTicketsFromServer() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTickets()
        }
    }


    /** start listening callbacks
     *
     */
    private fun subscribeCallbackFromSuccessStatus() {
        getResponse().observe(requireActivity(), Observer {
            renderSuccessResponse(it)
        })

        lifecycleScope.launchWhenCreated {
            viewModel.userData.collect {
                consumeApiResponse(it)
            }
        }
    }

    /** fun renders the response coming from the
     * ... server and parse it into the respective
     * ... object
     * */
    private fun renderSuccessResponse(response: Any?) {
        when (response) {
            is GetTicketsResponse -> {
                updateData(response)
            }
        }
    }

    /** updates the data from the api
     * ... to the respective conditions
     * ... and layout
     * */
    private fun updateData(response: GetTicketsResponse) {
        if (response.tickets.isEmpty()) {

            binding.rvTickets.gone()
            binding.layoutLotteryTickets.visible()

        } else {

            binding.layoutLotteryTickets.gone()
            binding.rvTickets.visible()

            var adapter = TicketsAdapter(response.tickets, requireContext()) {
                (activity as MainActivity).navigate(
                    HomeFragmentDirections.actionHomeFragmentToLotteryDetailPageFragment(it)
                )
            }
            binding.rvTickets.adapter = adapter
        }
    }

}