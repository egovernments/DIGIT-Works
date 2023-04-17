import 'package:flutter/material.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;

import '../../blocs/localization/app_localization.dart';

class DropDownDialog extends StatefulWidget {
  final List<String> options;
  final Function(String?) onChanged;
  final bool isDisabled;
  final String? label;
  String selectedOption;

  DropDownDialog(
      {super.key,
      required this.options,
      required this.onChanged,
      required this.selectedOption,
      this.isDisabled = false,
      this.label});

  @override
  State<StatefulWidget> createState() {
    return _DropDownDialogState();
  }
}

class _DropDownDialogState extends State<DropDownDialog> {
  final ScrollController _scrollController = ScrollController();

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.centerLeft,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          InkWell(
            onTap: widget.isDisabled
                ? null
                : () {
                    _showDialog(context);
                  },
            child: Container(
              width: 120,
              height: 40,
              padding: const EdgeInsets.all(4.0),
              decoration: BoxDecoration(
                border: Border.all(color: Colors.grey),
                borderRadius: BorderRadius.circular(4),
              ),
              child: Row(
                children: [
                  SizedBox(
                    width: 80,
                    child: Text(
                      AppLocalizations.of(context)
                          .translate(widget.selectedOption),
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(fontSize: 12),
                    ),
                  ),
                  const Icon(
                    Icons.arrow_drop_down,
                    size: 20,
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  _showDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          scrollable: true,
          title: Text(
            AppLocalizations.of(context)
                .translate(widget.label ?? i18.common.selectAnOption),
            style: const TextStyle(fontSize: 12, fontWeight: FontWeight.w700),
          ),
          content: SizedBox(
            height: 180,
            width: 200,
            child: Scrollbar(
              controller: _scrollController,
              thumbVisibility: true,
              child: SingleChildScrollView(
                controller: _scrollController,
                physics: const ClampingScrollPhysics(),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: widget.options.map((option) {
                    return RadioListTile(
                      title: Text(
                          AppLocalizations.of(context).translate(option),
                          style: const TextStyle(
                              fontSize: 12, fontWeight: FontWeight.w400)),
                      value: option,
                      groupValue: widget.selectedOption,
                      onChanged: (value) {
                        setState(() {
                          widget.selectedOption = value ?? '';
                        });
                        widget.onChanged(value);
                        Navigator.pop(context);
                      },
                    );
                  }).toList(),
                ),
              ),
            ),
          ),
        );
      },
    );
  }
}
