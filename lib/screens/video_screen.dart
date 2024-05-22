import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import 'package:visibility_detector/visibility_detector.dart';
class VideoScreen extends StatefulWidget {
  const VideoScreen({super.key});

  @override
  State<VideoScreen> createState() => _VideoScreenState();
}

class _VideoScreenState extends State<VideoScreen> {

  late VideoPlayerController controller;

  @override
  void dispose() {
    super.dispose();
    setState(() {
      controller.pause();
      controller.dispose();
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    controller = VideoPlayerController.networkUrl(
      Uri.parse("https://flutter.github.io/assets-for-api-docs/assets/videos/bee.mp4"),
    );
    controller.initialize();
    controller.setLooping(true);
  }

  @override
  Widget build(BuildContext context) {
    return AspectRatio(
      aspectRatio: controller.value.aspectRatio,
      child: Stack(
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          VisibilityDetector(
            key: Key("unique-key"),
            onVisibilityChanged: (info){
              if(info.visibleFraction == 0){
                controller.pause();
              }
              else{
                controller.play();
              }
            },
              child: VideoPlayer(controller)
          ),
          VideoProgressIndicator(controller, allowScrubbing: true),
          Container(
            alignment: Alignment.bottomCenter,
            child: Row(
              children: [
                IconButton(onPressed: (){}, icon: Icon(Icons.thumb_up_alt_outlined)),
                IconButton(onPressed: (){}, icon: Icon(Icons.thumb_down_alt_outlined)),
                IconButton(onPressed: (){}, icon: Icon(Icons.share)),
                // IconButton(onPressed: (){
                //   setState(() {
                //     if(!controller.value.isPlaying){
                //       controller.play();
                //     }else{
                //       controller.pause();
                //     }
                //   });
                // }, icon: Icon(!controller.value.isPlaying ? Icons.play_arrow: Icons.pause)),
              ],
            ),
          )
        ],
      ),
    );
  }
}
