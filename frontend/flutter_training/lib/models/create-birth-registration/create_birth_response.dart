import 'package:dart_mappable/dart_mappable.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'create_birth_response.mapper.dart';

@MappableClass()
class CreateBirthResponse with CreateBirthResponseMappable {
  final StatsMap? statsMap;

  CreateBirthResponse({this.statsMap});
}

@MappableClass()
class StatsMap with StatsMapMappable {
  @JsonKey(name: 'Sucessful Records')
  final int? successfulRecords;
  @JsonKey(name: 'Total Records')
  final int? totalRecords;
  @JsonKey(name: 'Failed Records')
  final int? failedRecords;

  StatsMap({
    this.failedRecords,
    this.successfulRecords,
    this.totalRecords,
  });
}
