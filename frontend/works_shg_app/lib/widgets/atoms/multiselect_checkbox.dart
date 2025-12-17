// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';

class MultiSelectSearchCheckBox extends StatefulWidget {
  final String label;
  final Function(List<String>)? onChange;
  final List<String> options;
  final List<String> selectedOptions;
  final String? hintText;

  const MultiSelectSearchCheckBox({
    super.key,
    required this.label,
    required this.options,
    required this.selectedOptions,
    this.hintText,
    this.onChange,
  });

  @override
  MultiSelectSearchCheckBoxState createState() =>
      MultiSelectSearchCheckBoxState();
}

class MultiSelectSearchCheckBoxState extends State<MultiSelectSearchCheckBox> {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        if (FocusScope.of(context).hasFocus) {
          FocusScope.of(context).unfocus();
        }
      },
      child: Container(
        padding: const EdgeInsets.all(2.0),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                widget.label,
                style: Theme.of(context).digitTextTheme(context).headingS,
              ),
              const SizedBox(height: 8),
              TypeAheadFormField<String>(
                textFieldConfiguration: TextFieldConfiguration(
                  decoration: InputDecoration(
                    border: const OutlineInputBorder(),
                    hintText: widget.hintText ?? 'Skills',
                    suffixIconConstraints:
                        const BoxConstraints(minWidth: 0, minHeight: 0),
                    suffixStyle: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w400,
                        color: Theme.of(context).primaryColorDark),
                    suffixIcon: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Icon(Icons.search_sharp)),
                  ),
                ),
                suggestionsBoxVerticalOffset: -10,
                suggestionsCallback: (pattern) {
                  return widget.options
                      .where((option) =>
                          AppLocalizations.of(context).translate("COMMON_MASTERS_SKILLS_$option").toLowerCase().contains(pattern.toLowerCase()))
                      .toList();
                },
                itemBuilder: (context, optionData) {
                  return StatefulBuilder(
                    builder: (BuildContext context, StateSetter set) {
                      return CheckboxListTile(
                        title: Text(AppLocalizations.of(context)
                            .translate('COMMON_MASTERS_SKILLS_$optionData')),
                        controlAffinity: ListTileControlAffinity.leading,
                        value: widget.selectedOptions.contains(optionData),
                        onChanged: (selected) {
                          set(() {
                            if (selected ?? false) {
                              if (!widget.selectedOptions.contains(optionData)) {
                                set(() {
                                  widget.selectedOptions.add(optionData);
                                });
                                widget.onChange?.call(widget.selectedOptions);
                              }
                            } else {
                              if (widget.selectedOptions.contains(optionData)) {
                                set(() {
                                  widget.selectedOptions.remove(optionData);
                                });
                                widget.onChange?.call(widget.selectedOptions);
                              }
                            }
                          });
                        },
                      );
                    },
                  );
                },
                hideSuggestionsOnKeyboardHide: true,
                hideOnEmpty: true,
                hideOnLoading: true,
                keepSuggestionsOnLoading: true,
                animationStart: 0.5,
                onSuggestionSelected: (String suggestion) {
                  setState(() {});
                },
                suggestionsBoxDecoration: SuggestionsBoxDecoration(
                    hasScrollbar: true,
                    elevation: 20,
                    constraints: BoxConstraints(
                        maxHeight: MediaQuery.of(context).size.height / 2.5)),
              ),
              StatefulBuilder(
                  builder: (BuildContext context, StateSetter setChip) {
                return Container(
                  height: MediaQuery.sizeOf(context).height*0.3,
                  margin: const EdgeInsets.only(top: 8),
                  child: SingleChildScrollView(
                    child: Wrap(
                      direction: Axis.vertical,
                        spacing: 3.0,
                        runSpacing: 3.0,
                        children: widget.selectedOptions
                            .map((option) => Tooltip(
                                  message: AppLocalizations.of(context)
                                      .translate('COMMON_MASTERS_SKILLS_$option'),
                                  preferBelow: false,
                                  child: Chip(
                                    backgroundColor: Theme.of(context).colorTheme.paper.secondary,
                                    label: SizedBox(
                                      width: MediaQuery.sizeOf(context).width*0.66,
                                      child: Text(
                                        AppLocalizations.of(context).translate(
                                            'COMMON_MASTERS_SKILLS_$option'),
                                        overflow: TextOverflow.ellipsis,
                                        softWrap: true,
                                        maxLines: 3,
                                      ),
                                    ),
                                    padding: const EdgeInsets.all(2.0),
                                    deleteIcon: const Icon(Icons.cancel),
                                    onDeleted: () {
                                      setChip(() {
                                        widget.selectedOptions.remove(option);
                                      });
                                      widget.onChange?.call(widget.selectedOptions);
                                    },
                                    deleteButtonTooltipMessage: '',
                                  ),
                                ))
                            .toList()),
                  ),
                );
              })
            ],
          ),
        ),
      ),
    );
  }
}
