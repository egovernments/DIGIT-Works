// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'measurement_book.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$MeasurementInboxBlocEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        create,
    required TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)
        search,
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        searchRepeat,
    required TResult Function(int sortCode) sort,
    required TResult Function() clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        create,
    TResult? Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        searchRepeat,
    TResult? Function(int sortCode)? sort,
    TResult? Function()? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        create,
    TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        searchRepeat,
    TResult Function(int sortCode)? sort,
    TResult Function()? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementBookInboxBlocEvent value) create,
    required TResult Function(MeasurementBookInboxSearchBlocEvent value) search,
    required TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)
        searchRepeat,
    required TResult Function(MeasurementBookInboxSortBlocEvent value) sort,
    required TResult Function(MeasurementBookInboxBlocClearEvent value) clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementBookInboxBlocEvent value)? create,
    TResult? Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult? Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult? Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult? Function(MeasurementBookInboxBlocClearEvent value)? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementBookInboxBlocEvent value)? create,
    TResult Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult Function(MeasurementBookInboxBlocClearEvent value)? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MeasurementInboxBlocEventCopyWith<$Res> {
  factory $MeasurementInboxBlocEventCopyWith(MeasurementInboxBlocEvent value,
          $Res Function(MeasurementInboxBlocEvent) then) =
      _$MeasurementInboxBlocEventCopyWithImpl<$Res, MeasurementInboxBlocEvent>;
}

/// @nodoc
class _$MeasurementInboxBlocEventCopyWithImpl<$Res,
        $Val extends MeasurementInboxBlocEvent>
    implements $MeasurementInboxBlocEventCopyWith<$Res> {
  _$MeasurementInboxBlocEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$MeasurementBookInboxBlocEventImplCopyWith<$Res> {
  factory _$$MeasurementBookInboxBlocEventImplCopyWith(
          _$MeasurementBookInboxBlocEventImpl value,
          $Res Function(_$MeasurementBookInboxBlocEventImpl) then) =
      __$$MeasurementBookInboxBlocEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String tenantId,
      String businessService,
      String moduleName,
      int limit,
      int offset});
}

