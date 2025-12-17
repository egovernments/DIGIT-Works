import 'package:freezed_annotation/freezed_annotation.dart';

part 'adhar_response.freezed.dart';
part 'adhar_response.g.dart';

@freezed
class AdharCardResponse with _$AdharCardResponse {
  const factory AdharCardResponse({
    @JsonKey(name: 'status')
    String? status,
    @JsonKey(name: 'txn')
    String? txn,
    @JsonKey(name: 'ret')
    String? ret,
    @JsonKey(name: 'err')
    String? err,
    @JsonKey(name: 'errMsg')
    String? errMsg,
    @JsonKey(name: 'responseCode')
    String? responseCode,
    @JsonKey(name: 'uidToken')
    String? uidToken,
    @JsonKey(name: 'mobileNumber')
    String? mobileNumber,
    @JsonKey(name: 'email')
    String? email

       
  }) = _AdharCardResponse;

  factory AdharCardResponse.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AdharCardResponseFromJson(json);

     
}