import 'package:flutter/material.dart';

class DetailsScreen extends StatelessWidget {
  const DetailsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Details Screen')),
      body: const Center(
        child: Text('Welcome to the Details Screen!', style: TextStyle(fontSize: 20)),
        
      ),
    );
  }
}
