import 'package:freezed_annotation/freezed_annotation.dart';

part 'home_config_model.freezed.dart';
part 'home_config_model.g.dart';

@freezed
class HomeConfigModel with _$HomeConfigModel {
  factory HomeConfigModel({ required List<HomeAction> actions}) =
      _HomeConfigModel;

  factory HomeConfigModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$HomeConfigModelFromJson(json);
}

@freezed
class HomeAction with _$HomeAction {
  
  const factory HomeAction({
    required int id,
    required String displayName,
    required String parentModule,
   required bool enabled,
   required String tenantId,
    required String url,
    
    
  }) = _HomeAction;

  factory HomeAction.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$HomeActionFromJson(json);
}


