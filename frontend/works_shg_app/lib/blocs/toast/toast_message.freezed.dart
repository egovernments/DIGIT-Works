// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'toast_message.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$ToastEvent {
  bool? get showToast => throw _privateConstructorUsedError;
  String? get toastMessage => throw _privateConstructorUsedError;
  String? get toastType => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            bool? showToast, String? toastMessage, String? toastType)
        show,
    required TResult Function(
            bool? showToast, String? toastMessage, String? toastType)
        dismiss,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(bool? showToast, String? toastMessage, String? toastType)?
        show,
    TResult? Function(bool? showToast, String? toastMessage, String? toastType)?
        dismiss,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(bool? showToast, String? toastMessage, String? toastType)?
        show,
    TResult Function(bool? showToast, String? toastMessage, String? toastType)?
        dismiss,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(ToastShowEvent value) show,
    required TResult Function(ToastDismissEvent value) dismiss,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(ToastShowEvent value)? show,
    TResult? Function(ToastDismissEvent value)? dismiss,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(ToastShowEvent value)? show,
    TResult Function(ToastDismissEvent value)? dismiss,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ToastEventCopyWith<ToastEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ToastEventCopyWith<$Res> {
  factory $ToastEventCopyWith(
          ToastEvent value, $Res Function(ToastEvent) then) =
      _$ToastEventCopyWithImpl<$Res, ToastEvent>;
  @useResult
  $Res call({bool? showToast, String? toastMessage, String? toastType});
}

/// @nodoc
class _$ToastEventCopyWithImpl<$Res, $Val extends ToastEvent>
    implements $ToastEventCopyWith<$Res> {
  _$ToastEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? showToast = freezed,
    Object? toastMessage = freezed,
    Object? toastType = freezed,
  }) {
    return _then(_value.copyWith(
      showToast: freezed == showToast
          ? _value.showToast
          : showToast // ignore: cast_nullable_to_non_nullable
              as bool?,
      toastMessage: freezed == toastMessage
          ? _value.toastMessage
          : toastMessage // ignore: cast_nullable_to_non_nullable
              as String?,
      toastType: freezed == toastType
          ? _value.toastType
          : toastType // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$ToastShowEventCopyWith<$Res>
    implements $ToastEventCopyWith<$Res> {
  factory _$$ToastShowEventCopyWith(
          _$ToastShowEvent value, $Res Function(_$ToastShowEvent) then) =
      __$$ToastShowEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool? showToast, String? toastMessage, String? toastType});
}

/// @nodoc
class __$$ToastShowEventCopyWithImpl<$Res>
    extends _$ToastEventCopyWithImpl<$Res, _$ToastShowEvent>
    implements _$$ToastShowEventCopyWith<$Res> {
  __$$ToastShowEventCopyWithImpl(
      _$ToastShowEvent _value, $Res Function(_$ToastShowEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? showToast = freezed,
    Object? toastMessage = freezed,
    Object? toastType = freezed,
  }) {
    return _then(_$ToastShowEvent(
      showToast: freezed == showToast
          ? _value.showToast
          : showToast // ignore: cast_nullable_to_non_nullable
              as bool?,
      toastMessage: freezed == toastMessage
          ? _value.toastMessage
          : toastMessage // ignore: cast_nullable_to_non_nullable
              as String?,
      toastType: freezed == toastType
          ? _value.toastType
          : toastType // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$ToastShowEvent implements ToastShowEvent {
  const _$ToastShowEvent({this.showToast, this.toastMessage, this.toastType});

  @override
  final bool? showToast;
  @override
  final String? toastMessage;
  @override
  final String? toastType;

  @override
  String toString() {
    return 'ToastEvent.show(showToast: $showToast, toastMessage: $toastMessage, toastType: $toastType)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ToastShowEvent &&
            (identical(other.showToast, showToast) ||
                other.showToast == showToast) &&
            (identical(other.toastMessage, toastMessage) ||
                other.toastMessage == toastMessage) &&
            (identical(other.toastType, toastType) ||
                other.toastType == toastType));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, showToast, toastMessage, toastType);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ToastShowEventCopyWith<_$ToastShowEvent> get copyWith =>
      __$$ToastShowEventCopyWithImpl<_$ToastShowEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            bool? showToast, String? toastMessage, String? toastType)
        show,
    required TResult Function(
            bool? showToast, String? toastMessage, String? toastType)
        dismiss,
  }) {
    return show(showToast, toastMessage, toastType);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(bool? showToast, String? toastMessage, String? toastType)?
        show,
    TResult? Function(bool? showToast, String? toastMessage, String? toastType)?
        dismiss,
  }) {
    return show?.call(showToast, toastMessage, toastType);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(bool? showToast, String? toastMessage, String? toastType)?
        show,
    TResult Function(bool? showToast, String? toastMessage, String? toastType)?
        dismiss,
    required TResult orElse(),
  }) {
    if (show != null) {
      return show(showToast, toastMessage, toastType);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(ToastShowEvent value) show,
    required TResult Function(ToastDismissEvent value) dismiss,
  }) {
    return show(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(ToastShowEvent value)? show,
    TResult? Function(ToastDismissEvent value)? dismiss,
  }) {
    return show?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(ToastShowEvent value)? show,
    TResult Function(ToastDismissEvent value)? dismiss,
    required TResult orElse(),
  }) {
    if (show != null) {
      return show(this);
    }
    return orElse();
  }
}

