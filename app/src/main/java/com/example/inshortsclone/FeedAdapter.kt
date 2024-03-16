package com.example.inshortsclone

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class FeedAdapter(val context:Context, var data : List<FeedItem> = mutableListOf()) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private var isplaying = false
    private val handler = Handler()

    private lateinit var player: ExoPlayer
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
//        holder.bind(feedList[position])

        val videoUrl  = data[position].link

        holder.textView.text = data[position].description
        val user = "shorts by "
        holder.jName.text = data[position].jName
        "$user${" " + holder.jName.text}".also { holder.jName.text = it }


        player = ExoPlayer.Builder(context).build()

        holder.playerView.player = player

        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
//        Toast.makeText(context, videoUrl, Toast.LENGTH_SHORT).show()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.pause()

    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun updateFeedItems(newFeedItems: List<FeedItem>) {
        data = newFeedItems.toMutableList()
        notifyDataSetChanged()
    }

   inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


       val textView: TextView = itemView.findViewById(R.id.Description)
       val jName:TextView = itemView.findViewById(R.id.signName)

       //Video Player Setup Entities
       //val videoView = itemView.findViewById<VideoView>(R.id.videoView)
       val card1 : CardView = itemView.findViewById(R.id.card1)
       var playerView = itemView.findViewById<PlayerView>(R.id.player)
//       val playbutton:ImageButton = itemView.findViewById(R.id.playButton)
//       val pauseButton:ImageButton = itemView.findViewById(R.id.pauseButton)
//       val videoSeekBar:SeekBar = itemView.findViewById(R.id.videoSeekBar)
//       val videoSeekBarTimeFlash:TextView = itemView.findViewById(R.id.videoSeekBarTimeFlash)
    }

//    private fun setUpSeekBar(holder: FeedViewHolder) {
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
//
//
//    private fun playVideo(holder: FeedViewHolder){
//        holder.videoView.start()
//        holder.playbutton.visibility = View.GONE
//        holder.pauseButton.visibility = View.VISIBLE
//        isplaying = true
//    }
//
//    private fun pauseVideo(holder: FeedViewHolder){
//        holder.videoView.pause()
//        holder.playbutton.visibility = View.VISIBLE
//        holder.pauseButton.visibility = View.GONE
//        isplaying = false
//    }
//
//    private fun updateSeekBar(holder: FeedViewHolder): Runnable {
//        return object : Runnable {
//            override fun run() {
//                holder.videoSeekBar.progress = holder.videoView.currentPosition
//                handler.postDelayed(this, 1000) // Update every second
//            }
//        }
//    }
//
//
//    private fun showTimeFlash(holder: FeedViewHolder, position: Int) {
//        val timestamp = millisecondsToTimeString(position)
//            holder.videoSeekBarTimeFlash.text = timestamp
//            holder.videoSeekBarTimeFlash.visibility = View.VISIBLE
//       // Show for 200 milliseconds
//        handler.postDelayed({ holder.videoSeekBarTimeFlash.visibility = View.GONE },1200)
//    }

    private fun millisecondsToTimeString(milliseconds: Int): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
    }
}

data class FeedItem(val link: String="", val description: String="", val jName: String="")

