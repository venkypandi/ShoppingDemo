package com.venkatesh.shoppingdemo.ui.cartfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.venkatesh.shoppingdemo.R
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts
import com.venkatesh.shoppingdemo.databinding.CartFragmentBinding
import com.venkatesh.shoppingdemo.databinding.HomeFragmentBinding
import com.venkatesh.shoppingdemo.ui.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel:CartViewModel by viewModels()
    private var _binding: CartFragmentBinding? = null
    private val binding get() = _binding

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartFragmentBinding.inflate(inflater,container,false)

        cartViewModel.productDetails.observe(viewLifecycleOwner){
            if(it.isNullOrEmpty()){
                binding!!.rvCart.visibility = View.GONE
                binding!!.ivEmpty.visibility = View.VISIBLE
                binding!!.btnContinue.visibility = View.VISIBLE
            }else{
                binding!!.rvCart.visibility = View.VISIBLE
                binding!!.ivEmpty.visibility = View.GONE
                binding!!.btnContinue.visibility = View.GONE

                cartAdapter = CartAdapter(
                    requireContext(),
                    it
                ){cartProducts -> updateQuantity(cartProducts) }
                binding!!.rvCart.adapter = cartAdapter
            }
        }

        binding!!.btnContinue.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return _binding!!.root
    }

    private fun updateQuantity(cartProducts: CartProducts) {


    }


}