/// @nodoc
class __$$MeasurementBookInboxBlocEventImplCopyWithImpl<$Res>
    extends _$MeasurementInboxBlocEventCopyWithImpl<$Res,
        _$MeasurementBookInboxBlocEventImpl>
    implements _$$MeasurementBookInboxBlocEventImplCopyWith<$Res> {
  __$$MeasurementBookInboxBlocEventImplCopyWithImpl(
      _$MeasurementBookInboxBlocEventImpl _value,
      $Res Function(_$MeasurementBookInboxBlocEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessService = null,
    Object? moduleName = null,
    Object? limit = null,
    Object? offset = null,
  }) {
    return _then(_$MeasurementBookInboxBlocEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      businessService: null == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String,
      moduleName: null == moduleName
          ? _value.moduleName
          : moduleName // ignore: cast_nullable_to_non_nullable
              as String,
      limit: null == limit
          ? _value.limit
          : limit // ignore: cast_nullable_to_non_nullable
              as int,
      offset: null == offset
          ? _value.offset
          : offset // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$MeasurementBookInboxBlocEventImpl
    implements MeasurementBookInboxBlocEvent {
  const _$MeasurementBookInboxBlocEventImpl(
      {required this.tenantId,
      required this.businessService,
      required this.moduleName,
      required this.limit,
      required this.offset});

  @override
  final String tenantId;
  @override
  final String businessService;
  @override
  final String moduleName;
  @override
  final int limit;
  @override
  final int offset;

  @override
  String toString() {
    return 'MeasurementInboxBlocEvent.create(tenantId: $tenantId, businessService: $businessService, moduleName: $moduleName, limit: $limit, offset: $offset)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementBookInboxBlocEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService) &&
            (identical(other.moduleName, moduleName) ||
                other.moduleName == moduleName) &&
            (identical(other.limit, limit) || other.limit == limit) &&
            (identical(other.offset, offset) || other.offset == offset));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenantId, businessService, moduleName, limit, offset);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementBookInboxBlocEventImplCopyWith<
          _$MeasurementBookInboxBlocEventImpl>
      get copyWith => __$$MeasurementBookInboxBlocEventImplCopyWithImpl<
          _$MeasurementBookInboxBlocEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        create,
    required TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)
        search,
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        searchRepeat,
    required TResult Function(int sortCode) sort,
    required TResult Function() clear,
  }) {
    return create(tenantId, businessService, moduleName, limit, offset);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        create,
    TResult? Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        searchRepeat,
    TResult? Function(int sortCode)? sort,
    TResult? Function()? clear,
  }) {
    return create?.call(tenantId, businessService, moduleName, limit, offset);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        create,
    TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        searchRepeat,
    TResult Function(int sortCode)? sort,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(tenantId, businessService, moduleName, limit, offset);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementBookInboxBlocEvent value) create,
    required TResult Function(MeasurementBookInboxSearchBlocEvent value) search,
    required TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)
        searchRepeat,
    required TResult Function(MeasurementBookInboxSortBlocEvent value) sort,
    required TResult Function(MeasurementBookInboxBlocClearEvent value) clear,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementBookInboxBlocEvent value)? create,
    TResult? Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult? Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult? Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult? Function(MeasurementBookInboxBlocClearEvent value)? clear,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementBookInboxBlocEvent value)? create,
    TResult Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult Function(MeasurementBookInboxBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class MeasurementBookInboxBlocEvent
    implements MeasurementInboxBlocEvent {
  const factory MeasurementBookInboxBlocEvent(
      {required final String tenantId,
      required final String businessService,
      required final String moduleName,
      required final int limit,
      required final int offset}) = _$MeasurementBookInboxBlocEventImpl;

  String get tenantId;
  String get businessService;
  String get moduleName;
  int get limit;
  int get offset;
  @JsonKey(ignore: true)
  _$$MeasurementBookInboxBlocEventImplCopyWith<
          _$MeasurementBookInboxBlocEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$MeasurementBookInboxSearchBlocEventImplCopyWith<$Res> {
  factory _$$MeasurementBookInboxSearchBlocEventImplCopyWith(
          _$MeasurementBookInboxSearchBlocEventImpl value,
          $Res Function(_$MeasurementBookInboxSearchBlocEventImpl) then) =
      __$$MeasurementBookInboxSearchBlocEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {List<String>? ward,
      List<String>? status,
      String? projectId,
      String? mbNumber,
      String? projectName,
      int limit,
      int offset,
      Map<String, Map<String, dynamic>> data});
}

/// @nodoc
class __$$MeasurementBookInboxSearchBlocEventImplCopyWithImpl<$Res>
    extends _$MeasurementInboxBlocEventCopyWithImpl<$Res,
        _$MeasurementBookInboxSearchBlocEventImpl>
    implements _$$MeasurementBookInboxSearchBlocEventImplCopyWith<$Res> {
  __$$MeasurementBookInboxSearchBlocEventImplCopyWithImpl(
      _$MeasurementBookInboxSearchBlocEventImpl _value,
      $Res Function(_$MeasurementBookInboxSearchBlocEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? ward = freezed,
    Object? status = freezed,
    Object? projectId = freezed,
    Object? mbNumber = freezed,
    Object? projectName = freezed,
    Object? limit = null,
    Object? offset = null,
    Object? data = null,
  }) {
    return _then(_$MeasurementBookInboxSearchBlocEventImpl(
      ward: freezed == ward
          ? _value._ward
          : ward // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      status: freezed == status
          ? _value._status
          : status // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      mbNumber: freezed == mbNumber
          ? _value.mbNumber
          : mbNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      limit: null == limit
          ? _value.limit
          : limit // ignore: cast_nullable_to_non_nullable
              as int,
      offset: null == offset
          ? _value.offset
          : offset // ignore: cast_nullable_to_non_nullable
              as int,
      data: null == data
          ? _value._data
          : data // ignore: cast_nullable_to_non_nullable
              as Map<String, Map<String, dynamic>>,
    ));
  }
}

/// @nodoc

class _$MeasurementBookInboxSearchBlocEventImpl
    implements MeasurementBookInboxSearchBlocEvent {
  const _$MeasurementBookInboxSearchBlocEventImpl(
      {final List<String>? ward,
      final List<String>? status,
      this.projectId,
      this.mbNumber,
      this.projectName,
      required this.limit,
      required this.offset,
      required final Map<String, Map<String, dynamic>> data})
      : _ward = ward,
        _status = status,
        _data = data;

  final List<String>? _ward;
  @override
  List<String>? get ward {
    final value = _ward;
    if (value == null) return null;
    if (_ward is EqualUnmodifiableListView) return _ward;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<String>? _status;
  @override
  List<String>? get status {
    final value = _status;
    if (value == null) return null;
    if (_status is EqualUnmodifiableListView) return _status;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final String? projectId;
  @override
  final String? mbNumber;
  @override
  final String? projectName;
  @override
  final int limit;
  @override
  final int offset;
  final Map<String, Map<String, dynamic>> _data;
  @override
  Map<String, Map<String, dynamic>> get data {
    if (_data is EqualUnmodifiableMapView) return _data;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableMapView(_data);
  }

  @override
  String toString() {
    return 'MeasurementInboxBlocEvent.search(ward: $ward, status: $status, projectId: $projectId, mbNumber: $mbNumber, projectName: $projectName, limit: $limit, offset: $offset, data: $data)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementBookInboxSearchBlocEventImpl &&
            const DeepCollectionEquality().equals(other._ward, _ward) &&
            const DeepCollectionEquality().equals(other._status, _status) &&
            (identical(other.projectId, projectId) ||
                other.projectId == projectId) &&
            (identical(other.mbNumber, mbNumber) ||
                other.mbNumber == mbNumber) &&
            (identical(other.projectName, projectName) ||
                other.projectName == projectName) &&
            (identical(other.limit, limit) || other.limit == limit) &&
            (identical(other.offset, offset) || other.offset == offset) &&
            const DeepCollectionEquality().equals(other._data, _data));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_ward),
      const DeepCollectionEquality().hash(_status),
      projectId,
      mbNumber,
      projectName,
      limit,
      offset,
      const DeepCollectionEquality().hash(_data));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementBookInboxSearchBlocEventImplCopyWith<
          _$MeasurementBookInboxSearchBlocEventImpl>
      get copyWith => __$$MeasurementBookInboxSearchBlocEventImplCopyWithImpl<
          _$MeasurementBookInboxSearchBlocEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        create,
    required TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)
        search,
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        searchRepeat,
    required TResult Function(int sortCode) sort,
    required TResult Function() clear,
  }) {
    return search(
        ward, status, projectId, mbNumber, projectName, limit, offset, data);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        create,
    TResult? Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        searchRepeat,
    TResult? Function(int sortCode)? sort,
    TResult? Function()? clear,
  }) {
    return search?.call(
        ward, status, projectId, mbNumber, projectName, limit, offset, data);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        create,
    TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        searchRepeat,
    TResult Function(int sortCode)? sort,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(
          ward, status, projectId, mbNumber, projectName, limit, offset, data);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementBookInboxBlocEvent value) create,
    required TResult Function(MeasurementBookInboxSearchBlocEvent value) search,
    required TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)
        searchRepeat,
    required TResult Function(MeasurementBookInboxSortBlocEvent value) sort,
    required TResult Function(MeasurementBookInboxBlocClearEvent value) clear,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementBookInboxBlocEvent value)? create,
    TResult? Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult? Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult? Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult? Function(MeasurementBookInboxBlocClearEvent value)? clear,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementBookInboxBlocEvent value)? create,
    TResult Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult Function(MeasurementBookInboxBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class MeasurementBookInboxSearchBlocEvent
    implements MeasurementInboxBlocEvent {
  const factory MeasurementBookInboxSearchBlocEvent(
          {final List<String>? ward,
          final List<String>? status,
          final String? projectId,
          final String? mbNumber,
          final String? projectName,
          required final int limit,
          required final int offset,
          required final Map<String, Map<String, dynamic>> data}) =
      _$MeasurementBookInboxSearchBlocEventImpl;

  List<String>? get ward;
  List<String>? get status;
  String? get projectId;
  String? get mbNumber;
  String? get projectName;
  int get limit;
  int get offset;
  Map<String, Map<String, dynamic>> get data;
  @JsonKey(ignore: true)
  _$$MeasurementBookInboxSearchBlocEventImplCopyWith<
          _$MeasurementBookInboxSearchBlocEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWith<$Res> {
  factory _$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWith(
          _$MeasurementBookInboxSearchRepeatBlocEventImpl value,
          $Res Function(_$MeasurementBookInboxSearchRepeatBlocEventImpl) then) =
      __$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String tenantId,
      String businessService,
      String moduleName,
      int limit,
      int offset});
}

