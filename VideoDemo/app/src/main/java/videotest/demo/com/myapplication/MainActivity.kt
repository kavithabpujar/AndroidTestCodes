package videotest.demo.com.myapplication

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var videoView: PlayerView
    private  var player:ExoPlayer ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        videoView = findViewById(R.id.video_view)

//        val uri = Uri.parse("https://www.demonuts.com/Demonuts/smallvideo.mp4")
//        videoView?.setVideoURI(uri)
//        val ctlr = MediaController (this)
//        ctlr.setMediaPlayer(videoView)
//        ctlr.setAnchorView(videoView);
//        videoView?.setMediaController(ctlr)
//        videoView?.requestFocus()
//        videoView?.start()
//





    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }



    private  fun initializePlayer(){
    // Create a default TrackSelector

//        val  bandwidthMeter=DefaultBandwidthMeter();
//
//    TrackSelection.Factory videoTrackSelectionFactory =
//            new AdaptiveTrackSelection.Factory(bandwidthMeter);
//    TrackSelector trackSelector =
//            new DefaultTrackSelector(videoTrackSelectionFactory);
//
//    //Initialize the player
//    player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//
//    //Initialize simpleExoPlayerView
//    SimpleExoPlayerView simpleExoPlayerView = findViewById(R.id.exoplayer);
//    simpleExoPlayerView.setPlayer(player);
//
//    // Produces DataSource instances through which media data is loaded.
//    DataSource.Factory dataSourceFactory =
//            new DefaultDataSourceFactory(this, Util.getUserAgent(this, "CloudinaryExoplayer"));
//
//    // Produces Extractor instances for parsing the media data.
//    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//
//    // This is the MediaSource representing the media to be played.
//    Uri videoUri = Uri.parse("any Cloudinary URL");
//    MediaSource videoSource = new ExtractorMediaSource(videoUri,
//            dataSourceFactory, extractorsFactory, null, null);
//
//    // Prepare the player with the source.
//    player.prepare(videoSource);

        val  bandwidthMeter=DefaultBandwidthMeter();

       val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter);
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
         player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        //Initialize simpleExoPlayerView
        videoView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
       val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "CloudinaryExoplayer"));

        // Produces Extractor instances for parsing the media data.
        val extractorsFactory =  DefaultExtractorsFactory();
        val uri = Uri.parse("https://www.demonuts.com/Demonuts/smallvideo.mp4")
        // This is the MediaSource representing the media to be played.
        val videoSource =  ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);

        // Prepare the player with the source.
        player?.prepare(videoSource);

}


    override fun onPause() {
        super.onPause()
        if(player !=  null) {
            player?.release();
            player= null
        }
    }




}




