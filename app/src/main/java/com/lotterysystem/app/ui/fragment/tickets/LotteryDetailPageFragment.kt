package com.lotterysystem.app.ui.fragment.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.lotterysystem.app.R
import com.lotterysystem.app.base.BaseFragment
import com.lotterysystem.app.databinding.FragmentLotteryDetailPageBinding
import com.lotterysystem.app.datastore.helper.PreferencesHelper
import com.lotterysystem.app.ticketsystem.LotteryTicketRules
import com.lotterysystem.app.ui.fragment.home.response.Ticket
import com.lotterysystem.app.ui.fragment.tickets.response.TicketDetailResponse
import com.lotterysystem.app.ui.fragment.tickets.viewmodel.TicketDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LotteryDetailPageFragment : BaseFragment() {

    private lateinit var rendererJob: Job
    private lateinit var responseJob: Job

    // read from bundle
    private lateinit var ticketParam: Ticket

    lateinit var binding: FragmentLotteryDetailPageBinding

    val viewModel by activityViewModels<TicketDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLotteryDetailPageBinding.inflate(inflater, container, false)

        ticketParam = LotteryDetailPageFragmentArgs.fromBundle(requireArguments()).ticketParam


        subscribeCallbackFromSuccessStatus()

        getScoreOnceFromDataSource()

        getTicketsFromServer()



        return binding.root
    }

    /* Get score once from the data source
    * ... and cancel that job after completion
    * ... so that it will stop listen for future
    * */
    private fun getScoreOnceFromDataSource() {
        var score = PreferencesHelper.getUserScore(requireContext())
        binding.txtTotalWins.text =
            "${requireContext().getString(R.string.your_total_score)} $score"
    }


    /*  fetch tickets from server
    *... using viewmodel
    * */
    private fun getTicketsFromServer() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTicketById(tickedId = "${ticketParam.id}")
        }
    }


    /** start listening callbacks
     *
     */
    private fun subscribeCallbackFromSuccessStatus() {
        rendererJob = lifecycleScope.launchWhenStarted {
            getResponse().observe(requireActivity(), Observer {
                renderSuccessResponse(it)
            })
        }

        responseJob = lifecycleScope.launchWhenStarted {
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
            is TicketDetailResponse -> {
                setData(response)
            }
        }
    }

    private fun setData(response: TicketDetailResponse) {
        binding.txtNumLine1.text = "${response.numbers[0]}"
        binding.txtNumLine2.text = "${response.numbers[1]}"
        binding.txtNumLine3.text = "${response.numbers[2]}"

        var oldScore = PreferencesHelper.getUserScore(requireContext())
        var newScore = oldScore + LotteryTicketRules.getScoreFromLine(response.numbers)
        binding.txtTotalWins.text =
            "${requireContext().getString(R.string.your_total_score)} $newScore"

        PreferencesHelper.setUserScore(newScore, requireContext())


    }

    // cancel jobs on pause
    override fun onPause() {
        super.onPause()
        rendererJob.cancel()
        responseJob.cancel()
    }

}