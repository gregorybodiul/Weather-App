package com.exemple.myweatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.navigation.fragment.findNavController
import com.exemple.myweatherapp.R
import com.exemple.myweatherapp.databinding.FragmentSplashBinding
import com.exemple.myweatherapp.util.Account
import kotlinx.coroutines.*

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        Account.getData(this.requireContext())

        CoroutineScope(Dispatchers.Default).launch {
            delay(500)
            withContext(Dispatchers.Main) {
                val animatePosText = animatePosText(binding.textSplash, -2000f, 0f)
                animatePosText.start()
                animatePosText.addEndListener { _, canceled, _, _ ->
                    if (!canceled) {
                        goToApp()
                    }
                }
            }
        }

        return binding.root
    }

    private fun goToApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            val bundle = Bundle()
            bundle.putString("city", Account.city)
            findNavController().navigate(R.id.action_splashFragment_to_weatherFragment, bundle)
        }, 1000)
    }

    @SuppressLint("RestrictedApi")
    private fun animatePosText(view: View, startPos: Float, finalPos: Float): SpringAnimation {
        view.visibility = View.VISIBLE
        val spring = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y, finalPos)
        spring.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        spring.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
        spring.setStartValue(startPos)
        return spring
    }
}