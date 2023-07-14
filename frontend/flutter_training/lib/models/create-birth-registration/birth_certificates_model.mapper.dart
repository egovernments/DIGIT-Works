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
      birthFatherInfo: container.$getOpt(map, 'birthFatherInfo'),
      birthMotherInfo: container.$getOpt(map, 'birthMotherInfo'),
      birthPresentaddr: container.$getOpt(map, 'birthPresentaddr'),
      birthPermaddr: container.$getOpt(map, 'birthPermaddr'),
      registrationno: container.$get(map, 'registrationno'),
      hospitalname: container.$getOpt(map, 'hospitalname'),
      dateofreportepoch: container.$getOpt(map, 'dateofreportepoch'),
      dateofbirthepoch: container.$getOpt(map, 'dateofbirthepoch'),
      genderStr: container.$get(map, 'genderStr'),
      firstname: container.$getOpt(map, 'firstname'),
      lastname: container.$getOpt(map, 'lastname'),
      placeofbirth: container.$getOpt(map, 'placeofbirth'),
      checkboxforaddress: container.$getOpt(map, 'checkboxforaddress'),
      informantsname: container.$getOpt(map, 'informantsname'),
      informantsaddress: container.$getOpt(map, 'informantsaddress'),
      tenantid: container.$get(map, 'tenantid'),
      excelrowindex: container.$getOpt(map, 'excelrowindex'),
      counter: container.$getOpt(map, 'counter'),
      dateofbirth: container.$getOpt(map, 'dateofbirth'),
      dateofreport: container.$getOpt(map, 'dateofreport'));

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
        'counter': container.$enc(b.counter, 'counter'),
        'dateofbirth': container.$enc(b.dateofbirth, 'dateofbirth'),
        'dateofreport': container.$enc(b.dateofreport, 'dateofreport')
      };

  @override
  String stringify(BirthCertificate self) =>
      'BirthCertificate(birthFatherInfo: ${container.asString(self.birthFatherInfo)}, birthMotherInfo: ${container.asString(self.birthMotherInfo)}, birthPresentaddr: ${container.asString(self.birthPresentaddr)}, birthPermaddr: ${container.asString(self.birthPermaddr)}, registrationno: ${container.asString(self.registrationno)}, hospitalname: ${container.asString(self.hospitalname)}, dateofbirth: ${container.asString(self.dateofbirth)}, dateofreportepoch: ${container.asString(self.dateofreportepoch)}, dateofbirthepoch: ${container.asString(self.dateofbirthepoch)}, dateofreport: ${container.asString(self.dateofreport)}, genderStr: ${container.asString(self.genderStr)}, firstname: ${container.asString(self.firstname)}, lastname: ${container.asString(self.lastname)}, placeofbirth: ${container.asString(self.placeofbirth)}, checkboxforaddress: ${container.asString(self.checkboxforaddress)}, informantsname: ${container.asString(self.informantsname)}, informantsaddress: ${container.asString(self.informantsaddress)}, tenantid: ${container.asString(self.tenantid)}, excelrowindex: ${container.asString(self.excelrowindex)}, counter: ${container.asString(self.counter)})';
  @override
  int hash(BirthCertificate self) =>
      container.hash(self.birthFatherInfo) ^
      container.hash(self.birthMotherInfo) ^
      container.hash(self.birthPresentaddr) ^
      container.hash(self.birthPermaddr) ^
      container.hash(self.registrationno) ^
      container.hash(self.hospitalname) ^
      container.hash(self.dateofbirth) ^
      container.hash(self.dateofreportepoch) ^
      container.hash(self.dateofbirthepoch) ^
      container.hash(self.dateofreport) ^
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
      container.isEqual(self.dateofbirth, other.dateofbirth) &&
      container.isEqual(self.dateofreportepoch, other.dateofreportepoch) &&
      container.isEqual(self.dateofbirthepoch, other.dateofbirthepoch) &&
      container.isEqual(self.dateofreport, other.dateofreport) &&
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
  BirthFatherInfoCopyWith<$R, BirthFatherInfo, BirthFatherInfo>?
      get birthFatherInfo;
  BirthMotherInfoCopyWith<$R, BirthMotherInfo, BirthMotherInfo>?
      get birthMotherInfo;
  BirthAddressCopyWith<$R, BirthAddress, BirthAddress>? get birthPresentaddr;
  BirthAddressCopyWith<$R, BirthAddress, BirthAddress>? get birthPermaddr;
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
      int? counter,
      int? dateofbirth,
      int? dateofreport});
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
  BirthFatherInfoCopyWith<$R, BirthFatherInfo, BirthFatherInfo>?
      get birthFatherInfo => $value.birthFatherInfo?.copyWith
          .chain($identity, (v) => call(birthFatherInfo: v));
  @override
  BirthMotherInfoCopyWith<$R, BirthMotherInfo, BirthMotherInfo>?
      get birthMotherInfo => $value.birthMotherInfo?.copyWith
          .chain($identity, (v) => call(birthMotherInfo: v));
  @override
  BirthAddressCopyWith<$R, BirthAddress, BirthAddress>? get birthPresentaddr =>
      $value.birthPresentaddr?.copyWith
          .chain($identity, (v) => call(birthPresentaddr: v));
  @override
  BirthAddressCopyWith<$R, BirthAddress, BirthAddress>? get birthPermaddr =>
      $value.birthPermaddr?.copyWith
          .chain($identity, (v) => call(birthPermaddr: v));
  @override
  $R call(
          {Object? birthFatherInfo = $none,
          Object? birthMotherInfo = $none,
          Object? birthPresentaddr = $none,
          Object? birthPermaddr = $none,
          String? registrationno,
          Object? hospitalname = $none,
          Object? dateofreportepoch = $none,
          Object? dateofbirthepoch = $none,
          String? genderStr,
          Object? firstname = $none,
          Object? lastname = $none,
          Object? placeofbirth = $none,
          Object? checkboxforaddress = $none,
          Object? informantsname = $none,
          Object? informantsaddress = $none,
          String? tenantid,
          Object? excelrowindex = $none,
          Object? counter = $none,
          Object? dateofbirth = $none,
          Object? dateofreport = $none}) =>
      $then(BirthCertificate(
          birthFatherInfo: or(birthFatherInfo, $value.birthFatherInfo),
          birthMotherInfo: or(birthMotherInfo, $value.birthMotherInfo),
          birthPresentaddr: or(birthPresentaddr, $value.birthPresentaddr),
          birthPermaddr: or(birthPermaddr, $value.birthPermaddr),
          registrationno: registrationno ?? $value.registrationno,
          hospitalname: or(hospitalname, $value.hospitalname),
          dateofreportepoch: or(dateofreportepoch, $value.dateofreportepoch),
          dateofbirthepoch: or(dateofbirthepoch, $value.dateofbirthepoch),
          genderStr: genderStr ?? $value.genderStr,
          firstname: or(firstname, $value.firstname),
          lastname: or(lastname, $value.lastname),
          placeofbirth: or(placeofbirth, $value.placeofbirth),
          checkboxforaddress: or(checkboxforaddress, $value.checkboxforaddress),
          informantsname: or(informantsname, $value.informantsname),
          informantsaddress: or(informantsaddress, $value.informantsaddress),
          tenantid: tenantid ?? $value.tenantid,
          excelrowindex: or(excelrowindex, $value.excelrowindex),
          counter: or(counter, $value.counter),
          dateofbirth: or(dateofbirth, $value.dateofbirth),
          dateofreport: or(dateofreport, $value.dateofreport)));
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
      firstname: container.$getOpt(map, 'firstname'),
      lastname: container.$getOpt(map, 'lastname'),
      aadharno: container.$getOpt(map, 'aadharno'),
      mobileno: container.$getOpt(map, 'mobileno'),
      religion: container.$getOpt(map, 'religion'),
      nationality: container.$getOpt(map, 'nationality'));

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
          {Object? firstname = $none,
          Object? lastname = $none,
          Object? aadharno = $none,
          Object? mobileno = $none,
          Object? religion = $none,
          Object? nationality = $none}) =>
      $then(BirthFatherInfo(
          firstname: or(firstname, $value.firstname),
          lastname: or(lastname, $value.lastname),
          aadharno: or(aadharno, $value.aadharno),
          mobileno: or(mobileno, $value.mobileno),
          religion: or(religion, $value.religion),
          nationality: or(nationality, $value.nationality)));
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
      firstname: container.$getOpt(map, 'firstname'),
      lastname: container.$getOpt(map, 'lastname'),
      aadharno: container.$getOpt(map, 'aadharno'),
      mobileno: container.$getOpt(map, 'mobileno'),
      religion: container.$getOpt(map, 'religion'),
      nationality: container.$getOpt(map, 'nationality'));

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
          {Object? firstname = $none,
          Object? lastname = $none,
          Object? aadharno = $none,
          Object? mobileno = $none,
          Object? religion = $none,
          Object? nationality = $none}) =>
      $then(BirthMotherInfo(
          firstname: or(firstname, $value.firstname),
          lastname: or(lastname, $value.lastname),
          aadharno: or(aadharno, $value.aadharno),
          mobileno: or(mobileno, $value.mobileno),
          religion: or(religion, $value.religion),
          nationality: or(nationality, $value.nationality)));
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
      buildingno: container.$getOpt(map, 'buildingno'),
      houseno: container.$getOpt(map, 'houseno'),
      streetname: container.$getOpt(map, 'streetname'),
      locality: container.$getOpt(map, 'locality'),
      tehsil: container.$getOpt(map, 'tehsil'),
      district: container.$getOpt(map, 'district'),
      city: container.$getOpt(map, 'city'),
      state: container.$getOpt(map, 'state'),
      pinno: container.$getOpt(map, 'pinno'),
      country: container.$getOpt(map, 'country'));

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
          {Object? buildingno = $none,
          Object? houseno = $none,
          Object? streetname = $none,
          Object? locality = $none,
          Object? tehsil = $none,
          Object? district = $none,
          Object? city = $none,
          Object? state = $none,
          Object? pinno = $none,
          Object? country = $none}) =>
      $then(BirthAddress(
          buildingno: or(buildingno, $value.buildingno),
          houseno: or(houseno, $value.houseno),
          streetname: or(streetname, $value.streetname),
          locality: or(locality, $value.locality),
          tehsil: or(tehsil, $value.tehsil),
          district: or(district, $value.district),
          city: or(city, $value.city),
          state: or(state, $value.state),
          pinno: or(pinno, $value.pinno),
          country: or(country, $value.country)));
}
