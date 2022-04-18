package com.venkatesh.shoppingdemo.ui.homefragment

import android.R
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts
import com.venkatesh.shoppingdemo.data.remote.model.Product
import com.venkatesh.shoppingdemo.databinding.HomeFragmentBinding
import com.venkatesh.shoppingdemo.ui.adapter.CartAdapter
import com.venkatesh.shoppingdemo.ui.adapter.ProductAdapter
import com.venkatesh.shoppingdemo.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding:HomeFragmentBinding? = null
    private val binding get() = _binding
    var count=0

    private lateinit var productAdapter: ProductAdapter
    private val homeViewModel by viewModels<HomeViewModel>()
    var actionbarheight = 0
    var cartList=ArrayList<CartProducts>()
    var checkFlag= false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater,container,false)
        homeViewModel.getProductDetails()

        val tv = TypedValue()
        if (requireContext().theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionbarheight =
                TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }

        binding!!.ivCart.setOnClickListener {
            val directions = HomeFragmentDirections.actionHomeFragmentToCartFragment()
            findNavController().navigate(directions)
        }

        homeViewModel.productDetails.observe(viewLifecycleOwner){
            cartList.clear()
            if(it.isNullOrEmpty()){
                count=0
            }else{
                cartList.addAll(it)

            }
            binding!!.tvCounter.text = cartList.size.toString()
        }


        homeViewModel.productResponse.observe(viewLifecycleOwner)  {
            if(it!=null){
                when(it.status){
                    Status.LOADING->{
                        binding!!.pbHome.visibility = View.VISIBLE
                    }
                    Status.SUCCESS->{
                        if(it.data != null){
                            productAdapter = ProductAdapter(requireActivity(),
                                it.data.products,
                                cartList
                            ) { data: Product,view:View
                                -> addToCart(data,view) }
                            binding?.rvProducts?.adapter = productAdapter

                        }
                        binding!!.pbHome.visibility = View.GONE

                    }
                    Status.ERROR->{
                        binding!!.pbHome.visibility = View.GONE

                    }
                }
            }
        }


        return _binding!!.root
    }

    private fun addToCart(data: Product,view: View) {
        checkFlag=false
        var image=loadBitmapFromView(view)
        // get location of the clicked view
        if (image != null) {
            animateView(view,image)
        }

            cartList.forEach {
                if(it.name == data.name){
                    checkFlag = true
                    var product=it
                    Log.d("addToCart: ",it.toString())
                    product.qty = (it.qty)+1
                    Toast.makeText(requireContext(), "Item already present in the cart", Toast.LENGTH_SHORT).show()
                    homeViewModel.updateCart(product)
                    return
                }
            }

        if(!checkFlag){
            insertProduct(data)
        }


    }


    private fun insertProduct(data:Product){
        homeViewModel.insertProduct(
            products = CartProducts(
                id =0,
                imageUrl = data.imageUrl,
                name = data.name,
                price = data.price,
                rating = data.rating,
                qty = 1
            )

        )
    }

    private fun animateView(cardView: View, b: Bitmap) {
        binding!!.imgCpy.setImageBitmap(b)
        binding!!.imgCpy.visibility = View.VISIBLE
        val u = IntArray(2)
        binding!!.ivCart.getLocationOnScreen(u)
        Log.d( "animateView: ",u.contentToString())
        binding!!.imgCpy.layoutParams = cardView.layoutParams

        val v = IntArray(2)
        cardView.getLocationOnScreen(v)

        val animSetXY = AnimatorSet()
        val x: ObjectAnimator = ObjectAnimator.ofFloat(
            binding!!.imgCpy,
            View.X,
            v[0].toFloat(),
            u[0].toFloat()-cardView.width/2+40f
        )
        val y: ObjectAnimator = ObjectAnimator.ofFloat(
            binding!!.imgCpy,
            View.Y,
            v[1].toFloat(),
            u[1].toFloat()-cardView.width
        )

        val sy = ObjectAnimator.ofFloat(binding!!.imgCpy, "scaleY", 0.8f, 0.1f)
        val sx = ObjectAnimator.ofFloat(binding!!.imgCpy, "scaleX", 0.8f, 0.1f)
        val alphaAnimator = ObjectAnimator.ofFloat(binding!!.imgCpy, View.ALPHA,1f,0f)
        animSetXY.playTogether(x, y, sx, sy,alphaAnimator)
        animSetXY.interpolator = AccelerateDecelerateInterpolator()
        animSetXY.duration = 1000
        animSetXY.start()
    }

    private fun loadBitmapFromView(view: View): Bitmap? {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }


}