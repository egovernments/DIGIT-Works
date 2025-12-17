import 'package:freezed_annotation/freezed_annotation.dart';

part 'homeConfigModel.freezed.dart';
part 'homeConfigModel.g.dart';

@freezed
class HomeConfigModel with _$HomeConfigModel {
  factory HomeConfigModel({@JsonKey(name: 'actions') required List<HomeAction> homeActions}) =
      _HomeConfigModel;

  factory HomeConfigModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$HomeConfigModelFromJson(json);
}

@freezed
class HomeAction with _$HomeAction {
  const factory HomeAction({
    @JsonKey(name: 'id') required int id,
    @JsonKey(name: 'displayName') required String displayName,
    @JsonKey(name: 'parentModule') required String parentModule,
    @JsonKey(name: 'enabled')required bool enabled,
    @JsonKey(name: 'tenantId')required String tenantId,
     @JsonKey(name: 'url')required String url,
    
    
  }) = _HomeAction;

  factory HomeAction.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$HomeActionFromJson(json);
}


