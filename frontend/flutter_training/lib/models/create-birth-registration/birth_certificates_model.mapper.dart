// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'birth_certificates_model.dart';

class BirthCertificatesListMapper extends MapperBase<BirthCertificatesList> {
  static MapperContainer container = MapperContainer(
    mappers: {BirthCertificatesListMapper()},
  )..linkAll({BirthCertificateMapper.container});

  @override
  BirthCertificatesListMapperElement createElement(MapperContainer container) {
    return BirthCertificatesListMapperElement._(this, container);
  }

  @override
  String get id => 'BirthCertificatesList';

  static final fromMap = container.fromMap<BirthCertificatesList>;
  static final fromJson = container.fromJson<BirthCertificatesList>;
}

class BirthCertificatesListMapperElement
    extends MapperElementBase<BirthCertificatesList> {
  BirthCertificatesListMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BirthCertificatesList decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BirthCertificatesList fromMap(Map<String, dynamic> map) =>
      BirthCertificatesList(birthCerts: container.$get(map, 'birthCerts'));

  @override
  Function get encoder => encode;
  dynamic encode(BirthCertificatesList v) => toMap(v);
  Map<String, dynamic> toMap(BirthCertificatesList b) =>
      {'birthCerts': container.$enc(b.birthCerts, 'birthCerts')};

  @override
  String stringify(BirthCertificatesList self) =>
      'BirthCertificatesList(birthCerts: ${container.asString(self.birthCerts)})';
  @override
  int hash(BirthCertificatesList self) => container.hash(self.birthCerts);
  @override
  bool equals(BirthCertificatesList self, BirthCertificatesList other) =>
      container.isEqual(self.birthCerts, other.birthCerts);
}

mixin BirthCertificatesListMappable {
  String toJson() => BirthCertificatesListMapper.container
      .toJson(this as BirthCertificatesList);
  Map<String, dynamic> toMap() => BirthCertificatesListMapper.container
      .toMap(this as BirthCertificatesList);
  BirthCertificatesListCopyWith<BirthCertificatesList, BirthCertificatesList,
          BirthCertificatesList>
      get copyWith => _BirthCertificatesListCopyWithImpl(
          this as BirthCertificatesList, $identity, $identity);
  @override
  String toString() => BirthCertificatesListMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BirthCertificatesListMapper.container.isEqual(this, other));
  @override
  int get hashCode => BirthCertificatesListMapper.container.hash(this);
}

extension BirthCertificatesListValueCopy<$R, $Out extends BirthCertificatesList>
    on ObjectCopyWith<$R, BirthCertificatesList, $Out> {
  BirthCertificatesListCopyWith<$R, BirthCertificatesList, $Out>
      get asBirthCertificatesList =>
          base.as((v, t, t2) => _BirthCertificatesListCopyWithImpl(v, t, t2));
}

typedef BirthCertificatesListCopyWithBound = BirthCertificatesList;

