extension StringExtension on String {
  String capitalize() {
    if (isEmpty) {
      return this;
    }
    return this[0].toUpperCase() + substring(1);
  }
  
  /// Masks the string leaving the first and last character visible.
  /// Returns null if the string is null.
  String? mask() {
    if (length <= 2) return this; // If the length is 2 or less, return as is.

    // Masking logic: first character + masked middle + last character
    return '${this[0]}${'*' * (length - 2)}${this[length - 1]}';
  }
}



