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
    int? monIndex,
    int? tueIndex,
    int? wedIndex,
    int? thursIndex,
    int? friIndex,
    int? satIndex,
  }) = _TableDataModel;

  factory TableDataModel.fromJson(Map<String, dynamic> json) =>
      _$TableDataModelFromJson(json);
}
