package com.venkatesh.shoppingdemo.ui.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.venkatesh.shoppingdemo.R
import com.venkatesh.shoppingdemo.data.remote.model.Product
import com.venkatesh.shoppingdemo.databinding.HomeFragmentBinding
import com.venkatesh.shoppingdemo.ui.adapter.ProductAdapter
import com.venkatesh.shoppingdemo.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding:HomeFragmentBinding? = null
    private val binding get() = _binding

    lateinit var productAdapter: ProductAdapter
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater,container,false)
        homeViewModel.getProductDetails()

        homeViewModel.productResponse.observe(viewLifecycleOwner)  {
            if(it!=null){
                when(it.status){
                    Status.LOADING->{

                    }
                    Status.SUCCESS->{
                        if(it.data != null){
                            productAdapter = ProductAdapter(requireActivity(),
                                it.data.products
                            ) { data: Product -> addToCart(data) }
                            binding?.rvProducts?.adapter = productAdapter

                        }
                    }
                    Status.ERROR->{

                    }
                }
            }
        }
        return _binding!!.root
    }

    private fun addToCart(data: Product) {

    }


}