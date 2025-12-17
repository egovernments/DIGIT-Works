// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'mb_detail_response.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

MBDetailResponse _$MBDetailResponseFromJson(Map<String, dynamic> json) {
  return _MBDetailResponse.fromJson(json);
}

/// @nodoc
mixin _$MBDetailResponse {
  @JsonKey(name: 'contract')
  Contract? get contract => throw _privateConstructorUsedError;
  @JsonKey(name: 'estimate')
  Estimate? get estimate => throw _privateConstructorUsedError;
  @JsonKey(name: 'period')
  Period? get period => throw _privateConstructorUsedError;
  @JsonKey(name: 'allMeasurements')
  dynamic get allMeasurements => throw _privateConstructorUsedError;
  @JsonKey(name: 'measurement')
  Measurement? get measurement => throw _privateConstructorUsedError;
  @JsonKey(name: 'musterRolls')
  dynamic get musterRolls => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MBDetailResponseCopyWith<MBDetailResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MBDetailResponseCopyWith<$Res> {
  factory $MBDetailResponseCopyWith(
          MBDetailResponse value, $Res Function(MBDetailResponse) then) =
      _$MBDetailResponseCopyWithImpl<$Res, MBDetailResponse>;
  @useResult
  $Res call(
      {@JsonKey(name: 'contract') Contract? contract,
      @JsonKey(name: 'estimate') Estimate? estimate,
      @JsonKey(name: 'period') Period? period,
      @JsonKey(name: 'allMeasurements') dynamic allMeasurements,
      @JsonKey(name: 'measurement') Measurement? measurement,
      @JsonKey(name: 'musterRolls') dynamic musterRolls});

  $ContractCopyWith<$Res>? get contract;
  $EstimateCopyWith<$Res>? get estimate;
  $PeriodCopyWith<$Res>? get period;
  $MeasurementCopyWith<$Res>? get measurement;
}

/// @nodoc
class _$MBDetailResponseCopyWithImpl<$Res, $Val extends MBDetailResponse>
    implements $MBDetailResponseCopyWith<$Res> {
  _$MBDetailResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contract = freezed,
    Object? estimate = freezed,
    Object? period = freezed,
    Object? allMeasurements = freezed,
    Object? measurement = freezed,
    Object? musterRolls = freezed,
  }) {
    return _then(_value.copyWith(
      contract: freezed == contract
          ? _value.contract
          : contract // ignore: cast_nullable_to_non_nullable
              as Contract?,
      estimate: freezed == estimate
          ? _value.estimate
          : estimate // ignore: cast_nullable_to_non_nullable
              as Estimate?,
      period: freezed == period
          ? _value.period
          : period // ignore: cast_nullable_to_non_nullable
              as Period?,
      allMeasurements: freezed == allMeasurements
          ? _value.allMeasurements
          : allMeasurements // ignore: cast_nullable_to_non_nullable
              as dynamic,
      measurement: freezed == measurement
          ? _value.measurement
          : measurement // ignore: cast_nullable_to_non_nullable
              as Measurement?,
      musterRolls: freezed == musterRolls
          ? _value.musterRolls
          : musterRolls // ignore: cast_nullable_to_non_nullable
              as dynamic,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $ContractCopyWith<$Res>? get contract {
    if (_value.contract == null) {
      return null;
    }

    return $ContractCopyWith<$Res>(_value.contract!, (value) {
      return _then(_value.copyWith(contract: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $EstimateCopyWith<$Res>? get estimate {
    if (_value.estimate == null) {
      return null;
    }

    return $EstimateCopyWith<$Res>(_value.estimate!, (value) {
      return _then(_value.copyWith(estimate: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $PeriodCopyWith<$Res>? get period {
    if (_value.period == null) {
      return null;
    }

    return $PeriodCopyWith<$Res>(_value.period!, (value) {
      return _then(_value.copyWith(period: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $MeasurementCopyWith<$Res>? get measurement {
    if (_value.measurement == null) {
      return null;
    }

    return $MeasurementCopyWith<$Res>(_value.measurement!, (value) {
      return _then(_value.copyWith(measurement: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$MBDetailResponseImplCopyWith<$Res>
    implements $MBDetailResponseCopyWith<$Res> {
  factory _$$MBDetailResponseImplCopyWith(_$MBDetailResponseImpl value,
          $Res Function(_$MBDetailResponseImpl) then) =
      __$$MBDetailResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'contract') Contract? contract,
      @JsonKey(name: 'estimate') Estimate? estimate,
      @JsonKey(name: 'period') Period? period,
      @JsonKey(name: 'allMeasurements') dynamic allMeasurements,
      @JsonKey(name: 'measurement') Measurement? measurement,
      @JsonKey(name: 'musterRolls') dynamic musterRolls});

  @override
  $ContractCopyWith<$Res>? get contract;
  @override
  $EstimateCopyWith<$Res>? get estimate;
  @override
  $PeriodCopyWith<$Res>? get period;
  @override
  $MeasurementCopyWith<$Res>? get measurement;
}

/// @nodoc
class __$$MBDetailResponseImplCopyWithImpl<$Res>
    extends _$MBDetailResponseCopyWithImpl<$Res, _$MBDetailResponseImpl>
    implements _$$MBDetailResponseImplCopyWith<$Res> {
  __$$MBDetailResponseImplCopyWithImpl(_$MBDetailResponseImpl _value,
      $Res Function(_$MBDetailResponseImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contract = freezed,
    Object? estimate = freezed,
    Object? period = freezed,
    Object? allMeasurements = freezed,
    Object? measurement = freezed,
    Object? musterRolls = freezed,
  }) {
    return _then(_$MBDetailResponseImpl(
      contract: freezed == contract
          ? _value.contract
          : contract // ignore: cast_nullable_to_non_nullable
              as Contract?,
      estimate: freezed == estimate
          ? _value.estimate
          : estimate // ignore: cast_nullable_to_non_nullable
              as Estimate?,
      period: freezed == period
          ? _value.period
          : period // ignore: cast_nullable_to_non_nullable
              as Period?,
      allMeasurements: freezed == allMeasurements
          ? _value.allMeasurements
          : allMeasurements // ignore: cast_nullable_to_non_nullable
              as dynamic,
      measurement: freezed == measurement
          ? _value.measurement
          : measurement // ignore: cast_nullable_to_non_nullable
              as Measurement?,
      musterRolls: freezed == musterRolls
          ? _value.musterRolls
          : musterRolls // ignore: cast_nullable_to_non_nullable
              as dynamic,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MBDetailResponseImpl implements _MBDetailResponse {
  const _$MBDetailResponseImpl(
      {@JsonKey(name: 'contract') this.contract,
      @JsonKey(name: 'estimate') this.estimate,
      @JsonKey(name: 'period') this.period,
      @JsonKey(name: 'allMeasurements') this.allMeasurements,
      @JsonKey(name: 'measurement') this.measurement,
      @JsonKey(name: 'musterRolls') this.musterRolls});

  factory _$MBDetailResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$MBDetailResponseImplFromJson(json);

  @override
  @JsonKey(name: 'contract')
  final Contract? contract;
  @override
  @JsonKey(name: 'estimate')
  final Estimate? estimate;
  @override
  @JsonKey(name: 'period')
  final Period? period;
  @override
  @JsonKey(name: 'allMeasurements')
  final dynamic allMeasurements;
  @override
  @JsonKey(name: 'measurement')
  final Measurement? measurement;
  @override
  @JsonKey(name: 'musterRolls')
  final dynamic musterRolls;

  @override
  String toString() {
    return 'MBDetailResponse(contract: $contract, estimate: $estimate, period: $period, allMeasurements: $allMeasurements, measurement: $measurement, musterRolls: $musterRolls)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MBDetailResponseImpl &&
            (identical(other.contract, contract) ||
                other.contract == contract) &&
            (identical(other.estimate, estimate) ||
                other.estimate == estimate) &&
            (identical(other.period, period) || other.period == period) &&
            const DeepCollectionEquality()
                .equals(other.allMeasurements, allMeasurements) &&
            (identical(other.measurement, measurement) ||
                other.measurement == measurement) &&
            const DeepCollectionEquality()
                .equals(other.musterRolls, musterRolls));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      contract,
      estimate,
      period,
      const DeepCollectionEquality().hash(allMeasurements),
      measurement,
      const DeepCollectionEquality().hash(musterRolls));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MBDetailResponseImplCopyWith<_$MBDetailResponseImpl> get copyWith =>
      __$$MBDetailResponseImplCopyWithImpl<_$MBDetailResponseImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MBDetailResponseImplToJson(
      this,
    );
  }
}

abstract class _MBDetailResponse implements MBDetailResponse {
  const factory _MBDetailResponse(
          {@JsonKey(name: 'contract') final Contract? contract,
          @JsonKey(name: 'estimate') final Estimate? estimate,
          @JsonKey(name: 'period') final Period? period,
          @JsonKey(name: 'allMeasurements') final dynamic allMeasurements,
          @JsonKey(name: 'measurement') final Measurement? measurement,
          @JsonKey(name: 'musterRolls') final dynamic musterRolls}) =
      _$MBDetailResponseImpl;

  factory _MBDetailResponse.fromJson(Map<String, dynamic> json) =
      _$MBDetailResponseImpl.fromJson;

  @override
  @JsonKey(name: 'contract')
  Contract? get contract;
  @override
  @JsonKey(name: 'estimate')
  Estimate? get estimate;
  @override
  @JsonKey(name: 'period')
  Period? get period;
  @override
  @JsonKey(name: 'allMeasurements')
  dynamic get allMeasurements;
  @override
  @JsonKey(name: 'measurement')
  Measurement? get measurement;
  @override
  @JsonKey(name: 'musterRolls')
  dynamic get musterRolls;
  @override
  @JsonKey(ignore: true)
  _$$MBDetailResponseImplCopyWith<_$MBDetailResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkFlow _$WorkFlowFromJson(Map<String, dynamic> json) {
  return _WorkFlow.fromJson(json);
}

/// @nodoc
mixin _$WorkFlow {
  @JsonKey(name: 'action')
  String? get action => throw _privateConstructorUsedError;
  @JsonKey(name: 'comment')
  String? get comment => throw _privateConstructorUsedError;
  @JsonKey(name: 'assignees')
  List<String>? get assignees => throw _privateConstructorUsedError;
  @JsonKey(name: 'documents')
  List<WorkFlowSupportDocument>? get documents =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WorkFlowCopyWith<WorkFlow> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkFlowCopyWith<$Res> {
  factory $WorkFlowCopyWith(WorkFlow value, $Res Function(WorkFlow) then) =
      _$WorkFlowCopyWithImpl<$Res, WorkFlow>;
  @useResult
  $Res call(
      {@JsonKey(name: 'action') String? action,
      @JsonKey(name: 'comment') String? comment,
      @JsonKey(name: 'assignees') List<String>? assignees,
      @JsonKey(name: 'documents') List<WorkFlowSupportDocument>? documents});
}

/// @nodoc
class _$WorkFlowCopyWithImpl<$Res, $Val extends WorkFlow>
    implements $WorkFlowCopyWith<$Res> {
  _$WorkFlowCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? action = freezed,
    Object? comment = freezed,
    Object? assignees = freezed,
    Object? documents = freezed,
  }) {
    return _then(_value.copyWith(
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      comment: freezed == comment
          ? _value.comment
          : comment // ignore: cast_nullable_to_non_nullable
              as String?,
      assignees: freezed == assignees
          ? _value.assignees
          : assignees // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      documents: freezed == documents
          ? _value.documents
          : documents // ignore: cast_nullable_to_non_nullable
              as List<WorkFlowSupportDocument>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WorkFlowImplCopyWith<$Res>
    implements $WorkFlowCopyWith<$Res> {
  factory _$$WorkFlowImplCopyWith(
          _$WorkFlowImpl value, $Res Function(_$WorkFlowImpl) then) =
      __$$WorkFlowImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'action') String? action,
      @JsonKey(name: 'comment') String? comment,
      @JsonKey(name: 'assignees') List<String>? assignees,
      @JsonKey(name: 'documents') List<WorkFlowSupportDocument>? documents});
}

/// @nodoc
class __$$WorkFlowImplCopyWithImpl<$Res>
    extends _$WorkFlowCopyWithImpl<$Res, _$WorkFlowImpl>
    implements _$$WorkFlowImplCopyWith<$Res> {
  __$$WorkFlowImplCopyWithImpl(
      _$WorkFlowImpl _value, $Res Function(_$WorkFlowImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? action = freezed,
    Object? comment = freezed,
    Object? assignees = freezed,
    Object? documents = freezed,
  }) {
    return _then(_$WorkFlowImpl(
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      comment: freezed == comment
          ? _value.comment
          : comment // ignore: cast_nullable_to_non_nullable
              as String?,
      assignees: freezed == assignees
          ? _value._assignees
          : assignees // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      documents: freezed == documents
          ? _value._documents
          : documents // ignore: cast_nullable_to_non_nullable
              as List<WorkFlowSupportDocument>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WorkFlowImpl implements _WorkFlow {
  const _$WorkFlowImpl(
      {@JsonKey(name: 'action')
          this.action,
      @JsonKey(name: 'comment')
          this.comment,
      @JsonKey(name: 'assignees')
          final List<String>? assignees,
      @JsonKey(name: 'documents')
          final List<WorkFlowSupportDocument>? documents})
      : _assignees = assignees,
        _documents = documents;

  factory _$WorkFlowImpl.fromJson(Map<String, dynamic> json) =>
      _$$WorkFlowImplFromJson(json);

  @override
  @JsonKey(name: 'action')
  final String? action;
  @override
  @JsonKey(name: 'comment')
  final String? comment;
  final List<String>? _assignees;
  @override
  @JsonKey(name: 'assignees')
  List<String>? get assignees {
    final value = _assignees;
    if (value == null) return null;
    if (_assignees is EqualUnmodifiableListView) return _assignees;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<WorkFlowSupportDocument>? _documents;
  @override
  @JsonKey(name: 'documents')
  List<WorkFlowSupportDocument>? get documents {
    final value = _documents;
    if (value == null) return null;
    if (_documents is EqualUnmodifiableListView) return _documents;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'WorkFlow(action: $action, comment: $comment, assignees: $assignees, documents: $documents)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WorkFlowImpl &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.comment, comment) || other.comment == comment) &&
            const DeepCollectionEquality()
                .equals(other._assignees, _assignees) &&
            const DeepCollectionEquality()
                .equals(other._documents, _documents));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      action,
      comment,
      const DeepCollectionEquality().hash(_assignees),
      const DeepCollectionEquality().hash(_documents));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WorkFlowImplCopyWith<_$WorkFlowImpl> get copyWith =>
      __$$WorkFlowImplCopyWithImpl<_$WorkFlowImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WorkFlowImplToJson(
      this,
    );
  }
}

abstract class _WorkFlow implements WorkFlow {
  const factory _WorkFlow(
      {@JsonKey(name: 'action')
          final String? action,
      @JsonKey(name: 'comment')
          final String? comment,
      @JsonKey(name: 'assignees')
          final List<String>? assignees,
      @JsonKey(name: 'documents')
          final List<WorkFlowSupportDocument>? documents}) = _$WorkFlowImpl;

  factory _WorkFlow.fromJson(Map<String, dynamic> json) =
      _$WorkFlowImpl.fromJson;

  @override
  @JsonKey(name: 'action')
  String? get action;
  @override
  @JsonKey(name: 'comment')
  String? get comment;
  @override
  @JsonKey(name: 'assignees')
  List<String>? get assignees;
  @override
  @JsonKey(name: 'documents')
  List<WorkFlowSupportDocument>? get documents;
  @override
  @JsonKey(ignore: true)
  _$$WorkFlowImplCopyWith<_$WorkFlowImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkFlowSupportDocument _$WorkFlowSupportDocumentFromJson(
    Map<String, dynamic> json) {
  return _WorkFlowSupportDocument.fromJson(json);
}

/// @nodoc
mixin _$WorkFlowSupportDocument {
  @JsonKey(name: 'documentType')
  String? get documentType => throw _privateConstructorUsedError;
  @JsonKey(name: 'documentUid')
  String? get documentUid => throw _privateConstructorUsedError;
  @JsonKey(name: 'fileName')
  String? get fileName => throw _privateConstructorUsedError;
  @JsonKey(name: 'fileStoreId')
  String? get fileStoreId => throw _privateConstructorUsedError;
  @JsonKey(name: 'tenantId')
  String? get tenantId => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WorkFlowSupportDocumentCopyWith<WorkFlowSupportDocument> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkFlowSupportDocumentCopyWith<$Res> {
  factory $WorkFlowSupportDocumentCopyWith(WorkFlowSupportDocument value,
          $Res Function(WorkFlowSupportDocument) then) =
      _$WorkFlowSupportDocumentCopyWithImpl<$Res, WorkFlowSupportDocument>;
  @useResult
  $Res call(
      {@JsonKey(name: 'documentType') String? documentType,
      @JsonKey(name: 'documentUid') String? documentUid,
      @JsonKey(name: 'fileName') String? fileName,
      @JsonKey(name: 'fileStoreId') String? fileStoreId,
      @JsonKey(name: 'tenantId') String? tenantId});
}

/// @nodoc
class _$WorkFlowSupportDocumentCopyWithImpl<$Res,
        $Val extends WorkFlowSupportDocument>
    implements $WorkFlowSupportDocumentCopyWith<$Res> {
  _$WorkFlowSupportDocumentCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? documentType = freezed,
    Object? documentUid = freezed,
    Object? fileName = freezed,
    Object? fileStoreId = freezed,
    Object? tenantId = freezed,
  }) {
    return _then(_value.copyWith(
      documentType: freezed == documentType
          ? _value.documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as String?,
      documentUid: freezed == documentUid
          ? _value.documentUid
          : documentUid // ignore: cast_nullable_to_non_nullable
              as String?,
      fileName: freezed == fileName
          ? _value.fileName
          : fileName // ignore: cast_nullable_to_non_nullable
              as String?,
      fileStoreId: freezed == fileStoreId
          ? _value.fileStoreId
          : fileStoreId // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WorkFlowSupportDocumentImplCopyWith<$Res>
    implements $WorkFlowSupportDocumentCopyWith<$Res> {
  factory _$$WorkFlowSupportDocumentImplCopyWith(
          _$WorkFlowSupportDocumentImpl value,
          $Res Function(_$WorkFlowSupportDocumentImpl) then) =
      __$$WorkFlowSupportDocumentImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'documentType') String? documentType,
      @JsonKey(name: 'documentUid') String? documentUid,
      @JsonKey(name: 'fileName') String? fileName,
      @JsonKey(name: 'fileStoreId') String? fileStoreId,
      @JsonKey(name: 'tenantId') String? tenantId});
}

/// @nodoc
class __$$WorkFlowSupportDocumentImplCopyWithImpl<$Res>
    extends _$WorkFlowSupportDocumentCopyWithImpl<$Res,
        _$WorkFlowSupportDocumentImpl>
    implements _$$WorkFlowSupportDocumentImplCopyWith<$Res> {
  __$$WorkFlowSupportDocumentImplCopyWithImpl(
      _$WorkFlowSupportDocumentImpl _value,
      $Res Function(_$WorkFlowSupportDocumentImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? documentType = freezed,
    Object? documentUid = freezed,
    Object? fileName = freezed,
    Object? fileStoreId = freezed,
    Object? tenantId = freezed,
  }) {
    return _then(_$WorkFlowSupportDocumentImpl(
      documentType: freezed == documentType
          ? _value.documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as String?,
      documentUid: freezed == documentUid
          ? _value.documentUid
          : documentUid // ignore: cast_nullable_to_non_nullable
              as String?,
      fileName: freezed == fileName
          ? _value.fileName
          : fileName // ignore: cast_nullable_to_non_nullable
              as String?,
      fileStoreId: freezed == fileStoreId
          ? _value.fileStoreId
          : fileStoreId // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WorkFlowSupportDocumentImpl implements _WorkFlowSupportDocument {
  const _$WorkFlowSupportDocumentImpl(
      {@JsonKey(name: 'documentType') this.documentType,
      @JsonKey(name: 'documentUid') this.documentUid,
      @JsonKey(name: 'fileName') this.fileName,
      @JsonKey(name: 'fileStoreId') this.fileStoreId,
      @JsonKey(name: 'tenantId') this.tenantId});

  factory _$WorkFlowSupportDocumentImpl.fromJson(Map<String, dynamic> json) =>
      _$$WorkFlowSupportDocumentImplFromJson(json);

  @override
  @JsonKey(name: 'documentType')
  final String? documentType;
  @override
  @JsonKey(name: 'documentUid')
  final String? documentUid;
  @override
  @JsonKey(name: 'fileName')
  final String? fileName;
  @override
  @JsonKey(name: 'fileStoreId')
  final String? fileStoreId;
  @override
  @JsonKey(name: 'tenantId')
  final String? tenantId;

  @override
  String toString() {
    return 'WorkFlowSupportDocument(documentType: $documentType, documentUid: $documentUid, fileName: $fileName, fileStoreId: $fileStoreId, tenantId: $tenantId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WorkFlowSupportDocumentImpl &&
            (identical(other.documentType, documentType) ||
                other.documentType == documentType) &&
            (identical(other.documentUid, documentUid) ||
                other.documentUid == documentUid) &&
            (identical(other.fileName, fileName) ||
                other.fileName == fileName) &&
            (identical(other.fileStoreId, fileStoreId) ||
                other.fileStoreId == fileStoreId) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, documentType, documentUid, fileName, fileStoreId, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WorkFlowSupportDocumentImplCopyWith<_$WorkFlowSupportDocumentImpl>
      get copyWith => __$$WorkFlowSupportDocumentImplCopyWithImpl<
          _$WorkFlowSupportDocumentImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WorkFlowSupportDocumentImplToJson(
      this,
    );
  }
}

abstract class _WorkFlowSupportDocument implements WorkFlowSupportDocument {
  const factory _WorkFlowSupportDocument(
          {@JsonKey(name: 'documentType') final String? documentType,
          @JsonKey(name: 'documentUid') final String? documentUid,
          @JsonKey(name: 'fileName') final String? fileName,
          @JsonKey(name: 'fileStoreId') final String? fileStoreId,
          @JsonKey(name: 'tenantId') final String? tenantId}) =
      _$WorkFlowSupportDocumentImpl;

  factory _WorkFlowSupportDocument.fromJson(Map<String, dynamic> json) =
      _$WorkFlowSupportDocumentImpl.fromJson;

  @override
  @JsonKey(name: 'documentType')
  String? get documentType;
  @override
  @JsonKey(name: 'documentUid')
  String? get documentUid;
  @override
  @JsonKey(name: 'fileName')
  String? get fileName;
  @override
  @JsonKey(name: 'fileStoreId')
  String? get fileStoreId;
  @override
  @JsonKey(name: 'tenantId')
  String? get tenantId;
  @override
  @JsonKey(ignore: true)
  _$$WorkFlowSupportDocumentImplCopyWith<_$WorkFlowSupportDocumentImpl>
      get copyWith => throw _privateConstructorUsedError;
}

MusterRoll _$MusterRollFromJson(Map<String, dynamic> json) {
  return _MusterRoll.fromJson(json);
}

/// @nodoc
mixin _$MusterRoll {
  @JsonKey(name: 'id')
  String? get id => throw _privateConstructorUsedError;
  @JsonKey(name: 'tenantId')
  String? get tenantId => throw _privateConstructorUsedError;
  @JsonKey(name: 'musterRollNumber')
  dynamic get musterRollNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'registerId')
  String? get registerId => throw _privateConstructorUsedError;
  @JsonKey(name: 'status')
  String? get status => throw _privateConstructorUsedError;
  @JsonKey(name: 'musterRollStatus')
  String? get musterRollStatus => throw _privateConstructorUsedError;
  @JsonKey(name: 'startDate')
  int? get startDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'endDate')
  int? get endDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'referenceId')
  String? get referenceId => throw _privateConstructorUsedError;
  @JsonKey(name: 'serviceCode')
  String? get serviceCode => throw _privateConstructorUsedError;
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails => throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  MusterRollAdditionalDetails? get additional =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterRollCopyWith<MusterRoll> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollCopyWith<$Res> {
  factory $MusterRollCopyWith(
          MusterRoll value, $Res Function(MusterRoll) then) =
      _$MusterRollCopyWithImpl<$Res, MusterRoll>;
  @useResult
  $Res call(
      {@JsonKey(name: 'id')
          String? id,
      @JsonKey(name: 'tenantId')
          String? tenantId,
      @JsonKey(name: 'musterRollNumber')
          dynamic musterRollNumber,
      @JsonKey(name: 'registerId')
          String? registerId,
      @JsonKey(name: 'status')
          String? status,
      @JsonKey(name: 'musterRollStatus')
          String? musterRollStatus,
      @JsonKey(name: 'startDate')
          int? startDate,
      @JsonKey(name: 'endDate')
          int? endDate,
      @JsonKey(name: 'referenceId')
          String? referenceId,
      @JsonKey(name: 'serviceCode')
          String? serviceCode,
      @JsonKey(name: 'auditDetails')
          AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          MusterRollAdditionalDetails? additional});

  $AuditDetailsCopyWith<$Res>? get auditDetails;
  $MusterRollAdditionalDetailsCopyWith<$Res>? get additional;
}

/// @nodoc
class _$MusterRollCopyWithImpl<$Res, $Val extends MusterRoll>
    implements $MusterRollCopyWith<$Res> {
  _$MusterRollCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? musterRollNumber = freezed,
    Object? registerId = freezed,
    Object? status = freezed,
    Object? musterRollStatus = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? referenceId = freezed,
    Object? serviceCode = freezed,
    Object? auditDetails = freezed,
    Object? additional = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      musterRollNumber: freezed == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as dynamic,
      registerId: freezed == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String?,
      status: freezed == status
          ? _value.status
          : status // ignore: cast_nullable_to_non_nullable
              as String?,
      musterRollStatus: freezed == musterRollStatus
          ? _value.musterRollStatus
          : musterRollStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
              as String?,
      serviceCode: freezed == serviceCode
          ? _value.serviceCode
          : serviceCode // ignore: cast_nullable_to_non_nullable
              as String?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      additional: freezed == additional
          ? _value.additional
          : additional // ignore: cast_nullable_to_non_nullable
              as MusterRollAdditionalDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AuditDetailsCopyWith<$Res>? get auditDetails {
    if (_value.auditDetails == null) {
      return null;
    }

    return $AuditDetailsCopyWith<$Res>(_value.auditDetails!, (value) {
      return _then(_value.copyWith(auditDetails: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterRollAdditionalDetailsCopyWith<$Res>? get additional {
    if (_value.additional == null) {
      return null;
    }

    return $MusterRollAdditionalDetailsCopyWith<$Res>(_value.additional!,
        (value) {
      return _then(_value.copyWith(additional: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$MusterRollImplCopyWith<$Res>
    implements $MusterRollCopyWith<$Res> {
  factory _$$MusterRollImplCopyWith(
          _$MusterRollImpl value, $Res Function(_$MusterRollImpl) then) =
      __$$MusterRollImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'id')
          String? id,
      @JsonKey(name: 'tenantId')
          String? tenantId,
      @JsonKey(name: 'musterRollNumber')
          dynamic musterRollNumber,
      @JsonKey(name: 'registerId')
          String? registerId,
      @JsonKey(name: 'status')
          String? status,
      @JsonKey(name: 'musterRollStatus')
          String? musterRollStatus,
      @JsonKey(name: 'startDate')
          int? startDate,
      @JsonKey(name: 'endDate')
          int? endDate,
      @JsonKey(name: 'referenceId')
          String? referenceId,
      @JsonKey(name: 'serviceCode')
          String? serviceCode,
      @JsonKey(name: 'auditDetails')
          AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          MusterRollAdditionalDetails? additional});

  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
  @override
  $MusterRollAdditionalDetailsCopyWith<$Res>? get additional;
}

/// @nodoc
class __$$MusterRollImplCopyWithImpl<$Res>
    extends _$MusterRollCopyWithImpl<$Res, _$MusterRollImpl>
    implements _$$MusterRollImplCopyWith<$Res> {
  __$$MusterRollImplCopyWithImpl(
      _$MusterRollImpl _value, $Res Function(_$MusterRollImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? musterRollNumber = freezed,
    Object? registerId = freezed,
    Object? status = freezed,
    Object? musterRollStatus = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? referenceId = freezed,
    Object? serviceCode = freezed,
    Object? auditDetails = freezed,
    Object? additional = freezed,
  }) {
    return _then(_$MusterRollImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      musterRollNumber: freezed == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as dynamic,
      registerId: freezed == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String?,
      status: freezed == status
          ? _value.status
          : status // ignore: cast_nullable_to_non_nullable
              as String?,
      musterRollStatus: freezed == musterRollStatus
          ? _value.musterRollStatus
          : musterRollStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
              as String?,
      serviceCode: freezed == serviceCode
          ? _value.serviceCode
          : serviceCode // ignore: cast_nullable_to_non_nullable
              as String?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      additional: freezed == additional
          ? _value.additional
          : additional // ignore: cast_nullable_to_non_nullable
              as MusterRollAdditionalDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MusterRollImpl implements _MusterRoll {
  const _$MusterRollImpl(
      {@JsonKey(name: 'id') this.id,
      @JsonKey(name: 'tenantId') this.tenantId,
      @JsonKey(name: 'musterRollNumber') this.musterRollNumber,
      @JsonKey(name: 'registerId') this.registerId,
      @JsonKey(name: 'status') this.status,
      @JsonKey(name: 'musterRollStatus') this.musterRollStatus,
      @JsonKey(name: 'startDate') this.startDate,
      @JsonKey(name: 'endDate') this.endDate,
      @JsonKey(name: 'referenceId') this.referenceId,
      @JsonKey(name: 'serviceCode') this.serviceCode,
      @JsonKey(name: 'auditDetails') this.auditDetails,
      @JsonKey(name: 'additionalDetails') this.additional});

  factory _$MusterRollImpl.fromJson(Map<String, dynamic> json) =>
      _$$MusterRollImplFromJson(json);

  @override
  @JsonKey(name: 'id')
  final String? id;
  @override
  @JsonKey(name: 'tenantId')
  final String? tenantId;
  @override
  @JsonKey(name: 'musterRollNumber')
  final dynamic musterRollNumber;
  @override
  @JsonKey(name: 'registerId')
  final String? registerId;
  @override
  @JsonKey(name: 'status')
  final String? status;
  @override
  @JsonKey(name: 'musterRollStatus')
  final String? musterRollStatus;
  @override
  @JsonKey(name: 'startDate')
  final int? startDate;
  @override
  @JsonKey(name: 'endDate')
  final int? endDate;
  @override
  @JsonKey(name: 'referenceId')
  final String? referenceId;
  @override
  @JsonKey(name: 'serviceCode')
  final String? serviceCode;
  @override
  @JsonKey(name: 'auditDetails')
  final AuditDetails? auditDetails;
  @override
  @JsonKey(name: 'additionalDetails')
  final MusterRollAdditionalDetails? additional;

  @override
  String toString() {
    return 'MusterRoll(id: $id, tenantId: $tenantId, musterRollNumber: $musterRollNumber, registerId: $registerId, status: $status, musterRollStatus: $musterRollStatus, startDate: $startDate, endDate: $endDate, referenceId: $referenceId, serviceCode: $serviceCode, auditDetails: $auditDetails, additional: $additional)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MusterRollImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            const DeepCollectionEquality()
                .equals(other.musterRollNumber, musterRollNumber) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.musterRollStatus, musterRollStatus) ||
                other.musterRollStatus == musterRollStatus) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            (identical(other.referenceId, referenceId) ||
                other.referenceId == referenceId) &&
            (identical(other.serviceCode, serviceCode) ||
                other.serviceCode == serviceCode) &&
            (identical(other.auditDetails, auditDetails) ||
                other.auditDetails == auditDetails) &&
            (identical(other.additional, additional) ||
                other.additional == additional));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      tenantId,
      const DeepCollectionEquality().hash(musterRollNumber),
      registerId,
      status,
      musterRollStatus,
      startDate,
      endDate,
      referenceId,
      serviceCode,
      auditDetails,
      additional);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MusterRollImplCopyWith<_$MusterRollImpl> get copyWith =>
      __$$MusterRollImplCopyWithImpl<_$MusterRollImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MusterRollImplToJson(
      this,
    );
  }
}

abstract class _MusterRoll implements MusterRoll {
  const factory _MusterRoll(
      {@JsonKey(name: 'id')
          final String? id,
      @JsonKey(name: 'tenantId')
          final String? tenantId,
      @JsonKey(name: 'musterRollNumber')
          final dynamic musterRollNumber,
      @JsonKey(name: 'registerId')
          final String? registerId,
      @JsonKey(name: 'status')
          final String? status,
      @JsonKey(name: 'musterRollStatus')
          final String? musterRollStatus,
      @JsonKey(name: 'startDate')
          final int? startDate,
      @JsonKey(name: 'endDate')
          final int? endDate,
      @JsonKey(name: 'referenceId')
          final String? referenceId,
      @JsonKey(name: 'serviceCode')
          final String? serviceCode,
      @JsonKey(name: 'auditDetails')
          final AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          final MusterRollAdditionalDetails? additional}) = _$MusterRollImpl;

  factory _MusterRoll.fromJson(Map<String, dynamic> json) =
      _$MusterRollImpl.fromJson;

  @override
  @JsonKey(name: 'id')
  String? get id;
  @override
  @JsonKey(name: 'tenantId')
  String? get tenantId;
  @override
  @JsonKey(name: 'musterRollNumber')
  dynamic get musterRollNumber;
  @override
  @JsonKey(name: 'registerId')
  String? get registerId;
  @override
  @JsonKey(name: 'status')
  String? get status;
  @override
  @JsonKey(name: 'musterRollStatus')
  String? get musterRollStatus;
  @override
  @JsonKey(name: 'startDate')
  int? get startDate;
  @override
  @JsonKey(name: 'endDate')
  int? get endDate;
  @override
  @JsonKey(name: 'referenceId')
  String? get referenceId;
  @override
  @JsonKey(name: 'serviceCode')
  String? get serviceCode;
  @override
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails;
  @override
  @JsonKey(name: 'additionalDetails')
  MusterRollAdditionalDetails? get additional;
  @override
  @JsonKey(ignore: true)
  _$$MusterRollImplCopyWith<_$MusterRollImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

MusterRollAdditionalDetails _$MusterRollAdditionalDetailsFromJson(
    Map<String, dynamic> json) {
  return _MusterRollAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$MusterRollAdditionalDetails {
  @JsonKey(name: 'ward')
  String? get ward => throw _privateConstructorUsedError;
  @JsonKey(name: 'orgId')
  String? get orgId => throw _privateConstructorUsedError;
  @JsonKey(name: 'amount')
  double? get amount => throw _privateConstructorUsedError;
  @JsonKey(name: 'orgName')
  String? get orgName => throw _privateConstructorUsedError;
  @JsonKey(name: 'locality')
  String? get locality => throw _privateConstructorUsedError;
  @JsonKey(name: 'projectId')
  String? get projectId => throw _privateConstructorUsedError;
  @JsonKey(name: 'contractId')
  String? get contractId => throw _privateConstructorUsedError;
  @JsonKey(name: 'projectDesc')
  String? get projectDesc => throw _privateConstructorUsedError;
  @JsonKey(name: 'projectName')
  String? get projectName => throw _privateConstructorUsedError;
  @JsonKey(name: 'projectType')
  String? get projectType => throw _privateConstructorUsedError;
  @JsonKey(name: 'executingAuthority')
  String? get executingAuthority => throw _privateConstructorUsedError;
  @JsonKey(name: 'attendanceRegisterNo')
  String? get attendanceRegisterNo => throw _privateConstructorUsedError;
  @JsonKey(name: 'attendanceRegisterName')
  String? get attendanceRegisterName => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterRollAdditionalDetailsCopyWith<MusterRollAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollAdditionalDetailsCopyWith<$Res> {
  factory $MusterRollAdditionalDetailsCopyWith(
          MusterRollAdditionalDetails value,
          $Res Function(MusterRollAdditionalDetails) then) =
      _$MusterRollAdditionalDetailsCopyWithImpl<$Res,
          MusterRollAdditionalDetails>;
  @useResult
  $Res call(
      {@JsonKey(name: 'ward') String? ward,
      @JsonKey(name: 'orgId') String? orgId,
      @JsonKey(name: 'amount') double? amount,
      @JsonKey(name: 'orgName') String? orgName,
      @JsonKey(name: 'locality') String? locality,
      @JsonKey(name: 'projectId') String? projectId,
      @JsonKey(name: 'contractId') String? contractId,
      @JsonKey(name: 'projectDesc') String? projectDesc,
      @JsonKey(name: 'projectName') String? projectName,
      @JsonKey(name: 'projectType') String? projectType,
      @JsonKey(name: 'executingAuthority') String? executingAuthority,
      @JsonKey(name: 'attendanceRegisterNo') String? attendanceRegisterNo,
      @JsonKey(name: 'attendanceRegisterName') String? attendanceRegisterName});
}

/// @nodoc
class _$MusterRollAdditionalDetailsCopyWithImpl<$Res,
        $Val extends MusterRollAdditionalDetails>
    implements $MusterRollAdditionalDetailsCopyWith<$Res> {
  _$MusterRollAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? ward = freezed,
    Object? orgId = freezed,
    Object? amount = freezed,
    Object? orgName = freezed,
    Object? locality = freezed,
    Object? projectId = freezed,
    Object? contractId = freezed,
    Object? projectDesc = freezed,
    Object? projectName = freezed,
    Object? projectType = freezed,
    Object? executingAuthority = freezed,
    Object? attendanceRegisterNo = freezed,
    Object? attendanceRegisterName = freezed,
  }) {
    return _then(_value.copyWith(
      ward: freezed == ward
          ? _value.ward
          : ward // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as double?,
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
      locality: freezed == locality
          ? _value.locality
          : locality // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      contractId: freezed == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectDesc: freezed == projectDesc
          ? _value.projectDesc
          : projectDesc // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectType: freezed == projectType
          ? _value.projectType
          : projectType // ignore: cast_nullable_to_non_nullable
              as String?,
      executingAuthority: freezed == executingAuthority
          ? _value.executingAuthority
          : executingAuthority // ignore: cast_nullable_to_non_nullable
              as String?,
      attendanceRegisterNo: freezed == attendanceRegisterNo
          ? _value.attendanceRegisterNo
          : attendanceRegisterNo // ignore: cast_nullable_to_non_nullable
              as String?,
      attendanceRegisterName: freezed == attendanceRegisterName
          ? _value.attendanceRegisterName
          : attendanceRegisterName // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$MusterRollAdditionalDetailsImplCopyWith<$Res>
    implements $MusterRollAdditionalDetailsCopyWith<$Res> {
  factory _$$MusterRollAdditionalDetailsImplCopyWith(
          _$MusterRollAdditionalDetailsImpl value,
          $Res Function(_$MusterRollAdditionalDetailsImpl) then) =
      __$$MusterRollAdditionalDetailsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'ward') String? ward,
      @JsonKey(name: 'orgId') String? orgId,
      @JsonKey(name: 'amount') double? amount,
      @JsonKey(name: 'orgName') String? orgName,
      @JsonKey(name: 'locality') String? locality,
      @JsonKey(name: 'projectId') String? projectId,
      @JsonKey(name: 'contractId') String? contractId,
      @JsonKey(name: 'projectDesc') String? projectDesc,
      @JsonKey(name: 'projectName') String? projectName,
      @JsonKey(name: 'projectType') String? projectType,
      @JsonKey(name: 'executingAuthority') String? executingAuthority,
      @JsonKey(name: 'attendanceRegisterNo') String? attendanceRegisterNo,
      @JsonKey(name: 'attendanceRegisterName') String? attendanceRegisterName});
}

/// @nodoc
class __$$MusterRollAdditionalDetailsImplCopyWithImpl<$Res>
    extends _$MusterRollAdditionalDetailsCopyWithImpl<$Res,
        _$MusterRollAdditionalDetailsImpl>
    implements _$$MusterRollAdditionalDetailsImplCopyWith<$Res> {
  __$$MusterRollAdditionalDetailsImplCopyWithImpl(
      _$MusterRollAdditionalDetailsImpl _value,
      $Res Function(_$MusterRollAdditionalDetailsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? ward = freezed,
    Object? orgId = freezed,
    Object? amount = freezed,
    Object? orgName = freezed,
    Object? locality = freezed,
    Object? projectId = freezed,
    Object? contractId = freezed,
    Object? projectDesc = freezed,
    Object? projectName = freezed,
    Object? projectType = freezed,
    Object? executingAuthority = freezed,
    Object? attendanceRegisterNo = freezed,
    Object? attendanceRegisterName = freezed,
  }) {
    return _then(_$MusterRollAdditionalDetailsImpl(
      ward: freezed == ward
          ? _value.ward
          : ward // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as double?,
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
      locality: freezed == locality
          ? _value.locality
          : locality // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      contractId: freezed == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectDesc: freezed == projectDesc
          ? _value.projectDesc
          : projectDesc // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectType: freezed == projectType
          ? _value.projectType
          : projectType // ignore: cast_nullable_to_non_nullable
              as String?,
      executingAuthority: freezed == executingAuthority
          ? _value.executingAuthority
          : executingAuthority // ignore: cast_nullable_to_non_nullable
              as String?,
      attendanceRegisterNo: freezed == attendanceRegisterNo
          ? _value.attendanceRegisterNo
          : attendanceRegisterNo // ignore: cast_nullable_to_non_nullable
              as String?,
      attendanceRegisterName: freezed == attendanceRegisterName
          ? _value.attendanceRegisterName
          : attendanceRegisterName // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MusterRollAdditionalDetailsImpl
    implements _MusterRollAdditionalDetails {
  const _$MusterRollAdditionalDetailsImpl(
      {@JsonKey(name: 'ward') this.ward,
      @JsonKey(name: 'orgId') this.orgId,
      @JsonKey(name: 'amount') this.amount,
      @JsonKey(name: 'orgName') this.orgName,
      @JsonKey(name: 'locality') this.locality,
      @JsonKey(name: 'projectId') this.projectId,
      @JsonKey(name: 'contractId') this.contractId,
      @JsonKey(name: 'projectDesc') this.projectDesc,
      @JsonKey(name: 'projectName') this.projectName,
      @JsonKey(name: 'projectType') this.projectType,
      @JsonKey(name: 'executingAuthority') this.executingAuthority,
      @JsonKey(name: 'attendanceRegisterNo') this.attendanceRegisterNo,
      @JsonKey(name: 'attendanceRegisterName') this.attendanceRegisterName});

  factory _$MusterRollAdditionalDetailsImpl.fromJson(
          Map<String, dynamic> json) =>
      _$$MusterRollAdditionalDetailsImplFromJson(json);

  @override
  @JsonKey(name: 'ward')
  final String? ward;
  @override
  @JsonKey(name: 'orgId')
  final String? orgId;
  @override
  @JsonKey(name: 'amount')
  final double? amount;
  @override
  @JsonKey(name: 'orgName')
  final String? orgName;
  @override
  @JsonKey(name: 'locality')
  final String? locality;
  @override
  @JsonKey(name: 'projectId')
  final String? projectId;
  @override
  @JsonKey(name: 'contractId')
  final String? contractId;
  @override
  @JsonKey(name: 'projectDesc')
  final String? projectDesc;
  @override
  @JsonKey(name: 'projectName')
  final String? projectName;
  @override
  @JsonKey(name: 'projectType')
  final String? projectType;
  @override
  @JsonKey(name: 'executingAuthority')
  final String? executingAuthority;
  @override
  @JsonKey(name: 'attendanceRegisterNo')
  final String? attendanceRegisterNo;
  @override
  @JsonKey(name: 'attendanceRegisterName')
  final String? attendanceRegisterName;

  @override
  String toString() {
    return 'MusterRollAdditionalDetails(ward: $ward, orgId: $orgId, amount: $amount, orgName: $orgName, locality: $locality, projectId: $projectId, contractId: $contractId, projectDesc: $projectDesc, projectName: $projectName, projectType: $projectType, executingAuthority: $executingAuthority, attendanceRegisterNo: $attendanceRegisterNo, attendanceRegisterName: $attendanceRegisterName)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MusterRollAdditionalDetailsImpl &&
            (identical(other.ward, ward) || other.ward == ward) &&
            (identical(other.orgId, orgId) || other.orgId == orgId) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.orgName, orgName) || other.orgName == orgName) &&
            (identical(other.locality, locality) ||
                other.locality == locality) &&
            (identical(other.projectId, projectId) ||
                other.projectId == projectId) &&
            (identical(other.contractId, contractId) ||
                other.contractId == contractId) &&
            (identical(other.projectDesc, projectDesc) ||
                other.projectDesc == projectDesc) &&
            (identical(other.projectName, projectName) ||
                other.projectName == projectName) &&
            (identical(other.projectType, projectType) ||
                other.projectType == projectType) &&
            (identical(other.executingAuthority, executingAuthority) ||
                other.executingAuthority == executingAuthority) &&
            (identical(other.attendanceRegisterNo, attendanceRegisterNo) ||
                other.attendanceRegisterNo == attendanceRegisterNo) &&
            (identical(other.attendanceRegisterName, attendanceRegisterName) ||
                other.attendanceRegisterName == attendanceRegisterName));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      ward,
      orgId,
      amount,
      orgName,
      locality,
      projectId,
      contractId,
      projectDesc,
      projectName,
      projectType,
      executingAuthority,
      attendanceRegisterNo,
      attendanceRegisterName);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MusterRollAdditionalDetailsImplCopyWith<_$MusterRollAdditionalDetailsImpl>
      get copyWith => __$$MusterRollAdditionalDetailsImplCopyWithImpl<
          _$MusterRollAdditionalDetailsImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MusterRollAdditionalDetailsImplToJson(
      this,
    );
  }
}

abstract class _MusterRollAdditionalDetails
    implements MusterRollAdditionalDetails {
  const factory _MusterRollAdditionalDetails(
          {@JsonKey(name: 'ward')
              final String? ward,
          @JsonKey(name: 'orgId')
              final String? orgId,
          @JsonKey(name: 'amount')
              final double? amount,
          @JsonKey(name: 'orgName')
              final String? orgName,
          @JsonKey(name: 'locality')
              final String? locality,
          @JsonKey(name: 'projectId')
              final String? projectId,
          @JsonKey(name: 'contractId')
              final String? contractId,
          @JsonKey(name: 'projectDesc')
              final String? projectDesc,
          @JsonKey(name: 'projectName')
              final String? projectName,
          @JsonKey(name: 'projectType')
              final String? projectType,
          @JsonKey(name: 'executingAuthority')
              final String? executingAuthority,
          @JsonKey(name: 'attendanceRegisterNo')
              final String? attendanceRegisterNo,
          @JsonKey(name: 'attendanceRegisterName')
              final String? attendanceRegisterName}) =
      _$MusterRollAdditionalDetailsImpl;

  factory _MusterRollAdditionalDetails.fromJson(Map<String, dynamic> json) =
      _$MusterRollAdditionalDetailsImpl.fromJson;

  @override
  @JsonKey(name: 'ward')
  String? get ward;
  @override
  @JsonKey(name: 'orgId')
  String? get orgId;
  @override
  @JsonKey(name: 'amount')
  double? get amount;
  @override
  @JsonKey(name: 'orgName')
  String? get orgName;
  @override
  @JsonKey(name: 'locality')
  String? get locality;
  @override
  @JsonKey(name: 'projectId')
  String? get projectId;
  @override
  @JsonKey(name: 'contractId')
  String? get contractId;
  @override
  @JsonKey(name: 'projectDesc')
  String? get projectDesc;
  @override
  @JsonKey(name: 'projectName')
  String? get projectName;
  @override
  @JsonKey(name: 'projectType')
  String? get projectType;
  @override
  @JsonKey(name: 'executingAuthority')
  String? get executingAuthority;
  @override
  @JsonKey(name: 'attendanceRegisterNo')
  String? get attendanceRegisterNo;
  @override
  @JsonKey(name: 'attendanceRegisterName')
  String? get attendanceRegisterName;
  @override
  @JsonKey(ignore: true)
  _$$MusterRollAdditionalDetailsImplCopyWith<_$MusterRollAdditionalDetailsImpl>
      get copyWith => throw _privateConstructorUsedError;
}

Measurement _$MeasurementFromJson(Map<String, dynamic> json) {
  return _Measurement.fromJson(json);
}

/// @nodoc
mixin _$Measurement {
  @JsonKey(name: 'id')
  String? get id => throw _privateConstructorUsedError;
  @JsonKey(name: 'tenantId')
  String? get tenantId => throw _privateConstructorUsedError;
  @JsonKey(name: 'measurementNumber')
  String? get measurementNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'physicalRefNumber')
  String? get physicalRefNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'referenceId')
  String? get referenceId => throw _privateConstructorUsedError;
  @JsonKey(name: 'entryDate')
  int? get entryDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'isActive')
  bool? get isActive => throw _privateConstructorUsedError;
  @JsonKey(name: 'wfStatus')
  String? get wfStatus => throw _privateConstructorUsedError;
  @JsonKey(name: 'workflow')
  WorkFlow? get workflow => throw _privateConstructorUsedError;
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails => throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  MeasurementAdditionalDetail? get additionalDetail =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'measures')
  List<Measure>? get measures => throw _privateConstructorUsedError;
  @JsonKey(name: 'documents')
  List<WorkflowDocument>? get documents => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MeasurementCopyWith<Measurement> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MeasurementCopyWith<$Res> {
  factory $MeasurementCopyWith(
          Measurement value, $Res Function(Measurement) then) =
      _$MeasurementCopyWithImpl<$Res, Measurement>;
  @useResult
  $Res call(
      {@JsonKey(name: 'id')
          String? id,
      @JsonKey(name: 'tenantId')
          String? tenantId,
      @JsonKey(name: 'measurementNumber')
          String? measurementNumber,
      @JsonKey(name: 'physicalRefNumber')
          String? physicalRefNumber,
      @JsonKey(name: 'referenceId')
          String? referenceId,
      @JsonKey(name: 'entryDate')
          int? entryDate,
      @JsonKey(name: 'isActive')
          bool? isActive,
      @JsonKey(name: 'wfStatus')
          String? wfStatus,
      @JsonKey(name: 'workflow')
          WorkFlow? workflow,
      @JsonKey(name: 'auditDetails')
          AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          MeasurementAdditionalDetail? additionalDetail,
      @JsonKey(name: 'measures')
          List<Measure>? measures,
      @JsonKey(name: 'documents')
          List<WorkflowDocument>? documents});

  $WorkFlowCopyWith<$Res>? get workflow;
  $AuditDetailsCopyWith<$Res>? get auditDetails;
  $MeasurementAdditionalDetailCopyWith<$Res>? get additionalDetail;
}

/// @nodoc
class _$MeasurementCopyWithImpl<$Res, $Val extends Measurement>
    implements $MeasurementCopyWith<$Res> {
  _$MeasurementCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? measurementNumber = freezed,
    Object? physicalRefNumber = freezed,
    Object? referenceId = freezed,
    Object? entryDate = freezed,
    Object? isActive = freezed,
    Object? wfStatus = freezed,
    Object? workflow = freezed,
    Object? auditDetails = freezed,
    Object? additionalDetail = freezed,
    Object? measures = freezed,
    Object? documents = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      measurementNumber: freezed == measurementNumber
          ? _value.measurementNumber
          : measurementNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      physicalRefNumber: freezed == physicalRefNumber
          ? _value.physicalRefNumber
          : physicalRefNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
              as String?,
      entryDate: freezed == entryDate
          ? _value.entryDate
          : entryDate // ignore: cast_nullable_to_non_nullable
              as int?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
      wfStatus: freezed == wfStatus
          ? _value.wfStatus
          : wfStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      workflow: freezed == workflow
          ? _value.workflow
          : workflow // ignore: cast_nullable_to_non_nullable
              as WorkFlow?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      additionalDetail: freezed == additionalDetail
          ? _value.additionalDetail
          : additionalDetail // ignore: cast_nullable_to_non_nullable
              as MeasurementAdditionalDetail?,
      measures: freezed == measures
          ? _value.measures
          : measures // ignore: cast_nullable_to_non_nullable
              as List<Measure>?,
      documents: freezed == documents
          ? _value.documents
          : documents // ignore: cast_nullable_to_non_nullable
              as List<WorkflowDocument>?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $WorkFlowCopyWith<$Res>? get workflow {
    if (_value.workflow == null) {
      return null;
    }

    return $WorkFlowCopyWith<$Res>(_value.workflow!, (value) {
      return _then(_value.copyWith(workflow: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $AuditDetailsCopyWith<$Res>? get auditDetails {
    if (_value.auditDetails == null) {
      return null;
    }

    return $AuditDetailsCopyWith<$Res>(_value.auditDetails!, (value) {
      return _then(_value.copyWith(auditDetails: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $MeasurementAdditionalDetailCopyWith<$Res>? get additionalDetail {
    if (_value.additionalDetail == null) {
      return null;
    }

    return $MeasurementAdditionalDetailCopyWith<$Res>(_value.additionalDetail!,
        (value) {
      return _then(_value.copyWith(additionalDetail: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$MeasurementImplCopyWith<$Res>
    implements $MeasurementCopyWith<$Res> {
  factory _$$MeasurementImplCopyWith(
          _$MeasurementImpl value, $Res Function(_$MeasurementImpl) then) =
      __$$MeasurementImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'id')
          String? id,
      @JsonKey(name: 'tenantId')
          String? tenantId,
      @JsonKey(name: 'measurementNumber')
          String? measurementNumber,
      @JsonKey(name: 'physicalRefNumber')
          String? physicalRefNumber,
      @JsonKey(name: 'referenceId')
          String? referenceId,
      @JsonKey(name: 'entryDate')
          int? entryDate,
      @JsonKey(name: 'isActive')
          bool? isActive,
      @JsonKey(name: 'wfStatus')
          String? wfStatus,
      @JsonKey(name: 'workflow')
          WorkFlow? workflow,
      @JsonKey(name: 'auditDetails')
          AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          MeasurementAdditionalDetail? additionalDetail,
      @JsonKey(name: 'measures')
          List<Measure>? measures,
      @JsonKey(name: 'documents')
          List<WorkflowDocument>? documents});

  @override
  $WorkFlowCopyWith<$Res>? get workflow;
  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
  @override
  $MeasurementAdditionalDetailCopyWith<$Res>? get additionalDetail;
}

/// @nodoc
class __$$MeasurementImplCopyWithImpl<$Res>
    extends _$MeasurementCopyWithImpl<$Res, _$MeasurementImpl>
    implements _$$MeasurementImplCopyWith<$Res> {
  __$$MeasurementImplCopyWithImpl(
      _$MeasurementImpl _value, $Res Function(_$MeasurementImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? measurementNumber = freezed,
    Object? physicalRefNumber = freezed,
    Object? referenceId = freezed,
    Object? entryDate = freezed,
    Object? isActive = freezed,
    Object? wfStatus = freezed,
    Object? workflow = freezed,
    Object? auditDetails = freezed,
    Object? additionalDetail = freezed,
    Object? measures = freezed,
    Object? documents = freezed,
  }) {
    return _then(_$MeasurementImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      measurementNumber: freezed == measurementNumber
          ? _value.measurementNumber
          : measurementNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      physicalRefNumber: freezed == physicalRefNumber
          ? _value.physicalRefNumber
          : physicalRefNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
              as String?,
      entryDate: freezed == entryDate
          ? _value.entryDate
          : entryDate // ignore: cast_nullable_to_non_nullable
              as int?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
      wfStatus: freezed == wfStatus
          ? _value.wfStatus
          : wfStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      workflow: freezed == workflow
          ? _value.workflow
          : workflow // ignore: cast_nullable_to_non_nullable
              as WorkFlow?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      additionalDetail: freezed == additionalDetail
          ? _value.additionalDetail
          : additionalDetail // ignore: cast_nullable_to_non_nullable
              as MeasurementAdditionalDetail?,
      measures: freezed == measures
          ? _value._measures
          : measures // ignore: cast_nullable_to_non_nullable
              as List<Measure>?,
      documents: freezed == documents
          ? _value._documents
          : documents // ignore: cast_nullable_to_non_nullable
              as List<WorkflowDocument>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MeasurementImpl implements _Measurement {
  const _$MeasurementImpl(
      {@JsonKey(name: 'id') this.id,
      @JsonKey(name: 'tenantId') this.tenantId,
      @JsonKey(name: 'measurementNumber') this.measurementNumber,
      @JsonKey(name: 'physicalRefNumber') this.physicalRefNumber,
      @JsonKey(name: 'referenceId') this.referenceId,
      @JsonKey(name: 'entryDate') this.entryDate,
      @JsonKey(name: 'isActive') this.isActive,
      @JsonKey(name: 'wfStatus') this.wfStatus,
      @JsonKey(name: 'workflow') this.workflow,
      @JsonKey(name: 'auditDetails') this.auditDetails,
      @JsonKey(name: 'additionalDetails') this.additionalDetail,
      @JsonKey(name: 'measures') final List<Measure>? measures,
      @JsonKey(name: 'documents') final List<WorkflowDocument>? documents})
      : _measures = measures,
        _documents = documents;

  factory _$MeasurementImpl.fromJson(Map<String, dynamic> json) =>
      _$$MeasurementImplFromJson(json);

  @override
  @JsonKey(name: 'id')
  final String? id;
  @override
  @JsonKey(name: 'tenantId')
  final String? tenantId;
  @override
  @JsonKey(name: 'measurementNumber')
  final String? measurementNumber;
  @override
  @JsonKey(name: 'physicalRefNumber')
  final String? physicalRefNumber;
  @override
  @JsonKey(name: 'referenceId')
  final String? referenceId;
  @override
  @JsonKey(name: 'entryDate')
  final int? entryDate;
  @override
  @JsonKey(name: 'isActive')
  final bool? isActive;
  @override
  @JsonKey(name: 'wfStatus')
  final String? wfStatus;
  @override
  @JsonKey(name: 'workflow')
  final WorkFlow? workflow;
  @override
  @JsonKey(name: 'auditDetails')
  final AuditDetails? auditDetails;
  @override
  @JsonKey(name: 'additionalDetails')
  final MeasurementAdditionalDetail? additionalDetail;
  final List<Measure>? _measures;
  @override
  @JsonKey(name: 'measures')
  List<Measure>? get measures {
    final value = _measures;
    if (value == null) return null;
    if (_measures is EqualUnmodifiableListView) return _measures;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<WorkflowDocument>? _documents;
  @override
  @JsonKey(name: 'documents')
  List<WorkflowDocument>? get documents {
    final value = _documents;
    if (value == null) return null;
    if (_documents is EqualUnmodifiableListView) return _documents;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'Measurement(id: $id, tenantId: $tenantId, measurementNumber: $measurementNumber, physicalRefNumber: $physicalRefNumber, referenceId: $referenceId, entryDate: $entryDate, isActive: $isActive, wfStatus: $wfStatus, workflow: $workflow, auditDetails: $auditDetails, additionalDetail: $additionalDetail, measures: $measures, documents: $documents)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.measurementNumber, measurementNumber) ||
                other.measurementNumber == measurementNumber) &&
            (identical(other.physicalRefNumber, physicalRefNumber) ||
                other.physicalRefNumber == physicalRefNumber) &&
            (identical(other.referenceId, referenceId) ||
                other.referenceId == referenceId) &&
            (identical(other.entryDate, entryDate) ||
                other.entryDate == entryDate) &&
            (identical(other.isActive, isActive) ||
                other.isActive == isActive) &&
            (identical(other.wfStatus, wfStatus) ||
                other.wfStatus == wfStatus) &&
            (identical(other.workflow, workflow) ||
                other.workflow == workflow) &&
            (identical(other.auditDetails, auditDetails) ||
                other.auditDetails == auditDetails) &&
            (identical(other.additionalDetail, additionalDetail) ||
                other.additionalDetail == additionalDetail) &&
            const DeepCollectionEquality().equals(other._measures, _measures) &&
            const DeepCollectionEquality()
                .equals(other._documents, _documents));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      tenantId,
      measurementNumber,
      physicalRefNumber,
      referenceId,
      entryDate,
      isActive,
      wfStatus,
      workflow,
      auditDetails,
      additionalDetail,
      const DeepCollectionEquality().hash(_measures),
      const DeepCollectionEquality().hash(_documents));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementImplCopyWith<_$MeasurementImpl> get copyWith =>
      __$$MeasurementImplCopyWithImpl<_$MeasurementImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MeasurementImplToJson(
      this,
    );
  }
}

abstract class _Measurement implements Measurement {
  const factory _Measurement(
      {@JsonKey(name: 'id')
          final String? id,
      @JsonKey(name: 'tenantId')
          final String? tenantId,
      @JsonKey(name: 'measurementNumber')
          final String? measurementNumber,
      @JsonKey(name: 'physicalRefNumber')
          final String? physicalRefNumber,
      @JsonKey(name: 'referenceId')
          final String? referenceId,
      @JsonKey(name: 'entryDate')
          final int? entryDate,
      @JsonKey(name: 'isActive')
          final bool? isActive,
      @JsonKey(name: 'wfStatus')
          final String? wfStatus,
      @JsonKey(name: 'workflow')
          final WorkFlow? workflow,
      @JsonKey(name: 'auditDetails')
          final AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          final MeasurementAdditionalDetail? additionalDetail,
      @JsonKey(name: 'measures')
          final List<Measure>? measures,
      @JsonKey(name: 'documents')
          final List<WorkflowDocument>? documents}) = _$MeasurementImpl;

  factory _Measurement.fromJson(Map<String, dynamic> json) =
      _$MeasurementImpl.fromJson;

  @override
  @JsonKey(name: 'id')
  String? get id;
  @override
  @JsonKey(name: 'tenantId')
  String? get tenantId;
  @override
  @JsonKey(name: 'measurementNumber')
  String? get measurementNumber;
  @override
  @JsonKey(name: 'physicalRefNumber')
  String? get physicalRefNumber;
  @override
  @JsonKey(name: 'referenceId')
  String? get referenceId;
  @override
  @JsonKey(name: 'entryDate')
  int? get entryDate;
  @override
  @JsonKey(name: 'isActive')
  bool? get isActive;
  @override
  @JsonKey(name: 'wfStatus')
  String? get wfStatus;
  @override
  @JsonKey(name: 'workflow')
  WorkFlow? get workflow;
  @override
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails;
  @override
  @JsonKey(name: 'additionalDetails')
  MeasurementAdditionalDetail? get additionalDetail;
  @override
  @JsonKey(name: 'measures')
  List<Measure>? get measures;
  @override
  @JsonKey(name: 'documents')
  List<WorkflowDocument>? get documents;
  @override
  @JsonKey(ignore: true)
  _$$MeasurementImplCopyWith<_$MeasurementImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

MeasurementAdditionalDetail _$MeasurementAdditionalDetailFromJson(
    Map<String, dynamic> json) {
  return _MeasurementAdditionalDetail.fromJson(json);
}

/// @nodoc
mixin _$MeasurementAdditionalDetail {
  @JsonKey(name: 'endDate')
  int? get endDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'sorAmount')
  double? get sorAmount => throw _privateConstructorUsedError;
  @JsonKey(name: 'startDate')
  int? get startDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'totalAmount')
  double? get totalAmount => throw _privateConstructorUsedError;
  @JsonKey(name: 'nonSorAmount')
  double? get nonSorAmount => throw _privateConstructorUsedError;
  @JsonKey(name: 'musterRollNumber')
  dynamic get musterRollNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'source')
  dynamic get source => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MeasurementAdditionalDetailCopyWith<MeasurementAdditionalDetail>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MeasurementAdditionalDetailCopyWith<$Res> {
  factory $MeasurementAdditionalDetailCopyWith(
          MeasurementAdditionalDetail value,
          $Res Function(MeasurementAdditionalDetail) then) =
      _$MeasurementAdditionalDetailCopyWithImpl<$Res,
          MeasurementAdditionalDetail>;
  @useResult
  $Res call(
      {@JsonKey(name: 'endDate') int? endDate,
      @JsonKey(name: 'sorAmount') double? sorAmount,
      @JsonKey(name: 'startDate') int? startDate,
      @JsonKey(name: 'totalAmount') double? totalAmount,
      @JsonKey(name: 'nonSorAmount') double? nonSorAmount,
      @JsonKey(name: 'musterRollNumber') dynamic musterRollNumber,
      @JsonKey(name: 'source') dynamic source});
}

/// @nodoc
class _$MeasurementAdditionalDetailCopyWithImpl<$Res,
        $Val extends MeasurementAdditionalDetail>
    implements $MeasurementAdditionalDetailCopyWith<$Res> {
  _$MeasurementAdditionalDetailCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? endDate = freezed,
    Object? sorAmount = freezed,
    Object? startDate = freezed,
    Object? totalAmount = freezed,
    Object? nonSorAmount = freezed,
    Object? musterRollNumber = freezed,
    Object? source = freezed,
  }) {
    return _then(_value.copyWith(
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      sorAmount: freezed == sorAmount
          ? _value.sorAmount
          : sorAmount // ignore: cast_nullable_to_non_nullable
              as double?,
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      totalAmount: freezed == totalAmount
          ? _value.totalAmount
          : totalAmount // ignore: cast_nullable_to_non_nullable
              as double?,
      nonSorAmount: freezed == nonSorAmount
          ? _value.nonSorAmount
          : nonSorAmount // ignore: cast_nullable_to_non_nullable
              as double?,
      musterRollNumber: freezed == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as dynamic,
      source: freezed == source
          ? _value.source
          : source // ignore: cast_nullable_to_non_nullable
              as dynamic,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$MeasurementAdditionalDetailImplCopyWith<$Res>
    implements $MeasurementAdditionalDetailCopyWith<$Res> {
  factory _$$MeasurementAdditionalDetailImplCopyWith(
          _$MeasurementAdditionalDetailImpl value,
          $Res Function(_$MeasurementAdditionalDetailImpl) then) =
      __$$MeasurementAdditionalDetailImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'endDate') int? endDate,
      @JsonKey(name: 'sorAmount') double? sorAmount,
      @JsonKey(name: 'startDate') int? startDate,
      @JsonKey(name: 'totalAmount') double? totalAmount,
      @JsonKey(name: 'nonSorAmount') double? nonSorAmount,
      @JsonKey(name: 'musterRollNumber') dynamic musterRollNumber,
      @JsonKey(name: 'source') dynamic source});
}

/// @nodoc
class __$$MeasurementAdditionalDetailImplCopyWithImpl<$Res>
    extends _$MeasurementAdditionalDetailCopyWithImpl<$Res,
        _$MeasurementAdditionalDetailImpl>
    implements _$$MeasurementAdditionalDetailImplCopyWith<$Res> {
  __$$MeasurementAdditionalDetailImplCopyWithImpl(
      _$MeasurementAdditionalDetailImpl _value,
      $Res Function(_$MeasurementAdditionalDetailImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? endDate = freezed,
    Object? sorAmount = freezed,
    Object? startDate = freezed,
    Object? totalAmount = freezed,
    Object? nonSorAmount = freezed,
    Object? musterRollNumber = freezed,
    Object? source = freezed,
  }) {
    return _then(_$MeasurementAdditionalDetailImpl(
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      sorAmount: freezed == sorAmount
          ? _value.sorAmount
          : sorAmount // ignore: cast_nullable_to_non_nullable
              as double?,
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      totalAmount: freezed == totalAmount
          ? _value.totalAmount
          : totalAmount // ignore: cast_nullable_to_non_nullable
              as double?,
      nonSorAmount: freezed == nonSorAmount
          ? _value.nonSorAmount
          : nonSorAmount // ignore: cast_nullable_to_non_nullable
              as double?,
      musterRollNumber: freezed == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as dynamic,
      source: freezed == source
          ? _value.source
          : source // ignore: cast_nullable_to_non_nullable
              as dynamic,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MeasurementAdditionalDetailImpl
    implements _MeasurementAdditionalDetail {
  const _$MeasurementAdditionalDetailImpl(
      {@JsonKey(name: 'endDate') this.endDate,
      @JsonKey(name: 'sorAmount') this.sorAmount,
      @JsonKey(name: 'startDate') this.startDate,
      @JsonKey(name: 'totalAmount') this.totalAmount,
      @JsonKey(name: 'nonSorAmount') this.nonSorAmount,
      @JsonKey(name: 'musterRollNumber') this.musterRollNumber,
      @JsonKey(name: 'source') this.source});

  factory _$MeasurementAdditionalDetailImpl.fromJson(
          Map<String, dynamic> json) =>
      _$$MeasurementAdditionalDetailImplFromJson(json);

  @override
  @JsonKey(name: 'endDate')
  final int? endDate;
  @override
  @JsonKey(name: 'sorAmount')
  final double? sorAmount;
  @override
  @JsonKey(name: 'startDate')
  final int? startDate;
  @override
  @JsonKey(name: 'totalAmount')
  final double? totalAmount;
  @override
  @JsonKey(name: 'nonSorAmount')
  final double? nonSorAmount;
  @override
  @JsonKey(name: 'musterRollNumber')
  final dynamic musterRollNumber;
  @override
  @JsonKey(name: 'source')
  final dynamic source;

  @override
  String toString() {
    return 'MeasurementAdditionalDetail(endDate: $endDate, sorAmount: $sorAmount, startDate: $startDate, totalAmount: $totalAmount, nonSorAmount: $nonSorAmount, musterRollNumber: $musterRollNumber, source: $source)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementAdditionalDetailImpl &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            (identical(other.sorAmount, sorAmount) ||
                other.sorAmount == sorAmount) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.totalAmount, totalAmount) ||
                other.totalAmount == totalAmount) &&
            (identical(other.nonSorAmount, nonSorAmount) ||
                other.nonSorAmount == nonSorAmount) &&
            const DeepCollectionEquality()
                .equals(other.musterRollNumber, musterRollNumber) &&
            const DeepCollectionEquality().equals(other.source, source));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      endDate,
      sorAmount,
      startDate,
      totalAmount,
      nonSorAmount,
      const DeepCollectionEquality().hash(musterRollNumber),
      const DeepCollectionEquality().hash(source));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementAdditionalDetailImplCopyWith<_$MeasurementAdditionalDetailImpl>
      get copyWith => __$$MeasurementAdditionalDetailImplCopyWithImpl<
          _$MeasurementAdditionalDetailImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MeasurementAdditionalDetailImplToJson(
      this,
    );
  }
}

abstract class _MeasurementAdditionalDetail
    implements MeasurementAdditionalDetail {
  const factory _MeasurementAdditionalDetail(
          {@JsonKey(name: 'endDate') final int? endDate,
          @JsonKey(name: 'sorAmount') final double? sorAmount,
          @JsonKey(name: 'startDate') final int? startDate,
          @JsonKey(name: 'totalAmount') final double? totalAmount,
          @JsonKey(name: 'nonSorAmount') final double? nonSorAmount,
          @JsonKey(name: 'musterRollNumber') final dynamic musterRollNumber,
          @JsonKey(name: 'source') final dynamic source}) =
      _$MeasurementAdditionalDetailImpl;

  factory _MeasurementAdditionalDetail.fromJson(Map<String, dynamic> json) =
      _$MeasurementAdditionalDetailImpl.fromJson;

  @override
  @JsonKey(name: 'endDate')
  int? get endDate;
  @override
  @JsonKey(name: 'sorAmount')
  double? get sorAmount;
  @override
  @JsonKey(name: 'startDate')
  int? get startDate;
  @override
  @JsonKey(name: 'totalAmount')
  double? get totalAmount;
  @override
  @JsonKey(name: 'nonSorAmount')
  double? get nonSorAmount;
  @override
  @JsonKey(name: 'musterRollNumber')
  dynamic get musterRollNumber;
  @override
  @JsonKey(name: 'source')
  dynamic get source;
  @override
  @JsonKey(ignore: true)
  _$$MeasurementAdditionalDetailImplCopyWith<_$MeasurementAdditionalDetailImpl>
      get copyWith => throw _privateConstructorUsedError;
}

Period _$PeriodFromJson(Map<String, dynamic> json) {
  return _Period.fromJson(json);
}

/// @nodoc
mixin _$Period {
  @JsonKey(name: 'startDate')
  int? get startDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'endDate')
  int? get endDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'message')
  String? get message => throw _privateConstructorUsedError;
  @JsonKey(name: 'type')
  String? get type => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $PeriodCopyWith<Period> get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $PeriodCopyWith<$Res> {
  factory $PeriodCopyWith(Period value, $Res Function(Period) then) =
      _$PeriodCopyWithImpl<$Res, Period>;
  @useResult
  $Res call(
      {@JsonKey(name: 'startDate') int? startDate,
      @JsonKey(name: 'endDate') int? endDate,
      @JsonKey(name: 'message') String? message,
      @JsonKey(name: 'type') String? type});
}

/// @nodoc
class _$PeriodCopyWithImpl<$Res, $Val extends Period>
    implements $PeriodCopyWith<$Res> {
  _$PeriodCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? message = freezed,
    Object? type = freezed,
  }) {
    return _then(_value.copyWith(
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      message: freezed == message
          ? _value.message
          : message // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$PeriodImplCopyWith<$Res> implements $PeriodCopyWith<$Res> {
  factory _$$PeriodImplCopyWith(
          _$PeriodImpl value, $Res Function(_$PeriodImpl) then) =
      __$$PeriodImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'startDate') int? startDate,
      @JsonKey(name: 'endDate') int? endDate,
      @JsonKey(name: 'message') String? message,
      @JsonKey(name: 'type') String? type});
}

/// @nodoc
class __$$PeriodImplCopyWithImpl<$Res>
    extends _$PeriodCopyWithImpl<$Res, _$PeriodImpl>
    implements _$$PeriodImplCopyWith<$Res> {
  __$$PeriodImplCopyWithImpl(
      _$PeriodImpl _value, $Res Function(_$PeriodImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? message = freezed,
    Object? type = freezed,
  }) {
    return _then(_$PeriodImpl(
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      message: freezed == message
          ? _value.message
          : message // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$PeriodImpl implements _Period {
  const _$PeriodImpl(
      {@JsonKey(name: 'startDate') this.startDate,
      @JsonKey(name: 'endDate') this.endDate,
      @JsonKey(name: 'message') this.message,
      @JsonKey(name: 'type') this.type});

  factory _$PeriodImpl.fromJson(Map<String, dynamic> json) =>
      _$$PeriodImplFromJson(json);

  @override
  @JsonKey(name: 'startDate')
  final int? startDate;
  @override
  @JsonKey(name: 'endDate')
  final int? endDate;
  @override
  @JsonKey(name: 'message')
  final String? message;
  @override
  @JsonKey(name: 'type')
  final String? type;

  @override
  String toString() {
    return 'Period(startDate: $startDate, endDate: $endDate, message: $message, type: $type)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$PeriodImpl &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            (identical(other.message, message) || other.message == message) &&
            (identical(other.type, type) || other.type == type));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, startDate, endDate, message, type);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$PeriodImplCopyWith<_$PeriodImpl> get copyWith =>
      __$$PeriodImplCopyWithImpl<_$PeriodImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$PeriodImplToJson(
      this,
    );
  }
}

abstract class _Period implements Period {
  const factory _Period(
      {@JsonKey(name: 'startDate') final int? startDate,
      @JsonKey(name: 'endDate') final int? endDate,
      @JsonKey(name: 'message') final String? message,
      @JsonKey(name: 'type') final String? type}) = _$PeriodImpl;

  factory _Period.fromJson(Map<String, dynamic> json) = _$PeriodImpl.fromJson;

  @override
  @JsonKey(name: 'startDate')
  int? get startDate;
  @override
  @JsonKey(name: 'endDate')
  int? get endDate;
  @override
  @JsonKey(name: 'message')
  String? get message;
  @override
  @JsonKey(name: 'type')
  String? get type;
  @override
  @JsonKey(ignore: true)
  _$$PeriodImplCopyWith<_$PeriodImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

Estimate _$EstimateFromJson(Map<String, dynamic> json) {
  return _Estimate.fromJson(json);
}

/// @nodoc
mixin _$Estimate {
  @JsonKey(name: 'id')
  String? get id => throw _privateConstructorUsedError;
  @JsonKey(name: 'tenantId')
  String? get tenantId => throw _privateConstructorUsedError;
  @JsonKey(name: 'estimateNumber')
  String? get estimateNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'revisionNumber')
  String? get revisionNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'businessService')
  String? get businessService => throw _privateConstructorUsedError;
  @JsonKey(name: 'oldUuid')
  String? get oldUuid => throw _privateConstructorUsedError;
  @JsonKey(name: 'projectId')
  String? get projectId => throw _privateConstructorUsedError;
  @JsonKey(name: 'versionNumber')
  int? get versionNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'proposalDate')
  int? get proposalDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'status')
  String? get status => throw _privateConstructorUsedError;
  @JsonKey(name: 'wfStatus')
  String? get wfStatus => throw _privateConstructorUsedError;
  @JsonKey(name: 'name')
  String? get name => throw _privateConstructorUsedError;
  @JsonKey(name: 'referenceNumber')
  String? get referenceNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'description')
  String? get description => throw _privateConstructorUsedError;
  @JsonKey(name: 'executingDepartment')
  String? get executingDepartment => throw _privateConstructorUsedError;
  @JsonKey(name: 'address')
  EstimateAddress? get address => throw _privateConstructorUsedError;
  @JsonKey(name: 'estimateDetails')
  List<EstimateDetail>? get estimateDetails =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $EstimateCopyWith<Estimate> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EstimateCopyWith<$Res> {
  factory $EstimateCopyWith(Estimate value, $Res Function(Estimate) then) =
      _$EstimateCopyWithImpl<$Res, Estimate>;
  @useResult
  $Res call(
      {@JsonKey(name: 'id') String? id,
      @JsonKey(name: 'tenantId') String? tenantId,
      @JsonKey(name: 'estimateNumber') String? estimateNumber,
      @JsonKey(name: 'revisionNumber') String? revisionNumber,
      @JsonKey(name: 'businessService') String? businessService,
      @JsonKey(name: 'oldUuid') String? oldUuid,
      @JsonKey(name: 'projectId') String? projectId,
      @JsonKey(name: 'versionNumber') int? versionNumber,
      @JsonKey(name: 'proposalDate') int? proposalDate,
      @JsonKey(name: 'status') String? status,
      @JsonKey(name: 'wfStatus') String? wfStatus,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'referenceNumber') String? referenceNumber,
      @JsonKey(name: 'description') String? description,
      @JsonKey(name: 'executingDepartment') String? executingDepartment,
      @JsonKey(name: 'address') EstimateAddress? address,
      @JsonKey(name: 'estimateDetails') List<EstimateDetail>? estimateDetails,
      @JsonKey(name: 'auditDetails') AuditDetails? auditDetails});

  $EstimateAddressCopyWith<$Res>? get address;
  $AuditDetailsCopyWith<$Res>? get auditDetails;
}

/// @nodoc
class _$EstimateCopyWithImpl<$Res, $Val extends Estimate>
    implements $EstimateCopyWith<$Res> {
  _$EstimateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? estimateNumber = freezed,
    Object? revisionNumber = freezed,
    Object? businessService = freezed,
    Object? oldUuid = freezed,
    Object? projectId = freezed,
    Object? versionNumber = freezed,
    Object? proposalDate = freezed,
    Object? status = freezed,
    Object? wfStatus = freezed,
    Object? name = freezed,
    Object? referenceNumber = freezed,
    Object? description = freezed,
    Object? executingDepartment = freezed,
    Object? address = freezed,
    Object? estimateDetails = freezed,
    Object? auditDetails = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      estimateNumber: freezed == estimateNumber
          ? _value.estimateNumber
          : estimateNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      revisionNumber: freezed == revisionNumber
          ? _value.revisionNumber
          : revisionNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      oldUuid: freezed == oldUuid
          ? _value.oldUuid
          : oldUuid // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      versionNumber: freezed == versionNumber
          ? _value.versionNumber
          : versionNumber // ignore: cast_nullable_to_non_nullable
              as int?,
      proposalDate: freezed == proposalDate
          ? _value.proposalDate
          : proposalDate // ignore: cast_nullable_to_non_nullable
              as int?,
      status: freezed == status
          ? _value.status
          : status // ignore: cast_nullable_to_non_nullable
              as String?,
      wfStatus: freezed == wfStatus
          ? _value.wfStatus
          : wfStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceNumber: freezed == referenceNumber
          ? _value.referenceNumber
          : referenceNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      description: freezed == description
          ? _value.description
          : description // ignore: cast_nullable_to_non_nullable
              as String?,
      executingDepartment: freezed == executingDepartment
          ? _value.executingDepartment
          : executingDepartment // ignore: cast_nullable_to_non_nullable
              as String?,
      address: freezed == address
          ? _value.address
          : address // ignore: cast_nullable_to_non_nullable
              as EstimateAddress?,
      estimateDetails: freezed == estimateDetails
          ? _value.estimateDetails
          : estimateDetails // ignore: cast_nullable_to_non_nullable
              as List<EstimateDetail>?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $EstimateAddressCopyWith<$Res>? get address {
    if (_value.address == null) {
      return null;
    }

    return $EstimateAddressCopyWith<$Res>(_value.address!, (value) {
      return _then(_value.copyWith(address: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $AuditDetailsCopyWith<$Res>? get auditDetails {
    if (_value.auditDetails == null) {
      return null;
    }

    return $AuditDetailsCopyWith<$Res>(_value.auditDetails!, (value) {
      return _then(_value.copyWith(auditDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$EstimateImplCopyWith<$Res>
    implements $EstimateCopyWith<$Res> {
  factory _$$EstimateImplCopyWith(
          _$EstimateImpl value, $Res Function(_$EstimateImpl) then) =
      __$$EstimateImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'id') String? id,
      @JsonKey(name: 'tenantId') String? tenantId,
      @JsonKey(name: 'estimateNumber') String? estimateNumber,
      @JsonKey(name: 'revisionNumber') String? revisionNumber,
      @JsonKey(name: 'businessService') String? businessService,
      @JsonKey(name: 'oldUuid') String? oldUuid,
      @JsonKey(name: 'projectId') String? projectId,
      @JsonKey(name: 'versionNumber') int? versionNumber,
      @JsonKey(name: 'proposalDate') int? proposalDate,
      @JsonKey(name: 'status') String? status,
      @JsonKey(name: 'wfStatus') String? wfStatus,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'referenceNumber') String? referenceNumber,
      @JsonKey(name: 'description') String? description,
      @JsonKey(name: 'executingDepartment') String? executingDepartment,
      @JsonKey(name: 'address') EstimateAddress? address,
      @JsonKey(name: 'estimateDetails') List<EstimateDetail>? estimateDetails,
      @JsonKey(name: 'auditDetails') AuditDetails? auditDetails});

  @override
  $EstimateAddressCopyWith<$Res>? get address;
  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
}

/// @nodoc
class __$$EstimateImplCopyWithImpl<$Res>
    extends _$EstimateCopyWithImpl<$Res, _$EstimateImpl>
    implements _$$EstimateImplCopyWith<$Res> {
  __$$EstimateImplCopyWithImpl(
      _$EstimateImpl _value, $Res Function(_$EstimateImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? estimateNumber = freezed,
    Object? revisionNumber = freezed,
    Object? businessService = freezed,
    Object? oldUuid = freezed,
    Object? projectId = freezed,
    Object? versionNumber = freezed,
    Object? proposalDate = freezed,
    Object? status = freezed,
    Object? wfStatus = freezed,
    Object? name = freezed,
    Object? referenceNumber = freezed,
    Object? description = freezed,
    Object? executingDepartment = freezed,
    Object? address = freezed,
    Object? estimateDetails = freezed,
    Object? auditDetails = freezed,
  }) {
    return _then(_$EstimateImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      estimateNumber: freezed == estimateNumber
          ? _value.estimateNumber
          : estimateNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      revisionNumber: freezed == revisionNumber
          ? _value.revisionNumber
          : revisionNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      oldUuid: freezed == oldUuid
          ? _value.oldUuid
          : oldUuid // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      versionNumber: freezed == versionNumber
          ? _value.versionNumber
          : versionNumber // ignore: cast_nullable_to_non_nullable
              as int?,
      proposalDate: freezed == proposalDate
          ? _value.proposalDate
          : proposalDate // ignore: cast_nullable_to_non_nullable
              as int?,
      status: freezed == status
          ? _value.status
          : status // ignore: cast_nullable_to_non_nullable
              as String?,
      wfStatus: freezed == wfStatus
          ? _value.wfStatus
          : wfStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceNumber: freezed == referenceNumber
          ? _value.referenceNumber
          : referenceNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      description: freezed == description
          ? _value.description
          : description // ignore: cast_nullable_to_non_nullable
              as String?,
      executingDepartment: freezed == executingDepartment
          ? _value.executingDepartment
          : executingDepartment // ignore: cast_nullable_to_non_nullable
              as String?,
      address: freezed == address
          ? _value.address
          : address // ignore: cast_nullable_to_non_nullable
              as EstimateAddress?,
      estimateDetails: freezed == estimateDetails
          ? _value._estimateDetails
          : estimateDetails // ignore: cast_nullable_to_non_nullable
              as List<EstimateDetail>?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$EstimateImpl implements _Estimate {
  const _$EstimateImpl(
      {@JsonKey(name: 'id')
          this.id,
      @JsonKey(name: 'tenantId')
          this.tenantId,
      @JsonKey(name: 'estimateNumber')
          this.estimateNumber,
      @JsonKey(name: 'revisionNumber')
          this.revisionNumber,
      @JsonKey(name: 'businessService')
          this.businessService,
      @JsonKey(name: 'oldUuid')
          this.oldUuid,
      @JsonKey(name: 'projectId')
          this.projectId,
      @JsonKey(name: 'versionNumber')
          this.versionNumber,
      @JsonKey(name: 'proposalDate')
          this.proposalDate,
      @JsonKey(name: 'status')
          this.status,
      @JsonKey(name: 'wfStatus')
          this.wfStatus,
      @JsonKey(name: 'name')
          this.name,
      @JsonKey(name: 'referenceNumber')
          this.referenceNumber,
      @JsonKey(name: 'description')
          this.description,
      @JsonKey(name: 'executingDepartment')
          this.executingDepartment,
      @JsonKey(name: 'address')
          this.address,
      @JsonKey(name: 'estimateDetails')
          final List<EstimateDetail>? estimateDetails,
      @JsonKey(name: 'auditDetails')
          this.auditDetails})
      : _estimateDetails = estimateDetails;

  factory _$EstimateImpl.fromJson(Map<String, dynamic> json) =>
      _$$EstimateImplFromJson(json);

  @override
  @JsonKey(name: 'id')
  final String? id;
  @override
  @JsonKey(name: 'tenantId')
  final String? tenantId;
  @override
  @JsonKey(name: 'estimateNumber')
  final String? estimateNumber;
  @override
  @JsonKey(name: 'revisionNumber')
  final String? revisionNumber;
  @override
  @JsonKey(name: 'businessService')
  final String? businessService;
  @override
  @JsonKey(name: 'oldUuid')
  final String? oldUuid;
  @override
  @JsonKey(name: 'projectId')
  final String? projectId;
  @override
  @JsonKey(name: 'versionNumber')
  final int? versionNumber;
  @override
  @JsonKey(name: 'proposalDate')
  final int? proposalDate;
  @override
  @JsonKey(name: 'status')
  final String? status;
  @override
  @JsonKey(name: 'wfStatus')
  final String? wfStatus;
  @override
  @JsonKey(name: 'name')
  final String? name;
  @override
  @JsonKey(name: 'referenceNumber')
  final String? referenceNumber;
  @override
  @JsonKey(name: 'description')
  final String? description;
  @override
  @JsonKey(name: 'executingDepartment')
  final String? executingDepartment;
  @override
  @JsonKey(name: 'address')
  final EstimateAddress? address;
  final List<EstimateDetail>? _estimateDetails;
  @override
  @JsonKey(name: 'estimateDetails')
  List<EstimateDetail>? get estimateDetails {
    final value = _estimateDetails;
    if (value == null) return null;
    if (_estimateDetails is EqualUnmodifiableListView) return _estimateDetails;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'auditDetails')
  final AuditDetails? auditDetails;

  @override
  String toString() {
    return 'Estimate(id: $id, tenantId: $tenantId, estimateNumber: $estimateNumber, revisionNumber: $revisionNumber, businessService: $businessService, oldUuid: $oldUuid, projectId: $projectId, versionNumber: $versionNumber, proposalDate: $proposalDate, status: $status, wfStatus: $wfStatus, name: $name, referenceNumber: $referenceNumber, description: $description, executingDepartment: $executingDepartment, address: $address, estimateDetails: $estimateDetails, auditDetails: $auditDetails)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$EstimateImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.estimateNumber, estimateNumber) ||
                other.estimateNumber == estimateNumber) &&
            (identical(other.revisionNumber, revisionNumber) ||
                other.revisionNumber == revisionNumber) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService) &&
            (identical(other.oldUuid, oldUuid) || other.oldUuid == oldUuid) &&
            (identical(other.projectId, projectId) ||
                other.projectId == projectId) &&
            (identical(other.versionNumber, versionNumber) ||
                other.versionNumber == versionNumber) &&
            (identical(other.proposalDate, proposalDate) ||
                other.proposalDate == proposalDate) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.wfStatus, wfStatus) ||
                other.wfStatus == wfStatus) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.referenceNumber, referenceNumber) ||
                other.referenceNumber == referenceNumber) &&
            (identical(other.description, description) ||
                other.description == description) &&
            (identical(other.executingDepartment, executingDepartment) ||
                other.executingDepartment == executingDepartment) &&
            (identical(other.address, address) || other.address == address) &&
            const DeepCollectionEquality()
                .equals(other._estimateDetails, _estimateDetails) &&
            (identical(other.auditDetails, auditDetails) ||
                other.auditDetails == auditDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      tenantId,
      estimateNumber,
      revisionNumber,
      businessService,
      oldUuid,
      projectId,
      versionNumber,
      proposalDate,
      status,
      wfStatus,
      name,
      referenceNumber,
      description,
      executingDepartment,
      address,
      const DeepCollectionEquality().hash(_estimateDetails),
      auditDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$EstimateImplCopyWith<_$EstimateImpl> get copyWith =>
      __$$EstimateImplCopyWithImpl<_$EstimateImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$EstimateImplToJson(
      this,
    );
  }
}

abstract class _Estimate implements Estimate {
  const factory _Estimate(
      {@JsonKey(name: 'id')
          final String? id,
      @JsonKey(name: 'tenantId')
          final String? tenantId,
      @JsonKey(name: 'estimateNumber')
          final String? estimateNumber,
      @JsonKey(name: 'revisionNumber')
          final String? revisionNumber,
      @JsonKey(name: 'businessService')
          final String? businessService,
      @JsonKey(name: 'oldUuid')
          final String? oldUuid,
      @JsonKey(name: 'projectId')
          final String? projectId,
      @JsonKey(name: 'versionNumber')
          final int? versionNumber,
      @JsonKey(name: 'proposalDate')
          final int? proposalDate,
      @JsonKey(name: 'status')
          final String? status,
      @JsonKey(name: 'wfStatus')
          final String? wfStatus,
      @JsonKey(name: 'name')
          final String? name,
      @JsonKey(name: 'referenceNumber')
          final String? referenceNumber,
      @JsonKey(name: 'description')
          final String? description,
      @JsonKey(name: 'executingDepartment')
          final String? executingDepartment,
      @JsonKey(name: 'address')
          final EstimateAddress? address,
      @JsonKey(name: 'estimateDetails')
          final List<EstimateDetail>? estimateDetails,
      @JsonKey(name: 'auditDetails')
          final AuditDetails? auditDetails}) = _$EstimateImpl;

  factory _Estimate.fromJson(Map<String, dynamic> json) =
      _$EstimateImpl.fromJson;

  @override
  @JsonKey(name: 'id')
  String? get id;
  @override
  @JsonKey(name: 'tenantId')
  String? get tenantId;
  @override
  @JsonKey(name: 'estimateNumber')
  String? get estimateNumber;
  @override
  @JsonKey(name: 'revisionNumber')
  String? get revisionNumber;
  @override
  @JsonKey(name: 'businessService')
  String? get businessService;
  @override
  @JsonKey(name: 'oldUuid')
  String? get oldUuid;
  @override
  @JsonKey(name: 'projectId')
  String? get projectId;
  @override
  @JsonKey(name: 'versionNumber')
  int? get versionNumber;
  @override
  @JsonKey(name: 'proposalDate')
  int? get proposalDate;
  @override
  @JsonKey(name: 'status')
  String? get status;
  @override
  @JsonKey(name: 'wfStatus')
  String? get wfStatus;
  @override
  @JsonKey(name: 'name')
  String? get name;
  @override
  @JsonKey(name: 'referenceNumber')
  String? get referenceNumber;
  @override
  @JsonKey(name: 'description')
  String? get description;
  @override
  @JsonKey(name: 'executingDepartment')
  String? get executingDepartment;
  @override
  @JsonKey(name: 'address')
  EstimateAddress? get address;
  @override
  @JsonKey(name: 'estimateDetails')
  List<EstimateDetail>? get estimateDetails;
  @override
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails;
  @override
  @JsonKey(ignore: true)
  _$$EstimateImplCopyWith<_$EstimateImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

EstimateDetail _$EstimateDetailFromJson(Map<String, dynamic> json) {
  return _EstimateDetail.fromJson(json);
}

/// @nodoc
mixin _$EstimateDetail {
  @JsonKey(name: 'id')
  String? get id => throw _privateConstructorUsedError;
  @JsonKey(name: 'previousLineItemId')
  String? get previousLineItemId => throw _privateConstructorUsedError;
  @JsonKey(name: 'sorId')
  String? get sorId => throw _privateConstructorUsedError;
  @JsonKey(name: 'category')
  String? get category => throw _privateConstructorUsedError;
  @JsonKey(name: 'name')
  String? get name => throw _privateConstructorUsedError;
  @JsonKey(name: 'description')
  String? get description => throw _privateConstructorUsedError;
  @JsonKey(name: 'unitRate')
  double? get unitRate => throw _privateConstructorUsedError;
  @JsonKey(name: 'noOfunit')
  num? get noOfunit => throw _privateConstructorUsedError;
  @JsonKey(name: 'uom')
  String? get uom => throw _privateConstructorUsedError;
  @JsonKey(name: 'uomValue')
  double? get uomValue => throw _privateConstructorUsedError;
  @JsonKey(name: 'length')
  num? get length => throw _privateConstructorUsedError;
  @JsonKey(name: 'width')
  num? get width => throw _privateConstructorUsedError;
  @JsonKey(name: 'height')
  num? get height => throw _privateConstructorUsedError;
  @JsonKey(name: 'quantity')
  num? get quantity => throw _privateConstructorUsedError;
  @JsonKey(name: 'isDeduction')
  bool? get isDeduction => throw _privateConstructorUsedError;
  @JsonKey(name: 'isActive')
  bool? get isActive => throw _privateConstructorUsedError;
  @JsonKey(name: 'amountDetail')
  List<AmoutDetail>? get amountDetails => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $EstimateDetailCopyWith<EstimateDetail> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EstimateDetailCopyWith<$Res> {
  factory $EstimateDetailCopyWith(
          EstimateDetail value, $Res Function(EstimateDetail) then) =
      _$EstimateDetailCopyWithImpl<$Res, EstimateDetail>;
  @useResult
  $Res call(
      {@JsonKey(name: 'id') String? id,
      @JsonKey(name: 'previousLineItemId') String? previousLineItemId,
      @JsonKey(name: 'sorId') String? sorId,
      @JsonKey(name: 'category') String? category,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'description') String? description,
      @JsonKey(name: 'unitRate') double? unitRate,
      @JsonKey(name: 'noOfunit') num? noOfunit,
      @JsonKey(name: 'uom') String? uom,
      @JsonKey(name: 'uomValue') double? uomValue,
      @JsonKey(name: 'length') num? length,
      @JsonKey(name: 'width') num? width,
      @JsonKey(name: 'height') num? height,
      @JsonKey(name: 'quantity') num? quantity,
      @JsonKey(name: 'isDeduction') bool? isDeduction,
      @JsonKey(name: 'isActive') bool? isActive,
      @JsonKey(name: 'amountDetail') List<AmoutDetail>? amountDetails});
}

/// @nodoc
class _$EstimateDetailCopyWithImpl<$Res, $Val extends EstimateDetail>
    implements $EstimateDetailCopyWith<$Res> {
  _$EstimateDetailCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? previousLineItemId = freezed,
    Object? sorId = freezed,
    Object? category = freezed,
    Object? name = freezed,
    Object? description = freezed,
    Object? unitRate = freezed,
    Object? noOfunit = freezed,
    Object? uom = freezed,
    Object? uomValue = freezed,
    Object? length = freezed,
    Object? width = freezed,
    Object? height = freezed,
    Object? quantity = freezed,
    Object? isDeduction = freezed,
    Object? isActive = freezed,
    Object? amountDetails = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      previousLineItemId: freezed == previousLineItemId
          ? _value.previousLineItemId
          : previousLineItemId // ignore: cast_nullable_to_non_nullable
              as String?,
      sorId: freezed == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String?,
      category: freezed == category
          ? _value.category
          : category // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      description: freezed == description
          ? _value.description
          : description // ignore: cast_nullable_to_non_nullable
              as String?,
      unitRate: freezed == unitRate
          ? _value.unitRate
          : unitRate // ignore: cast_nullable_to_non_nullable
              as double?,
      noOfunit: freezed == noOfunit
          ? _value.noOfunit
          : noOfunit // ignore: cast_nullable_to_non_nullable
              as num?,
      uom: freezed == uom
          ? _value.uom
          : uom // ignore: cast_nullable_to_non_nullable
              as String?,
      uomValue: freezed == uomValue
          ? _value.uomValue
          : uomValue // ignore: cast_nullable_to_non_nullable
              as double?,
      length: freezed == length
          ? _value.length
          : length // ignore: cast_nullable_to_non_nullable
              as num?,
      width: freezed == width
          ? _value.width
          : width // ignore: cast_nullable_to_non_nullable
              as num?,
      height: freezed == height
          ? _value.height
          : height // ignore: cast_nullable_to_non_nullable
              as num?,
      quantity: freezed == quantity
          ? _value.quantity
          : quantity // ignore: cast_nullable_to_non_nullable
              as num?,
      isDeduction: freezed == isDeduction
          ? _value.isDeduction
          : isDeduction // ignore: cast_nullable_to_non_nullable
              as bool?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
      amountDetails: freezed == amountDetails
          ? _value.amountDetails
          : amountDetails // ignore: cast_nullable_to_non_nullable
              as List<AmoutDetail>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$EstimateDetailImplCopyWith<$Res>
    implements $EstimateDetailCopyWith<$Res> {
  factory _$$EstimateDetailImplCopyWith(_$EstimateDetailImpl value,
          $Res Function(_$EstimateDetailImpl) then) =
      __$$EstimateDetailImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'id') String? id,
      @JsonKey(name: 'previousLineItemId') String? previousLineItemId,
      @JsonKey(name: 'sorId') String? sorId,
      @JsonKey(name: 'category') String? category,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'description') String? description,
      @JsonKey(name: 'unitRate') double? unitRate,
      @JsonKey(name: 'noOfunit') num? noOfunit,
      @JsonKey(name: 'uom') String? uom,
      @JsonKey(name: 'uomValue') double? uomValue,
      @JsonKey(name: 'length') num? length,
      @JsonKey(name: 'width') num? width,
      @JsonKey(name: 'height') num? height,
      @JsonKey(name: 'quantity') num? quantity,
      @JsonKey(name: 'isDeduction') bool? isDeduction,
      @JsonKey(name: 'isActive') bool? isActive,
      @JsonKey(name: 'amountDetail') List<AmoutDetail>? amountDetails});
}

/// @nodoc
class __$$EstimateDetailImplCopyWithImpl<$Res>
    extends _$EstimateDetailCopyWithImpl<$Res, _$EstimateDetailImpl>
    implements _$$EstimateDetailImplCopyWith<$Res> {
  __$$EstimateDetailImplCopyWithImpl(
      _$EstimateDetailImpl _value, $Res Function(_$EstimateDetailImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? previousLineItemId = freezed,
    Object? sorId = freezed,
    Object? category = freezed,
    Object? name = freezed,
    Object? description = freezed,
    Object? unitRate = freezed,
    Object? noOfunit = freezed,
    Object? uom = freezed,
    Object? uomValue = freezed,
    Object? length = freezed,
    Object? width = freezed,
    Object? height = freezed,
    Object? quantity = freezed,
    Object? isDeduction = freezed,
    Object? isActive = freezed,
    Object? amountDetails = freezed,
  }) {
    return _then(_$EstimateDetailImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      previousLineItemId: freezed == previousLineItemId
          ? _value.previousLineItemId
          : previousLineItemId // ignore: cast_nullable_to_non_nullable
              as String?,
      sorId: freezed == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String?,
      category: freezed == category
          ? _value.category
          : category // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      description: freezed == description
          ? _value.description
          : description // ignore: cast_nullable_to_non_nullable
              as String?,
      unitRate: freezed == unitRate
          ? _value.unitRate
          : unitRate // ignore: cast_nullable_to_non_nullable
              as double?,
      noOfunit: freezed == noOfunit
          ? _value.noOfunit
          : noOfunit // ignore: cast_nullable_to_non_nullable
              as num?,
      uom: freezed == uom
          ? _value.uom
          : uom // ignore: cast_nullable_to_non_nullable
              as String?,
      uomValue: freezed == uomValue
          ? _value.uomValue
          : uomValue // ignore: cast_nullable_to_non_nullable
              as double?,
      length: freezed == length
          ? _value.length
          : length // ignore: cast_nullable_to_non_nullable
              as num?,
      width: freezed == width
          ? _value.width
          : width // ignore: cast_nullable_to_non_nullable
              as num?,
      height: freezed == height
          ? _value.height
          : height // ignore: cast_nullable_to_non_nullable
              as num?,
      quantity: freezed == quantity
          ? _value.quantity
          : quantity // ignore: cast_nullable_to_non_nullable
              as num?,
      isDeduction: freezed == isDeduction
          ? _value.isDeduction
          : isDeduction // ignore: cast_nullable_to_non_nullable
              as bool?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
      amountDetails: freezed == amountDetails
          ? _value._amountDetails
          : amountDetails // ignore: cast_nullable_to_non_nullable
              as List<AmoutDetail>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$EstimateDetailImpl implements _EstimateDetail {
  const _$EstimateDetailImpl(
      {@JsonKey(name: 'id') this.id,
      @JsonKey(name: 'previousLineItemId') this.previousLineItemId,
      @JsonKey(name: 'sorId') this.sorId,
      @JsonKey(name: 'category') this.category,
      @JsonKey(name: 'name') this.name,
      @JsonKey(name: 'description') this.description,
      @JsonKey(name: 'unitRate') this.unitRate,
      @JsonKey(name: 'noOfunit') this.noOfunit,
      @JsonKey(name: 'uom') this.uom,
      @JsonKey(name: 'uomValue') this.uomValue,
      @JsonKey(name: 'length') this.length,
      @JsonKey(name: 'width') this.width,
      @JsonKey(name: 'height') this.height,
      @JsonKey(name: 'quantity') this.quantity,
      @JsonKey(name: 'isDeduction') this.isDeduction,
      @JsonKey(name: 'isActive') this.isActive,
      @JsonKey(name: 'amountDetail') final List<AmoutDetail>? amountDetails})
      : _amountDetails = amountDetails;

  factory _$EstimateDetailImpl.fromJson(Map<String, dynamic> json) =>
      _$$EstimateDetailImplFromJson(json);

  @override
  @JsonKey(name: 'id')
  final String? id;
  @override
  @JsonKey(name: 'previousLineItemId')
  final String? previousLineItemId;
  @override
  @JsonKey(name: 'sorId')
  final String? sorId;
  @override
  @JsonKey(name: 'category')
  final String? category;
  @override
  @JsonKey(name: 'name')
  final String? name;
  @override
  @JsonKey(name: 'description')
  final String? description;
  @override
  @JsonKey(name: 'unitRate')
  final double? unitRate;
  @override
  @JsonKey(name: 'noOfunit')
  final num? noOfunit;
  @override
  @JsonKey(name: 'uom')
  final String? uom;
  @override
  @JsonKey(name: 'uomValue')
  final double? uomValue;
  @override
  @JsonKey(name: 'length')
  final num? length;
  @override
  @JsonKey(name: 'width')
  final num? width;
  @override
  @JsonKey(name: 'height')
  final num? height;
  @override
  @JsonKey(name: 'quantity')
  final num? quantity;
  @override
  @JsonKey(name: 'isDeduction')
  final bool? isDeduction;
  @override
  @JsonKey(name: 'isActive')
  final bool? isActive;
  final List<AmoutDetail>? _amountDetails;
  @override
  @JsonKey(name: 'amountDetail')
  List<AmoutDetail>? get amountDetails {
    final value = _amountDetails;
    if (value == null) return null;
    if (_amountDetails is EqualUnmodifiableListView) return _amountDetails;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'EstimateDetail(id: $id, previousLineItemId: $previousLineItemId, sorId: $sorId, category: $category, name: $name, description: $description, unitRate: $unitRate, noOfunit: $noOfunit, uom: $uom, uomValue: $uomValue, length: $length, width: $width, height: $height, quantity: $quantity, isDeduction: $isDeduction, isActive: $isActive, amountDetails: $amountDetails)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$EstimateDetailImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.previousLineItemId, previousLineItemId) ||
                other.previousLineItemId == previousLineItemId) &&
            (identical(other.sorId, sorId) || other.sorId == sorId) &&
            (identical(other.category, category) ||
                other.category == category) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.description, description) ||
                other.description == description) &&
            (identical(other.unitRate, unitRate) ||
                other.unitRate == unitRate) &&
            (identical(other.noOfunit, noOfunit) ||
                other.noOfunit == noOfunit) &&
            (identical(other.uom, uom) || other.uom == uom) &&
            (identical(other.uomValue, uomValue) ||
                other.uomValue == uomValue) &&
            (identical(other.length, length) || other.length == length) &&
            (identical(other.width, width) || other.width == width) &&
            (identical(other.height, height) || other.height == height) &&
            (identical(other.quantity, quantity) ||
                other.quantity == quantity) &&
            (identical(other.isDeduction, isDeduction) ||
                other.isDeduction == isDeduction) &&
            (identical(other.isActive, isActive) ||
                other.isActive == isActive) &&
            const DeepCollectionEquality()
                .equals(other._amountDetails, _amountDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      previousLineItemId,
      sorId,
      category,
      name,
      description,
      unitRate,
      noOfunit,
      uom,
      uomValue,
      length,
      width,
      height,
      quantity,
      isDeduction,
      isActive,
      const DeepCollectionEquality().hash(_amountDetails));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$EstimateDetailImplCopyWith<_$EstimateDetailImpl> get copyWith =>
      __$$EstimateDetailImplCopyWithImpl<_$EstimateDetailImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$EstimateDetailImplToJson(
      this,
    );
  }
}

abstract class _EstimateDetail implements EstimateDetail {
  const factory _EstimateDetail(
      {@JsonKey(name: 'id')
          final String? id,
      @JsonKey(name: 'previousLineItemId')
          final String? previousLineItemId,
      @JsonKey(name: 'sorId')
          final String? sorId,
      @JsonKey(name: 'category')
          final String? category,
      @JsonKey(name: 'name')
          final String? name,
      @JsonKey(name: 'description')
          final String? description,
      @JsonKey(name: 'unitRate')
          final double? unitRate,
      @JsonKey(name: 'noOfunit')
          final num? noOfunit,
      @JsonKey(name: 'uom')
          final String? uom,
      @JsonKey(name: 'uomValue')
          final double? uomValue,
      @JsonKey(name: 'length')
          final num? length,
      @JsonKey(name: 'width')
          final num? width,
      @JsonKey(name: 'height')
          final num? height,
      @JsonKey(name: 'quantity')
          final num? quantity,
      @JsonKey(name: 'isDeduction')
          final bool? isDeduction,
      @JsonKey(name: 'isActive')
          final bool? isActive,
      @JsonKey(name: 'amountDetail')
          final List<AmoutDetail>? amountDetails}) = _$EstimateDetailImpl;

  factory _EstimateDetail.fromJson(Map<String, dynamic> json) =
      _$EstimateDetailImpl.fromJson;

  @override
  @JsonKey(name: 'id')
  String? get id;
  @override
  @JsonKey(name: 'previousLineItemId')
  String? get previousLineItemId;
  @override
  @JsonKey(name: 'sorId')
  String? get sorId;
  @override
  @JsonKey(name: 'category')
  String? get category;
  @override
  @JsonKey(name: 'name')
  String? get name;
  @override
  @JsonKey(name: 'description')
  String? get description;
  @override
  @JsonKey(name: 'unitRate')
  double? get unitRate;
  @override
  @JsonKey(name: 'noOfunit')
  num? get noOfunit;
  @override
  @JsonKey(name: 'uom')
  String? get uom;
  @override
  @JsonKey(name: 'uomValue')
  double? get uomValue;
  @override
  @JsonKey(name: 'length')
  num? get length;
  @override
  @JsonKey(name: 'width')
  num? get width;
  @override
  @JsonKey(name: 'height')
  num? get height;
  @override
  @JsonKey(name: 'quantity')
  num? get quantity;
  @override
  @JsonKey(name: 'isDeduction')
  bool? get isDeduction;
  @override
  @JsonKey(name: 'isActive')
  bool? get isActive;
  @override
  @JsonKey(name: 'amountDetail')
  List<AmoutDetail>? get amountDetails;
  @override
  @JsonKey(ignore: true)
  _$$EstimateDetailImplCopyWith<_$EstimateDetailImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

AmoutDetail _$AmoutDetailFromJson(Map<String, dynamic> json) {
  return _AmoutDetail.fromJson(json);
}

/// @nodoc
mixin _$AmoutDetail {
  @JsonKey(name: 'type')
  String? get type => throw _privateConstructorUsedError;
  @JsonKey(name: 'amount')
  double? get amount => throw _privateConstructorUsedError;
  @JsonKey(name: 'id')
  String? get id => throw _privateConstructorUsedError;
  @JsonKey(name: 'isActive')
  bool? get isActive => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AmoutDetailCopyWith<AmoutDetail> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AmoutDetailCopyWith<$Res> {
  factory $AmoutDetailCopyWith(
          AmoutDetail value, $Res Function(AmoutDetail) then) =
      _$AmoutDetailCopyWithImpl<$Res, AmoutDetail>;
  @useResult
  $Res call(
      {@JsonKey(name: 'type') String? type,
      @JsonKey(name: 'amount') double? amount,
      @JsonKey(name: 'id') String? id,
      @JsonKey(name: 'isActive') bool? isActive});
}

/// @nodoc
class _$AmoutDetailCopyWithImpl<$Res, $Val extends AmoutDetail>
    implements $AmoutDetailCopyWith<$Res> {
  _$AmoutDetailCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? type = freezed,
    Object? amount = freezed,
    Object? id = freezed,
    Object? isActive = freezed,
  }) {
    return _then(_value.copyWith(
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as double?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$AmoutDetailImplCopyWith<$Res>
    implements $AmoutDetailCopyWith<$Res> {
  factory _$$AmoutDetailImplCopyWith(
          _$AmoutDetailImpl value, $Res Function(_$AmoutDetailImpl) then) =
      __$$AmoutDetailImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'type') String? type,
      @JsonKey(name: 'amount') double? amount,
      @JsonKey(name: 'id') String? id,
      @JsonKey(name: 'isActive') bool? isActive});
}

/// @nodoc
class __$$AmoutDetailImplCopyWithImpl<$Res>
    extends _$AmoutDetailCopyWithImpl<$Res, _$AmoutDetailImpl>
    implements _$$AmoutDetailImplCopyWith<$Res> {
  __$$AmoutDetailImplCopyWithImpl(
      _$AmoutDetailImpl _value, $Res Function(_$AmoutDetailImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? type = freezed,
    Object? amount = freezed,
    Object? id = freezed,
    Object? isActive = freezed,
  }) {
    return _then(_$AmoutDetailImpl(
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as double?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AmoutDetailImpl implements _AmoutDetail {
  const _$AmoutDetailImpl(
      {@JsonKey(name: 'type') this.type,
      @JsonKey(name: 'amount') this.amount,
      @JsonKey(name: 'id') this.id,
      @JsonKey(name: 'isActive') this.isActive});

  factory _$AmoutDetailImpl.fromJson(Map<String, dynamic> json) =>
      _$$AmoutDetailImplFromJson(json);

  @override
  @JsonKey(name: 'type')
  final String? type;
  @override
  @JsonKey(name: 'amount')
  final double? amount;
  @override
  @JsonKey(name: 'id')
  final String? id;
  @override
  @JsonKey(name: 'isActive')
  final bool? isActive;

  @override
  String toString() {
    return 'AmoutDetail(type: $type, amount: $amount, id: $id, isActive: $isActive)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AmoutDetailImpl &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.isActive, isActive) ||
                other.isActive == isActive));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, type, amount, id, isActive);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AmoutDetailImplCopyWith<_$AmoutDetailImpl> get copyWith =>
      __$$AmoutDetailImplCopyWithImpl<_$AmoutDetailImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AmoutDetailImplToJson(
      this,
    );
  }
}

abstract class _AmoutDetail implements AmoutDetail {
  const factory _AmoutDetail(
      {@JsonKey(name: 'type') final String? type,
      @JsonKey(name: 'amount') final double? amount,
      @JsonKey(name: 'id') final String? id,
      @JsonKey(name: 'isActive') final bool? isActive}) = _$AmoutDetailImpl;

  factory _AmoutDetail.fromJson(Map<String, dynamic> json) =
      _$AmoutDetailImpl.fromJson;

  @override
  @JsonKey(name: 'type')
  String? get type;
  @override
  @JsonKey(name: 'amount')
  double? get amount;
  @override
  @JsonKey(name: 'id')
  String? get id;
  @override
  @JsonKey(name: 'isActive')
  bool? get isActive;
  @override
  @JsonKey(ignore: true)
  _$$AmoutDetailImplCopyWith<_$AmoutDetailImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

EstimateAddress _$EstimateAddressFromJson(Map<String, dynamic> json) {
  return _EstimateAddress.fromJson(json);
}

/// @nodoc
mixin _$EstimateAddress {
  @JsonKey(name: 'id')
  String? get id => throw _privateConstructorUsedError;
  @JsonKey(name: 'tenantId')
  String? get tenantId => throw _privateConstructorUsedError;
  @JsonKey(name: 'doorNo')
  String? get doorNo => throw _privateConstructorUsedError;
  @JsonKey(name: 'latitude')
  double? get latitude => throw _privateConstructorUsedError;
  @JsonKey(name: 'longitude')
  double? get longitude => throw _privateConstructorUsedError;
  @JsonKey(name: 'locationAccuracy')
  double? get locationAccuracy => throw _privateConstructorUsedError;
  @JsonKey(name: 'type')
  String? get type => throw _privateConstructorUsedError;
  @JsonKey(name: 'addressNumber')
  String? get addressNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'addressLine1')
  String? get addressLine1 => throw _privateConstructorUsedError;
  @JsonKey(name: 'addressLine2')
  String? get addressLine2 => throw _privateConstructorUsedError;
  @JsonKey(name: 'landmark')
  String? get landmark => throw _privateConstructorUsedError;
  @JsonKey(name: 'city')
  String? get city => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $EstimateAddressCopyWith<EstimateAddress> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EstimateAddressCopyWith<$Res> {
  factory $EstimateAddressCopyWith(
          EstimateAddress value, $Res Function(EstimateAddress) then) =
      _$EstimateAddressCopyWithImpl<$Res, EstimateAddress>;
  @useResult
  $Res call(
      {@JsonKey(name: 'id') String? id,
      @JsonKey(name: 'tenantId') String? tenantId,
      @JsonKey(name: 'doorNo') String? doorNo,
      @JsonKey(name: 'latitude') double? latitude,
      @JsonKey(name: 'longitude') double? longitude,
      @JsonKey(name: 'locationAccuracy') double? locationAccuracy,
      @JsonKey(name: 'type') String? type,
      @JsonKey(name: 'addressNumber') String? addressNumber,
      @JsonKey(name: 'addressLine1') String? addressLine1,
      @JsonKey(name: 'addressLine2') String? addressLine2,
      @JsonKey(name: 'landmark') String? landmark,
      @JsonKey(name: 'city') String? city});
}

/// @nodoc
class _$EstimateAddressCopyWithImpl<$Res, $Val extends EstimateAddress>
    implements $EstimateAddressCopyWith<$Res> {
  _$EstimateAddressCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? doorNo = freezed,
    Object? latitude = freezed,
    Object? longitude = freezed,
    Object? locationAccuracy = freezed,
    Object? type = freezed,
    Object? addressNumber = freezed,
    Object? addressLine1 = freezed,
    Object? addressLine2 = freezed,
    Object? landmark = freezed,
    Object? city = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      doorNo: freezed == doorNo
          ? _value.doorNo
          : doorNo // ignore: cast_nullable_to_non_nullable
              as String?,
      latitude: freezed == latitude
          ? _value.latitude
          : latitude // ignore: cast_nullable_to_non_nullable
              as double?,
      longitude: freezed == longitude
          ? _value.longitude
          : longitude // ignore: cast_nullable_to_non_nullable
              as double?,
      locationAccuracy: freezed == locationAccuracy
          ? _value.locationAccuracy
          : locationAccuracy // ignore: cast_nullable_to_non_nullable
              as double?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      addressNumber: freezed == addressNumber
          ? _value.addressNumber
          : addressNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      addressLine1: freezed == addressLine1
          ? _value.addressLine1
          : addressLine1 // ignore: cast_nullable_to_non_nullable
              as String?,
      addressLine2: freezed == addressLine2
          ? _value.addressLine2
          : addressLine2 // ignore: cast_nullable_to_non_nullable
              as String?,
      landmark: freezed == landmark
          ? _value.landmark
          : landmark // ignore: cast_nullable_to_non_nullable
              as String?,
      city: freezed == city
          ? _value.city
          : city // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$EstimateAddressImplCopyWith<$Res>
    implements $EstimateAddressCopyWith<$Res> {
  factory _$$EstimateAddressImplCopyWith(_$EstimateAddressImpl value,
          $Res Function(_$EstimateAddressImpl) then) =
      __$$EstimateAddressImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'id') String? id,
      @JsonKey(name: 'tenantId') String? tenantId,
      @JsonKey(name: 'doorNo') String? doorNo,
      @JsonKey(name: 'latitude') double? latitude,
      @JsonKey(name: 'longitude') double? longitude,
      @JsonKey(name: 'locationAccuracy') double? locationAccuracy,
      @JsonKey(name: 'type') String? type,
      @JsonKey(name: 'addressNumber') String? addressNumber,
      @JsonKey(name: 'addressLine1') String? addressLine1,
      @JsonKey(name: 'addressLine2') String? addressLine2,
      @JsonKey(name: 'landmark') String? landmark,
      @JsonKey(name: 'city') String? city});
}

/// @nodoc
class __$$EstimateAddressImplCopyWithImpl<$Res>
    extends _$EstimateAddressCopyWithImpl<$Res, _$EstimateAddressImpl>
    implements _$$EstimateAddressImplCopyWith<$Res> {
  __$$EstimateAddressImplCopyWithImpl(
      _$EstimateAddressImpl _value, $Res Function(_$EstimateAddressImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? doorNo = freezed,
    Object? latitude = freezed,
    Object? longitude = freezed,
    Object? locationAccuracy = freezed,
    Object? type = freezed,
    Object? addressNumber = freezed,
    Object? addressLine1 = freezed,
    Object? addressLine2 = freezed,
    Object? landmark = freezed,
    Object? city = freezed,
  }) {
    return _then(_$EstimateAddressImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      doorNo: freezed == doorNo
          ? _value.doorNo
          : doorNo // ignore: cast_nullable_to_non_nullable
              as String?,
      latitude: freezed == latitude
          ? _value.latitude
          : latitude // ignore: cast_nullable_to_non_nullable
              as double?,
      longitude: freezed == longitude
          ? _value.longitude
          : longitude // ignore: cast_nullable_to_non_nullable
              as double?,
      locationAccuracy: freezed == locationAccuracy
          ? _value.locationAccuracy
          : locationAccuracy // ignore: cast_nullable_to_non_nullable
              as double?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      addressNumber: freezed == addressNumber
          ? _value.addressNumber
          : addressNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      addressLine1: freezed == addressLine1
          ? _value.addressLine1
          : addressLine1 // ignore: cast_nullable_to_non_nullable
              as String?,
      addressLine2: freezed == addressLine2
          ? _value.addressLine2
          : addressLine2 // ignore: cast_nullable_to_non_nullable
              as String?,
      landmark: freezed == landmark
          ? _value.landmark
          : landmark // ignore: cast_nullable_to_non_nullable
              as String?,
      city: freezed == city
          ? _value.city
          : city // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$EstimateAddressImpl implements _EstimateAddress {
  const _$EstimateAddressImpl(
      {@JsonKey(name: 'id') this.id,
      @JsonKey(name: 'tenantId') this.tenantId,
      @JsonKey(name: 'doorNo') this.doorNo,
      @JsonKey(name: 'latitude') this.latitude,
      @JsonKey(name: 'longitude') this.longitude,
      @JsonKey(name: 'locationAccuracy') this.locationAccuracy,
      @JsonKey(name: 'type') this.type,
      @JsonKey(name: 'addressNumber') this.addressNumber,
      @JsonKey(name: 'addressLine1') this.addressLine1,
      @JsonKey(name: 'addressLine2') this.addressLine2,
      @JsonKey(name: 'landmark') this.landmark,
      @JsonKey(name: 'city') this.city});

  factory _$EstimateAddressImpl.fromJson(Map<String, dynamic> json) =>
      _$$EstimateAddressImplFromJson(json);

  @override
  @JsonKey(name: 'id')
  final String? id;
  @override
  @JsonKey(name: 'tenantId')
  final String? tenantId;
  @override
  @JsonKey(name: 'doorNo')
  final String? doorNo;
  @override
  @JsonKey(name: 'latitude')
  final double? latitude;
  @override
  @JsonKey(name: 'longitude')
  final double? longitude;
  @override
  @JsonKey(name: 'locationAccuracy')
  final double? locationAccuracy;
  @override
  @JsonKey(name: 'type')
  final String? type;
  @override
  @JsonKey(name: 'addressNumber')
  final String? addressNumber;
  @override
  @JsonKey(name: 'addressLine1')
  final String? addressLine1;
  @override
  @JsonKey(name: 'addressLine2')
  final String? addressLine2;
  @override
  @JsonKey(name: 'landmark')
  final String? landmark;
  @override
  @JsonKey(name: 'city')
  final String? city;

  @override
  String toString() {
    return 'EstimateAddress(id: $id, tenantId: $tenantId, doorNo: $doorNo, latitude: $latitude, longitude: $longitude, locationAccuracy: $locationAccuracy, type: $type, addressNumber: $addressNumber, addressLine1: $addressLine1, addressLine2: $addressLine2, landmark: $landmark, city: $city)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$EstimateAddressImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.doorNo, doorNo) || other.doorNo == doorNo) &&
            (identical(other.latitude, latitude) ||
                other.latitude == latitude) &&
            (identical(other.longitude, longitude) ||
                other.longitude == longitude) &&
            (identical(other.locationAccuracy, locationAccuracy) ||
                other.locationAccuracy == locationAccuracy) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.addressNumber, addressNumber) ||
                other.addressNumber == addressNumber) &&
            (identical(other.addressLine1, addressLine1) ||
                other.addressLine1 == addressLine1) &&
            (identical(other.addressLine2, addressLine2) ||
                other.addressLine2 == addressLine2) &&
            (identical(other.landmark, landmark) ||
                other.landmark == landmark) &&
            (identical(other.city, city) || other.city == city));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      tenantId,
      doorNo,
      latitude,
      longitude,
      locationAccuracy,
      type,
      addressNumber,
      addressLine1,
      addressLine2,
      landmark,
      city);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$EstimateAddressImplCopyWith<_$EstimateAddressImpl> get copyWith =>
      __$$EstimateAddressImplCopyWithImpl<_$EstimateAddressImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$EstimateAddressImplToJson(
      this,
    );
  }
}

abstract class _EstimateAddress implements EstimateAddress {
  const factory _EstimateAddress(
      {@JsonKey(name: 'id') final String? id,
      @JsonKey(name: 'tenantId') final String? tenantId,
      @JsonKey(name: 'doorNo') final String? doorNo,
      @JsonKey(name: 'latitude') final double? latitude,
      @JsonKey(name: 'longitude') final double? longitude,
      @JsonKey(name: 'locationAccuracy') final double? locationAccuracy,
      @JsonKey(name: 'type') final String? type,
      @JsonKey(name: 'addressNumber') final String? addressNumber,
      @JsonKey(name: 'addressLine1') final String? addressLine1,
      @JsonKey(name: 'addressLine2') final String? addressLine2,
      @JsonKey(name: 'landmark') final String? landmark,
      @JsonKey(name: 'city') final String? city}) = _$EstimateAddressImpl;

  factory _EstimateAddress.fromJson(Map<String, dynamic> json) =
      _$EstimateAddressImpl.fromJson;

  @override
  @JsonKey(name: 'id')
  String? get id;
  @override
  @JsonKey(name: 'tenantId')
  String? get tenantId;
  @override
  @JsonKey(name: 'doorNo')
  String? get doorNo;
  @override
  @JsonKey(name: 'latitude')
  double? get latitude;
  @override
  @JsonKey(name: 'longitude')
  double? get longitude;
  @override
  @JsonKey(name: 'locationAccuracy')
  double? get locationAccuracy;
  @override
  @JsonKey(name: 'type')
  String? get type;
  @override
  @JsonKey(name: 'addressNumber')
  String? get addressNumber;
  @override
  @JsonKey(name: 'addressLine1')
  String? get addressLine1;
  @override
  @JsonKey(name: 'addressLine2')
  String? get addressLine2;
  @override
  @JsonKey(name: 'landmark')
  String? get landmark;
  @override
  @JsonKey(name: 'city')
  String? get city;
  @override
  @JsonKey(ignore: true)
  _$$EstimateAddressImplCopyWith<_$EstimateAddressImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

SorObject _$SorObjectFromJson(Map<String, dynamic> json) {
  return _SorObject.fromJson(json);
}

/// @nodoc
mixin _$SorObject {
  String? get sorId => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;
  List<FilteredMeasurementsMeasure> get filteredMeasurementsMeasure =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $SorObjectCopyWith<SorObject> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $SorObjectCopyWith<$Res> {
  factory $SorObjectCopyWith(SorObject value, $Res Function(SorObject) then) =
      _$SorObjectCopyWithImpl<$Res, SorObject>;
  @useResult
  $Res call(
      {String? sorId,
      String? id,
      List<FilteredMeasurementsMeasure> filteredMeasurementsMeasure});
}

/// @nodoc
class _$SorObjectCopyWithImpl<$Res, $Val extends SorObject>
    implements $SorObjectCopyWith<$Res> {
  _$SorObjectCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? sorId = freezed,
    Object? id = freezed,
    Object? filteredMeasurementsMeasure = null,
  }) {
    return _then(_value.copyWith(
      sorId: freezed == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      filteredMeasurementsMeasure: null == filteredMeasurementsMeasure
          ? _value.filteredMeasurementsMeasure
          : filteredMeasurementsMeasure // ignore: cast_nullable_to_non_nullable
              as List<FilteredMeasurementsMeasure>,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$SorObjectImplCopyWith<$Res>
    implements $SorObjectCopyWith<$Res> {
  factory _$$SorObjectImplCopyWith(
          _$SorObjectImpl value, $Res Function(_$SorObjectImpl) then) =
      __$$SorObjectImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? sorId,
      String? id,
      List<FilteredMeasurementsMeasure> filteredMeasurementsMeasure});
}

/// @nodoc
class __$$SorObjectImplCopyWithImpl<$Res>
    extends _$SorObjectCopyWithImpl<$Res, _$SorObjectImpl>
    implements _$$SorObjectImplCopyWith<$Res> {
  __$$SorObjectImplCopyWithImpl(
      _$SorObjectImpl _value, $Res Function(_$SorObjectImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? sorId = freezed,
    Object? id = freezed,
    Object? filteredMeasurementsMeasure = null,
  }) {
    return _then(_$SorObjectImpl(
      sorId: freezed == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      filteredMeasurementsMeasure: null == filteredMeasurementsMeasure
          ? _value._filteredMeasurementsMeasure
          : filteredMeasurementsMeasure // ignore: cast_nullable_to_non_nullable
              as List<FilteredMeasurementsMeasure>,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$SorObjectImpl implements _SorObject {
  const _$SorObjectImpl(
      {this.sorId,
      this.id,
      final List<FilteredMeasurementsMeasure> filteredMeasurementsMeasure =
          const []})
      : _filteredMeasurementsMeasure = filteredMeasurementsMeasure;

  factory _$SorObjectImpl.fromJson(Map<String, dynamic> json) =>
      _$$SorObjectImplFromJson(json);

  @override
  final String? sorId;
  @override
  final String? id;
  final List<FilteredMeasurementsMeasure> _filteredMeasurementsMeasure;
  @override
  @JsonKey()
  List<FilteredMeasurementsMeasure> get filteredMeasurementsMeasure {
    if (_filteredMeasurementsMeasure is EqualUnmodifiableListView)
      return _filteredMeasurementsMeasure;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_filteredMeasurementsMeasure);
  }

  @override
  String toString() {
    return 'SorObject(sorId: $sorId, id: $id, filteredMeasurementsMeasure: $filteredMeasurementsMeasure)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SorObjectImpl &&
            (identical(other.sorId, sorId) || other.sorId == sorId) &&
            (identical(other.id, id) || other.id == id) &&
            const DeepCollectionEquality().equals(
                other._filteredMeasurementsMeasure,
                _filteredMeasurementsMeasure));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, sorId, id,
      const DeepCollectionEquality().hash(_filteredMeasurementsMeasure));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SorObjectImplCopyWith<_$SorObjectImpl> get copyWith =>
      __$$SorObjectImplCopyWithImpl<_$SorObjectImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$SorObjectImplToJson(
      this,
    );
  }
}

abstract class _SorObject implements SorObject {
  const factory _SorObject(
      {final String? sorId,
      final String? id,
      final List<FilteredMeasurementsMeasure>
          filteredMeasurementsMeasure}) = _$SorObjectImpl;

  factory _SorObject.fromJson(Map<String, dynamic> json) =
      _$SorObjectImpl.fromJson;

  @override
  String? get sorId;
  @override
  String? get id;
  @override
  List<FilteredMeasurementsMeasure> get filteredMeasurementsMeasure;
  @override
  @JsonKey(ignore: true)
  _$$SorObjectImplCopyWith<_$SorObjectImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
