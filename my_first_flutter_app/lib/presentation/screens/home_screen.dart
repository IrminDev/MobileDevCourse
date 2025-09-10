import 'package:flutter/material.dart';
import '../widgets/custom_button.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Home Screen')),
      body: Center(
        child: Column(
          children: [
            Text(
              'Welcome to the Home Screen!',
              style: TextStyle(fontSize: 20),
            ),
            Padding(padding: EdgeInsets.all(16), child: Text('Welcome to the Home Screen!')),
            TextField(
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Enter something',
              ),
              
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text("Are you learning?"),
                Checkbox(value: false, onChanged: (bool? value) {}),
              ],
            ),
            CustomButton(
              text: 'Go to Details',
              onPressed: () {
                Navigator.pushNamed(context, '/details');
              },
            ),
          ]
        )
      ),
    );
  }
}