abstract class ToastShowEvent implements ToastEvent {
  const factory ToastShowEvent(
      {final bool? showToast,
      final String? toastMessage,
      final String? toastType}) = _$ToastShowEvent;

  @override
  bool? get showToast;
  @override
  String? get toastMessage;
  @override
  String? get toastType;
  @override
  @JsonKey(ignore: true)
  _$$ToastShowEventCopyWith<_$ToastShowEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ToastDismissEventCopyWith<$Res>
    implements $ToastEventCopyWith<$Res> {
  factory _$$ToastDismissEventCopyWith(
          _$ToastDismissEvent value, $Res Function(_$ToastDismissEvent) then) =
      __$$ToastDismissEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool? showToast, String? toastMessage, String? toastType});
}

/// @nodoc
class __$$ToastDismissEventCopyWithImpl<$Res>
    extends _$ToastEventCopyWithImpl<$Res, _$ToastDismissEvent>
    implements _$$ToastDismissEventCopyWith<$Res> {
  __$$ToastDismissEventCopyWithImpl(
      _$ToastDismissEvent _value, $Res Function(_$ToastDismissEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? showToast = freezed,
    Object? toastMessage = freezed,
    Object? toastType = freezed,
  }) {
    return _then(_$ToastDismissEvent(
      showToast: freezed == showToast
          ? _value.showToast
          : showToast // ignore: cast_nullable_to_non_nullable
              as bool?,
      toastMessage: freezed == toastMessage
          ? _value.toastMessage
          : toastMessage // ignore: cast_nullable_to_non_nullable
              as String?,
      toastType: freezed == toastType
          ? _value.toastType
          : toastType // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$ToastDismissEvent implements ToastDismissEvent {
  const _$ToastDismissEvent(
      {this.showToast, this.toastMessage, this.toastType});

  @override
  final bool? showToast;
  @override
  final String? toastMessage;
  @override
  final String? toastType;

  @override
  String toString() {
    return 'ToastEvent.dismiss(showToast: $showToast, toastMessage: $toastMessage, toastType: $toastType)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ToastDismissEvent &&
            (identical(other.showToast, showToast) ||
                other.showToast == showToast) &&
            (identical(other.toastMessage, toastMessage) ||
                other.toastMessage == toastMessage) &&
            (identical(other.toastType, toastType) ||
                other.toastType == toastType));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, showToast, toastMessage, toastType);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ToastDismissEventCopyWith<_$ToastDismissEvent> get copyWith =>
      __$$ToastDismissEventCopyWithImpl<_$ToastDismissEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            bool? showToast, String? toastMessage, String? toastType)
        show,
    required TResult Function(
            bool? showToast, String? toastMessage, String? toastType)
        dismiss,
  }) {
    return dismiss(showToast, toastMessage, toastType);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(bool? showToast, String? toastMessage, String? toastType)?
        show,
    TResult? Function(bool? showToast, String? toastMessage, String? toastType)?
        dismiss,
  }) {
    return dismiss?.call(showToast, toastMessage, toastType);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(bool? showToast, String? toastMessage, String? toastType)?
        show,
    TResult Function(bool? showToast, String? toastMessage, String? toastType)?
        dismiss,
    required TResult orElse(),
  }) {
    if (dismiss != null) {
      return dismiss(showToast, toastMessage, toastType);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(ToastShowEvent value) show,
    required TResult Function(ToastDismissEvent value) dismiss,
  }) {
    return dismiss(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(ToastShowEvent value)? show,
    TResult? Function(ToastDismissEvent value)? dismiss,
  }) {
    return dismiss?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(ToastShowEvent value)? show,
    TResult Function(ToastDismissEvent value)? dismiss,
    required TResult orElse(),
  }) {
    if (dismiss != null) {
      return dismiss(this);
    }
    return orElse();
  }
}

