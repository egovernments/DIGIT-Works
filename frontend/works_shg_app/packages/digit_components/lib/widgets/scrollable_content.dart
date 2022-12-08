import 'package:flutter/material.dart';

class ScrollableContent extends StatelessWidget {
  final Widget? header;
  final Widget? footer;
  final bool? primary;
  final ScrollController? controller;
  final MainAxisAlignment mainAxisAlignment;
  final CrossAxisAlignment crossAxisAlignment;
  final List<Widget> children;

  const ScrollableContent({
    super.key,
    this.footer,
    this.header,
    this.primary,
    this.controller,
    this.mainAxisAlignment = MainAxisAlignment.start,
    this.crossAxisAlignment = CrossAxisAlignment.start,
    this.children = const <Widget>[],
  });

  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      controller: controller,
      primary: primary,
      slivers: [
        if (header != null) SliverToBoxAdapter(child: header),
        SliverFillRemaining(
          hasScrollBody: false,
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: crossAxisAlignment,
              children: [
                Column(
                  mainAxisAlignment: mainAxisAlignment,
                  children: children,
                ),
                if (footer != null) ...[
                  const SizedBox(height: 16),
                  footer!,
                ],
              ],
            ),
          ),
        ),
      ],
    );
  }
}