abstract class BirthCertificatesListCopyWith<$R,
        $In extends BirthCertificatesList, $Out extends BirthCertificatesList>
    implements ObjectCopyWith<$R, $In, $Out> {
  BirthCertificatesListCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BirthCertificatesList>(
          Then<BirthCertificatesList, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, BirthCertificate,
          BirthCertificateCopyWith<$R, BirthCertificate, BirthCertificate>>
      get birthCerts;
  $R call({List<BirthCertificate>? birthCerts});
}

class _BirthCertificatesListCopyWithImpl<$R, $Out extends BirthCertificatesList>
    extends CopyWithBase<$R, BirthCertificatesList, $Out>
    implements BirthCertificatesListCopyWith<$R, BirthCertificatesList, $Out> {
  _BirthCertificatesListCopyWithImpl(super.value, super.then, super.then2);
  @override
  BirthCertificatesListCopyWith<$R2, BirthCertificatesList, $Out2>
      chain<$R2, $Out2 extends BirthCertificatesList>(
              Then<BirthCertificatesList, $Out2> t, Then<$Out2, $R2> t2) =>
          _BirthCertificatesListCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, BirthCertificate,
          BirthCertificateCopyWith<$R, BirthCertificate, BirthCertificate>>
      get birthCerts => ListCopyWith(
          $value.birthCerts,
          (v, t) => v.copyWith.chain<$R, BirthCertificate>($identity, t),
          (v) => call(birthCerts: v));
  @override
  $R call({List<BirthCertificate>? birthCerts}) =>
      $then(BirthCertificatesList(birthCerts: birthCerts ?? $value.birthCerts));
}

class BirthCertificateMapper extends MapperBase<BirthCertificate> {
  static MapperContainer container = MapperContainer(
    mappers: {BirthCertificateMapper()},
  )..linkAll({
      BirthFatherInfoMapper.container,
      BirthMotherInfoMapper.container,
      BirthAddressMapper.container,
    });

  @override
  BirthCertificateMapperElement createElement(MapperContainer container) {
    return BirthCertificateMapperElement._(this, container);
  }

  @override
  String get id => 'BirthCertificate';

  static final fromMap = container.fromMap<BirthCertificate>;
  static final fromJson = container.fromJson<BirthCertificate>;
}

class BirthCertificateMapperElement
    extends MapperElementBase<BirthCertificate> {
  BirthCertificateMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BirthCertificate decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BirthCertificate fromMap(Map<String, dynamic> map) => BirthCertificate(
      birthFatherInfo: container.$get(map, 'birthFatherInfo'),
      birthMotherInfo: container.$get(map, 'birthMotherInfo'),
      birthPresentaddr: container.$get(map, 'birthPresentaddr'),
      birthPermaddr: container.$get(map, 'birthPermaddr'),
      registrationno: container.$get(map, 'registrationno'),
      hospitalname: container.$get(map, 'hospitalname'),
      dateofreportepoch: container.$get(map, 'dateofreportepoch'),
      dateofbirthepoch: container.$get(map, 'dateofbirthepoch'),
      genderStr: container.$get(map, 'genderStr'),
      firstname: container.$get(map, 'firstname'),
      lastname: container.$get(map, 'lastname'),
      placeofbirth: container.$get(map, 'placeofbirth'),
      checkboxforaddress: container.$get(map, 'checkboxforaddress'),
      informantsname: container.$get(map, 'informantsname'),
      informantsaddress: container.$get(map, 'informantsaddress'),
      tenantid: container.$get(map, 'tenantid'),
      excelrowindex: container.$get(map, 'excelrowindex'),
      counter: container.$get(map, 'counter'));

  @override
  Function get encoder => encode;
  dynamic encode(BirthCertificate v) => toMap(v);
  Map<String, dynamic> toMap(BirthCertificate b) => {
        'birthFatherInfo': container.$enc(b.birthFatherInfo, 'birthFatherInfo'),
        'birthMotherInfo': container.$enc(b.birthMotherInfo, 'birthMotherInfo'),
        'birthPresentaddr':
            container.$enc(b.birthPresentaddr, 'birthPresentaddr'),
        'birthPermaddr': container.$enc(b.birthPermaddr, 'birthPermaddr'),
        'registrationno': container.$enc(b.registrationno, 'registrationno'),
        'hospitalname': container.$enc(b.hospitalname, 'hospitalname'),
        'dateofreportepoch':
            container.$enc(b.dateofreportepoch, 'dateofreportepoch'),
        'dateofbirthepoch':
            container.$enc(b.dateofbirthepoch, 'dateofbirthepoch'),
        'genderStr': container.$enc(b.genderStr, 'genderStr'),
        'firstname': container.$enc(b.firstname, 'firstname'),
        'lastname': container.$enc(b.lastname, 'lastname'),
        'placeofbirth': container.$enc(b.placeofbirth, 'placeofbirth'),
        'checkboxforaddress':
            container.$enc(b.checkboxforaddress, 'checkboxforaddress'),
        'informantsname': container.$enc(b.informantsname, 'informantsname'),
        'informantsaddress':
            container.$enc(b.informantsaddress, 'informantsaddress'),
        'tenantid': container.$enc(b.tenantid, 'tenantid'),
        'excelrowindex': container.$enc(b.excelrowindex, 'excelrowindex'),
        'counter': container.$enc(b.counter, 'counter')
      };

  @override
  String stringify(BirthCertificate self) =>
      'BirthCertificate(birthFatherInfo: ${container.asString(self.birthFatherInfo)}, birthMotherInfo: ${container.asString(self.birthMotherInfo)}, birthPresentaddr: ${container.asString(self.birthPresentaddr)}, birthPermaddr: ${container.asString(self.birthPermaddr)}, registrationno: ${container.asString(self.registrationno)}, hospitalname: ${container.asString(self.hospitalname)}, dateofreportepoch: ${container.asString(self.dateofreportepoch)}, dateofbirthepoch: ${container.asString(self.dateofbirthepoch)}, genderStr: ${container.asString(self.genderStr)}, firstname: ${container.asString(self.firstname)}, lastname: ${container.asString(self.lastname)}, placeofbirth: ${container.asString(self.placeofbirth)}, checkboxforaddress: ${container.asString(self.checkboxforaddress)}, informantsname: ${container.asString(self.informantsname)}, informantsaddress: ${container.asString(self.informantsaddress)}, tenantid: ${container.asString(self.tenantid)}, excelrowindex: ${container.asString(self.excelrowindex)}, counter: ${container.asString(self.counter)})';
  @override
  int hash(BirthCertificate self) =>
      container.hash(self.birthFatherInfo) ^
      container.hash(self.birthMotherInfo) ^
      container.hash(self.birthPresentaddr) ^
      container.hash(self.birthPermaddr) ^
      container.hash(self.registrationno) ^
      container.hash(self.hospitalname) ^
      container.hash(self.dateofreportepoch) ^
      container.hash(self.dateofbirthepoch) ^
      container.hash(self.genderStr) ^
      container.hash(self.firstname) ^
      container.hash(self.lastname) ^
      container.hash(self.placeofbirth) ^
      container.hash(self.checkboxforaddress) ^
      container.hash(self.informantsname) ^
      container.hash(self.informantsaddress) ^
      container.hash(self.tenantid) ^
      container.hash(self.excelrowindex) ^
      container.hash(self.counter);
  @override
  bool equals(BirthCertificate self, BirthCertificate other) =>
      container.isEqual(self.birthFatherInfo, other.birthFatherInfo) &&
      container.isEqual(self.birthMotherInfo, other.birthMotherInfo) &&
      container.isEqual(self.birthPresentaddr, other.birthPresentaddr) &&
      container.isEqual(self.birthPermaddr, other.birthPermaddr) &&
      container.isEqual(self.registrationno, other.registrationno) &&
      container.isEqual(self.hospitalname, other.hospitalname) &&
      container.isEqual(self.dateofreportepoch, other.dateofreportepoch) &&
      container.isEqual(self.dateofbirthepoch, other.dateofbirthepoch) &&
      container.isEqual(self.genderStr, other.genderStr) &&
      container.isEqual(self.firstname, other.firstname) &&
      container.isEqual(self.lastname, other.lastname) &&
      container.isEqual(self.placeofbirth, other.placeofbirth) &&
      container.isEqual(self.checkboxforaddress, other.checkboxforaddress) &&
      container.isEqual(self.informantsname, other.informantsname) &&
      container.isEqual(self.informantsaddress, other.informantsaddress) &&
      container.isEqual(self.tenantid, other.tenantid) &&
      container.isEqual(self.excelrowindex, other.excelrowindex) &&
      container.isEqual(self.counter, other.counter);
}

mixin BirthCertificateMappable {
  String toJson() =>
      BirthCertificateMapper.container.toJson(this as BirthCertificate);
  Map<String, dynamic> toMap() =>
      BirthCertificateMapper.container.toMap(this as BirthCertificate);
  BirthCertificateCopyWith<BirthCertificate, BirthCertificate, BirthCertificate>
      get copyWith => _BirthCertificateCopyWithImpl(
          this as BirthCertificate, $identity, $identity);
  @override
  String toString() => BirthCertificateMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BirthCertificateMapper.container.isEqual(this, other));
  @override
  int get hashCode => BirthCertificateMapper.container.hash(this);
}

