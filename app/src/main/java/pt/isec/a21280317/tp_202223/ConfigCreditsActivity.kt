package pt.isec.a21280317.tp_202223

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigCreditsBinding
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigCreditsLandscapeBinding
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigSpgameBinding
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigUserLandscapeBinding

class ConfigCreditsActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityConfigCreditsBinding
    private lateinit var bindingLandscapeBinding: ActivityConfigCreditsLandscapeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bindingLandscapeBinding = ActivityConfigCreditsLandscapeBinding.inflate(layoutInflater)
            setContentView(bindingLandscapeBinding.root)

        } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding = ActivityConfigCreditsBinding.inflate(layoutInflater)
            setContentView(binding.root)

        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}