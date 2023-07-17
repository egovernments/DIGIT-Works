// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'create_birth_response.dart';

class CreateBirthResponseMapper extends MapperBase<CreateBirthResponse> {
  static MapperContainer container = MapperContainer(
    mappers: {CreateBirthResponseMapper()},
  )..linkAll({StatsMapMapper.container});

  @override
  CreateBirthResponseMapperElement createElement(MapperContainer container) {
    return CreateBirthResponseMapperElement._(this, container);
  }

  @override
  String get id => 'CreateBirthResponse';

  static final fromMap = container.fromMap<CreateBirthResponse>;
  static final fromJson = container.fromJson<CreateBirthResponse>;
}

class CreateBirthResponseMapperElement
    extends MapperElementBase<CreateBirthResponse> {
  CreateBirthResponseMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  CreateBirthResponse decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  CreateBirthResponse fromMap(Map<String, dynamic> map) =>
      CreateBirthResponse(statsMap: container.$getOpt(map, 'statsMap'));

  @override
  Function get encoder => encode;
  dynamic encode(CreateBirthResponse v) => toMap(v);
  Map<String, dynamic> toMap(CreateBirthResponse c) =>
      {'statsMap': container.$enc(c.statsMap, 'statsMap')};

  @override
  String stringify(CreateBirthResponse self) =>
      'CreateBirthResponse(statsMap: ${container.asString(self.statsMap)})';
  @override
  int hash(CreateBirthResponse self) => container.hash(self.statsMap);
  @override
  bool equals(CreateBirthResponse self, CreateBirthResponse other) =>
      container.isEqual(self.statsMap, other.statsMap);
}

mixin CreateBirthResponseMappable {
  String toJson() =>
      CreateBirthResponseMapper.container.toJson(this as CreateBirthResponse);
  Map<String, dynamic> toMap() =>
      CreateBirthResponseMapper.container.toMap(this as CreateBirthResponse);
  CreateBirthResponseCopyWith<CreateBirthResponse, CreateBirthResponse,
          CreateBirthResponse>
      get copyWith => _CreateBirthResponseCopyWithImpl(
          this as CreateBirthResponse, $identity, $identity);
  @override
  String toString() => CreateBirthResponseMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          CreateBirthResponseMapper.container.isEqual(this, other));
  @override
  int get hashCode => CreateBirthResponseMapper.container.hash(this);
}

extension CreateBirthResponseValueCopy<$R, $Out extends CreateBirthResponse>
    on ObjectCopyWith<$R, CreateBirthResponse, $Out> {
  CreateBirthResponseCopyWith<$R, CreateBirthResponse, $Out>
      get asCreateBirthResponse =>
          base.as((v, t, t2) => _CreateBirthResponseCopyWithImpl(v, t, t2));
}

typedef CreateBirthResponseCopyWithBound = CreateBirthResponse;

abstract class CreateBirthResponseCopyWith<$R, $In extends CreateBirthResponse,
    $Out extends CreateBirthResponse> implements ObjectCopyWith<$R, $In, $Out> {
  CreateBirthResponseCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends CreateBirthResponse>(
          Then<CreateBirthResponse, $Out2> t, Then<$Out2, $R2> t2);
  StatsMapCopyWith<$R, StatsMap, StatsMap>? get statsMap;
  $R call({StatsMap? statsMap});
}

class _CreateBirthResponseCopyWithImpl<$R, $Out extends CreateBirthResponse>
    extends CopyWithBase<$R, CreateBirthResponse, $Out>
    implements CreateBirthResponseCopyWith<$R, CreateBirthResponse, $Out> {
  _CreateBirthResponseCopyWithImpl(super.value, super.then, super.then2);
  @override
  CreateBirthResponseCopyWith<$R2, CreateBirthResponse, $Out2>
      chain<$R2, $Out2 extends CreateBirthResponse>(
              Then<CreateBirthResponse, $Out2> t, Then<$Out2, $R2> t2) =>
          _CreateBirthResponseCopyWithImpl($value, t, t2);

  @override
  StatsMapCopyWith<$R, StatsMap, StatsMap>? get statsMap =>
      $value.statsMap?.copyWith.chain($identity, (v) => call(statsMap: v));
  @override
  $R call({Object? statsMap = $none}) =>
      $then(CreateBirthResponse(statsMap: or(statsMap, $value.statsMap)));
}

