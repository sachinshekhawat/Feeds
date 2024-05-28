import 'package:flutter/material.dart';
import 'package:flutter_projects/screens/negative_screen.dart';
import 'package:flutter_projects/screens/positive_screen.dart';
import 'package:flutter_projects/screens/video_screen.dart';
import 'package:flutter_projects/services/json_service.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int currentTabIndex = 0;

  PageController _pageController = PageController();

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }

  void _onHorizontalDrag(DragUpdateDetails details) {
    if (details.delta.dx > 0) {
      Navigator.of(context).push(MaterialPageRoute(builder: (context) {
        return NegativeScreen();
      }));
    } else if (details.delta.dx < 0) {
      Navigator.of(context).push(MaterialPageRoute(builder: (context) {
        return PositiveScreen();
      }));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[200],
      appBar: AppBar(
        backgroundColor: Colors.grey[100],
        title: Text(
          "Tamarind",
          style: TextStyle(color: Colors.black),
        ),
        actions: [
          IconButton(
            onPressed: () {},
            icon: Icon(Icons.person, color: Colors.black),
          ),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        items: [
          BottomNavigationBarItem(icon: Icon(Icons.home_filled), label: "Home"),
          BottomNavigationBarItem(icon: Icon(Icons.movie_creation), label: "Reels"),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: "Profile"),
        ],
        currentIndex: currentTabIndex,
        onTap: (index) {
          setState(() {
            currentTabIndex = index;
          });
        },
        selectedItemColor: Colors.blue,
        unselectedItemColor: Colors.black,
      ),
      body: GestureDetector(
        onHorizontalDragUpdate: _onHorizontalDrag,
        child: currentTabIndex == 0
            ? PageView.builder(
          controller: _pageController,
          itemCount: DATA.length,
          scrollDirection: Axis.vertical,
          itemBuilder: (context, i) {
            return Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                width: MediaQuery.of(context).size.width,
                height: MediaQuery.of(context).size.height / 1.3,
                decoration: BoxDecoration(
                  color: Colors.grey[100],
                  borderRadius: BorderRadius.all(Radius.circular(10)),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey.withOpacity(0.5),
                      spreadRadius: 5,
                      blurRadius: 7,
                      offset: Offset(0, 3),
                    ),
                  ],
                ),
                child: DATA[i]["tag"] == "video"
                    ? VideoScreen()
                    : DATA[i]["tag"] == "imageAndText"
                    ? Container(
                  // Add your image and text widget here
                )
                    : Container(
                  // Add your text widget here
                ),
              ),
            );
          },
        )
            : currentTabIndex == 1
            ? Container() // Placeholder for Reels screen
            : Container(), // Placeholder for Profile screen
      ),
    );
  }
}
