import 'package:dart_mappable/dart_mappable.dart';

part 'create_birth_response.mapper.dart';

@MappableClass()
class CreateBirthResponse with CreateBirthResponseMappable {
  final StatsMap? statsMap;

  CreateBirthResponse({this.statsMap});
}

@MappableClass()
class StatsMap with StatsMapMappable {
  @MappableField(key: 'Sucessful Records')
  final int? successfulRecords;
  @MappableField(key: 'Total Records')
  final int? totalRecords;
  @MappableField(key: 'Failed Records')
  final int? failedRecords;

  StatsMap({
    this.failedRecords,
    this.successfulRecords,
    this.totalRecords,
  });
}
