import 'package:flutter/material.dart';
import 'package:smooth_page_indicator/smooth_page_indicator.dart'; // Import smooth_page_indicator
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
  final PageController _pageController = PageController(initialPage: 1, viewportFraction: 0.8);

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
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
      body: currentTabIndex == 0
          ? Column(
        children: [
          Expanded(
            child:_buildHomeCard(context)
          ),
          _buildPageIndicator(),
        ],
      )
          : currentTabIndex == 1
          ? Container() // Placeholder for Reels screen
          : Container(), // Placeholder for Profile screen
    );
  }

  Widget _buildCard(BuildContext context, Widget screen) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 8.0),
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height / 1.3,
        decoration: BoxDecoration(
          color: Colors.transparent,
          borderRadius: BorderRadius.all(Radius.circular(20)),
          boxShadow: [
            BoxShadow(
              color: Colors.black26,
              spreadRadius: 2,
              blurRadius: 10,
              offset: Offset(0, 4),
            ),
          ],
        ),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(20),
          child: screen,
        ),
      ),
    );
  }

  Widget _buildHomeCard(BuildContext context) {
    return PageView.builder(
      itemCount: DATA.length,
      scrollDirection: Axis.vertical,
      itemBuilder: (context, i) {
        return Padding(
          padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 8.0),
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height / 1.3,
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.all(Radius.circular(20)),
              boxShadow: [
                BoxShadow(
                  color: Colors.black26,
                  spreadRadius: 2,
                  blurRadius: 10,
                  offset: Offset(0, 4),
                ),
              ],
            ),
            child: PageView(
              controller: _pageController,
              children: [
                _buildCard(context, NegativeScreen()), // Left card (Negative)
                ClipRRect(
                  borderRadius: BorderRadius.circular(20),
                  child: DATA[i]["tag"] == "video"
                      ? VideoScreen()
                      : DATA[i]["tag"] == "imageAndText"
                      ? Container(
                    // Add your image and text widget here
                  )
                      : Container(
                    // Add your text widget here
                  ),
                ),         // Middle card (Home)
                _buildCard(context, PositiveScreen()),  // Right card (Positive)
              ],
            ),
          ),
        );
      },
    );
  }

  Widget _buildPageIndicator() {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 16.0),
      child: SmoothPageIndicator(
        controller: _pageController,
        count: 3, // Number of pages
        effect: WormEffect(
          dotHeight: 12.0,
          dotWidth: 12.0,
          activeDotColor: Colors.blue,
          dotColor: Colors.grey,
        ),
      ),
    );
  }
}
