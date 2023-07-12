import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;

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
    return Tooltip(
      message: AppLocalizations.of(context)
          .translate('COMMON_MASTERS_SKILLS_${widget.selectedOption}'),
      // triggerMode: TooltipTriggerMode.longPress,
      child: Align(
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
                  border: Border.all(
                      color: widget.isDisabled
                          ? const Color.fromRGBO(149, 148, 148, 0.5)
                          : const DigitColors().black),
                  borderRadius: BorderRadius.circular(4),
                ),
                child: Row(
                  children: [
                    SizedBox(
                      width: 80,
                      child: Text(
                        widget.selectedOption != null &&
                                widget.selectedOption.isNotEmpty
                            ? AppLocalizations.of(context).translate(
                                'COMMON_MASTERS_SKILLS_${widget.selectedOption}')
                            : '',
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(
                            fontSize: 12,
                            color: widget.isDisabled
                                ? const Color.fromRGBO(149, 148, 148, 1)
                                : const DigitColors().black),
                      ),
                    ),
                    Icon(
                      Icons.arrow_drop_down,
                      color: widget.isDisabled
                          ? const Color.fromRGBO(149, 148, 148, 1)
                          : const DigitColors().black,
                      size: 20,
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  _showDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          contentPadding: const EdgeInsets.all(4.0),
          scrollable: true,
          title: Text(
            AppLocalizations.of(context)
                .translate(widget.label ?? i18.common.selectAnOption),
            style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w700,
                color: widget.isDisabled
                    ? const Color.fromRGBO(149, 148, 148, 1)
                    : const DigitColors().black),
          ),
          content: SizedBox(
            height:
                widget.options.length < 7 ? widget.options.length * 45 : 250,
            child: Scrollbar(
              controller: _scrollController,
              thumbVisibility: true,
              child: SingleChildScrollView(
                padding: EdgeInsets.zero,
                controller: _scrollController,
                physics: const ClampingScrollPhysics(),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisSize: MainAxisSize.min,
                  children: widget.options.map((option) {
                    return RadioListTile(
                      contentPadding: const EdgeInsets.only(
                          left: 8.0, top: 0.0, bottom: 0.0),
                      visualDensity: const VisualDensity(
                          horizontal: VisualDensity.minimumDensity,
                          vertical: VisualDensity.minimumDensity),
                      title: Text(
                          AppLocalizations.of(context)
                              .translate('COMMON_MASTERS_SKILLS_$option'),
                          style: const TextStyle(
                              fontSize: 16, fontWeight: FontWeight.w400)),
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