/// @nodoc
class __$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWithImpl<$Res>
    extends _$MeasurementInboxBlocEventCopyWithImpl<$Res,
        _$MeasurementBookInboxSearchRepeatBlocEventImpl>
    implements _$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWith<$Res> {
  __$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWithImpl(
      _$MeasurementBookInboxSearchRepeatBlocEventImpl _value,
      $Res Function(_$MeasurementBookInboxSearchRepeatBlocEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessService = null,
    Object? moduleName = null,
    Object? limit = null,
    Object? offset = null,
  }) {
    return _then(_$MeasurementBookInboxSearchRepeatBlocEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      businessService: null == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String,
      moduleName: null == moduleName
          ? _value.moduleName
          : moduleName // ignore: cast_nullable_to_non_nullable
              as String,
      limit: null == limit
          ? _value.limit
          : limit // ignore: cast_nullable_to_non_nullable
              as int,
      offset: null == offset
          ? _value.offset
          : offset // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$MeasurementBookInboxSearchRepeatBlocEventImpl
    implements MeasurementBookInboxSearchRepeatBlocEvent {
  const _$MeasurementBookInboxSearchRepeatBlocEventImpl(
      {required this.tenantId,
      required this.businessService,
      required this.moduleName,
      required this.limit,
      required this.offset});

  @override
  final String tenantId;
  @override
  final String businessService;
  @override
  final String moduleName;
  @override
  final int limit;
  @override
  final int offset;

  @override
  String toString() {
    return 'MeasurementInboxBlocEvent.searchRepeat(tenantId: $tenantId, businessService: $businessService, moduleName: $moduleName, limit: $limit, offset: $offset)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementBookInboxSearchRepeatBlocEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService) &&
            (identical(other.moduleName, moduleName) ||
                other.moduleName == moduleName) &&
            (identical(other.limit, limit) || other.limit == limit) &&
            (identical(other.offset, offset) || other.offset == offset));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenantId, businessService, moduleName, limit, offset);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWith<
          _$MeasurementBookInboxSearchRepeatBlocEventImpl>
      get copyWith =>
          __$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWithImpl<
                  _$MeasurementBookInboxSearchRepeatBlocEventImpl>(
              this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        create,
    required TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)
        search,
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        searchRepeat,
    required TResult Function(int sortCode) sort,
    required TResult Function() clear,
  }) {
    return searchRepeat(tenantId, businessService, moduleName, limit, offset);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        create,
    TResult? Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        searchRepeat,
    TResult? Function(int sortCode)? sort,
    TResult? Function()? clear,
  }) {
    return searchRepeat?.call(
        tenantId, businessService, moduleName, limit, offset);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        create,
    TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        searchRepeat,
    TResult Function(int sortCode)? sort,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (searchRepeat != null) {
      return searchRepeat(tenantId, businessService, moduleName, limit, offset);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementBookInboxBlocEvent value) create,
    required TResult Function(MeasurementBookInboxSearchBlocEvent value) search,
    required TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)
        searchRepeat,
    required TResult Function(MeasurementBookInboxSortBlocEvent value) sort,
    required TResult Function(MeasurementBookInboxBlocClearEvent value) clear,
  }) {
    return searchRepeat(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementBookInboxBlocEvent value)? create,
    TResult? Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult? Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult? Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult? Function(MeasurementBookInboxBlocClearEvent value)? clear,
  }) {
    return searchRepeat?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementBookInboxBlocEvent value)? create,
    TResult Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult Function(MeasurementBookInboxBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (searchRepeat != null) {
      return searchRepeat(this);
    }
    return orElse();
  }
}

