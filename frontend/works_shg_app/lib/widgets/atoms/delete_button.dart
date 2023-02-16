import 'package:flutter/material.dart';

class DeleteButton extends StatelessWidget {
  final void Function()? onTap;
  const DeleteButton({super.key, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.centerLeft,
      child: GestureDetector(
        onTap: onTap,
        child: const Icon(
          IconData(0xe1b9, fontFamily: 'MaterialIcons'),
          color: Colors.grey,
        ),
      ),
    );
  }
}