class StatsMapMapper extends MapperBase<StatsMap> {
  static MapperContainer container = MapperContainer(
    mappers: {StatsMapMapper()},
  );

  @override
  StatsMapMapperElement createElement(MapperContainer container) {
    return StatsMapMapperElement._(this, container);
  }

  @override
  String get id => 'StatsMap';

  static final fromMap = container.fromMap<StatsMap>;
  static final fromJson = container.fromJson<StatsMap>;
}

class StatsMapMapperElement extends MapperElementBase<StatsMap> {
  StatsMapMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  StatsMap decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  StatsMap fromMap(Map<String, dynamic> map) => StatsMap(
      failedRecords: container.$getOpt(map, 'Failed Records'),
      successfulRecords: container.$getOpt(map, 'Sucessful Records'),
      totalRecords: container.$getOpt(map, 'Total Records'));

  @override
  Function get encoder => encode;
  dynamic encode(StatsMap v) => toMap(v);
  Map<String, dynamic> toMap(StatsMap s) => {
        'Failed Records': container.$enc(s.failedRecords, 'failedRecords'),
        'Sucessful Records':
            container.$enc(s.successfulRecords, 'successfulRecords'),
        'Total Records': container.$enc(s.totalRecords, 'totalRecords')
      };

  @override
  String stringify(StatsMap self) =>
      'StatsMap(successfulRecords: ${container.asString(self.successfulRecords)}, totalRecords: ${container.asString(self.totalRecords)}, failedRecords: ${container.asString(self.failedRecords)})';
  @override
  int hash(StatsMap self) =>
      container.hash(self.successfulRecords) ^
      container.hash(self.totalRecords) ^
      container.hash(self.failedRecords);
  @override
  bool equals(StatsMap self, StatsMap other) =>
      container.isEqual(self.successfulRecords, other.successfulRecords) &&
      container.isEqual(self.totalRecords, other.totalRecords) &&
      container.isEqual(self.failedRecords, other.failedRecords);
}

mixin StatsMapMappable {
  String toJson() => StatsMapMapper.container.toJson(this as StatsMap);
  Map<String, dynamic> toMap() =>
      StatsMapMapper.container.toMap(this as StatsMap);
  StatsMapCopyWith<StatsMap, StatsMap, StatsMap> get copyWith =>
      _StatsMapCopyWithImpl(this as StatsMap, $identity, $identity);
  @override
  String toString() => StatsMapMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          StatsMapMapper.container.isEqual(this, other));
  @override
  int get hashCode => StatsMapMapper.container.hash(this);
}

extension StatsMapValueCopy<$R, $Out extends StatsMap>
    on ObjectCopyWith<$R, StatsMap, $Out> {
  StatsMapCopyWith<$R, StatsMap, $Out> get asStatsMap =>
      base.as((v, t, t2) => _StatsMapCopyWithImpl(v, t, t2));
}

typedef StatsMapCopyWithBound = StatsMap;

abstract class StatsMapCopyWith<$R, $In extends StatsMap, $Out extends StatsMap>
    implements ObjectCopyWith<$R, $In, $Out> {
  StatsMapCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends StatsMap>(
      Then<StatsMap, $Out2> t, Then<$Out2, $R2> t2);
  $R call({int? failedRecords, int? successfulRecords, int? totalRecords});
}

class _StatsMapCopyWithImpl<$R, $Out extends StatsMap>
    extends CopyWithBase<$R, StatsMap, $Out>
    implements StatsMapCopyWith<$R, StatsMap, $Out> {
  _StatsMapCopyWithImpl(super.value, super.then, super.then2);
  @override
  StatsMapCopyWith<$R2, StatsMap, $Out2> chain<$R2, $Out2 extends StatsMap>(
          Then<StatsMap, $Out2> t, Then<$Out2, $R2> t2) =>
      _StatsMapCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? failedRecords = $none,
          Object? successfulRecords = $none,
          Object? totalRecords = $none}) =>
      $then(StatsMap(
          failedRecords: or(failedRecords, $value.failedRecords),
          successfulRecords: or(successfulRecords, $value.successfulRecords),
          totalRecords: or(totalRecords, $value.totalRecords)));
}