abstract class MeasurementBookInboxSearchRepeatBlocEvent
    implements MeasurementInboxBlocEvent {
  const factory MeasurementBookInboxSearchRepeatBlocEvent(
          {required final String tenantId,
          required final String businessService,
          required final String moduleName,
          required final int limit,
          required final int offset}) =
      _$MeasurementBookInboxSearchRepeatBlocEventImpl;

  String get tenantId;
  String get businessService;
  String get moduleName;
  int get limit;
  int get offset;
  @JsonKey(ignore: true)
  _$$MeasurementBookInboxSearchRepeatBlocEventImplCopyWith<
          _$MeasurementBookInboxSearchRepeatBlocEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$MeasurementBookInboxSortBlocEventImplCopyWith<$Res> {
  factory _$$MeasurementBookInboxSortBlocEventImplCopyWith(
          _$MeasurementBookInboxSortBlocEventImpl value,
          $Res Function(_$MeasurementBookInboxSortBlocEventImpl) then) =
      __$$MeasurementBookInboxSortBlocEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({int sortCode});
}

/// @nodoc
class __$$MeasurementBookInboxSortBlocEventImplCopyWithImpl<$Res>
    extends _$MeasurementInboxBlocEventCopyWithImpl<$Res,
        _$MeasurementBookInboxSortBlocEventImpl>
    implements _$$MeasurementBookInboxSortBlocEventImplCopyWith<$Res> {
  __$$MeasurementBookInboxSortBlocEventImplCopyWithImpl(
      _$MeasurementBookInboxSortBlocEventImpl _value,
      $Res Function(_$MeasurementBookInboxSortBlocEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? sortCode = null,
  }) {
    return _then(_$MeasurementBookInboxSortBlocEventImpl(
      sortCode: null == sortCode
          ? _value.sortCode
          : sortCode // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$MeasurementBookInboxSortBlocEventImpl
    implements MeasurementBookInboxSortBlocEvent {
  const _$MeasurementBookInboxSortBlocEventImpl({required this.sortCode});

  @override
  final int sortCode;

  @override
  String toString() {
    return 'MeasurementInboxBlocEvent.sort(sortCode: $sortCode)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementBookInboxSortBlocEventImpl &&
            (identical(other.sortCode, sortCode) ||
                other.sortCode == sortCode));
  }

  @override
  int get hashCode => Object.hash(runtimeType, sortCode);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementBookInboxSortBlocEventImplCopyWith<
          _$MeasurementBookInboxSortBlocEventImpl>
      get copyWith => __$$MeasurementBookInboxSortBlocEventImplCopyWithImpl<
          _$MeasurementBookInboxSortBlocEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        create,
    required TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)
        search,
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        searchRepeat,
    required TResult Function(int sortCode) sort,
    required TResult Function() clear,
  }) {
    return sort(sortCode);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        create,
    TResult? Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        searchRepeat,
    TResult? Function(int sortCode)? sort,
    TResult? Function()? clear,
  }) {
    return sort?.call(sortCode);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        create,
    TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        searchRepeat,
    TResult Function(int sortCode)? sort,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (sort != null) {
      return sort(sortCode);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementBookInboxBlocEvent value) create,
    required TResult Function(MeasurementBookInboxSearchBlocEvent value) search,
    required TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)
        searchRepeat,
    required TResult Function(MeasurementBookInboxSortBlocEvent value) sort,
    required TResult Function(MeasurementBookInboxBlocClearEvent value) clear,
  }) {
    return sort(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementBookInboxBlocEvent value)? create,
    TResult? Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult? Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult? Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult? Function(MeasurementBookInboxBlocClearEvent value)? clear,
  }) {
    return sort?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementBookInboxBlocEvent value)? create,
    TResult Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult Function(MeasurementBookInboxBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (sort != null) {
      return sort(this);
    }
    return orElse();
  }
}

