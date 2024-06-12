import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import 'package:visibility_detector/visibility_detector.dart';
import 'package:flutter_projects/screens/video_model.dart';

class VideoScreen extends StatefulWidget {
  const VideoScreen({super.key});

  @override
  State<VideoScreen> createState() => _VideoScreenState();
}

class _VideoScreenState extends State<VideoScreen> {
  late VideoPlayerController controller;
  bool isPlaying = true;

  @override
  void dispose() {
    controller.pause();
    controller.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    controller = VideoPlayerController.networkUrl(
      Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
    )..initialize().then((_) {
      setState(() {}); // Ensure the first frame is shown after the video is initialized
    });
    controller.setLooping(false); // Ensure the video does not loop
    controller.play();
    // controller.addListener(() {
    //   if (controller.value.position == controller.value.duration) {
    //     setState(() {
    //       isPlaying = false;
    //     });
    //   }
    // });
    VideoModel.controller = controller;
  }

  @override
  Widget build(BuildContext context) {
    return AspectRatio(
      aspectRatio: controller.value.aspectRatio,
      child: Stack(
        fit: StackFit.expand,
        children: <Widget>[
          GestureDetector(
            onTap: () {
              setState(() {
                if (controller.value.isPlaying) {
                  controller.pause();
                } else {
                  controller.play();
                }
                isPlaying = controller.value.isPlaying;
              });
            },
            child: VisibilityDetector(
                onVisibilityChanged: (VisibilityInfo info) {
                  if(info.visibleFraction*100<20){
                    setState(() {
                      if (controller.value.isPlaying) {
                        controller.pause();
                      }
                    });
                  }
                },
                key: Key("unique key"),
                child: VideoPlayer(controller)),
          ),
          // Positioned.fill(
          //   child: Align(
          //     alignment: Alignment.center,
          //     child:
          //   ),
          // ),
          Positioned(
            bottom: 0,
            right: 0,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                IconButton(
                  onPressed: () {
                    setState(() {
                      if (controller.value.isPlaying) {
                        controller.pause();
                      } else {
                        controller.play();
                      }
                      isPlaying = controller.value.isPlaying;
                    });
                  },
                  icon: Icon(
                    isPlaying ? Icons.pause : Icons.play_arrow,
                    //size: 50,
                    color: Colors.white,
                  ),
                ),
                IconButton(onPressed: () {}, icon: Icon(Icons.thumb_up_alt_outlined,color: Colors.grey,)),
                SizedBox(height: 10),
                IconButton(onPressed: () {}, icon: Icon(Icons.share,color: Colors.grey,)),
                SizedBox(height: 10),
                IconButton(onPressed: () {}, icon: Icon(Icons.more_vert,color: Colors.grey,)),
              ],
            ),
          ),
        ],
      ),
    );
  }

}