extension BirthCertificateValueCopy<$R, $Out extends BirthCertificate>
    on ObjectCopyWith<$R, BirthCertificate, $Out> {
  BirthCertificateCopyWith<$R, BirthCertificate, $Out> get asBirthCertificate =>
      base.as((v, t, t2) => _BirthCertificateCopyWithImpl(v, t, t2));
}

typedef BirthCertificateCopyWithBound = BirthCertificate;

abstract class BirthCertificateCopyWith<$R, $In extends BirthCertificate,
    $Out extends BirthCertificate> implements ObjectCopyWith<$R, $In, $Out> {
  BirthCertificateCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BirthCertificate>(
          Then<BirthCertificate, $Out2> t, Then<$Out2, $R2> t2);
  BirthFatherInfoCopyWith<$R, BirthFatherInfo, BirthFatherInfo>
      get birthFatherInfo;
  BirthMotherInfoCopyWith<$R, BirthMotherInfo, BirthMotherInfo>
      get birthMotherInfo;
  BirthAddressCopyWith<$R, BirthAddress, BirthAddress> get birthPresentaddr;
  BirthAddressCopyWith<$R, BirthAddress, BirthAddress> get birthPermaddr;
  $R call(
      {BirthFatherInfo? birthFatherInfo,
      BirthMotherInfo? birthMotherInfo,
      BirthAddress? birthPresentaddr,
      BirthAddress? birthPermaddr,
      String? registrationno,
      String? hospitalname,
      int? dateofreportepoch,
      int? dateofbirthepoch,
      String? genderStr,
      String? firstname,
      String? lastname,
      String? placeofbirth,
      bool? checkboxforaddress,
      String? informantsname,
      String? informantsaddress,
      String? tenantid,
      int? excelrowindex,
      int? counter});
}

