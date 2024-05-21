import 'package:flutter/material.dart';
class NegativeScreen extends StatefulWidget {
  const NegativeScreen({super.key});

  @override
  State<NegativeScreen> createState() => _NegativeScreenState();
}

class _NegativeScreenState extends State<NegativeScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        automaticallyImplyLeading: false,
        backgroundColor: Colors.white,
        title: Text("Negative Screen",style: TextStyle(color: Colors.black),),
        actions: [
          IconButton(onPressed: (){
            Navigator.of(context).pop();
          }, icon: Icon(Icons.arrow_forward,color: Colors.black,))
        ],
      ),
      floatingActionButton: Align(
        alignment: Alignment.bottomCenter,
        child: FloatingActionButton(
          onPressed: (){

          },
          child: Icon(Icons.arrow_drop_up,size: 40,),
        ),
      ),
    );
  }
}
