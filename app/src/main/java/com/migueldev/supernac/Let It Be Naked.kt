package com.migueldev.supernac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.migueldev.supernac.databinding.ActivityLetItBeNakedBinding


class Let_It_Be_Naked : AppCompatActivity() {
    private lateinit var binding: ActivityLetItBeNakedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetItBeNakedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btClique.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                binding.tvNaked.text = "When I find myself in times of trouble \n Mother Mary comes to me \n Speaking words of wisdom\n Let it be\nAnd in my hour of darkness\n She is standing right in front of me\nSpeaking words of wisdom\nLet it be\nLet is be, let it be\nLet it be, let it be\nWhisper words of wisdom\nLet it be\nAnd when the brokenhearted people\nLiving in the world agree\nThere will be an answer\nLet it be\nFor though they may be parted\nThere is still a chance that they will see\nThere will be an answer\nLet it be\nLet is be, let it be\nLet it be, let it be\nYeah, there will be an answer\nLet it be\nLet is be, let it be\nLet it be, let it be\nWhisper words of wisdom\nLet it be\nLet is be, let it be\nLet it be, yeah, let it be\nWhisper words of wisdom\nLet it be\nAnd when the night is cloudy\nThere is still a light that shines on me\nShine on 'til tomorrow\nLet it be\nI wake up to the sound of music\nMother Mary comes to me\nSpeaking words of wisdom\nLet it be, yeah\nLet it be, let it be\nLet it be, yeah, let it be\nOh, there will be an answer\nLet it be\nLet it be, let it be\nLet it be, yeah, let it be\nWhisper words of wisdom\nLet it be"
            }
            })
        binding.tvNaked.movementMethod = ScrollingMovementMethod.getInstance()


    }
}