class _BirthCertificateCopyWithImpl<$R, $Out extends BirthCertificate>
    extends CopyWithBase<$R, BirthCertificate, $Out>
    implements BirthCertificateCopyWith<$R, BirthCertificate, $Out> {
  _BirthCertificateCopyWithImpl(super.value, super.then, super.then2);
  @override
  BirthCertificateCopyWith<$R2, BirthCertificate, $Out2>
      chain<$R2, $Out2 extends BirthCertificate>(
              Then<BirthCertificate, $Out2> t, Then<$Out2, $R2> t2) =>
          _BirthCertificateCopyWithImpl($value, t, t2);

  @override
  BirthFatherInfoCopyWith<$R, BirthFatherInfo, BirthFatherInfo>
      get birthFatherInfo => $value.birthFatherInfo.copyWith
          .chain($identity, (v) => call(birthFatherInfo: v));
  @override
  BirthMotherInfoCopyWith<$R, BirthMotherInfo, BirthMotherInfo>
      get birthMotherInfo => $value.birthMotherInfo.copyWith
          .chain($identity, (v) => call(birthMotherInfo: v));
  @override
  BirthAddressCopyWith<$R, BirthAddress, BirthAddress> get birthPresentaddr =>
      $value.birthPresentaddr.copyWith
          .chain($identity, (v) => call(birthPresentaddr: v));
  @override
  BirthAddressCopyWith<$R, BirthAddress, BirthAddress> get birthPermaddr =>
      $value.birthPermaddr.copyWith
          .chain($identity, (v) => call(birthPermaddr: v));
  @override
  $R call(
          {BirthFatherInfo? birthFatherInfo,
          BirthMotherInfo? birthMotherInfo,
          BirthAddress? birthPresentaddr,
          BirthAddress? birthPermaddr,
          String? registrationno,
          String? hospitalname,
          int? dateofreportepoch,
          int? dateofbirthepoch,
          String? genderStr,
          String? firstname,
          String? lastname,
          String? placeofbirth,
          bool? checkboxforaddress,
          String? informantsname,
          String? informantsaddress,
          String? tenantid,
          int? excelrowindex,
          int? counter}) =>
      $then(BirthCertificate(
          birthFatherInfo: birthFatherInfo ?? $value.birthFatherInfo,
          birthMotherInfo: birthMotherInfo ?? $value.birthMotherInfo,
          birthPresentaddr: birthPresentaddr ?? $value.birthPresentaddr,
          birthPermaddr: birthPermaddr ?? $value.birthPermaddr,
          registrationno: registrationno ?? $value.registrationno,
          hospitalname: hospitalname ?? $value.hospitalname,
          dateofreportepoch: dateofreportepoch ?? $value.dateofreportepoch,
          dateofbirthepoch: dateofbirthepoch ?? $value.dateofbirthepoch,
          genderStr: genderStr ?? $value.genderStr,
          firstname: firstname ?? $value.firstname,
          lastname: lastname ?? $value.lastname,
          placeofbirth: placeofbirth ?? $value.placeofbirth,
          checkboxforaddress: checkboxforaddress ?? $value.checkboxforaddress,
          informantsname: informantsname ?? $value.informantsname,
          informantsaddress: informantsaddress ?? $value.informantsaddress,
          tenantid: tenantid ?? $value.tenantid,
          excelrowindex: excelrowindex ?? $value.excelrowindex,
          counter: counter ?? $value.counter));
}

