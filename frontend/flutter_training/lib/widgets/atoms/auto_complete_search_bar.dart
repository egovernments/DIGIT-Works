import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;

class AutoCompleteSearchBar extends StatelessWidget {
  final String labelText;
  final bool? isRequired;
  final Future<List<dynamic>> Function(String) callBack;
  final TextEditingController controller;
  final Function(dynamic) onSuggestionSelected;
  final Widget Function(BuildContext, dynamic) listTile;
  final SuggestionsBoxController? suggestionsBoxController;
  final bool? isEnabled;
  final int? maxLength;
  final int? minCharsForSuggestions;
  final String? requiredMessage;
  final List<FilteringTextInputFormatter>? inputFormatter;
  final TextInputType? textInputType;
  final String? hintText;

  const AutoCompleteSearchBar(
      {Key? key,
      required this.labelText,
      required this.callBack,
      required this.onSuggestionSelected,
      required this.listTile,
      required this.controller,
      this.suggestionsBoxController,
      this.isRequired,
      this.isEnabled,
      this.requiredMessage,
      this.inputFormatter,
      this.textInputType,
      this.hintText,
      this.maxLength,
      this.minCharsForSuggestions})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraints) {
      if (constraints.maxWidth > 760) {
        return Align(
          alignment: Alignment.centerLeft,
          child: Container(
              padding:
                  const EdgeInsets.symmetric(vertical: 8.0, horizontal: 0.0),
              alignment: Alignment.centerLeft,
              margin: const EdgeInsets.only(top: 5.0, bottom: 5, right: 16),
              child: Container(
                width: MediaQuery.of(context).size.width / 2,
                padding: const EdgeInsets.only(top: 18, bottom: 3),
                child: _autoComplete(context),
              )),
        );
      } else {
        return Container(
            margin:
                const EdgeInsets.only(top: 5.0, bottom: 5, right: 8, left: 8),
            child: Column(
              children: [
                Container(
                    padding: const EdgeInsets.only(top: 18, bottom: 3),
                    child: _autoComplete(context)),
              ],
            ));
      }
    });
  }

  Widget _text(BuildContext context) {
    return Align(
      alignment: Alignment.centerLeft,
      child: Wrap(direction: Axis.horizontal, children: <Widget>[
        Text(labelText,
            textAlign: TextAlign.left,
            style: TextStyle(
                fontWeight: FontWeight.w400,
                fontSize: 16,
                color: (isEnabled ?? true)
                    ? Theme.of(context).primaryColorDark
                    : Colors.grey)),
        Text((isRequired ?? false) ? '* ' : ' ',
            textAlign: TextAlign.left,
            style: TextStyle(
                fontWeight: FontWeight.w400,
                fontSize: 16,
                color: (isEnabled ?? true)
                    ? Theme.of(context).primaryColorDark
                    : Colors.grey)),
      ]),
    );
  }

  Widget _autoComplete(BuildContext context) {
    return TypeAheadFormField(
      key: key,
      textFieldConfiguration: TextFieldConfiguration(
          inputFormatters: inputFormatter,
          keyboardType: textInputType ?? TextInputType.text,
          enabled: (isEnabled ?? true),
          maxLength: maxLength,
          controller: controller,
          style: TextStyle(
              color: (isEnabled ?? true)
                  ? Theme.of(context).primaryColorDark
                  : Colors.grey,
              fontSize: 16),
          decoration: InputDecoration(
            hintText: hintText ?? '',
            border: const OutlineInputBorder(
              borderRadius: BorderRadius.zero,
            ),
            filled: true,
            fillColor: Colors.white,
            prefixIconConstraints:
                const BoxConstraints(minWidth: 0, minHeight: 0),
            prefixStyle: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w400,
                color: Theme.of(context).primaryColorDark),
            prefixIcon: const Padding(
                padding: EdgeInsets.all(8.0), child: Icon(Icons.search_sharp)),
          )),
      loadingBuilder: (BuildContext context) {
        return const SizedBox(
            height: 100, child: Center(child: CircularProgressIndicator()));
      },
      noItemsFoundBuilder: (BuildContext context) {
        return SizedBox(
          height: 30,
          child: Center(
            child: Text(
              AppLocalizations.of(context).translate(i18.common.noItems),
              style: DigitTheme.instance.mobileTheme.textTheme.bodyMedium
                  ?.apply(color: const DigitColors().davyGray),
            ),
          ),
        );
      },
      suggestionsBoxVerticalOffset: 0.0,
      direction: AxisDirection.down,
      minCharsForSuggestions: minCharsForSuggestions ?? 0,
      hideSuggestionsOnKeyboardHide: true,
      suggestionsBoxController: suggestionsBoxController,
      suggestionsCallback: (pattern) async {
        // FocusScope.of(context).unfocus();
        return await callBack(pattern);
      },
      itemBuilder: listTile,
      onSuggestionSelected: onSuggestionSelected,
      onSuggestionsBoxToggle: (opened) {
        if (!opened) {
          controller.clear();
        }
      },
      validator: isRequired == null || !isRequired!
          ? null
          : (val) {
              if (val == null || val.trim().isEmpty) {
                return requiredMessage;
              }
              return null;
            },
    );
  }
}
