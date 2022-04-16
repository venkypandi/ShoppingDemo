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
import android.view.animation.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.venkatesh.shoppingdemo.data.remote.model.Product
import com.venkatesh.shoppingdemo.databinding.HomeFragmentBinding
import com.venkatesh.shoppingdemo.ui.adapter.ProductAdapter
import com.venkatesh.shoppingdemo.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding:HomeFragmentBinding? = null
    private val binding get() = _binding
    var count=0

    lateinit var productAdapter: ProductAdapter
    private val homeViewModel by viewModels<HomeViewModel>()
    var actionbarheight = 0

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

        homeViewModel.productResponse.observe(viewLifecycleOwner)  {
            if(it!=null){
                when(it.status){
                    Status.LOADING->{
                        binding!!.pbHome.visibility = View.VISIBLE
                    }
                    Status.SUCCESS->{
                        if(it.data != null){
                            productAdapter = ProductAdapter(requireActivity(),
                                it.data.products
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
        count++
        binding!!.tvCounter.text = count.toString()
        var image=loadBitmapFromView(view)
        Toast.makeText(requireContext(), "Item Clicked", Toast.LENGTH_SHORT).show()
        // get location of the clicked view
        if (image != null) {
            animateView(view,image)
        }


    }

    private fun animateView(cardView: View, b: Bitmap) {
        binding!!.imgCpy.setImageBitmap(b)
        binding!!.imgCpy.visibility = View.VISIBLE
        val u = IntArray(2)
        binding!!.ivCart.getLocationInWindow(u)
        Log.d( "animateView: ",u.contentToString())
        binding!!.imgCpy.top=cardView.top
        binding!!.imgCpy.left=cardView.left
        binding!!.imgCpy.right=cardView.right
        binding!!.imgCpy.bottom=cardView.bottom

        val animSetXY = AnimatorSet()
        val y: ObjectAnimator = ObjectAnimator.ofFloat(
            binding!!.imgCpy,
            "translationY",
            binding!!.imgCpy.right.toFloat(),
            u[1].toFloat()
        )
        Log.d("animateView:1 ",binding!!.imgCpy.right.toString())
        Log.d("animateView: 2",binding!!.imgCpy.bottom.toString())

        val x: ObjectAnimator = ObjectAnimator.ofFloat(
            binding!!.imgCpy,
            "translationX",
            binding!!.imgCpy.bottom.toFloat(),
            u[0].toFloat()
        )

        val sy = ObjectAnimator.ofFloat(binding!!.imgCpy, "scaleY", 0.8f, 0.1f)
        val sx = ObjectAnimator.ofFloat(binding!!.imgCpy, "scaleX", 0.8f, 0.1f)
        animSetXY.playTogether(x, y, sx, sy)
        animSetXY.duration = 6500
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