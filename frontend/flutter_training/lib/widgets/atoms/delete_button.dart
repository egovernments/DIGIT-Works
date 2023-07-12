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
          Icons.delete_outline_outlined,
          color: Color.fromRGBO(80, 90, 95, 1),
        ),
      ),
    );
  }
}
