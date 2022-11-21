package com.proximabeta.game.cont

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.proximabeta.game.cont.R
import com.proximabeta.game.cont.databinding.ActivityBallsBinding


class BallsActivity : AppCompatActivity() {

    private var score = 0
    private var level = 1

    private var streamMaxVolume = 0
    private var currentVol = 0
    private var newVol = 0

    private var handler: Handler? = null
    private var popSound: MediaPlayer? = null
    private var lostSound: MediaPlayer? = null
    private var audioManager: AudioManager? = null

    private var posBall0x = 0f
    private var posBall0y = 0f
    private var posBall1x = 0f
    private var posBall1y = 0f
    private var posBall2x = 0f
    private var posBall2y = 0f
    private var posBall3x = 0f
    private var posBall3y = 0f
    private var posBall4x = 0f
    private var posBall4y = 0f
    private var posBall5x = 0f
    private var posBall5y = 0f

    private var redBall0x = 0f
    private var redBall0y = 0f
    private var redBall1x = 0f
    private var redBall1y = 0f

    private lateinit var binding: ActivityBallsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityBallsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        streamMaxVolume = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        currentVol = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
        newVol = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)

        posBall0x = binding.ball0.translationX
        posBall0y = binding.ball0.translationY

        posBall1x = binding.ball1.translationX
        posBall1y = binding.ball1.translationY

        posBall2x = binding.ball2.translationX
        posBall2y = binding.ball2.translationY

        posBall3x = binding.ball3.translationX
        posBall3y = binding.ball3.translationY

        posBall4x = binding.ball4.translationX
        posBall4y = binding.ball4.translationY

        posBall5x = binding.ball5.translationX
        posBall5y = binding.ball5.translationY

        redBall0x = binding.redBall0.translationX
        redBall0y = binding.redBall0.translationY

        redBall1x = binding.redBall1.translationX
        redBall1y = binding.redBall1.translationY

        handler = Handler(Looper.getMainLooper())
    }

    fun ballClicked(view: View) {
        if (popSound != null) {
            popSound!!.stop()
            popSound!!.release()
            popSound = null
        }

        popSound = MediaPlayer.create(this, R.raw.pop)
        popSound!!.start()

        view.visibility = View.INVISIBLE
        score++
        popSound!!.start()
        checkScoreLevel()
    }

    fun redBallClicked(view: View?) {
        if (lostSound != null) {
            lostSound!!.stop()
            lostSound!!.release()
            lostSound = null
        }
        lostSound = MediaPlayer.create(this, R.raw.lost)
        lostSound!!.start()

        Toast.makeText(this, "RED BALL CLICKED\nYOU LOOSE START AGAIN", Toast.LENGTH_LONG).show()
        handler!!.postDelayed({ pLevel1() }, 1000)
    }

    @SuppressLint("SetTextI18n")
    private fun checkScoreLevel() {
        binding.scoreTxt.text = "Score = $score"
        when (score) {
            1 -> {
                resetViews()
                handler!!.postDelayed({ pLevel2() }, 1000)
            }
            3 -> {
                resetViews()
                handler!!.postDelayed({ pLevel3() }, 1000)
            }
            6 -> {
                resetViews()
                handler!!.postDelayed({ pLevel4() }, 1000)
            }
            9 -> {
                resetViews()
                handler!!.postDelayed({ pLevel5() }, 1000)
            }
            11 -> {
                resetViews()
                handler!!.postDelayed({ pLevel6() }, 2000)
            }
            15 -> {
                resetViews()
                handler!!.postDelayed({ pLevel7() }, 2000)
            }
            18 -> {
                resetViews()
                handler!!.postDelayed({ pLevel8() }, 2500)
            }
            23 -> {
                resetViews()
                handler!!.postDelayed({ pLevel9() }, 2500)
            }
            29 -> {
                resetViews()
                handler!!.postDelayed({ pLevel10() }, 2000)
            }
            35 -> {
                resetViews()
                binding.levelTxt.text = "Congrats, You won"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel1() {
        resetViews()
        binding.levelTxt.text = "Level 1"
        binding.scoreTxt.text = "Score = 0"

        score = 0
        level = 1

        binding.ball0.visibility = View.VISIBLE
        binding.ball0.animate().translationXBy(1200f).duration = 4000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel2() {
        resetViews()
        score = 1
        level = 2

        binding.levelTxt.text = "Level 2"
        binding.scoreTxt.text = "Score = $score"

        binding.ball0.visibility = View.VISIBLE
        binding.ball0.animate().translationXBy(1500f).duration = 4000
        binding.ball3.visibility = View.VISIBLE
        binding.ball3.animate().translationXBy(-1500f).duration = 4000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel3() {
        resetViews()
        score = 3
        level = 3

        binding.levelTxt.text = "Level 3"
        binding.scoreTxt.text = "Score = $score"

        binding.ball0.visibility = View.VISIBLE
        binding.ball0.animate().translationXBy(1500f).translationYBy(1000f).duration = 4000
        binding.ball3.visibility = View.VISIBLE
        binding.ball3.animate().translationXBy(-1500f).duration = 3000
        binding.ball2.visibility = View.VISIBLE
        binding.ball2.animate().translationXBy(1500f).duration = 4000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel4() {
        resetViews()
        score = 6
        level = 4

        binding.levelTxt.text = "Level 4"
        binding.scoreTxt.text = "Score = $score"

        binding.ball0.visibility = View.VISIBLE
        binding.ball0.animate().translationXBy(1500f).translationYBy(1000f).duration = 4000
        binding.ball1.visibility = View.VISIBLE
        binding.ball1.animate().translationXBy(1500f).translationYBy(1000f).duration = 3500
        binding.ball2.visibility = View.VISIBLE
        binding.ball2.animate().translationXBy(1500f).duration = 4000
        binding.redBall0.visibility = View.VISIBLE
        binding.redBall0.animate().translationXBy(1500f).translationYBy(1000f).duration = 3000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel5() {
        resetViews()
        score = 9
        level = 5

        binding.levelTxt.text = "Level 5"
        binding.scoreTxt.text = "Score = $score"

        binding.ball3.visibility = View.VISIBLE
        binding.ball3.animate().translationXBy(-1500f).duration = 3000
        binding.ball2.visibility = View.VISIBLE
        binding.ball2.animate().translationXBy(1500f).duration = 4000
        binding.redBall0.visibility = View.VISIBLE
        binding.redBall0.animate().translationXBy(1500f).translationYBy(-1000f).duration = 4000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel6() {
        resetViews()
        score = 11
        level = 6

        binding.levelTxt.text = "Level 6"
        binding.scoreTxt.text = "Score = $score"

        binding.ball0.visibility = View.VISIBLE
        binding.ball0.animate().translationXBy(1500f).duration = 4000
        binding.ball3.visibility = View.VISIBLE
        binding.ball3.animate().translationXBy(-1500f).duration = 4000
        binding.ball2.visibility = View.VISIBLE
        binding.ball2.animate().translationXBy(1500f).duration = 4000
        binding.redBall0.visibility = View.VISIBLE
        binding.redBall0.animate().translationXBy(1500f).translationYBy(-1000f).duration = 4000
        binding.ball5.visibility = View.VISIBLE
        binding.ball5.animate().translationYBy(-1500f).duration = 4000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel7() {
        resetViews()
        score = 15
        level = 7

        binding.levelTxt.text = "Level 7"
        binding.scoreTxt.text = "Score = $score"

        binding.ball3.visibility = View.VISIBLE
        binding.ball3.animate().translationXBy(-1500f).duration = 4000
        binding.ball2.visibility = View.VISIBLE
        binding.ball2.animate().translationXBy(1500f).duration = 4000
        binding.redBall0.visibility = View.VISIBLE
        binding.redBall0.animate().translationXBy(1500f).translationYBy(-1000f).duration = 4000
        binding.redBall1.visibility = View.VISIBLE
        binding.redBall1.animate().translationXBy(-1500f).translationYBy(1000f).duration = 4000
        binding.ball5.visibility = View.VISIBLE
        binding.ball5.animate().translationYBy(-1500f).duration = 4000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel8() {
        resetViews()
        score = 18
        level = 8

        binding.levelTxt.text = "Level 8"
        binding.scoreTxt.text = "Score = $score"

        binding.ball0.visibility = View.VISIBLE
        binding.ball0.animate().translationYBy(1000f).duration = 4000
        binding.ball1.visibility = View.VISIBLE
        binding.ball1.animate().translationXBy(1500f).translationYBy(1000f).duration = 3500
        binding.ball3.visibility = View.VISIBLE
        binding.ball3.animate().translationXBy(-1500f).duration = 4000
        binding.ball2.visibility = View.VISIBLE
        binding.ball2.animate().translationXBy(1500f).duration = 4000
        binding.redBall1.visibility = View.VISIBLE
        binding.redBall1.animate().translationXBy(-1500f).translationYBy(1000f).duration = 4000
        binding.ball5.visibility = View.VISIBLE
        binding.ball5.animate().translationYBy(-1500f).duration = 4000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel9() {
        resetViews()
        score = 23
        level = 9

        binding.levelTxt.text = "Level 9"
        binding.scoreTxt.text = "Score = $score"

        binding.ball0.visibility = View.VISIBLE
        binding.ball0.animate().translationXBy(1500f).duration = 4000
        binding.ball1.visibility = View.VISIBLE
        binding.ball1.animate().translationXBy(1500f).duration = 4000
        binding.ball2.visibility = View.VISIBLE
        binding.ball2.animate().translationXBy(1500f).duration = 4000
        binding.redBall0.visibility = View.VISIBLE
        binding.redBall0.animate().translationXBy(1500f).duration = 4000
        binding.redBall1.visibility = View.VISIBLE
        binding.redBall1.animate().translationXBy(-1500f).duration = 4000
        binding.redBall1.visibility = View.VISIBLE
        binding.redBall1.animate().translationXBy(-1500f).duration = 4000
        binding.ball3.visibility = View.VISIBLE
        binding.ball3.animate().translationXBy(-1500f).duration = 4000
        binding.ball4.visibility = View.VISIBLE
        binding.ball4.animate().translationXBy(-1500f).duration = 4000
        binding.ball5.visibility = View.VISIBLE
        binding.ball5.animate().translationXBy(-1500f).duration = 4000
    }

    @SuppressLint("SetTextI18n")
    private fun pLevel10() {
        resetViews()
        score = 29
        level = 10

        binding.levelTxt.text = "Level 10"
        binding.scoreTxt.text = "Score = $score"

        binding.ball0.visibility = View.VISIBLE
        binding.ball0.animate().translationXBy(1500f).translationYBy(1000f).duration = 4000
        binding.ball1.visibility = View.VISIBLE
        binding.ball1.animate().translationXBy(1500f).duration = 4000
        binding.ball2.visibility = View.VISIBLE
        binding.ball2.animate().translationXBy(1500f).duration = 4000
        binding.redBall0.visibility = View.VISIBLE
        binding.redBall0.animate().translationXBy(1500f).translationYBy(-1000f).duration = 3000
        binding.redBall1.visibility = View.VISIBLE
        binding.redBall1.animate().translationXBy(-1500f).duration = 3000
        binding.redBall1.visibility = View.VISIBLE
        binding.redBall1.animate().translationXBy(-1500f).duration = 4000
        binding.ball3.visibility = View.VISIBLE
        binding.ball3.animate().translationXBy(-1500f).duration = 4000
        binding.ball4.visibility = View.VISIBLE
        binding.ball4.animate().translationXBy(-1500f).duration = 4000
        binding.ball5.visibility = View.VISIBLE
        binding.ball5.animate().translationXBy(-1500f).translationYBy(-1000f).duration = 4000
    }

    private fun resetViews() {
        binding.ball0.clearAnimation()
        binding.ball0.translationX = posBall0x
        binding.ball0.translationY = posBall0y
        binding.ball0.visibility = View.INVISIBLE

        binding.ball1.clearAnimation()
        binding.ball1.translationX = posBall1x
        binding.ball1.translationY = posBall1y
        binding.ball1.visibility = View.INVISIBLE

        binding.ball2.clearAnimation()
        binding.ball2.translationX = posBall2x
        binding.ball2.translationY = posBall2y
        binding.ball2.visibility = View.INVISIBLE

        binding.ball3.clearAnimation()
        binding.ball3.translationX = posBall3x
        binding.ball3.translationY = posBall3y
        binding.ball3.visibility = View.INVISIBLE

        binding.ball4.clearAnimation()
        binding.ball4.translationX = posBall4x
        binding.ball4.translationY = posBall4y
        binding.ball4.visibility = View.INVISIBLE

        binding.ball5.clearAnimation()
        binding.ball5.translationX = posBall5x
        binding.ball5.translationY = posBall5y
        binding.ball5.visibility = View.INVISIBLE

        binding.redBall0.clearAnimation()
        binding.redBall0.translationX = redBall0x
        binding.redBall0.translationY = redBall0y
        binding.redBall0.visibility = View.INVISIBLE

        binding.redBall1.clearAnimation()
        binding.redBall1.translationX = redBall1x
        binding.redBall1.translationY = redBall1y
        binding.redBall1.visibility = View.INVISIBLE
    }

    fun restartGame(view: View?) {
        resetViews()
        pLevel1()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val action = event.action
        return when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (action == KeyEvent.ACTION_DOWN) {
                    nextLevel(level)
                    level++
                }
                true
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (action == KeyEvent.ACTION_UP) {
                    nextLevel(level)
                    level++
                }
                true
            }
            else -> super.dispatchKeyEvent(event)
        }
    }

    fun restartLevel(view: View?) {
        when (level) {
            1 -> {
                resetViews()
                pLevel1()
            }
            2 -> {
                resetViews()
                pLevel2()
            }
            3 -> {
                resetViews()
                pLevel3()
            }
            4 -> {
                resetViews()
                pLevel4()
            }
            5 -> {
                resetViews()
                pLevel5()
            }
            6 -> {
                resetViews()
                pLevel6()
            }
            7 -> {
                resetViews()
                pLevel7()
            }
            8 -> {
                resetViews()
                pLevel8()
            }
            9 -> {
                resetViews()
                pLevel9()
            }
            10 -> {
                resetViews()
                pLevel10()
            }
        }
    }

    private fun nextLevel(l: Int) {
        when (l) {
            1 -> {
                resetViews()
                pLevel1()
            }
            2 -> {
                resetViews()
                pLevel2()
            }
            3 -> {
                resetViews()
                pLevel3()
            }
            4 -> {
                resetViews()
                pLevel4()
            }
            5 -> {
                resetViews()
                pLevel5()
            }
            6 -> {
                resetViews()
                pLevel6()
            }
            7 -> {
                resetViews()
                pLevel7()
            }
            8 -> {
                resetViews()
                pLevel8()
            }
            9 -> {
                resetViews()
                pLevel9()
            }
            10 -> {
                resetViews()
                pLevel10()
            }
        }
    }
}