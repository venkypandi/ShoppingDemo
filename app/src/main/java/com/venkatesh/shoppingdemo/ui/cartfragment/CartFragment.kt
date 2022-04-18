package com.venkatesh.shoppingdemo.ui.cartfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
    var cartList=ArrayList<CartProducts>()
    var sum:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartFragmentBinding.inflate(inflater,container,false)

        cartViewModel.productDetails.observe(viewLifecycleOwner){
            sum=0
            cartList.clear()
            if(it.isNullOrEmpty()){
                binding!!.rvCart.visibility = View.GONE
                binding!!.ivEmpty.visibility = View.VISIBLE
                binding!!.btnContinue.visibility = View.VISIBLE
            }else{
                binding!!.rvCart.visibility = View.VISIBLE
                binding!!.ivEmpty.visibility = View.GONE
                binding!!.btnContinue.visibility = View.GONE
                cartList.addAll(it)
                cartAdapter = CartAdapter(
                    requireContext(),
                    it
                ){cartProducts,num -> updateQuantity(cartProducts,num) }
                binding!!.rvCart.adapter = cartAdapter
            }

            sum=cartList.sumOf { it.price.toInt()*it.qty }
            binding!!.tvPrice.text = "₹"+sum.toString()
        }


        binding!!.btnContinue.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding!!.btnCheckout.setOnClickListener {
            if(cartList.size==0){
                Toast.makeText(requireContext(), "Please add any item in the cart to proceed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val directions = CartFragmentDirections.actionCartFragmentToCheckoutFragment()
            findNavController().navigate(directions)
        }
        return _binding!!.root
    }

    private fun updateQuantity(cartProducts: CartProducts,num:Int) {
        when(num){
            0->{
                cartProducts.qty-=1
                cartViewModel.updateCart(cartProducts)
            }
            1->{
                cartProducts.qty+=1
                cartViewModel.updateCart(cartProducts)
            }
            -1->{
                cartViewModel.deleteProducts(cartProducts)
                cartAdapter.notifyDataSetChanged()
            }
        }
    }


}