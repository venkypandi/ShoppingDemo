package com.venkatesh.shoppingdemo.ui.checkoutfragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.venkatesh.shoppingdemo.databinding.FragmentCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding

    private val viewModel: CheckoutViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater,container,false)
        val timer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding!!.apply {
                    ivConfirm.visibility=View.GONE
                    tvDesc.visibility=View.GONE
                    btnContinue.visibility=View.GONE
                    pbHome.visibility = View.VISIBLE
                    pbHome.progress = 30-(millisUntilFinished/1000).toInt()
                    tvVerify.visibility = View.VISIBLE
                    tvVerify.text="Please complete your payment within ${millisUntilFinished/1000} seconds"
                }
            }
            override fun onFinish() {
                binding!!.apply {
                    ivConfirm.visibility=View.VISIBLE
                    tvDesc.visibility=View.VISIBLE
                    btnContinue.visibility=View.VISIBLE
                    pbHome.visibility = View.GONE
                    pbHome.progress = 30
                    tvVerify.visibility = View.GONE
                }

            }
        }
        timer.start()

        viewModel.deleteAllProducts()

        binding!!.btnContinue.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return _binding!!.root
    }

}