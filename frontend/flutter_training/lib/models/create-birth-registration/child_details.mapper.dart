// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'child_details.dart';

class ChildDetailsMapper extends MapperBase<ChildDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {ChildDetailsMapper()},
  );

  @override
  ChildDetailsMapperElement createElement(MapperContainer container) {
    return ChildDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'ChildDetails';

  static final fromMap = container.fromMap<ChildDetails>;
  static final fromJson = container.fromJson<ChildDetails>;
}

class ChildDetailsMapperElement extends MapperElementBase<ChildDetails> {
  ChildDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ChildDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ChildDetails fromMap(Map<String, dynamic> map) => ChildDetails(
      regNo: container.$get(map, 'regNo'),
      dateOfBirth: container.$get(map, 'dateOfBirth'),
      checkBoxForAddress: container.$getOpt(map, 'checkBoxForAddress') ?? true,
      firstName: container.$get(map, 'firstName'),
      lastName: container.$get(map, 'lastName'),
      genderStr: container.$get(map, 'genderStr'),
      hospitalName: container.$get(map, 'hospitalName'),
      informantName: container.$get(map, 'informantName'),
      informantAddress: container.$get(map, 'informantAddress'),
      placeOfBirth: container.$get(map, 'placeOfBirth'),
      dateOfReport: container.$get(map, 'dateOfReport'));

  @override
  Function get encoder => encode;
  dynamic encode(ChildDetails v) => toMap(v);
  Map<String, dynamic> toMap(ChildDetails c) => {
        'regNo': container.$enc(c.regNo, 'regNo'),
        'dateOfBirth': container.$enc(c.dateOfBirth, 'dateOfBirth'),
        'checkBoxForAddress':
            container.$enc(c.checkBoxForAddress, 'checkBoxForAddress'),
        'firstName': container.$enc(c.firstName, 'firstName'),
        'lastName': container.$enc(c.lastName, 'lastName'),
        'genderStr': container.$enc(c.genderStr, 'genderStr'),
        'hospitalName': container.$enc(c.hospitalName, 'hospitalName'),
        'informantName': container.$enc(c.informantName, 'informantName'),
        'informantAddress':
            container.$enc(c.informantAddress, 'informantAddress'),
        'placeOfBirth': container.$enc(c.placeOfBirth, 'placeOfBirth'),
        'dateOfReport': container.$enc(c.dateOfReport, 'dateOfReport')
      };

  @override
  String stringify(ChildDetails self) =>
      'ChildDetails(regNo: ${container.asString(self.regNo)}, firstName: ${container.asString(self.firstName)}, lastName: ${container.asString(self.lastName)}, hospitalName: ${container.asString(self.hospitalName)}, placeOfBirth: ${container.asString(self.placeOfBirth)}, checkBoxForAddress: ${container.asString(self.checkBoxForAddress)}, genderStr: ${container.asString(self.genderStr)}, dateOfReport: ${container.asString(self.dateOfReport)}, dateOfBirth: ${container.asString(self.dateOfBirth)}, informantName: ${container.asString(self.informantName)}, informantAddress: ${container.asString(self.informantAddress)})';
  @override
  int hash(ChildDetails self) =>
      container.hash(self.regNo) ^
      container.hash(self.firstName) ^
      container.hash(self.lastName) ^
      container.hash(self.hospitalName) ^
      container.hash(self.placeOfBirth) ^
      container.hash(self.checkBoxForAddress) ^
      container.hash(self.genderStr) ^
      container.hash(self.dateOfReport) ^
      container.hash(self.dateOfBirth) ^
      container.hash(self.informantName) ^
      container.hash(self.informantAddress);
  @override
  bool equals(ChildDetails self, ChildDetails other) =>
      container.isEqual(self.regNo, other.regNo) &&
      container.isEqual(self.firstName, other.firstName) &&
      container.isEqual(self.lastName, other.lastName) &&
      container.isEqual(self.hospitalName, other.hospitalName) &&
      container.isEqual(self.placeOfBirth, other.placeOfBirth) &&
      container.isEqual(self.checkBoxForAddress, other.checkBoxForAddress) &&
      container.isEqual(self.genderStr, other.genderStr) &&
      container.isEqual(self.dateOfReport, other.dateOfReport) &&
      container.isEqual(self.dateOfBirth, other.dateOfBirth) &&
      container.isEqual(self.informantName, other.informantName) &&
      container.isEqual(self.informantAddress, other.informantAddress);
}

mixin ChildDetailsMappable {
  String toJson() => ChildDetailsMapper.container.toJson(this as ChildDetails);
  Map<String, dynamic> toMap() =>
      ChildDetailsMapper.container.toMap(this as ChildDetails);
  ChildDetailsCopyWith<ChildDetails, ChildDetails, ChildDetails> get copyWith =>
      _ChildDetailsCopyWithImpl(this as ChildDetails, $identity, $identity);
  @override
  String toString() => ChildDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ChildDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => ChildDetailsMapper.container.hash(this);
}

extension ChildDetailsValueCopy<$R, $Out extends ChildDetails>
    on ObjectCopyWith<$R, ChildDetails, $Out> {
  ChildDetailsCopyWith<$R, ChildDetails, $Out> get asChildDetails =>
      base.as((v, t, t2) => _ChildDetailsCopyWithImpl(v, t, t2));
}

typedef ChildDetailsCopyWithBound = ChildDetails;

abstract class ChildDetailsCopyWith<$R, $In extends ChildDetails,
    $Out extends ChildDetails> implements ObjectCopyWith<$R, $In, $Out> {
  ChildDetailsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends ChildDetails>(
      Then<ChildDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? regNo,
      int? dateOfBirth,
      bool? checkBoxForAddress,
      String? firstName,
      String? lastName,
      String? genderStr,
      String? hospitalName,
      String? informantName,
      String? informantAddress,
      String? placeOfBirth,
      int? dateOfReport});
}

class _ChildDetailsCopyWithImpl<$R, $Out extends ChildDetails>
    extends CopyWithBase<$R, ChildDetails, $Out>
    implements ChildDetailsCopyWith<$R, ChildDetails, $Out> {
  _ChildDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  ChildDetailsCopyWith<$R2, ChildDetails, $Out2>
      chain<$R2, $Out2 extends ChildDetails>(
              Then<ChildDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _ChildDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {String? regNo,
          int? dateOfBirth,
          bool? checkBoxForAddress,
          String? firstName,
          String? lastName,
          String? genderStr,
          String? hospitalName,
          String? informantName,
          String? informantAddress,
          String? placeOfBirth,
          int? dateOfReport}) =>
      $then(ChildDetails(
          regNo: regNo ?? $value.regNo,
          dateOfBirth: dateOfBirth ?? $value.dateOfBirth,
          checkBoxForAddress: checkBoxForAddress ?? $value.checkBoxForAddress,
          firstName: firstName ?? $value.firstName,
          lastName: lastName ?? $value.lastName,
          genderStr: genderStr ?? $value.genderStr,
          hospitalName: hospitalName ?? $value.hospitalName,
          informantName: informantName ?? $value.informantName,
          informantAddress: informantAddress ?? $value.informantAddress,
          placeOfBirth: placeOfBirth ?? $value.placeOfBirth,
          dateOfReport: dateOfReport ?? $value.dateOfReport));
}
