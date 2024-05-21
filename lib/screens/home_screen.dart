//import 'package:cached_video_player/cached_video_player.dart';
import 'package:flutter/material.dart';
import 'package:flutter_projects/screens/video_screen.dart';
import 'package:flutter_projects/services/json_service.dart';
import 'package:video_player/video_player.dart';
class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {

  int currentTabIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        title: Text("Tamarind",style: TextStyle(color: Colors.black),),
        actions: [
          IconButton(onPressed: (){}, icon: Icon(Icons.person,color: Colors.black,)),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.values.last,
          items: [
            BottomNavigationBarItem(icon: Icon(Icons.camera),label: "Tab 1"),
            BottomNavigationBarItem(icon: Icon(Icons.camera),label: "Tab 2"),
            BottomNavigationBarItem(icon: Icon(Icons.camera),label: "Tab 3"),
        ],
        currentIndex: currentTabIndex,
        onTap: (index){
            setState(() {
              currentTabIndex = index;
            });
        },
        selectedItemColor: Colors.blue,
        unselectedItemColor: Colors.black,
      ),
      body: currentTabIndex==0? ListView.builder(
          physics: PageScrollPhysics(),
          itemCount: DATA.length,
          shrinkWrap: false,
          itemBuilder: (context,index){
            return Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                width: MediaQuery.of(context).size.width,
                height: MediaQuery.of(context).size.height/1.3,
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.all(Radius.circular(10))
                ),
                child: Stack(
                  children: [
                    Container(//video container
                      width: MediaQuery.of(context).size.width,
                      height: MediaQuery.of(context).size.height/1.3,
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.all(Radius.circular(10))
                      ),
                      child: DATA[index]["tag"] == "video"?
                      Container(
                        width: MediaQuery.of(context).size.width,
                        height: MediaQuery.of(context).size.height/1.3,
                        decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.all(Radius.circular(10))
                        ),
                        child: VideoScreen(),
                      ):
                      DATA[index]["tag"] == "imageAndText"?
                      Container(
                        width: MediaQuery.of(context).size.width,
                        height: MediaQuery.of(context).size.height/1.3,
                      ):
                      Container(
                        width: MediaQuery.of(context).size.width,
                        height: MediaQuery.of(context).size.height/1.3,
                      ),
                    ),
                    Container(
                      alignment: Alignment.center,
                      width: MediaQuery.of(context).size.width,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          IconButton(onPressed: (){}, icon: Icon(Icons.arrow_left,size: 50,)),
                          IconButton(onPressed: (){}, icon: Icon(Icons.arrow_right,size: 50,))
                        ],
                      ),
                    ),
                    Container(
                      alignment: Alignment.bottomCenter,
                      child: Row(
                        children: [
                          IconButton(onPressed: (){}, icon: Icon(Icons.thumb_up_alt_outlined)),
                          IconButton(onPressed: (){}, icon: Icon(Icons.thumb_down_alt_outlined)),
                          IconButton(onPressed: (){}, icon: Icon(Icons.share)),
                        ],
                      ),
                    )
                  ],
                )
              )
            );
      }): currentTabIndex==1?Container():Container(),
    );
  }
}