class BirthFatherInfoMapper extends MapperBase<BirthFatherInfo> {
  static MapperContainer container = MapperContainer(
    mappers: {BirthFatherInfoMapper()},
  );

  @override
  BirthFatherInfoMapperElement createElement(MapperContainer container) {
    return BirthFatherInfoMapperElement._(this, container);
  }

  @override
  String get id => 'BirthFatherInfo';

  static final fromMap = container.fromMap<BirthFatherInfo>;
  static final fromJson = container.fromJson<BirthFatherInfo>;
}

class BirthFatherInfoMapperElement extends MapperElementBase<BirthFatherInfo> {
  BirthFatherInfoMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BirthFatherInfo decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BirthFatherInfo fromMap(Map<String, dynamic> map) => BirthFatherInfo(
      firstname: container.$get(map, 'firstname'),
      lastname: container.$get(map, 'lastname'),
      aadharno: container.$get(map, 'aadharno'),
      mobileno: container.$get(map, 'mobileno'),
      religion: container.$get(map, 'religion'),
      nationality: container.$get(map, 'nationality'));

  @override
  Function get encoder => encode;
  dynamic encode(BirthFatherInfo v) => toMap(v);
  Map<String, dynamic> toMap(BirthFatherInfo b) => {
        'firstname': container.$enc(b.firstname, 'firstname'),
        'lastname': container.$enc(b.lastname, 'lastname'),
        'aadharno': container.$enc(b.aadharno, 'aadharno'),
        'mobileno': container.$enc(b.mobileno, 'mobileno'),
        'religion': container.$enc(b.religion, 'religion'),
        'nationality': container.$enc(b.nationality, 'nationality')
      };

  @override
  String stringify(BirthFatherInfo self) =>
      'BirthFatherInfo(firstname: ${container.asString(self.firstname)}, lastname: ${container.asString(self.lastname)}, aadharno: ${container.asString(self.aadharno)}, mobileno: ${container.asString(self.mobileno)}, religion: ${container.asString(self.religion)}, nationality: ${container.asString(self.nationality)})';
  @override
  int hash(BirthFatherInfo self) =>
      container.hash(self.firstname) ^
      container.hash(self.lastname) ^
      container.hash(self.aadharno) ^
      container.hash(self.mobileno) ^
      container.hash(self.religion) ^
      container.hash(self.nationality);
  @override
  bool equals(BirthFatherInfo self, BirthFatherInfo other) =>
      container.isEqual(self.firstname, other.firstname) &&
      container.isEqual(self.lastname, other.lastname) &&
      container.isEqual(self.aadharno, other.aadharno) &&
      container.isEqual(self.mobileno, other.mobileno) &&
      container.isEqual(self.religion, other.religion) &&
      container.isEqual(self.nationality, other.nationality);
}

mixin BirthFatherInfoMappable {
  String toJson() =>
      BirthFatherInfoMapper.container.toJson(this as BirthFatherInfo);
  Map<String, dynamic> toMap() =>
      BirthFatherInfoMapper.container.toMap(this as BirthFatherInfo);
  BirthFatherInfoCopyWith<BirthFatherInfo, BirthFatherInfo, BirthFatherInfo>
      get copyWith => _BirthFatherInfoCopyWithImpl(
          this as BirthFatherInfo, $identity, $identity);
  @override
  String toString() => BirthFatherInfoMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BirthFatherInfoMapper.container.isEqual(this, other));
  @override
  int get hashCode => BirthFatherInfoMapper.container.hash(this);
}

extension BirthFatherInfoValueCopy<$R, $Out extends BirthFatherInfo>
    on ObjectCopyWith<$R, BirthFatherInfo, $Out> {
  BirthFatherInfoCopyWith<$R, BirthFatherInfo, $Out> get asBirthFatherInfo =>
      base.as((v, t, t2) => _BirthFatherInfoCopyWithImpl(v, t, t2));
}

typedef BirthFatherInfoCopyWithBound = BirthFatherInfo;

abstract class BirthFatherInfoCopyWith<$R, $In extends BirthFatherInfo,
    $Out extends BirthFatherInfo> implements ObjectCopyWith<$R, $In, $Out> {
  BirthFatherInfoCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BirthFatherInfo>(
          Then<BirthFatherInfo, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? firstname,
      String? lastname,
      String? aadharno,
      String? mobileno,
      String? religion,
      String? nationality});
}

