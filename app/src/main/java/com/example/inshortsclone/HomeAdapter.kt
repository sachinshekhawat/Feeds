package com.example.inshortsclone

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(val context:Context) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var feedList = mutableListOf<HomeItem>()
    private var isplaying = false
    private val handler = Handler()
    private lateinit var player: ExoPlayer


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view2, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
//        holder.bind(feedList[position])

        val videoUrl:String  = feedList[position].link
        val user = "shorts by :"
        holder.sign.text = feedList[position].jName
        "$user${" " +  holder.sign.text + "(me)"}".also { holder.sign.text = it }
        holder.textView.text = feedList[position].description

//        holder.videoView.foreground = null
        // Set the video URL to VideoView

//        try {
            // Set the video URL to VideoView
//            holder.videoView.setVideoURI(Uri.parse(videoUrl))

        player = ExoPlayer.Builder(context).build()

        holder.playerView.player = player

        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
//        Toast.makeText(context, videoUrl, Toast.LENGTH_SHORT).show()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.pause()

//            val layoutParams = holder.videoView.layoutParams
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//            layoutParams.height = context.resources.getDimensionPixelSize(R.dimen.video_height) // Adjust the height as needed
//            holder.videoView.layoutParams = layoutParams

//            holder.playbutton.visibility = View.VISIBLE

//            holder.videoView.setOnClickListener{
//                togglePlayback(holder)
//            }

//            holder.playbutton.setOnClickListener{
//                playVideo(holder)
//            }

//            holder.pauseButton.setOnClickListener{
//                pauseVideo(holder)
//            }


//            holder.card2.setOnClickListener{
//                if(isplaying){
//                    holder.playbutton.visibility = View.GONE
//                    holder.pauseButton.visibility = View.GONE
//                }else{
//                    holder.playbutton.visibility = View.VISIBLE
//                    holder.pauseButton.visibility = View.GONE
//                }
//            }
//
//            setUpSeekBar(holder)
//            holder.videoView.setOnPreparedListener { mp ->
//                holder.videoSeekBar.max = mp.duration
//                handler.post(updateSeekBar(holder))
//            }
//            holder.videoView.setOnCompletionListener { mp ->
//
//            }
//            holder.videoView.setOnErrorListener { mp, what, extra ->
//                Log.e("VideoPlaybackError", "Error occurred during video playback: $what")
//                // Return true if the error is handled, false otherwise
//                false
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            // Handle any exceptions that occur during URI parsing or playback setup
//        }
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    fun setData(data: List<HomeItem>) {
        feedList = data.toMutableList()
        notifyDataSetChanged()
    }

//    private fun togglePlayback(holder: HomeViewHolder) {
//        if (isplaying) {
//            pauseVideo(holder)
//        } else {
//            playVideo(holder)
//        }
//    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val videoView = itemView.findViewById<VideoView>(R.id.videoView)
        val textView = itemView.findViewById<TextView>(R.id.Description)
        var playerView = itemView.findViewById<PlayerView>(R.id.player)
//        val playbutton: ImageButton = itemView.findViewById(R.id.playButton)
//        val pauseButton: ImageButton = itemView.findViewById(R.id.pauseButton)
        val sign = itemView.findViewById<TextView>(R.id.signName)
        val card2:CardView = itemView.findViewById(R.id.card2)
//        val videoSeekBar: SeekBar = itemView.findViewById(R.id.videoSeekBar)
//        val videoSeekBarTimeFlash:TextView = itemView.findViewById(R.id.videoSeekBarTimeFlash)
    }

//    private fun playVideo(holder: HomeViewHolder){
//        holder.videoView.start()
////        holder.playbutton.visibility = View.GONE
////        holder.pauseButton.visibility = View.VISIBLE
//        isplaying = true
//    }

//    private fun pauseVideo(holder: HomeViewHolder){
//        holder.videoView.pause()
////        holder.playbutton.visibility = View.VISIBLE
////        holder.pauseButton.visibility = View.GONE
//        isplaying = false
//    }

//    private fun updateSeekBar(holder: HomeViewHolder): Runnable {
//        return object : Runnable {
//            override fun run() {
//                holder.videoSeekBar.progress = holder.videoView.currentPosition
//                handler.postDelayed(this, 1000) // Update every second
//            }
//        }
//    }

//    private fun setUpSeekBar(holder: HomeViewHolder ) {
//        holder.videoSeekBar.setOnSeekBarChangeListener(object :
//            SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(
//                seekBar: SeekBar?,
//                progress: Int,
//                fromUser: Boolean
//            ) {
//                if (fromUser) {
//                    holder.videoView.seekTo(progress)
//                    showTimeFlash(holder, progress)
//                }
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//
//        })
//
//        holder.videoSeekBar.setOnClickListener {
//            val seekToPosition = holder.videoSeekBar.progress
//            holder.videoView.seekTo(seekToPosition)
//            showTimeFlash(holder, seekToPosition)
//        }
//    }

//    private fun showTimeFlash(holder:HomeViewHolder, position: Int) {
//        val timestamp = millisecondsToTimeString(position)
//        holder.videoSeekBarTimeFlash.text = timestamp
//        holder.videoSeekBarTimeFlash.visibility = View.VISIBLE
//        // Show for 200 milliseconds
//        handler.postDelayed({ holder.videoSeekBarTimeFlash.visibility = View.GONE },1200)
//    }

    private fun millisecondsToTimeString(milliseconds: Int): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
    }
}

data class HomeItem(val link: String="", val description: String="",val jName:String = "")

