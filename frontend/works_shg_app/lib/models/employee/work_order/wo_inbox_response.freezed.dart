// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'wo_inbox_response.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

WOInboxResponse _$WOInboxResponseFromJson(Map<String, dynamic> json) {
  return _WOInboxResponse.fromJson(json);
}

/// @nodoc
mixin _$WOInboxResponse {
  @JsonKey(name: 'totalCount')
  int? get totalCount => throw _privateConstructorUsedError;
  @JsonKey(name: 'nearingSlaCount')
  int? get nearingSlaCount => throw _privateConstructorUsedError;
  @JsonKey(name: 'statusMap')
  List<StatusMap>? get statusMap => throw _privateConstructorUsedError;
  @JsonKey(name: 'items')
  List<WOItemData>? get items => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WOInboxResponseCopyWith<WOInboxResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WOInboxResponseCopyWith<$Res> {
  factory $WOInboxResponseCopyWith(
          WOInboxResponse value, $Res Function(WOInboxResponse) then) =
      _$WOInboxResponseCopyWithImpl<$Res, WOInboxResponse>;
  @useResult
  $Res call(
      {@JsonKey(name: 'totalCount') int? totalCount,
      @JsonKey(name: 'nearingSlaCount') int? nearingSlaCount,
      @JsonKey(name: 'statusMap') List<StatusMap>? statusMap,
      @JsonKey(name: 'items') List<WOItemData>? items});
}

/// @nodoc
class _$WOInboxResponseCopyWithImpl<$Res, $Val extends WOInboxResponse>
    implements $WOInboxResponseCopyWith<$Res> {
  _$WOInboxResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? totalCount = freezed,
    Object? nearingSlaCount = freezed,
    Object? statusMap = freezed,
    Object? items = freezed,
  }) {
    return _then(_value.copyWith(
      totalCount: freezed == totalCount
          ? _value.totalCount
          : totalCount // ignore: cast_nullable_to_non_nullable
              as int?,
      nearingSlaCount: freezed == nearingSlaCount
          ? _value.nearingSlaCount
          : nearingSlaCount // ignore: cast_nullable_to_non_nullable
              as int?,
      statusMap: freezed == statusMap
          ? _value.statusMap
          : statusMap // ignore: cast_nullable_to_non_nullable
              as List<StatusMap>?,
      items: freezed == items
          ? _value.items
          : items // ignore: cast_nullable_to_non_nullable
              as List<WOItemData>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WOInboxResponseImplCopyWith<$Res>
    implements $WOInboxResponseCopyWith<$Res> {
  factory _$$WOInboxResponseImplCopyWith(_$WOInboxResponseImpl value,
          $Res Function(_$WOInboxResponseImpl) then) =
      __$$WOInboxResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'totalCount') int? totalCount,
      @JsonKey(name: 'nearingSlaCount') int? nearingSlaCount,
      @JsonKey(name: 'statusMap') List<StatusMap>? statusMap,
      @JsonKey(name: 'items') List<WOItemData>? items});
}

