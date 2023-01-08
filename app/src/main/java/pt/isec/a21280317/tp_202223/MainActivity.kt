package pt.isec.a21280317.tp_202223

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import pt.isec.a21280317.tp_202223.databinding.ActivityMainBinding
import pt.isec.a21280317.tp_202223.databinding.ActivityMainLandscapeBinding


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingLandscapeBinding: ActivityMainLandscapeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bindingLandscapeBinding = ActivityMainLandscapeBinding.inflate(layoutInflater)
            setContentView(R.layout.activity_main_landscape)

            bindingLandscapeBinding.btnSP.setOnClickListener {
                val intent = Intent(this, ConfigSPGameActivity::class.java)
                startActivity(intent)
            }

            bindingLandscapeBinding.btnMP.setOnClickListener {
                val intent = Intent(this, ConfigMPGameActivity::class.java)
                startActivity(intent)
            }

            bindingLandscapeBinding.btnInfo.setOnClickListener {
                val intent = Intent(this, ConfigUserActivity::class.java)
                startActivity(intent)
            }
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnSP.setOnClickListener {
                val intent = Intent(this, ConfigSPGameActivity::class.java)
                startActivity(intent)
            }

            binding.btnMP.setOnClickListener {
                val intent = Intent(this, ConfigMPGameActivity::class.java)
                startActivity(intent)
            }

            binding.btnInfo.setOnClickListener {
                val intent = Intent(this, ConfigUserActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_credits, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent = Intent(this, ConfigCreditsActivity::class.java)
        startActivity(intent)

        return super.onOptionsItemSelected(item)
    }


}