abstract class ToastDismissEvent implements ToastEvent {
  const factory ToastDismissEvent(
      {final bool? showToast,
      final String? toastMessage,
      final String? toastType}) = _$ToastDismissEvent;

  @override
  bool? get showToast;
  @override
  String? get toastMessage;
  @override
  String? get toastType;
  @override
  @JsonKey(ignore: true)
  _$$ToastDismissEventCopyWith<_$ToastDismissEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$ToastState {
  bool get showToast => throw _privateConstructorUsedError;
  String? get toastMessage => throw _privateConstructorUsedError;
  String? get toastType => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ToastStateCopyWith<ToastState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ToastStateCopyWith<$Res> {
  factory $ToastStateCopyWith(
          ToastState value, $Res Function(ToastState) then) =
      _$ToastStateCopyWithImpl<$Res, ToastState>;
  @useResult
  $Res call({bool showToast, String? toastMessage, String? toastType});
}

/// @nodoc
class _$ToastStateCopyWithImpl<$Res, $Val extends ToastState>
    implements $ToastStateCopyWith<$Res> {
  _$ToastStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? showToast = null,
    Object? toastMessage = freezed,
    Object? toastType = freezed,
  }) {
    return _then(_value.copyWith(
      showToast: null == showToast
          ? _value.showToast
          : showToast // ignore: cast_nullable_to_non_nullable
              as bool,
      toastMessage: freezed == toastMessage
          ? _value.toastMessage
          : toastMessage // ignore: cast_nullable_to_non_nullable
              as String?,
      toastType: freezed == toastType
          ? _value.toastType
          : toastType // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_ToastStateCopyWith<$Res>
    implements $ToastStateCopyWith<$Res> {
  factory _$$_ToastStateCopyWith(
          _$_ToastState value, $Res Function(_$_ToastState) then) =
      __$$_ToastStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool showToast, String? toastMessage, String? toastType});
}

/// @nodoc
class __$$_ToastStateCopyWithImpl<$Res>
    extends _$ToastStateCopyWithImpl<$Res, _$_ToastState>
    implements _$$_ToastStateCopyWith<$Res> {
  __$$_ToastStateCopyWithImpl(
      _$_ToastState _value, $Res Function(_$_ToastState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? showToast = null,
    Object? toastMessage = freezed,
    Object? toastType = freezed,
  }) {
    return _then(_$_ToastState(
      showToast: null == showToast
          ? _value.showToast
          : showToast // ignore: cast_nullable_to_non_nullable
              as bool,
      toastMessage: freezed == toastMessage
          ? _value.toastMessage
          : toastMessage // ignore: cast_nullable_to_non_nullable
              as String?,
      toastType: freezed == toastType
          ? _value.toastType
          : toastType // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$_ToastState extends _ToastState {
  const _$_ToastState(
      {this.showToast = false, this.toastMessage, this.toastType})
      : super._();

  @override
  @JsonKey()
  final bool showToast;
  @override
  final String? toastMessage;
  @override
  final String? toastType;

  @override
  String toString() {
    return 'ToastState(showToast: $showToast, toastMessage: $toastMessage, toastType: $toastType)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_ToastState &&
            (identical(other.showToast, showToast) ||
                other.showToast == showToast) &&
            (identical(other.toastMessage, toastMessage) ||
                other.toastMessage == toastMessage) &&
            (identical(other.toastType, toastType) ||
                other.toastType == toastType));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, showToast, toastMessage, toastType);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ToastStateCopyWith<_$_ToastState> get copyWith =>
      __$$_ToastStateCopyWithImpl<_$_ToastState>(this, _$identity);
}

abstract class _ToastState extends ToastState {
  const factory _ToastState(
      {final bool showToast,
      final String? toastMessage,
      final String? toastType}) = _$_ToastState;
  const _ToastState._() : super._();

  @override
  bool get showToast;
  @override
  String? get toastMessage;
  @override
  String? get toastType;
  @override
  @JsonKey(ignore: true)
  _$$_ToastStateCopyWith<_$_ToastState> get copyWith =>
      throw _privateConstructorUsedError;
}
