// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'individual_wms_search.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$IndividualWMSSearchEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchWMSIndividualNameEvent value) nameSearch,
    required TResult Function(SearchWMSIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchWMSIndividualEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchWMSIndividualEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchWMSIndividualEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $IndividualWMSSearchEventCopyWith<$Res> {
  factory $IndividualWMSSearchEventCopyWith(IndividualWMSSearchEvent value,
          $Res Function(IndividualWMSSearchEvent) then) =
      _$IndividualWMSSearchEventCopyWithImpl<$Res, IndividualWMSSearchEvent>;
}

/// @nodoc
class _$IndividualWMSSearchEventCopyWithImpl<$Res,
        $Val extends IndividualWMSSearchEvent>
    implements $IndividualWMSSearchEventCopyWith<$Res> {
  _$IndividualWMSSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$SearchWMSIndividualNameEventImplCopyWith<$Res> {
  factory _$$SearchWMSIndividualNameEventImplCopyWith(
          _$SearchWMSIndividualNameEventImpl value,
          $Res Function(_$SearchWMSIndividualNameEventImpl) then) =
      __$$SearchWMSIndividualNameEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, String name});
}

/// @nodoc
class __$$SearchWMSIndividualNameEventImplCopyWithImpl<$Res>
    extends _$IndividualWMSSearchEventCopyWithImpl<$Res,
        _$SearchWMSIndividualNameEventImpl>
    implements _$$SearchWMSIndividualNameEventImplCopyWith<$Res> {
  __$$SearchWMSIndividualNameEventImplCopyWithImpl(
      _$SearchWMSIndividualNameEventImpl _value,
      $Res Function(_$SearchWMSIndividualNameEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? name = null,
  }) {
    return _then(_$SearchWMSIndividualNameEventImpl(
      tenant: null == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as String,
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchWMSIndividualNameEventImpl
    with DiagnosticableTreeMixin
    implements SearchWMSIndividualNameEvent {
  const _$SearchWMSIndividualNameEventImpl(
      {required this.tenant, required this.name});

  @override
  final String tenant;
  @override
  final String name;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchEvent.nameSearch(tenant: $tenant, name: $name)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualWMSSearchEvent.nameSearch'))
      ..add(DiagnosticsProperty('tenant', tenant))
      ..add(DiagnosticsProperty('name', name));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchWMSIndividualNameEventImpl &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            (identical(other.name, name) || other.name == name));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenant, name);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchWMSIndividualNameEventImplCopyWith<
          _$SearchWMSIndividualNameEventImpl>
      get copyWith => __$$SearchWMSIndividualNameEventImplCopyWithImpl<
          _$SearchWMSIndividualNameEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return nameSearch(tenant, name);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return nameSearch?.call(tenant, name);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (nameSearch != null) {
      return nameSearch(tenant, name);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchWMSIndividualNameEvent value) nameSearch,
    required TResult Function(SearchWMSIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchWMSIndividualEvent value) dispose,
  }) {
    return nameSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchWMSIndividualEvent value)? dispose,
  }) {
    return nameSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchWMSIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (nameSearch != null) {
      return nameSearch(this);
    }
    return orElse();
  }
}

abstract class SearchWMSIndividualNameEvent
    implements IndividualWMSSearchEvent {
  const factory SearchWMSIndividualNameEvent(
      {required final String tenant,
      required final String name}) = _$SearchWMSIndividualNameEventImpl;

  String get tenant;
  String get name;
  @JsonKey(ignore: true)
  _$$SearchWMSIndividualNameEventImplCopyWith<
          _$SearchWMSIndividualNameEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchWMSIndividualIdEventImplCopyWith<$Res> {
  factory _$$SearchWMSIndividualIdEventImplCopyWith(
          _$SearchWMSIndividualIdEventImpl value,
          $Res Function(_$SearchWMSIndividualIdEventImpl) then) =
      __$$SearchWMSIndividualIdEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, List<String>? ids});
}

/// @nodoc
class __$$SearchWMSIndividualIdEventImplCopyWithImpl<$Res>
    extends _$IndividualWMSSearchEventCopyWithImpl<$Res,
        _$SearchWMSIndividualIdEventImpl>
    implements _$$SearchWMSIndividualIdEventImplCopyWith<$Res> {
  __$$SearchWMSIndividualIdEventImplCopyWithImpl(
      _$SearchWMSIndividualIdEventImpl _value,
      $Res Function(_$SearchWMSIndividualIdEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? ids = freezed,
  }) {
    return _then(_$SearchWMSIndividualIdEventImpl(
      tenant: null == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as String,
      ids: freezed == ids
          ? _value._ids
          : ids // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ));
  }
}

/// @nodoc

class _$SearchWMSIndividualIdEventImpl
    with DiagnosticableTreeMixin
    implements SearchWMSIndividualIdEvent {
  const _$SearchWMSIndividualIdEventImpl(
      {required this.tenant, final List<String>? ids})
      : _ids = ids;

  @override
  final String tenant;
  final List<String>? _ids;
  @override
  List<String>? get ids {
    final value = _ids;
    if (value == null) return null;
    if (_ids is EqualUnmodifiableListView) return _ids;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchEvent.idSearch(tenant: $tenant, ids: $ids)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualWMSSearchEvent.idSearch'))
      ..add(DiagnosticsProperty('tenant', tenant))
      ..add(DiagnosticsProperty('ids', ids));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchWMSIndividualIdEventImpl &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            const DeepCollectionEquality().equals(other._ids, _ids));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenant, const DeepCollectionEquality().hash(_ids));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchWMSIndividualIdEventImplCopyWith<_$SearchWMSIndividualIdEventImpl>
      get copyWith => __$$SearchWMSIndividualIdEventImplCopyWithImpl<
          _$SearchWMSIndividualIdEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return idSearch(tenant, ids);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return idSearch?.call(tenant, ids);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (idSearch != null) {
      return idSearch(tenant, ids);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchWMSIndividualNameEvent value) nameSearch,
    required TResult Function(SearchWMSIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchWMSIndividualEvent value) dispose,
  }) {
    return idSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchWMSIndividualEvent value)? dispose,
  }) {
    return idSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchWMSIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (idSearch != null) {
      return idSearch(this);
    }
    return orElse();
  }
}

