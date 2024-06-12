import 'package:flutter/material.dart';
import 'package:flutter_projects/screens/home_screen.dart'; // Make sure the path is correct

class NegativeScreen extends StatefulWidget {
  @override
  _NegativeScreenState createState() => _NegativeScreenState();
}

class _NegativeScreenState extends State<NegativeScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: GestureDetector(
        onHorizontalDragUpdate: (details) {
          if (details.delta.dx < 0) {
            Navigator.of(context).pop();
          }
        },
        child: Center(
          child: Text(
            "Negative Screen Content",
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
              Icon(Icons.arrow_drop_down, size: 20),
              Text("See more",style: TextStyle(fontSize: 10),),
            ],
          ),
        ),
      ),
    );
  }
}
