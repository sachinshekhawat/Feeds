import 'package:flutter/material.dart';
import 'package:flutter_projects/screens/home_screen.dart'; // Make sure the path is correct

class PositiveScreen extends StatefulWidget {
  @override
  _PositiveScreenState createState() => _PositiveScreenState();
}

class _PositiveScreenState extends State<PositiveScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: GestureDetector(
        onHorizontalDragUpdate: (details) {
          if (details.delta.dx > 0) {
            // Swipe right: Do nothing
          } else if (details.delta.dx < 0) {
            // Swipe left: Navigate back to HomeScreen
            Navigator.of(context).pop();
          }
        },
        child: Center(
          child: Text(
            "Positive Screen Content",
            style: TextStyle(fontSize: 24),
          ),
        ),
      ),
      floatingActionButton: Align(
        alignment: Alignment.bottomCenter,
        child: FloatingActionButton.extended(
          onPressed: () {
            // Define what happens when the button is pressed
          },
          label: Row(
            children: [
              Icon(Icons.arrow_drop_down, size: 30),
              SizedBox(width: 5), // Add some space between the icon and text
              Text("See more"),
            ],
          ),
        ),
      ),
    );
  }
}