abstract class SearchWMSIndividualIdEvent implements IndividualWMSSearchEvent {
  const factory SearchWMSIndividualIdEvent(
      {required final String tenant,
      final List<String>? ids}) = _$SearchWMSIndividualIdEventImpl;

  String get tenant;
  List<String>? get ids;
  @JsonKey(ignore: true)
  _$$SearchWMSIndividualIdEventImplCopyWith<_$SearchWMSIndividualIdEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeSearchWMSIndividualEventImplCopyWith<$Res> {
  factory _$$DisposeSearchWMSIndividualEventImplCopyWith(
          _$DisposeSearchWMSIndividualEventImpl value,
          $Res Function(_$DisposeSearchWMSIndividualEventImpl) then) =
      __$$DisposeSearchWMSIndividualEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeSearchWMSIndividualEventImplCopyWithImpl<$Res>
    extends _$IndividualWMSSearchEventCopyWithImpl<$Res,
        _$DisposeSearchWMSIndividualEventImpl>
    implements _$$DisposeSearchWMSIndividualEventImplCopyWith<$Res> {
  __$$DisposeSearchWMSIndividualEventImplCopyWithImpl(
      _$DisposeSearchWMSIndividualEventImpl _value,
      $Res Function(_$DisposeSearchWMSIndividualEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeSearchWMSIndividualEventImpl
    with DiagnosticableTreeMixin
    implements DisposeSearchWMSIndividualEvent {
  const _$DisposeSearchWMSIndividualEventImpl();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchEvent.dispose()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualWMSSearchEvent.dispose'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeSearchWMSIndividualEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchWMSIndividualNameEvent value) nameSearch,
    required TResult Function(SearchWMSIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchWMSIndividualEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchWMSIndividualEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchWMSIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeSearchWMSIndividualEvent
    implements IndividualWMSSearchEvent {
  const factory DisposeSearchWMSIndividualEvent() =
      _$DisposeSearchWMSIndividualEventImpl;
}

/// @nodoc
mixin _$IndividualWMSSearchState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(WMSIndividualListModel? individualListModel)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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
abstract class $IndividualWMSSearchStateCopyWith<$Res> {
  factory $IndividualWMSSearchStateCopyWith(IndividualWMSSearchState value,
          $Res Function(IndividualWMSSearchState) then) =
      _$IndividualWMSSearchStateCopyWithImpl<$Res, IndividualWMSSearchState>;
}

/// @nodoc
class _$IndividualWMSSearchStateCopyWithImpl<$Res,
        $Val extends IndividualWMSSearchState>
    implements $IndividualWMSSearchStateCopyWith<$Res> {
  _$IndividualWMSSearchStateCopyWithImpl(this._value, this._then);

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
    extends _$IndividualWMSSearchStateCopyWithImpl<$Res, _$InitialImpl>
    implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial with DiagnosticableTreeMixin {
  const _$InitialImpl() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualWMSSearchState.initial'));
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
    required TResult Function(WMSIndividualListModel? individualListModel)
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
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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

abstract class _Initial extends IndividualWMSSearchState {
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
    extends _$IndividualWMSSearchStateCopyWithImpl<$Res, _$LoadingImpl>
    implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading with DiagnosticableTreeMixin {
  const _$LoadingImpl() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualWMSSearchState.loading'));
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
    required TResult Function(WMSIndividualListModel? individualListModel)
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
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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

abstract class _Loading extends IndividualWMSSearchState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({WMSIndividualListModel? individualListModel});
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$IndividualWMSSearchStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualListModel = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == individualListModel
          ? _value.individualListModel
          : individualListModel // ignore: cast_nullable_to_non_nullable
              as WMSIndividualListModel?,
    ));
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded with DiagnosticableTreeMixin {
  const _$LoadedImpl(this.individualListModel) : super._();

  @override
  final WMSIndividualListModel? individualListModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchState.loaded(individualListModel: $individualListModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualWMSSearchState.loaded'))
      ..add(DiagnosticsProperty('individualListModel', individualListModel));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.individualListModel, individualListModel) ||
                other.individualListModel == individualListModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualListModel);

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
    required TResult Function(WMSIndividualListModel? individualListModel)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(individualListModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(individualListModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(individualListModel);
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

abstract class _Loaded extends IndividualWMSSearchState {
  const factory _Loaded(final WMSIndividualListModel? individualListModel) =
      _$LoadedImpl;
  const _Loaded._() : super._();

  WMSIndividualListModel? get individualListModel;
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
    extends _$IndividualWMSSearchStateCopyWithImpl<$Res, _$ErrorImpl>
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

class _$ErrorImpl extends _Error with DiagnosticableTreeMixin {
  const _$ErrorImpl(this.error) : super._();

  @override
  final String? error;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualWMSSearchState.error'))
      ..add(DiagnosticsProperty('error', error));
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
    required TResult Function(WMSIndividualListModel? individualListModel)
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
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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

abstract class _Error extends IndividualWMSSearchState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
