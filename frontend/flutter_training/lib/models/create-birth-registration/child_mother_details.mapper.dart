// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'child_mother_details.dart';

class ChildMotherDetailsMapper extends MapperBase<ChildMotherDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {ChildMotherDetailsMapper()},
  );

  @override
  ChildMotherDetailsMapperElement createElement(MapperContainer container) {
    return ChildMotherDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'ChildMotherDetails';

  static final fromMap = container.fromMap<ChildMotherDetails>;
  static final fromJson = container.fromJson<ChildMotherDetails>;
}

class ChildMotherDetailsMapperElement
    extends MapperElementBase<ChildMotherDetails> {
  ChildMotherDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ChildMotherDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ChildMotherDetails fromMap(Map<String, dynamic> map) => ChildMotherDetails(
      firstName: container.$get(map, 'firstName'),
      lastName: container.$get(map, 'lastName'),
      aadhaarNo: container.$getOpt(map, 'aadhaarNo'),
      mobileNo: container.$getOpt(map, 'mobileNo'),
      nationality: container.$get(map, 'nationality'),
      religion: container.$getOpt(map, 'religion'));

  @override
  Function get encoder => encode;
  dynamic encode(ChildMotherDetails v) => toMap(v);
  Map<String, dynamic> toMap(ChildMotherDetails c) => {
        'firstName': container.$enc(c.firstName, 'firstName'),
        'lastName': container.$enc(c.lastName, 'lastName'),
        'aadhaarNo': container.$enc(c.aadhaarNo, 'aadhaarNo'),
        'mobileNo': container.$enc(c.mobileNo, 'mobileNo'),
        'nationality': container.$enc(c.nationality, 'nationality'),
        'religion': container.$enc(c.religion, 'religion')
      };

  @override
  String stringify(ChildMotherDetails self) =>
      'ChildMotherDetails(firstName: ${container.asString(self.firstName)}, lastName: ${container.asString(self.lastName)}, religion: ${container.asString(self.religion)}, nationality: ${container.asString(self.nationality)}, aadhaarNo: ${container.asString(self.aadhaarNo)}, mobileNo: ${container.asString(self.mobileNo)})';
  @override
  int hash(ChildMotherDetails self) =>
      container.hash(self.firstName) ^
      container.hash(self.lastName) ^
      container.hash(self.religion) ^
      container.hash(self.nationality) ^
      container.hash(self.aadhaarNo) ^
      container.hash(self.mobileNo);
  @override
  bool equals(ChildMotherDetails self, ChildMotherDetails other) =>
      container.isEqual(self.firstName, other.firstName) &&
      container.isEqual(self.lastName, other.lastName) &&
      container.isEqual(self.religion, other.religion) &&
      container.isEqual(self.nationality, other.nationality) &&
      container.isEqual(self.aadhaarNo, other.aadhaarNo) &&
      container.isEqual(self.mobileNo, other.mobileNo);
}

mixin ChildMotherDetailsMappable {
  String toJson() =>
      ChildMotherDetailsMapper.container.toJson(this as ChildMotherDetails);
  Map<String, dynamic> toMap() =>
      ChildMotherDetailsMapper.container.toMap(this as ChildMotherDetails);
  ChildMotherDetailsCopyWith<ChildMotherDetails, ChildMotherDetails,
          ChildMotherDetails>
      get copyWith => _ChildMotherDetailsCopyWithImpl(
          this as ChildMotherDetails, $identity, $identity);
  @override
  String toString() => ChildMotherDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ChildMotherDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => ChildMotherDetailsMapper.container.hash(this);
}

extension ChildMotherDetailsValueCopy<$R, $Out extends ChildMotherDetails>
    on ObjectCopyWith<$R, ChildMotherDetails, $Out> {
  ChildMotherDetailsCopyWith<$R, ChildMotherDetails, $Out>
      get asChildMotherDetails =>
          base.as((v, t, t2) => _ChildMotherDetailsCopyWithImpl(v, t, t2));
}

typedef ChildMotherDetailsCopyWithBound = ChildMotherDetails;

abstract class ChildMotherDetailsCopyWith<$R, $In extends ChildMotherDetails,
    $Out extends ChildMotherDetails> implements ObjectCopyWith<$R, $In, $Out> {
  ChildMotherDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ChildMotherDetails>(
          Then<ChildMotherDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? firstName,
      String? lastName,
      String? aadhaarNo,
      String? mobileNo,
      String? nationality,
      String? religion});
}

class _ChildMotherDetailsCopyWithImpl<$R, $Out extends ChildMotherDetails>
    extends CopyWithBase<$R, ChildMotherDetails, $Out>
    implements ChildMotherDetailsCopyWith<$R, ChildMotherDetails, $Out> {
  _ChildMotherDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  ChildMotherDetailsCopyWith<$R2, ChildMotherDetails, $Out2>
      chain<$R2, $Out2 extends ChildMotherDetails>(
              Then<ChildMotherDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _ChildMotherDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {String? firstName,
          String? lastName,
          Object? aadhaarNo = $none,
          Object? mobileNo = $none,
          String? nationality,
          Object? religion = $none}) =>
      $then(ChildMotherDetails(
          firstName: firstName ?? $value.firstName,
          lastName: lastName ?? $value.lastName,
          aadhaarNo: or(aadhaarNo, $value.aadhaarNo),
          mobileNo: or(mobileNo, $value.mobileNo),
          nationality: nationality ?? $value.nationality,
          religion: or(religion, $value.religion)));
}