abstract class MeasurementBookInboxSortBlocEvent
    implements MeasurementInboxBlocEvent {
  const factory MeasurementBookInboxSortBlocEvent(
      {required final int sortCode}) = _$MeasurementBookInboxSortBlocEventImpl;

  int get sortCode;
  @JsonKey(ignore: true)
  _$$MeasurementBookInboxSortBlocEventImplCopyWith<
          _$MeasurementBookInboxSortBlocEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$MeasurementBookInboxBlocClearEventImplCopyWith<$Res> {
  factory _$$MeasurementBookInboxBlocClearEventImplCopyWith(
          _$MeasurementBookInboxBlocClearEventImpl value,
          $Res Function(_$MeasurementBookInboxBlocClearEventImpl) then) =
      __$$MeasurementBookInboxBlocClearEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$MeasurementBookInboxBlocClearEventImplCopyWithImpl<$Res>
    extends _$MeasurementInboxBlocEventCopyWithImpl<$Res,
        _$MeasurementBookInboxBlocClearEventImpl>
    implements _$$MeasurementBookInboxBlocClearEventImplCopyWith<$Res> {
  __$$MeasurementBookInboxBlocClearEventImplCopyWithImpl(
      _$MeasurementBookInboxBlocClearEventImpl _value,
      $Res Function(_$MeasurementBookInboxBlocClearEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$MeasurementBookInboxBlocClearEventImpl
    implements MeasurementBookInboxBlocClearEvent {
  const _$MeasurementBookInboxBlocClearEventImpl();

  @override
  String toString() {
    return 'MeasurementInboxBlocEvent.clear()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementBookInboxBlocClearEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        create,
    required TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)
        search,
    required TResult Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)
        searchRepeat,
    required TResult Function(int sortCode) sort,
    required TResult Function() clear,
  }) {
    return clear();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        create,
    TResult? Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult? Function(String tenantId, String businessService,
            String moduleName, int limit, int offset)?
        searchRepeat,
    TResult? Function(int sortCode)? sort,
    TResult? Function()? clear,
  }) {
    return clear?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        create,
    TResult Function(
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            int limit,
            int offset,
            Map<String, Map<String, dynamic>> data)?
        search,
    TResult Function(String tenantId, String businessService, String moduleName,
            int limit, int offset)?
        searchRepeat,
    TResult Function(int sortCode)? sort,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementBookInboxBlocEvent value) create,
    required TResult Function(MeasurementBookInboxSearchBlocEvent value) search,
    required TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)
        searchRepeat,
    required TResult Function(MeasurementBookInboxSortBlocEvent value) sort,
    required TResult Function(MeasurementBookInboxBlocClearEvent value) clear,
  }) {
    return clear(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementBookInboxBlocEvent value)? create,
    TResult? Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult? Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult? Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult? Function(MeasurementBookInboxBlocClearEvent value)? clear,
  }) {
    return clear?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementBookInboxBlocEvent value)? create,
    TResult Function(MeasurementBookInboxSearchBlocEvent value)? search,
    TResult Function(MeasurementBookInboxSearchRepeatBlocEvent value)?
        searchRepeat,
    TResult Function(MeasurementBookInboxSortBlocEvent value)? sort,
    TResult Function(MeasurementBookInboxBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(this);
    }
    return orElse();
  }
}