class _BirthFatherInfoCopyWithImpl<$R, $Out extends BirthFatherInfo>
    extends CopyWithBase<$R, BirthFatherInfo, $Out>
    implements BirthFatherInfoCopyWith<$R, BirthFatherInfo, $Out> {
  _BirthFatherInfoCopyWithImpl(super.value, super.then, super.then2);
  @override
  BirthFatherInfoCopyWith<$R2, BirthFatherInfo, $Out2>
      chain<$R2, $Out2 extends BirthFatherInfo>(
              Then<BirthFatherInfo, $Out2> t, Then<$Out2, $R2> t2) =>
          _BirthFatherInfoCopyWithImpl($value, t, t2);

  @override
  $R call(
          {String? firstname,
          String? lastname,
          String? aadharno,
          String? mobileno,
          String? religion,
          String? nationality}) =>
      $then(BirthFatherInfo(
          firstname: firstname ?? $value.firstname,
          lastname: lastname ?? $value.lastname,
          aadharno: aadharno ?? $value.aadharno,
          mobileno: mobileno ?? $value.mobileno,
          religion: religion ?? $value.religion,
          nationality: nationality ?? $value.nationality));
}

class BirthMotherInfoMapper extends MapperBase<BirthMotherInfo> {
  static MapperContainer container = MapperContainer(
    mappers: {BirthMotherInfoMapper()},
  );

  @override
  BirthMotherInfoMapperElement createElement(MapperContainer container) {
    return BirthMotherInfoMapperElement._(this, container);
  }

  @override
  String get id => 'BirthMotherInfo';

  static final fromMap = container.fromMap<BirthMotherInfo>;
  static final fromJson = container.fromJson<BirthMotherInfo>;
}

class BirthMotherInfoMapperElement extends MapperElementBase<BirthMotherInfo> {
  BirthMotherInfoMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BirthMotherInfo decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BirthMotherInfo fromMap(Map<String, dynamic> map) => BirthMotherInfo(
      firstname: container.$get(map, 'firstname'),
      lastname: container.$get(map, 'lastname'),
      aadharno: container.$get(map, 'aadharno'),
      mobileno: container.$get(map, 'mobileno'),
      religion: container.$get(map, 'religion'),
      nationality: container.$get(map, 'nationality'));

  @override
  Function get encoder => encode;
  dynamic encode(BirthMotherInfo v) => toMap(v);
  Map<String, dynamic> toMap(BirthMotherInfo b) => {
        'firstname': container.$enc(b.firstname, 'firstname'),
        'lastname': container.$enc(b.lastname, 'lastname'),
        'aadharno': container.$enc(b.aadharno, 'aadharno'),
        'mobileno': container.$enc(b.mobileno, 'mobileno'),
        'religion': container.$enc(b.religion, 'religion'),
        'nationality': container.$enc(b.nationality, 'nationality')
      };

  @override
  String stringify(BirthMotherInfo self) =>
      'BirthMotherInfo(firstname: ${container.asString(self.firstname)}, lastname: ${container.asString(self.lastname)}, aadharno: ${container.asString(self.aadharno)}, mobileno: ${container.asString(self.mobileno)}, religion: ${container.asString(self.religion)}, nationality: ${container.asString(self.nationality)})';
  @override
  int hash(BirthMotherInfo self) =>
      container.hash(self.firstname) ^
      container.hash(self.lastname) ^
      container.hash(self.aadharno) ^
      container.hash(self.mobileno) ^
      container.hash(self.religion) ^
      container.hash(self.nationality);
  @override
  bool equals(BirthMotherInfo self, BirthMotherInfo other) =>
      container.isEqual(self.firstname, other.firstname) &&
      container.isEqual(self.lastname, other.lastname) &&
      container.isEqual(self.aadharno, other.aadharno) &&
      container.isEqual(self.mobileno, other.mobileno) &&
      container.isEqual(self.religion, other.religion) &&
      container.isEqual(self.nationality, other.nationality);
}

