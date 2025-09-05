import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';

import 'edge_lightning.dart';

class MyApp extends StatelessWidget {
  Future<void> requestOverlayPermission() async {
    if (!await Permission.systemAlertWindow.isGranted) {
      await Permission.systemAlertWindow.request();
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text("Edge Lighting Test")),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(
                onPressed: () async {
                  await requestOverlayPermission();
                  EdgeLighting.show("#FF00FF");
                },
                child: Text("Start Edge Lighting"),
              ),
              ElevatedButton(
                onPressed: () {
                  EdgeLighting.hide();
                },
                child: Text("Stop Edge Lighting"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}