/// @nodoc
class __$$WOInboxResponseImplCopyWithImpl<$Res>
    extends _$WOInboxResponseCopyWithImpl<$Res, _$WOInboxResponseImpl>
    implements _$$WOInboxResponseImplCopyWith<$Res> {
  __$$WOInboxResponseImplCopyWithImpl(
      _$WOInboxResponseImpl _value, $Res Function(_$WOInboxResponseImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? totalCount = freezed,
    Object? nearingSlaCount = freezed,
    Object? statusMap = freezed,
    Object? items = freezed,
  }) {
    return _then(_$WOInboxResponseImpl(
      totalCount: freezed == totalCount
          ? _value.totalCount
          : totalCount // ignore: cast_nullable_to_non_nullable
              as int?,
      nearingSlaCount: freezed == nearingSlaCount
          ? _value.nearingSlaCount
          : nearingSlaCount // ignore: cast_nullable_to_non_nullable
              as int?,
      statusMap: freezed == statusMap
          ? _value._statusMap
          : statusMap // ignore: cast_nullable_to_non_nullable
              as List<StatusMap>?,
      items: freezed == items
          ? _value._items
          : items // ignore: cast_nullable_to_non_nullable
              as List<WOItemData>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WOInboxResponseImpl implements _WOInboxResponse {
  const _$WOInboxResponseImpl(
      {@JsonKey(name: 'totalCount') this.totalCount,
      @JsonKey(name: 'nearingSlaCount') this.nearingSlaCount,
      @JsonKey(name: 'statusMap') final List<StatusMap>? statusMap,
      @JsonKey(name: 'items') final List<WOItemData>? items})
      : _statusMap = statusMap,
        _items = items;

  factory _$WOInboxResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$WOInboxResponseImplFromJson(json);

  @override
  @JsonKey(name: 'totalCount')
  final int? totalCount;
  @override
  @JsonKey(name: 'nearingSlaCount')
  final int? nearingSlaCount;
  final List<StatusMap>? _statusMap;
  @override
  @JsonKey(name: 'statusMap')
  List<StatusMap>? get statusMap {
    final value = _statusMap;
    if (value == null) return null;
    if (_statusMap is EqualUnmodifiableListView) return _statusMap;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<WOItemData>? _items;
  @override
  @JsonKey(name: 'items')
  List<WOItemData>? get items {
    final value = _items;
    if (value == null) return null;
    if (_items is EqualUnmodifiableListView) return _items;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'WOInboxResponse(totalCount: $totalCount, nearingSlaCount: $nearingSlaCount, statusMap: $statusMap, items: $items)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WOInboxResponseImpl &&
            (identical(other.totalCount, totalCount) ||
                other.totalCount == totalCount) &&
            (identical(other.nearingSlaCount, nearingSlaCount) ||
                other.nearingSlaCount == nearingSlaCount) &&
            const DeepCollectionEquality()
                .equals(other._statusMap, _statusMap) &&
            const DeepCollectionEquality().equals(other._items, _items));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      totalCount,
      nearingSlaCount,
      const DeepCollectionEquality().hash(_statusMap),
      const DeepCollectionEquality().hash(_items));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WOInboxResponseImplCopyWith<_$WOInboxResponseImpl> get copyWith =>
      __$$WOInboxResponseImplCopyWithImpl<_$WOInboxResponseImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WOInboxResponseImplToJson(
      this,
    );
  }
}

abstract class _WOInboxResponse implements WOInboxResponse {
  const factory _WOInboxResponse(
          {@JsonKey(name: 'totalCount') final int? totalCount,
          @JsonKey(name: 'nearingSlaCount') final int? nearingSlaCount,
          @JsonKey(name: 'statusMap') final List<StatusMap>? statusMap,
          @JsonKey(name: 'items') final List<WOItemData>? items}) =
      _$WOInboxResponseImpl;

  factory _WOInboxResponse.fromJson(Map<String, dynamic> json) =
      _$WOInboxResponseImpl.fromJson;

  @override
  @JsonKey(name: 'totalCount')
  int? get totalCount;
  @override
  @JsonKey(name: 'nearingSlaCount')
  int? get nearingSlaCount;
  @override
  @JsonKey(name: 'statusMap')
  List<StatusMap>? get statusMap;
  @override
  @JsonKey(name: 'items')
  List<WOItemData>? get items;
  @override
  @JsonKey(ignore: true)
  _$$WOInboxResponseImplCopyWith<_$WOInboxResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WOItemData _$WOItemDataFromJson(Map<String, dynamic> json) {
  return _WOItemData.fromJson(json);
}

/// @nodoc
mixin _$WOItemData {
  @JsonKey(name: 'ProcessInstance')
  ProcessInstance? get processInstance => throw _privateConstructorUsedError;
  @JsonKey(name: 'businessObject')
  WOBusinessObject? get woBusinessObject => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WOItemDataCopyWith<WOItemData> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WOItemDataCopyWith<$Res> {
  factory $WOItemDataCopyWith(
          WOItemData value, $Res Function(WOItemData) then) =
      _$WOItemDataCopyWithImpl<$Res, WOItemData>;
  @useResult
  $Res call(
      {@JsonKey(name: 'ProcessInstance') ProcessInstance? processInstance,
      @JsonKey(name: 'businessObject') WOBusinessObject? woBusinessObject});

  $ProcessInstanceCopyWith<$Res>? get processInstance;
  $WOBusinessObjectCopyWith<$Res>? get woBusinessObject;
}

/// @nodoc
class _$WOItemDataCopyWithImpl<$Res, $Val extends WOItemData>
    implements $WOItemDataCopyWith<$Res> {
  _$WOItemDataCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? processInstance = freezed,
    Object? woBusinessObject = freezed,
  }) {
    return _then(_value.copyWith(
      processInstance: freezed == processInstance
          ? _value.processInstance
          : processInstance // ignore: cast_nullable_to_non_nullable
              as ProcessInstance?,
      woBusinessObject: freezed == woBusinessObject
          ? _value.woBusinessObject
          : woBusinessObject // ignore: cast_nullable_to_non_nullable
              as WOBusinessObject?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $ProcessInstanceCopyWith<$Res>? get processInstance {
    if (_value.processInstance == null) {
      return null;
    }

    return $ProcessInstanceCopyWith<$Res>(_value.processInstance!, (value) {
      return _then(_value.copyWith(processInstance: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $WOBusinessObjectCopyWith<$Res>? get woBusinessObject {
    if (_value.woBusinessObject == null) {
      return null;
    }

    return $WOBusinessObjectCopyWith<$Res>(_value.woBusinessObject!, (value) {
      return _then(_value.copyWith(woBusinessObject: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$WOItemDataImplCopyWith<$Res>
    implements $WOItemDataCopyWith<$Res> {
  factory _$$WOItemDataImplCopyWith(
          _$WOItemDataImpl value, $Res Function(_$WOItemDataImpl) then) =
      __$$WOItemDataImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'ProcessInstance') ProcessInstance? processInstance,
      @JsonKey(name: 'businessObject') WOBusinessObject? woBusinessObject});

  @override
  $ProcessInstanceCopyWith<$Res>? get processInstance;
  @override
  $WOBusinessObjectCopyWith<$Res>? get woBusinessObject;
}

/// @nodoc
class __$$WOItemDataImplCopyWithImpl<$Res>
    extends _$WOItemDataCopyWithImpl<$Res, _$WOItemDataImpl>
    implements _$$WOItemDataImplCopyWith<$Res> {
  __$$WOItemDataImplCopyWithImpl(
      _$WOItemDataImpl _value, $Res Function(_$WOItemDataImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? processInstance = freezed,
    Object? woBusinessObject = freezed,
  }) {
    return _then(_$WOItemDataImpl(
      processInstance: freezed == processInstance
          ? _value.processInstance
          : processInstance // ignore: cast_nullable_to_non_nullable
              as ProcessInstance?,
      woBusinessObject: freezed == woBusinessObject
          ? _value.woBusinessObject
          : woBusinessObject // ignore: cast_nullable_to_non_nullable
              as WOBusinessObject?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WOItemDataImpl implements _WOItemData {
  const _$WOItemDataImpl(
      {@JsonKey(name: 'ProcessInstance') this.processInstance,
      @JsonKey(name: 'businessObject') this.woBusinessObject});

  factory _$WOItemDataImpl.fromJson(Map<String, dynamic> json) =>
      _$$WOItemDataImplFromJson(json);

  @override
  @JsonKey(name: 'ProcessInstance')
  final ProcessInstance? processInstance;
  @override
  @JsonKey(name: 'businessObject')
  final WOBusinessObject? woBusinessObject;

  @override
  String toString() {
    return 'WOItemData(processInstance: $processInstance, woBusinessObject: $woBusinessObject)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WOItemDataImpl &&
            (identical(other.processInstance, processInstance) ||
                other.processInstance == processInstance) &&
            (identical(other.woBusinessObject, woBusinessObject) ||
                other.woBusinessObject == woBusinessObject));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, processInstance, woBusinessObject);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WOItemDataImplCopyWith<_$WOItemDataImpl> get copyWith =>
      __$$WOItemDataImplCopyWithImpl<_$WOItemDataImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WOItemDataImplToJson(
      this,
    );
  }
}

abstract class _WOItemData implements WOItemData {
  const factory _WOItemData(
      {@JsonKey(name: 'ProcessInstance')
          final ProcessInstance? processInstance,
      @JsonKey(name: 'businessObject')
          final WOBusinessObject? woBusinessObject}) = _$WOItemDataImpl;

  factory _WOItemData.fromJson(Map<String, dynamic> json) =
      _$WOItemDataImpl.fromJson;

  @override
  @JsonKey(name: 'ProcessInstance')
  ProcessInstance? get processInstance;
  @override
  @JsonKey(name: 'businessObject')
  WOBusinessObject? get woBusinessObject;
  @override
  @JsonKey(ignore: true)
  _$$WOItemDataImplCopyWith<_$WOItemDataImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WOBusinessObject _$WOBusinessObjectFromJson(Map<String, dynamic> json) {
  return _WOBusinessObject.fromJson(json);
}

/// @nodoc
mixin _$WOBusinessObject {
  @JsonKey(name: 'totalContractedAmount')
  double? get totalContractedAmount => throw _privateConstructorUsedError;
  @JsonKey(name: 'businessService')
  String? get businessService => throw _privateConstructorUsedError;
  @JsonKey(name: 'contractNumber')
  String? get contractNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'serviceSla')
  int? get serviceSla => throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  WOAdditionalDetails? get woAdditionalDetails =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WOBusinessObjectCopyWith<WOBusinessObject> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WOBusinessObjectCopyWith<$Res> {
  factory $WOBusinessObjectCopyWith(
          WOBusinessObject value, $Res Function(WOBusinessObject) then) =
      _$WOBusinessObjectCopyWithImpl<$Res, WOBusinessObject>;
  @useResult
  $Res call(
      {@JsonKey(name: 'totalContractedAmount')
          double? totalContractedAmount,
      @JsonKey(name: 'businessService')
          String? businessService,
      @JsonKey(name: 'contractNumber')
          String? contractNumber,
      @JsonKey(name: 'serviceSla')
          int? serviceSla,
      @JsonKey(name: 'additionalDetails')
          WOAdditionalDetails? woAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          AuditDetails? auditDetails});

  $WOAdditionalDetailsCopyWith<$Res>? get woAdditionalDetails;
  $AuditDetailsCopyWith<$Res>? get auditDetails;
}

/// @nodoc
class _$WOBusinessObjectCopyWithImpl<$Res, $Val extends WOBusinessObject>
    implements $WOBusinessObjectCopyWith<$Res> {
  _$WOBusinessObjectCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? totalContractedAmount = freezed,
    Object? businessService = freezed,
    Object? contractNumber = freezed,
    Object? serviceSla = freezed,
    Object? woAdditionalDetails = freezed,
    Object? auditDetails = freezed,
  }) {
    return _then(_value.copyWith(
      totalContractedAmount: freezed == totalContractedAmount
          ? _value.totalContractedAmount
          : totalContractedAmount // ignore: cast_nullable_to_non_nullable
              as double?,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      contractNumber: freezed == contractNumber
          ? _value.contractNumber
          : contractNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      serviceSla: freezed == serviceSla
          ? _value.serviceSla
          : serviceSla // ignore: cast_nullable_to_non_nullable
              as int?,
      woAdditionalDetails: freezed == woAdditionalDetails
          ? _value.woAdditionalDetails
          : woAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as WOAdditionalDetails?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $WOAdditionalDetailsCopyWith<$Res>? get woAdditionalDetails {
    if (_value.woAdditionalDetails == null) {
      return null;
    }

    return $WOAdditionalDetailsCopyWith<$Res>(_value.woAdditionalDetails!,
        (value) {
      return _then(_value.copyWith(woAdditionalDetails: value) as $Val);
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
abstract class _$$WOBusinessObjectImplCopyWith<$Res>
    implements $WOBusinessObjectCopyWith<$Res> {
  factory _$$WOBusinessObjectImplCopyWith(_$WOBusinessObjectImpl value,
          $Res Function(_$WOBusinessObjectImpl) then) =
      __$$WOBusinessObjectImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'totalContractedAmount')
          double? totalContractedAmount,
      @JsonKey(name: 'businessService')
          String? businessService,
      @JsonKey(name: 'contractNumber')
          String? contractNumber,
      @JsonKey(name: 'serviceSla')
          int? serviceSla,
      @JsonKey(name: 'additionalDetails')
          WOAdditionalDetails? woAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          AuditDetails? auditDetails});

  @override
  $WOAdditionalDetailsCopyWith<$Res>? get woAdditionalDetails;
  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
}

/// @nodoc
class __$$WOBusinessObjectImplCopyWithImpl<$Res>
    extends _$WOBusinessObjectCopyWithImpl<$Res, _$WOBusinessObjectImpl>
    implements _$$WOBusinessObjectImplCopyWith<$Res> {
  __$$WOBusinessObjectImplCopyWithImpl(_$WOBusinessObjectImpl _value,
      $Res Function(_$WOBusinessObjectImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? totalContractedAmount = freezed,
    Object? businessService = freezed,
    Object? contractNumber = freezed,
    Object? serviceSla = freezed,
    Object? woAdditionalDetails = freezed,
    Object? auditDetails = freezed,
  }) {
    return _then(_$WOBusinessObjectImpl(
      totalContractedAmount: freezed == totalContractedAmount
          ? _value.totalContractedAmount
          : totalContractedAmount // ignore: cast_nullable_to_non_nullable
              as double?,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      contractNumber: freezed == contractNumber
          ? _value.contractNumber
          : contractNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      serviceSla: freezed == serviceSla
          ? _value.serviceSla
          : serviceSla // ignore: cast_nullable_to_non_nullable
              as int?,
      woAdditionalDetails: freezed == woAdditionalDetails
          ? _value.woAdditionalDetails
          : woAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as WOAdditionalDetails?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WOBusinessObjectImpl implements _WOBusinessObject {
  const _$WOBusinessObjectImpl(
      {@JsonKey(name: 'totalContractedAmount') this.totalContractedAmount,
      @JsonKey(name: 'businessService') this.businessService,
      @JsonKey(name: 'contractNumber') this.contractNumber,
      @JsonKey(name: 'serviceSla') this.serviceSla,
      @JsonKey(name: 'additionalDetails') this.woAdditionalDetails,
      @JsonKey(name: 'auditDetails') this.auditDetails});

  factory _$WOBusinessObjectImpl.fromJson(Map<String, dynamic> json) =>
      _$$WOBusinessObjectImplFromJson(json);

  @override
  @JsonKey(name: 'totalContractedAmount')
  final double? totalContractedAmount;
  @override
  @JsonKey(name: 'businessService')
  final String? businessService;
  @override
  @JsonKey(name: 'contractNumber')
  final String? contractNumber;
  @override
  @JsonKey(name: 'serviceSla')
  final int? serviceSla;
  @override
  @JsonKey(name: 'additionalDetails')
  final WOAdditionalDetails? woAdditionalDetails;
  @override
  @JsonKey(name: 'auditDetails')
  final AuditDetails? auditDetails;

  @override
  String toString() {
    return 'WOBusinessObject(totalContractedAmount: $totalContractedAmount, businessService: $businessService, contractNumber: $contractNumber, serviceSla: $serviceSla, woAdditionalDetails: $woAdditionalDetails, auditDetails: $auditDetails)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WOBusinessObjectImpl &&
            (identical(other.totalContractedAmount, totalContractedAmount) ||
                other.totalContractedAmount == totalContractedAmount) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService) &&
            (identical(other.contractNumber, contractNumber) ||
                other.contractNumber == contractNumber) &&
            (identical(other.serviceSla, serviceSla) ||
                other.serviceSla == serviceSla) &&
            (identical(other.woAdditionalDetails, woAdditionalDetails) ||
                other.woAdditionalDetails == woAdditionalDetails) &&
            (identical(other.auditDetails, auditDetails) ||
                other.auditDetails == auditDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      totalContractedAmount,
      businessService,
      contractNumber,
      serviceSla,
      woAdditionalDetails,
      auditDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WOBusinessObjectImplCopyWith<_$WOBusinessObjectImpl> get copyWith =>
      __$$WOBusinessObjectImplCopyWithImpl<_$WOBusinessObjectImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WOBusinessObjectImplToJson(
      this,
    );
  }
}

abstract class _WOBusinessObject implements WOBusinessObject {
  const factory _WOBusinessObject(
      {@JsonKey(name: 'totalContractedAmount')
          final double? totalContractedAmount,
      @JsonKey(name: 'businessService')
          final String? businessService,
      @JsonKey(name: 'contractNumber')
          final String? contractNumber,
      @JsonKey(name: 'serviceSla')
          final int? serviceSla,
      @JsonKey(name: 'additionalDetails')
          final WOAdditionalDetails? woAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          final AuditDetails? auditDetails}) = _$WOBusinessObjectImpl;

  factory _WOBusinessObject.fromJson(Map<String, dynamic> json) =
      _$WOBusinessObjectImpl.fromJson;

  @override
  @JsonKey(name: 'totalContractedAmount')
  double? get totalContractedAmount;
  @override
  @JsonKey(name: 'businessService')
  String? get businessService;
  @override
  @JsonKey(name: 'contractNumber')
  String? get contractNumber;
  @override
  @JsonKey(name: 'serviceSla')
  int? get serviceSla;
  @override
  @JsonKey(name: 'additionalDetails')
  WOAdditionalDetails? get woAdditionalDetails;
  @override
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails;
  @override
  @JsonKey(ignore: true)
  _$$WOBusinessObjectImplCopyWith<_$WOBusinessObjectImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WOAdditionalDetails _$WOAdditionalDetailsFromJson(Map<String, dynamic> json) {
  return _WOAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$WOAdditionalDetails {
  @JsonKey(name: 'orgName')
  String? get orgName => throw _privateConstructorUsedError;
  @JsonKey(name: 'projectId')
  String? get projectId => throw _privateConstructorUsedError;
  @JsonKey(name: 'projectName')
  String? get projectName => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WOAdditionalDetailsCopyWith<WOAdditionalDetails> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WOAdditionalDetailsCopyWith<$Res> {
  factory $WOAdditionalDetailsCopyWith(
          WOAdditionalDetails value, $Res Function(WOAdditionalDetails) then) =
      _$WOAdditionalDetailsCopyWithImpl<$Res, WOAdditionalDetails>;
  @useResult
  $Res call(
      {@JsonKey(name: 'orgName') String? orgName,
      @JsonKey(name: 'projectId') String? projectId,
      @JsonKey(name: 'projectName') String? projectName});
}

/// @nodoc
class _$WOAdditionalDetailsCopyWithImpl<$Res, $Val extends WOAdditionalDetails>
    implements $WOAdditionalDetailsCopyWith<$Res> {
  _$WOAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? orgName = freezed,
    Object? projectId = freezed,
    Object? projectName = freezed,
  }) {
    return _then(_value.copyWith(
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WOAdditionalDetailsImplCopyWith<$Res>
    implements $WOAdditionalDetailsCopyWith<$Res> {
  factory _$$WOAdditionalDetailsImplCopyWith(_$WOAdditionalDetailsImpl value,
          $Res Function(_$WOAdditionalDetailsImpl) then) =
      __$$WOAdditionalDetailsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'orgName') String? orgName,
      @JsonKey(name: 'projectId') String? projectId,
      @JsonKey(name: 'projectName') String? projectName});
}

/// @nodoc
class __$$WOAdditionalDetailsImplCopyWithImpl<$Res>
    extends _$WOAdditionalDetailsCopyWithImpl<$Res, _$WOAdditionalDetailsImpl>
    implements _$$WOAdditionalDetailsImplCopyWith<$Res> {
  __$$WOAdditionalDetailsImplCopyWithImpl(_$WOAdditionalDetailsImpl _value,
      $Res Function(_$WOAdditionalDetailsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? orgName = freezed,
    Object? projectId = freezed,
    Object? projectName = freezed,
  }) {
    return _then(_$WOAdditionalDetailsImpl(
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WOAdditionalDetailsImpl implements _WOAdditionalDetails {
  const _$WOAdditionalDetailsImpl(
      {@JsonKey(name: 'orgName') this.orgName,
      @JsonKey(name: 'projectId') this.projectId,
      @JsonKey(name: 'projectName') this.projectName});

  factory _$WOAdditionalDetailsImpl.fromJson(Map<String, dynamic> json) =>
      _$$WOAdditionalDetailsImplFromJson(json);

  @override
  @JsonKey(name: 'orgName')
  final String? orgName;
  @override
  @JsonKey(name: 'projectId')
  final String? projectId;
  @override
  @JsonKey(name: 'projectName')
  final String? projectName;

  @override
  String toString() {
    return 'WOAdditionalDetails(orgName: $orgName, projectId: $projectId, projectName: $projectName)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WOAdditionalDetailsImpl &&
            (identical(other.orgName, orgName) || other.orgName == orgName) &&
            (identical(other.projectId, projectId) ||
                other.projectId == projectId) &&
            (identical(other.projectName, projectName) ||
                other.projectName == projectName));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, orgName, projectId, projectName);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WOAdditionalDetailsImplCopyWith<_$WOAdditionalDetailsImpl> get copyWith =>
      __$$WOAdditionalDetailsImplCopyWithImpl<_$WOAdditionalDetailsImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WOAdditionalDetailsImplToJson(
      this,
    );
  }
}

abstract class _WOAdditionalDetails implements WOAdditionalDetails {
  const factory _WOAdditionalDetails(
          {@JsonKey(name: 'orgName') final String? orgName,
          @JsonKey(name: 'projectId') final String? projectId,
          @JsonKey(name: 'projectName') final String? projectName}) =
      _$WOAdditionalDetailsImpl;

  factory _WOAdditionalDetails.fromJson(Map<String, dynamic> json) =
      _$WOAdditionalDetailsImpl.fromJson;

  @override
  @JsonKey(name: 'orgName')
  String? get orgName;
  @override
  @JsonKey(name: 'projectId')
  String? get projectId;
  @override
  @JsonKey(name: 'projectName')
  String? get projectName;
  @override
  @JsonKey(ignore: true)
  _$$WOAdditionalDetailsImplCopyWith<_$WOAdditionalDetailsImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