mixin BirthMotherInfoMappable {
  String toJson() =>
      BirthMotherInfoMapper.container.toJson(this as BirthMotherInfo);
  Map<String, dynamic> toMap() =>
      BirthMotherInfoMapper.container.toMap(this as BirthMotherInfo);
  BirthMotherInfoCopyWith<BirthMotherInfo, BirthMotherInfo, BirthMotherInfo>
      get copyWith => _BirthMotherInfoCopyWithImpl(
          this as BirthMotherInfo, $identity, $identity);
  @override
  String toString() => BirthMotherInfoMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BirthMotherInfoMapper.container.isEqual(this, other));
  @override
  int get hashCode => BirthMotherInfoMapper.container.hash(this);
}

extension BirthMotherInfoValueCopy<$R, $Out extends BirthMotherInfo>
    on ObjectCopyWith<$R, BirthMotherInfo, $Out> {
  BirthMotherInfoCopyWith<$R, BirthMotherInfo, $Out> get asBirthMotherInfo =>
      base.as((v, t, t2) => _BirthMotherInfoCopyWithImpl(v, t, t2));
}

typedef BirthMotherInfoCopyWithBound = BirthMotherInfo;

abstract class BirthMotherInfoCopyWith<$R, $In extends BirthMotherInfo,
    $Out extends BirthMotherInfo> implements ObjectCopyWith<$R, $In, $Out> {
  BirthMotherInfoCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BirthMotherInfo>(
          Then<BirthMotherInfo, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? firstname,
      String? lastname,
      String? aadharno,
      String? mobileno,
      String? religion,
      String? nationality});
}

class _BirthMotherInfoCopyWithImpl<$R, $Out extends BirthMotherInfo>
    extends CopyWithBase<$R, BirthMotherInfo, $Out>
    implements BirthMotherInfoCopyWith<$R, BirthMotherInfo, $Out> {
  _BirthMotherInfoCopyWithImpl(super.value, super.then, super.then2);
  @override
  BirthMotherInfoCopyWith<$R2, BirthMotherInfo, $Out2>
      chain<$R2, $Out2 extends BirthMotherInfo>(
              Then<BirthMotherInfo, $Out2> t, Then<$Out2, $R2> t2) =>
          _BirthMotherInfoCopyWithImpl($value, t, t2);

  @override
  $R call(
          {String? firstname,
          String? lastname,
          String? aadharno,
          String? mobileno,
          String? religion,
          String? nationality}) =>
      $then(BirthMotherInfo(
          firstname: firstname ?? $value.firstname,
          lastname: lastname ?? $value.lastname,
          aadharno: aadharno ?? $value.aadharno,
          mobileno: mobileno ?? $value.mobileno,
          religion: religion ?? $value.religion,
          nationality: nationality ?? $value.nationality));
}

class BirthAddressMapper extends MapperBase<BirthAddress> {
  static MapperContainer container = MapperContainer(
    mappers: {BirthAddressMapper()},
  );

  @override
  BirthAddressMapperElement createElement(MapperContainer container) {
    return BirthAddressMapperElement._(this, container);
  }

  @override
  String get id => 'BirthAddress';

  static final fromMap = container.fromMap<BirthAddress>;
  static final fromJson = container.fromJson<BirthAddress>;
}

class BirthAddressMapperElement extends MapperElementBase<BirthAddress> {
  BirthAddressMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BirthAddress decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BirthAddress fromMap(Map<String, dynamic> map) => BirthAddress(
      buildingno: container.$get(map, 'buildingno'),
      houseno: container.$get(map, 'houseno'),
      streetname: container.$get(map, 'streetname'),
      locality: container.$get(map, 'locality'),
      tehsil: container.$get(map, 'tehsil'),
      district: container.$get(map, 'district'),
      city: container.$get(map, 'city'),
      state: container.$get(map, 'state'),
      pinno: container.$get(map, 'pinno'),
      country: container.$get(map, 'country'));

  @override
  Function get encoder => encode;
  dynamic encode(BirthAddress v) => toMap(v);
  Map<String, dynamic> toMap(BirthAddress b) => {
        'buildingno': container.$enc(b.buildingno, 'buildingno'),
        'houseno': container.$enc(b.houseno, 'houseno'),
        'streetname': container.$enc(b.streetname, 'streetname'),
        'locality': container.$enc(b.locality, 'locality'),
        'tehsil': container.$enc(b.tehsil, 'tehsil'),
        'district': container.$enc(b.district, 'district'),
        'city': container.$enc(b.city, 'city'),
        'state': container.$enc(b.state, 'state'),
        'pinno': container.$enc(b.pinno, 'pinno'),
        'country': container.$enc(b.country, 'country')
      };