abstract class MeasurementBookInboxBlocClearEvent
    implements MeasurementInboxBlocEvent {
  const factory MeasurementBookInboxBlocClearEvent() =
      _$MeasurementBookInboxBlocClearEventImpl;
}

/// @nodoc
mixin _$MeasurementInboxState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MeasurementInboxStateCopyWith<$Res> {
  factory $MeasurementInboxStateCopyWith(MeasurementInboxState value,
          $Res Function(MeasurementInboxState) then) =
      _$MeasurementInboxStateCopyWithImpl<$Res, MeasurementInboxState>;
}

/// @nodoc
class _$MeasurementInboxStateCopyWithImpl<$Res,
        $Val extends MeasurementInboxState>
    implements $MeasurementInboxStateCopyWith<$Res> {
  _$MeasurementInboxStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$InitialImplCopyWith<$Res> {
  factory _$$InitialImplCopyWith(
          _$InitialImpl value, $Res Function(_$InitialImpl) then) =
      __$$InitialImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$InitialImplCopyWithImpl<$Res>
    extends _$MeasurementInboxStateCopyWithImpl<$Res, _$InitialImpl>
    implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial {
  const _$InitialImpl() : super._();

  @override
  String toString() {
    return 'MeasurementInboxState.initial()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$InitialImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return initial(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return initial?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial(this);
    }
    return orElse();
  }
}

abstract class _Initial extends MeasurementInboxState {
  const factory _Initial() = _$InitialImpl;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$LoadingImplCopyWith<$Res> {
  factory _$$LoadingImplCopyWith(
          _$LoadingImpl value, $Res Function(_$LoadingImpl) then) =
      __$$LoadingImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$LoadingImplCopyWithImpl<$Res>
    extends _$MeasurementInboxStateCopyWithImpl<$Res, _$LoadingImpl>
    implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading {
  const _$LoadingImpl() : super._();

  @override
  String toString() {
    return 'MeasurementInboxState.loading()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$LoadingImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loading(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loading?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading(this);
    }
    return orElse();
  }
}

abstract class _Loading extends MeasurementInboxState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {MBInboxResponse mbInboxResponse,
      bool isLoading,
      List<String>? ward,
      List<String>? status,
      String? projectId,
      String? mbNumber,
      String? projectName,
      bool search,
      Map<String, Map<String, dynamic>> data});

  $MBInboxResponseCopyWith<$Res> get mbInboxResponse;
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$MeasurementInboxStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? mbInboxResponse = null,
    Object? isLoading = null,
    Object? ward = freezed,
    Object? status = freezed,
    Object? projectId = freezed,
    Object? mbNumber = freezed,
    Object? projectName = freezed,
    Object? search = null,
    Object? data = null,
  }) {
    return _then(_$LoadedImpl(
      null == mbInboxResponse
          ? _value.mbInboxResponse
          : mbInboxResponse // ignore: cast_nullable_to_non_nullable
              as MBInboxResponse,
      null == isLoading
          ? _value.isLoading
          : isLoading // ignore: cast_nullable_to_non_nullable
              as bool,
      freezed == ward
          ? _value._ward
          : ward // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      freezed == status
          ? _value._status
          : status // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      freezed == mbNumber
          ? _value.mbNumber
          : mbNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      null == search
          ? _value.search
          : search // ignore: cast_nullable_to_non_nullable
              as bool,
      null == data
          ? _value._data
          : data // ignore: cast_nullable_to_non_nullable
              as Map<String, Map<String, dynamic>>,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $MBInboxResponseCopyWith<$Res> get mbInboxResponse {
    return $MBInboxResponseCopyWith<$Res>(_value.mbInboxResponse, (value) {
      return _then(_value.copyWith(mbInboxResponse: value));
    });
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl(
      this.mbInboxResponse,
      this.isLoading,
      final List<String>? ward,
      final List<String>? status,
      this.projectId,
      this.mbNumber,
      this.projectName,
      this.search,
      final Map<String, Map<String, dynamic>> data)
      : _ward = ward,
        _status = status,
        _data = data,
        super._();

  @override
  final MBInboxResponse mbInboxResponse;
  @override
  final bool isLoading;
  final List<String>? _ward;
  @override
  List<String>? get ward {
    final value = _ward;
    if (value == null) return null;
    if (_ward is EqualUnmodifiableListView) return _ward;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<String>? _status;
  @override
  List<String>? get status {
    final value = _status;
    if (value == null) return null;
    if (_status is EqualUnmodifiableListView) return _status;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final String? projectId;
  @override
  final String? mbNumber;
  @override
  final String? projectName;
  @override
  final bool search;
  final Map<String, Map<String, dynamic>> _data;
  @override
  Map<String, Map<String, dynamic>> get data {
    if (_data is EqualUnmodifiableMapView) return _data;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableMapView(_data);
  }

  @override
  String toString() {
    return 'MeasurementInboxState.loaded(mbInboxResponse: $mbInboxResponse, isLoading: $isLoading, ward: $ward, status: $status, projectId: $projectId, mbNumber: $mbNumber, projectName: $projectName, search: $search, data: $data)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.mbInboxResponse, mbInboxResponse) ||
                other.mbInboxResponse == mbInboxResponse) &&
            (identical(other.isLoading, isLoading) ||
                other.isLoading == isLoading) &&
            const DeepCollectionEquality().equals(other._ward, _ward) &&
            const DeepCollectionEquality().equals(other._status, _status) &&
            (identical(other.projectId, projectId) ||
                other.projectId == projectId) &&
            (identical(other.mbNumber, mbNumber) ||
                other.mbNumber == mbNumber) &&
            (identical(other.projectName, projectName) ||
                other.projectName == projectName) &&
            (identical(other.search, search) || other.search == search) &&
            const DeepCollectionEquality().equals(other._data, _data));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      mbInboxResponse,
      isLoading,
      const DeepCollectionEquality().hash(_ward),
      const DeepCollectionEquality().hash(_status),
      projectId,
      mbNumber,
      projectName,
      search,
      const DeepCollectionEquality().hash(_data));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      __$$LoadedImplCopyWithImpl<_$LoadedImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(mbInboxResponse, isLoading, ward, status, projectId, mbNumber,
        projectName, search, data);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(mbInboxResponse, isLoading, ward, status, projectId,
        mbNumber, projectName, search, data);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(mbInboxResponse, isLoading, ward, status, projectId,
          mbNumber, projectName, search, data);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loaded(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loaded?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(this);
    }
    return orElse();
  }
}

abstract class _Loaded extends MeasurementInboxState {
  const factory _Loaded(
      final MBInboxResponse mbInboxResponse,
      final bool isLoading,
      final List<String>? ward,
      final List<String>? status,
      final String? projectId,
      final String? mbNumber,
      final String? projectName,
      final bool search,
      final Map<String, Map<String, dynamic>> data) = _$LoadedImpl;
  const _Loaded._() : super._();

  MBInboxResponse get mbInboxResponse;
  bool get isLoading;
  List<String>? get ward;
  List<String>? get status;
  String? get projectId;
  String? get mbNumber;
  String? get projectName;
  bool get search;
  Map<String, Map<String, dynamic>> get data;
  @JsonKey(ignore: true)
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ErrorImplCopyWith<$Res> {
  factory _$$ErrorImplCopyWith(
          _$ErrorImpl value, $Res Function(_$ErrorImpl) then) =
      __$$ErrorImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$ErrorImplCopyWithImpl<$Res>
    extends _$MeasurementInboxStateCopyWithImpl<$Res, _$ErrorImpl>
    implements _$$ErrorImplCopyWith<$Res> {
  __$$ErrorImplCopyWithImpl(
      _$ErrorImpl _value, $Res Function(_$ErrorImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$ErrorImpl(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$ErrorImpl extends _Error {
  const _$ErrorImpl(this.error) : super._();

  @override
  final String? error;

  @override
  String toString() {
    return 'MeasurementInboxState.error(error: $error)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ErrorImpl &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      __$$ErrorImplCopyWithImpl<_$ErrorImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MBInboxResponse mbInboxResponse,
            bool isLoading,
            List<String>? ward,
            List<String>? status,
            String? projectId,
            String? mbNumber,
            String? projectName,
            bool search,
            Map<String, Map<String, dynamic>> data)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this.error);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return error(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return error?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this);
    }
    return orElse();
  }
}

abstract class _Error extends MeasurementInboxState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
