import 'package:flutter/services.dart';


///Main Service
class EdgeLighting {
  static const MethodChannel _channel = MethodChannel('edge_lighting');

  static Future<void> show(String colorHex) async {
    await _channel.invokeMethod('showEdgeLighting', {"color": colorHex});
  }

  static Future<void> hide() async {
    await _channel.invokeMethod('hideEdgeLighting');
  }
}