  @override
  String stringify(BirthAddress self) =>
      'BirthAddress(buildingno: ${container.asString(self.buildingno)}, houseno: ${container.asString(self.houseno)}, streetname: ${container.asString(self.streetname)}, locality: ${container.asString(self.locality)}, tehsil: ${container.asString(self.tehsil)}, district: ${container.asString(self.district)}, city: ${container.asString(self.city)}, state: ${container.asString(self.state)}, pinno: ${container.asString(self.pinno)}, country: ${container.asString(self.country)})';
  @override
  int hash(BirthAddress self) =>
      container.hash(self.buildingno) ^
      container.hash(self.houseno) ^
      container.hash(self.streetname) ^
      container.hash(self.locality) ^
      container.hash(self.tehsil) ^
      container.hash(self.district) ^
      container.hash(self.city) ^
      container.hash(self.state) ^
      container.hash(self.pinno) ^
      container.hash(self.country);
  @override
  bool equals(BirthAddress self, BirthAddress other) =>
      container.isEqual(self.buildingno, other.buildingno) &&
      container.isEqual(self.houseno, other.houseno) &&
      container.isEqual(self.streetname, other.streetname) &&
      container.isEqual(self.locality, other.locality) &&
      container.isEqual(self.tehsil, other.tehsil) &&
      container.isEqual(self.district, other.district) &&
      container.isEqual(self.city, other.city) &&
      container.isEqual(self.state, other.state) &&
      container.isEqual(self.pinno, other.pinno) &&
      container.isEqual(self.country, other.country);
}

mixin BirthAddressMappable {
  String toJson() => BirthAddressMapper.container.toJson(this as BirthAddress);
  Map<String, dynamic> toMap() =>
      BirthAddressMapper.container.toMap(this as BirthAddress);
  BirthAddressCopyWith<BirthAddress, BirthAddress, BirthAddress> get copyWith =>
      _BirthAddressCopyWithImpl(this as BirthAddress, $identity, $identity);
  @override
  String toString() => BirthAddressMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BirthAddressMapper.container.isEqual(this, other));
  @override
  int get hashCode => BirthAddressMapper.container.hash(this);
}

extension BirthAddressValueCopy<$R, $Out extends BirthAddress>
    on ObjectCopyWith<$R, BirthAddress, $Out> {
  BirthAddressCopyWith<$R, BirthAddress, $Out> get asBirthAddress =>
      base.as((v, t, t2) => _BirthAddressCopyWithImpl(v, t, t2));
}

typedef BirthAddressCopyWithBound = BirthAddress;

abstract class BirthAddressCopyWith<$R, $In extends BirthAddress,
    $Out extends BirthAddress> implements ObjectCopyWith<$R, $In, $Out> {
  BirthAddressCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends BirthAddress>(
      Then<BirthAddress, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? buildingno,
      String? houseno,
      String? streetname,
      String? locality,
      String? tehsil,
      String? district,
      String? city,
      String? state,
      String? pinno,
      String? country});
}

class _BirthAddressCopyWithImpl<$R, $Out extends BirthAddress>
    extends CopyWithBase<$R, BirthAddress, $Out>
    implements BirthAddressCopyWith<$R, BirthAddress, $Out> {
  _BirthAddressCopyWithImpl(super.value, super.then, super.then2);
  @override
  BirthAddressCopyWith<$R2, BirthAddress, $Out2>
      chain<$R2, $Out2 extends BirthAddress>(
              Then<BirthAddress, $Out2> t, Then<$Out2, $R2> t2) =>
          _BirthAddressCopyWithImpl($value, t, t2);

  @override
  $R call(
          {String? buildingno,
          String? houseno,
          String? streetname,
          String? locality,
          String? tehsil,
          String? district,
          String? city,
          String? state,
          String? pinno,
          String? country}) =>
      $then(BirthAddress(
          buildingno: buildingno ?? $value.buildingno,
          houseno: houseno ?? $value.houseno,
          streetname: streetname ?? $value.streetname,
          locality: locality ?? $value.locality,
          tehsil: tehsil ?? $value.tehsil,
          district: district ?? $value.district,
          city: city ?? $value.city,
          state: state ?? $value.state,
          pinno: pinno ?? $value.pinno,
          country: country ?? $value.country));
}
