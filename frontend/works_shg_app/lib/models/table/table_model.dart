import 'package:freezed_annotation/freezed_annotation.dart';

part 'table_model.freezed.dart';
part 'table_model.g.dart';

@freezed
class TableDataModel with _$TableDataModel {
  @JsonSerializable(explicitToJson: true)
  const factory TableDataModel({
    required String? name,
    required String? aadhaar,
    String? bankNumber,
    double? monIndex,
    double? tueIndex,
    double? wedIndex,
    double? thursIndex,
    double? friIndex,
    double? satIndex,
  }) = _TableDataModel;

  factory TableDataModel.fromJson(Map<String, dynamic> json) =>
      _$TableDataModelFromJson(json);
}
