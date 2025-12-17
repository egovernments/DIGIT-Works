
// Define your custom exception class
class CustomException implements Exception {
  final String message;
  CustomException(this.message);

  @override
  String toString() => message; // Override toString to return the message directly
}