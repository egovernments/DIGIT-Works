// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'mb_detail_view.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$MeasurementDetailBlocEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MeasurementDetailBlocEventCopyWith<$Res> {
  factory $MeasurementDetailBlocEventCopyWith(MeasurementDetailBlocEvent value,
          $Res Function(MeasurementDetailBlocEvent) then) =
      _$MeasurementDetailBlocEventCopyWithImpl<$Res,
          MeasurementDetailBlocEvent>;
}

/// @nodoc
class _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        $Val extends MeasurementDetailBlocEvent>
    implements $MeasurementDetailBlocEventCopyWith<$Res> {
  _$MeasurementDetailBlocEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$MeasurementUploadDocumentBlocEventImplCopyWith<$Res> {
  factory _$$MeasurementUploadDocumentBlocEventImplCopyWith(
          _$MeasurementUploadDocumentBlocEventImpl value,
          $Res Function(_$MeasurementUploadDocumentBlocEventImpl) then) =
      __$$MeasurementUploadDocumentBlocEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenantId, List<WorkflowDocument> workflowDocument});
}

/// @nodoc
class __$$MeasurementUploadDocumentBlocEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$MeasurementUploadDocumentBlocEventImpl>
    implements _$$MeasurementUploadDocumentBlocEventImplCopyWith<$Res> {
  __$$MeasurementUploadDocumentBlocEventImplCopyWithImpl(
      _$MeasurementUploadDocumentBlocEventImpl _value,
      $Res Function(_$MeasurementUploadDocumentBlocEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? workflowDocument = null,
  }) {
    return _then(_$MeasurementUploadDocumentBlocEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      workflowDocument: null == workflowDocument
          ? _value._workflowDocument
          : workflowDocument // ignore: cast_nullable_to_non_nullable
              as List<WorkflowDocument>,
    ));
  }
}

/// @nodoc

class _$MeasurementUploadDocumentBlocEventImpl
    implements MeasurementUploadDocumentBlocEvent {
  const _$MeasurementUploadDocumentBlocEventImpl(
      {required this.tenantId,
      required final List<WorkflowDocument> workflowDocument})
      : _workflowDocument = workflowDocument;

  @override
  final String tenantId;
  final List<WorkflowDocument> _workflowDocument;
  @override
  List<WorkflowDocument> get workflowDocument {
    if (_workflowDocument is EqualUnmodifiableListView)
      return _workflowDocument;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_workflowDocument);
  }

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.uploadDocument(tenantId: $tenantId, workflowDocument: $workflowDocument)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementUploadDocumentBlocEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            const DeepCollectionEquality()
                .equals(other._workflowDocument, _workflowDocument));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenantId,
      const DeepCollectionEquality().hash(_workflowDocument));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementUploadDocumentBlocEventImplCopyWith<
          _$MeasurementUploadDocumentBlocEventImpl>
      get copyWith => __$$MeasurementUploadDocumentBlocEventImplCopyWithImpl<
          _$MeasurementUploadDocumentBlocEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return uploadDocument(tenantId, workflowDocument);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return uploadDocument?.call(tenantId, workflowDocument);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (uploadDocument != null) {
      return uploadDocument(tenantId, workflowDocument);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return uploadDocument(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return uploadDocument?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (uploadDocument != null) {
      return uploadDocument(this);
    }
    return orElse();
  }
}

abstract class MeasurementUploadDocumentBlocEvent
    implements MeasurementDetailBlocEvent {
  const factory MeasurementUploadDocumentBlocEvent(
          {required final String tenantId,
          required final List<WorkflowDocument> workflowDocument}) =
      _$MeasurementUploadDocumentBlocEventImpl;

  String get tenantId;
  List<WorkflowDocument> get workflowDocument;
  @JsonKey(ignore: true)
  _$$MeasurementUploadDocumentBlocEventImplCopyWith<
          _$MeasurementUploadDocumentBlocEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$MeasurementDetailBookBlocEventImplCopyWith<$Res> {
  factory _$$MeasurementDetailBookBlocEventImplCopyWith(
          _$MeasurementDetailBookBlocEventImpl value,
          $Res Function(_$MeasurementDetailBookBlocEventImpl) then) =
      __$$MeasurementDetailBookBlocEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String tenantId,
      String contractNumber,
      String measurementNumber,
      MBScreen screenType});
}

/// @nodoc
class __$$MeasurementDetailBookBlocEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$MeasurementDetailBookBlocEventImpl>
    implements _$$MeasurementDetailBookBlocEventImplCopyWith<$Res> {
  __$$MeasurementDetailBookBlocEventImplCopyWithImpl(
      _$MeasurementDetailBookBlocEventImpl _value,
      $Res Function(_$MeasurementDetailBookBlocEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? contractNumber = null,
    Object? measurementNumber = null,
    Object? screenType = null,
  }) {
    return _then(_$MeasurementDetailBookBlocEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      contractNumber: null == contractNumber
          ? _value.contractNumber
          : contractNumber // ignore: cast_nullable_to_non_nullable
              as String,
      measurementNumber: null == measurementNumber
          ? _value.measurementNumber
          : measurementNumber // ignore: cast_nullable_to_non_nullable
              as String,
      screenType: null == screenType
          ? _value.screenType
          : screenType // ignore: cast_nullable_to_non_nullable
              as MBScreen,
    ));
  }
}

/// @nodoc

class _$MeasurementDetailBookBlocEventImpl
    implements MeasurementDetailBookBlocEvent {
  const _$MeasurementDetailBookBlocEventImpl(
      {required this.tenantId,
      required this.contractNumber,
      required this.measurementNumber,
      required this.screenType});

  @override
  final String tenantId;
  @override
  final String contractNumber;
  @override
  final String measurementNumber;
  @override
  final MBScreen screenType;

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.create(tenantId: $tenantId, contractNumber: $contractNumber, measurementNumber: $measurementNumber, screenType: $screenType)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementDetailBookBlocEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.contractNumber, contractNumber) ||
                other.contractNumber == contractNumber) &&
            (identical(other.measurementNumber, measurementNumber) ||
                other.measurementNumber == measurementNumber) &&
            (identical(other.screenType, screenType) ||
                other.screenType == screenType));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenantId, contractNumber, measurementNumber, screenType);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementDetailBookBlocEventImplCopyWith<
          _$MeasurementDetailBookBlocEventImpl>
      get copyWith => __$$MeasurementDetailBookBlocEventImplCopyWithImpl<
          _$MeasurementDetailBookBlocEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return create(tenantId, contractNumber, measurementNumber, screenType);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return create?.call(
        tenantId, contractNumber, measurementNumber, screenType);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(tenantId, contractNumber, measurementNumber, screenType);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class MeasurementDetailBookBlocEvent
    implements MeasurementDetailBlocEvent {
  const factory MeasurementDetailBookBlocEvent(
          {required final String tenantId,
          required final String contractNumber,
          required final String measurementNumber,
          required final MBScreen screenType}) =
      _$MeasurementDetailBookBlocEventImpl;

  String get tenantId;
  String get contractNumber;
  String get measurementNumber;
  MBScreen get screenType;
  @JsonKey(ignore: true)
  _$$MeasurementDetailBookBlocEventImplCopyWith<
          _$MeasurementDetailBookBlocEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$MeasurementDetailBlocClearEventImplCopyWith<$Res> {
  factory _$$MeasurementDetailBlocClearEventImplCopyWith(
          _$MeasurementDetailBlocClearEventImpl value,
          $Res Function(_$MeasurementDetailBlocClearEventImpl) then) =
      __$$MeasurementDetailBlocClearEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$MeasurementDetailBlocClearEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$MeasurementDetailBlocClearEventImpl>
    implements _$$MeasurementDetailBlocClearEventImplCopyWith<$Res> {
  __$$MeasurementDetailBlocClearEventImplCopyWithImpl(
      _$MeasurementDetailBlocClearEventImpl _value,
      $Res Function(_$MeasurementDetailBlocClearEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$MeasurementDetailBlocClearEventImpl
    implements MeasurementDetailBlocClearEvent {
  const _$MeasurementDetailBlocClearEventImpl();

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.clear()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementDetailBlocClearEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return clear();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return clear?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
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
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return clear(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return clear?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(this);
    }
    return orElse();
  }
}

abstract class MeasurementDetailBlocClearEvent
    implements MeasurementDetailBlocEvent {
  const factory MeasurementDetailBlocClearEvent() =
      _$MeasurementDetailBlocClearEventImpl;
}

/// @nodoc
abstract class _$$AddToMeasurementLineEventImplCopyWith<$Res> {
  factory _$$AddToMeasurementLineEventImplCopyWith(
          _$AddToMeasurementLineEventImpl value,
          $Res Function(_$AddToMeasurementLineEventImpl) then) =
      __$$AddToMeasurementLineEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String sorId,
      String type,
      int? index,
      int? measurementLineIndex,
      String? filteredMeasurementMeasureId,
      dynamic height,
      dynamic width,
      dynamic length,
      dynamic number,
      dynamic quantity,
      dynamic measurementSummary,
      bool single});
}

/// @nodoc
class __$$AddToMeasurementLineEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$AddToMeasurementLineEventImpl>
    implements _$$AddToMeasurementLineEventImplCopyWith<$Res> {
  __$$AddToMeasurementLineEventImplCopyWithImpl(
      _$AddToMeasurementLineEventImpl _value,
      $Res Function(_$AddToMeasurementLineEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? sorId = null,
    Object? type = null,
    Object? index = freezed,
    Object? measurementLineIndex = freezed,
    Object? filteredMeasurementMeasureId = freezed,
    Object? height = freezed,
    Object? width = freezed,
    Object? length = freezed,
    Object? number = freezed,
    Object? quantity = freezed,
    Object? measurementSummary = freezed,
    Object? single = null,
  }) {
    return _then(_$AddToMeasurementLineEventImpl(
      sorId: null == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String,
      type: null == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String,
      index: freezed == index
          ? _value.index
          : index // ignore: cast_nullable_to_non_nullable
              as int?,
      measurementLineIndex: freezed == measurementLineIndex
          ? _value.measurementLineIndex
          : measurementLineIndex // ignore: cast_nullable_to_non_nullable
              as int?,
      filteredMeasurementMeasureId: freezed == filteredMeasurementMeasureId
          ? _value.filteredMeasurementMeasureId
          : filteredMeasurementMeasureId // ignore: cast_nullable_to_non_nullable
              as String?,
      height: freezed == height
          ? _value.height
          : height // ignore: cast_nullable_to_non_nullable
              as dynamic,
      width: freezed == width
          ? _value.width
          : width // ignore: cast_nullable_to_non_nullable
              as dynamic,
      length: freezed == length
          ? _value.length
          : length // ignore: cast_nullable_to_non_nullable
              as dynamic,
      number: freezed == number
          ? _value.number
          : number // ignore: cast_nullable_to_non_nullable
              as dynamic,
      quantity: freezed == quantity
          ? _value.quantity
          : quantity // ignore: cast_nullable_to_non_nullable
              as dynamic,
      measurementSummary: freezed == measurementSummary
          ? _value.measurementSummary
          : measurementSummary // ignore: cast_nullable_to_non_nullable
              as dynamic,
      single: null == single
          ? _value.single
          : single // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc

class _$AddToMeasurementLineEventImpl implements AddToMeasurementLineEvent {
  const _$AddToMeasurementLineEventImpl(
      {required this.sorId,
      required this.type,
      this.index,
      this.measurementLineIndex,
      this.filteredMeasurementMeasureId,
      this.height,
      this.width,
      this.length,
      this.number,
      this.quantity,
      this.measurementSummary,
      required this.single});

  @override
  final String sorId;
  @override
  final String type;
  @override
  final int? index;
  @override
  final int? measurementLineIndex;
  @override
  final String? filteredMeasurementMeasureId;
  @override
  final dynamic height;
  @override
  final dynamic width;
  @override
  final dynamic length;
  @override
  final dynamic number;
  @override
  final dynamic quantity;
  @override
  final dynamic measurementSummary;
  @override
  final bool single;

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.addToMeasurementLineList(sorId: $sorId, type: $type, index: $index, measurementLineIndex: $measurementLineIndex, filteredMeasurementMeasureId: $filteredMeasurementMeasureId, height: $height, width: $width, length: $length, number: $number, quantity: $quantity, measurementSummary: $measurementSummary, single: $single)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AddToMeasurementLineEventImpl &&
            (identical(other.sorId, sorId) || other.sorId == sorId) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.index, index) || other.index == index) &&
            (identical(other.measurementLineIndex, measurementLineIndex) ||
                other.measurementLineIndex == measurementLineIndex) &&
            (identical(other.filteredMeasurementMeasureId,
                    filteredMeasurementMeasureId) ||
                other.filteredMeasurementMeasureId ==
                    filteredMeasurementMeasureId) &&
            const DeepCollectionEquality().equals(other.height, height) &&
            const DeepCollectionEquality().equals(other.width, width) &&
            const DeepCollectionEquality().equals(other.length, length) &&
            const DeepCollectionEquality().equals(other.number, number) &&
            const DeepCollectionEquality().equals(other.quantity, quantity) &&
            const DeepCollectionEquality()
                .equals(other.measurementSummary, measurementSummary) &&
            (identical(other.single, single) || other.single == single));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      sorId,
      type,
      index,
      measurementLineIndex,
      filteredMeasurementMeasureId,
      const DeepCollectionEquality().hash(height),
      const DeepCollectionEquality().hash(width),
      const DeepCollectionEquality().hash(length),
      const DeepCollectionEquality().hash(number),
      const DeepCollectionEquality().hash(quantity),
      const DeepCollectionEquality().hash(measurementSummary),
      single);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AddToMeasurementLineEventImplCopyWith<_$AddToMeasurementLineEventImpl>
      get copyWith => __$$AddToMeasurementLineEventImplCopyWithImpl<
          _$AddToMeasurementLineEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return addToMeasurementLineList(
        sorId,
        type,
        index,
        measurementLineIndex,
        filteredMeasurementMeasureId,
        height,
        width,
        length,
        number,
        quantity,
        measurementSummary,
        single);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return addToMeasurementLineList?.call(
        sorId,
        type,
        index,
        measurementLineIndex,
        filteredMeasurementMeasureId,
        height,
        width,
        length,
        number,
        quantity,
        measurementSummary,
        single);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (addToMeasurementLineList != null) {
      return addToMeasurementLineList(
          sorId,
          type,
          index,
          measurementLineIndex,
          filteredMeasurementMeasureId,
          height,
          width,
          length,
          number,
          quantity,
          measurementSummary,
          single);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return addToMeasurementLineList(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return addToMeasurementLineList?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (addToMeasurementLineList != null) {
      return addToMeasurementLineList(this);
    }
    return orElse();
  }
}

abstract class AddToMeasurementLineEvent implements MeasurementDetailBlocEvent {
  const factory AddToMeasurementLineEvent(
      {required final String sorId,
      required final String type,
      final int? index,
      final int? measurementLineIndex,
      final String? filteredMeasurementMeasureId,
      final dynamic height,
      final dynamic width,
      final dynamic length,
      final dynamic number,
      final dynamic quantity,
      final dynamic measurementSummary,
      required final bool single}) = _$AddToMeasurementLineEventImpl;

  String get sorId;
  String get type;
  int? get index;
  int? get measurementLineIndex;
  String? get filteredMeasurementMeasureId;
  dynamic get height;
  dynamic get width;
  dynamic get length;
  dynamic get number;
  dynamic get quantity;
  dynamic get measurementSummary;
  bool get single;
  @JsonKey(ignore: true)
  _$$AddToMeasurementLineEventImplCopyWith<_$AddToMeasurementLineEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$UpdateToMeasurementLineEventImplCopyWith<$Res> {
  factory _$$UpdateToMeasurementLineEventImplCopyWith(
          _$UpdateToMeasurementLineEventImpl value,
          $Res Function(_$UpdateToMeasurementLineEventImpl) then) =
      __$$UpdateToMeasurementLineEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {dynamic noOfUnit,
      dynamic cummulativePrevQty,
      String sorId,
      String type,
      int? index,
      int? measurementLineIndex,
      String? filteredMeasurementMeasureId,
      dynamic height,
      dynamic width,
      dynamic length,
      dynamic number,
      dynamic quantity,
      dynamic measurementSummary});
}

/// @nodoc
class __$$UpdateToMeasurementLineEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$UpdateToMeasurementLineEventImpl>
    implements _$$UpdateToMeasurementLineEventImplCopyWith<$Res> {
  __$$UpdateToMeasurementLineEventImplCopyWithImpl(
      _$UpdateToMeasurementLineEventImpl _value,
      $Res Function(_$UpdateToMeasurementLineEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? noOfUnit = freezed,
    Object? cummulativePrevQty = freezed,
    Object? sorId = null,
    Object? type = null,
    Object? index = freezed,
    Object? measurementLineIndex = freezed,
    Object? filteredMeasurementMeasureId = freezed,
    Object? height = freezed,
    Object? width = freezed,
    Object? length = freezed,
    Object? number = freezed,
    Object? quantity = freezed,
    Object? measurementSummary = freezed,
  }) {
    return _then(_$UpdateToMeasurementLineEventImpl(
      noOfUnit: freezed == noOfUnit
          ? _value.noOfUnit
          : noOfUnit // ignore: cast_nullable_to_non_nullable
              as dynamic,
      cummulativePrevQty: freezed == cummulativePrevQty
          ? _value.cummulativePrevQty
          : cummulativePrevQty // ignore: cast_nullable_to_non_nullable
              as dynamic,
      sorId: null == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String,
      type: null == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String,
      index: freezed == index
          ? _value.index
          : index // ignore: cast_nullable_to_non_nullable
              as int?,
      measurementLineIndex: freezed == measurementLineIndex
          ? _value.measurementLineIndex
          : measurementLineIndex // ignore: cast_nullable_to_non_nullable
              as int?,
      filteredMeasurementMeasureId: freezed == filteredMeasurementMeasureId
          ? _value.filteredMeasurementMeasureId
          : filteredMeasurementMeasureId // ignore: cast_nullable_to_non_nullable
              as String?,
      height: freezed == height
          ? _value.height
          : height // ignore: cast_nullable_to_non_nullable
              as dynamic,
      width: freezed == width
          ? _value.width
          : width // ignore: cast_nullable_to_non_nullable
              as dynamic,
      length: freezed == length
          ? _value.length
          : length // ignore: cast_nullable_to_non_nullable
              as dynamic,
      number: freezed == number
          ? _value.number
          : number // ignore: cast_nullable_to_non_nullable
              as dynamic,
      quantity: freezed == quantity
          ? _value.quantity
          : quantity // ignore: cast_nullable_to_non_nullable
              as dynamic,
      measurementSummary: freezed == measurementSummary
          ? _value.measurementSummary
          : measurementSummary // ignore: cast_nullable_to_non_nullable
              as dynamic,
    ));
  }
}

/// @nodoc

class _$UpdateToMeasurementLineEventImpl
    implements UpdateToMeasurementLineEvent {
  const _$UpdateToMeasurementLineEventImpl(
      {required this.noOfUnit,
      required this.cummulativePrevQty,
      required this.sorId,
      required this.type,
      this.index,
      this.measurementLineIndex,
      this.filteredMeasurementMeasureId,
      this.height,
      this.width,
      this.length,
      this.number,
      this.quantity,
      this.measurementSummary});

  @override
  final dynamic noOfUnit;
  @override
  final dynamic cummulativePrevQty;
  @override
  final String sorId;
  @override
  final String type;
  @override
  final int? index;
  @override
  final int? measurementLineIndex;
  @override
  final String? filteredMeasurementMeasureId;
  @override
  final dynamic height;
  @override
  final dynamic width;
  @override
  final dynamic length;
  @override
  final dynamic number;
  @override
  final dynamic quantity;
  @override
  final dynamic measurementSummary;

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.updateToMeasurementLineList(noOfUnit: $noOfUnit, cummulativePrevQty: $cummulativePrevQty, sorId: $sorId, type: $type, index: $index, measurementLineIndex: $measurementLineIndex, filteredMeasurementMeasureId: $filteredMeasurementMeasureId, height: $height, width: $width, length: $length, number: $number, quantity: $quantity, measurementSummary: $measurementSummary)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$UpdateToMeasurementLineEventImpl &&
            const DeepCollectionEquality().equals(other.noOfUnit, noOfUnit) &&
            const DeepCollectionEquality()
                .equals(other.cummulativePrevQty, cummulativePrevQty) &&
            (identical(other.sorId, sorId) || other.sorId == sorId) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.index, index) || other.index == index) &&
            (identical(other.measurementLineIndex, measurementLineIndex) ||
                other.measurementLineIndex == measurementLineIndex) &&
            (identical(other.filteredMeasurementMeasureId,
                    filteredMeasurementMeasureId) ||
                other.filteredMeasurementMeasureId ==
                    filteredMeasurementMeasureId) &&
            const DeepCollectionEquality().equals(other.height, height) &&
            const DeepCollectionEquality().equals(other.width, width) &&
            const DeepCollectionEquality().equals(other.length, length) &&
            const DeepCollectionEquality().equals(other.number, number) &&
            const DeepCollectionEquality().equals(other.quantity, quantity) &&
            const DeepCollectionEquality()
                .equals(other.measurementSummary, measurementSummary));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(noOfUnit),
      const DeepCollectionEquality().hash(cummulativePrevQty),
      sorId,
      type,
      index,
      measurementLineIndex,
      filteredMeasurementMeasureId,
      const DeepCollectionEquality().hash(height),
      const DeepCollectionEquality().hash(width),
      const DeepCollectionEquality().hash(length),
      const DeepCollectionEquality().hash(number),
      const DeepCollectionEquality().hash(quantity),
      const DeepCollectionEquality().hash(measurementSummary));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$UpdateToMeasurementLineEventImplCopyWith<
          _$UpdateToMeasurementLineEventImpl>
      get copyWith => __$$UpdateToMeasurementLineEventImplCopyWithImpl<
          _$UpdateToMeasurementLineEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return updateToMeasurementLineList(
        noOfUnit,
        cummulativePrevQty,
        sorId,
        type,
        index,
        measurementLineIndex,
        filteredMeasurementMeasureId,
        height,
        width,
        length,
        number,
        quantity,
        measurementSummary);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return updateToMeasurementLineList?.call(
        noOfUnit,
        cummulativePrevQty,
        sorId,
        type,
        index,
        measurementLineIndex,
        filteredMeasurementMeasureId,
        height,
        width,
        length,
        number,
        quantity,
        measurementSummary);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (updateToMeasurementLineList != null) {
      return updateToMeasurementLineList(
          noOfUnit,
          cummulativePrevQty,
          sorId,
          type,
          index,
          measurementLineIndex,
          filteredMeasurementMeasureId,
          height,
          width,
          length,
          number,
          quantity,
          measurementSummary);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return updateToMeasurementLineList(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return updateToMeasurementLineList?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (updateToMeasurementLineList != null) {
      return updateToMeasurementLineList(this);
    }
    return orElse();
  }
}

abstract class UpdateToMeasurementLineEvent
    implements MeasurementDetailBlocEvent {
  const factory UpdateToMeasurementLineEvent(
      {required final dynamic noOfUnit,
      required final dynamic cummulativePrevQty,
      required final String sorId,
      required final String type,
      final int? index,
      final int? measurementLineIndex,
      final String? filteredMeasurementMeasureId,
      final dynamic height,
      final dynamic width,
      final dynamic length,
      final dynamic number,
      final dynamic quantity,
      final dynamic measurementSummary}) = _$UpdateToMeasurementLineEventImpl;

  dynamic get noOfUnit;
  dynamic get cummulativePrevQty;
  String get sorId;
  String get type;
  int? get index;
  int? get measurementLineIndex;
  String? get filteredMeasurementMeasureId;
  dynamic get height;
  dynamic get width;
  dynamic get length;
  dynamic get number;
  dynamic get quantity;
  dynamic get measurementSummary;
  @JsonKey(ignore: true)
  _$$UpdateToMeasurementLineEventImplCopyWith<
          _$UpdateToMeasurementLineEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$UpdateViewModeEventImplCopyWith<$Res> {
  factory _$$UpdateViewModeEventImplCopyWith(_$UpdateViewModeEventImpl value,
          $Res Function(_$UpdateViewModeEventImpl) then) =
      __$$UpdateViewModeEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({bool updateView});
}

/// @nodoc
class __$$UpdateViewModeEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$UpdateViewModeEventImpl>
    implements _$$UpdateViewModeEventImplCopyWith<$Res> {
  __$$UpdateViewModeEventImplCopyWithImpl(_$UpdateViewModeEventImpl _value,
      $Res Function(_$UpdateViewModeEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? updateView = null,
  }) {
    return _then(_$UpdateViewModeEventImpl(
      updateView: null == updateView
          ? _value.updateView
          : updateView // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc

class _$UpdateViewModeEventImpl implements UpdateViewModeEvent {
  const _$UpdateViewModeEventImpl({required this.updateView});

  @override
  final bool updateView;

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.updateViewMode(updateView: $updateView)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$UpdateViewModeEventImpl &&
            (identical(other.updateView, updateView) ||
                other.updateView == updateView));
  }

  @override
  int get hashCode => Object.hash(runtimeType, updateView);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$UpdateViewModeEventImplCopyWith<_$UpdateViewModeEventImpl> get copyWith =>
      __$$UpdateViewModeEventImplCopyWithImpl<_$UpdateViewModeEventImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return updateViewMode(updateView);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return updateViewMode?.call(updateView);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (updateViewMode != null) {
      return updateViewMode(updateView);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return updateViewMode(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return updateViewMode?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (updateViewMode != null) {
      return updateViewMode(this);
    }
    return orElse();
  }
}

abstract class UpdateViewModeEvent implements MeasurementDetailBlocEvent {
  const factory UpdateViewModeEvent({required final bool updateView}) =
      _$UpdateViewModeEventImpl;

  bool get updateView;
  @JsonKey(ignore: true)
  _$$UpdateViewModeEventImplCopyWith<_$UpdateViewModeEventImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$CancelUpdateEventImplCopyWith<$Res> {
  factory _$$CancelUpdateEventImplCopyWith(_$CancelUpdateEventImpl value,
          $Res Function(_$CancelUpdateEventImpl) then) =
      __$$CancelUpdateEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {bool cancelUpdate,
      String sorId,
      dynamic filteredMeasurementsMeasureId,
      String type});
}

/// @nodoc
class __$$CancelUpdateEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$CancelUpdateEventImpl>
    implements _$$CancelUpdateEventImplCopyWith<$Res> {
  __$$CancelUpdateEventImplCopyWithImpl(_$CancelUpdateEventImpl _value,
      $Res Function(_$CancelUpdateEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? cancelUpdate = null,
    Object? sorId = null,
    Object? filteredMeasurementsMeasureId = freezed,
    Object? type = null,
  }) {
    return _then(_$CancelUpdateEventImpl(
      cancelUpdate: null == cancelUpdate
          ? _value.cancelUpdate
          : cancelUpdate // ignore: cast_nullable_to_non_nullable
              as bool,
      sorId: null == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String,
      filteredMeasurementsMeasureId: freezed == filteredMeasurementsMeasureId
          ? _value.filteredMeasurementsMeasureId
          : filteredMeasurementsMeasureId // ignore: cast_nullable_to_non_nullable
              as dynamic,
      type: null == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$CancelUpdateEventImpl implements CancelUpdateEvent {
  const _$CancelUpdateEventImpl(
      {required this.cancelUpdate,
      required this.sorId,
      required this.filteredMeasurementsMeasureId,
      required this.type});

  @override
  final bool cancelUpdate;
  @override
  final String sorId;
  @override
  final dynamic filteredMeasurementsMeasureId;
  @override
  final String type;

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.cancelUpdate(cancelUpdate: $cancelUpdate, sorId: $sorId, filteredMeasurementsMeasureId: $filteredMeasurementsMeasureId, type: $type)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CancelUpdateEventImpl &&
            (identical(other.cancelUpdate, cancelUpdate) ||
                other.cancelUpdate == cancelUpdate) &&
            (identical(other.sorId, sorId) || other.sorId == sorId) &&
            const DeepCollectionEquality().equals(
                other.filteredMeasurementsMeasureId,
                filteredMeasurementsMeasureId) &&
            (identical(other.type, type) || other.type == type));
  }

  @override
  int get hashCode => Object.hash(runtimeType, cancelUpdate, sorId,
      const DeepCollectionEquality().hash(filteredMeasurementsMeasureId), type);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CancelUpdateEventImplCopyWith<_$CancelUpdateEventImpl> get copyWith =>
      __$$CancelUpdateEventImplCopyWithImpl<_$CancelUpdateEventImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return cancelUpdate(
        this.cancelUpdate, sorId, filteredMeasurementsMeasureId, type);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return cancelUpdate?.call(
        this.cancelUpdate, sorId, filteredMeasurementsMeasureId, type);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (cancelUpdate != null) {
      return cancelUpdate(
          this.cancelUpdate, sorId, filteredMeasurementsMeasureId, type);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return cancelUpdate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return cancelUpdate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (cancelUpdate != null) {
      return cancelUpdate(this);
    }
    return orElse();
  }
}

abstract class CancelUpdateEvent implements MeasurementDetailBlocEvent {
  const factory CancelUpdateEvent(
      {required final bool cancelUpdate,
      required final String sorId,
      required final dynamic filteredMeasurementsMeasureId,
      required final String type}) = _$CancelUpdateEventImpl;

  bool get cancelUpdate;
  String get sorId;
  dynamic get filteredMeasurementsMeasureId;
  String get type;
  @JsonKey(ignore: true)
  _$$CancelUpdateEventImplCopyWith<_$CancelUpdateEventImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SubmitLineEventImplCopyWith<$Res> {
  factory _$$SubmitLineEventImplCopyWith(_$SubmitLineEventImpl value,
          $Res Function(_$SubmitLineEventImpl) then) =
      __$$SubmitLineEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {dynamic noOfUnit,
      dynamic cummulativePrevQty,
      String sorId,
      String type,
      int? index,
      int? measurementLineIndex,
      String? filteredMeasurementMeasureId});
}

/// @nodoc
class __$$SubmitLineEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$SubmitLineEventImpl> implements _$$SubmitLineEventImplCopyWith<$Res> {
  __$$SubmitLineEventImplCopyWithImpl(
      _$SubmitLineEventImpl _value, $Res Function(_$SubmitLineEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? noOfUnit = freezed,
    Object? cummulativePrevQty = freezed,
    Object? sorId = null,
    Object? type = null,
    Object? index = freezed,
    Object? measurementLineIndex = freezed,
    Object? filteredMeasurementMeasureId = freezed,
  }) {
    return _then(_$SubmitLineEventImpl(
      noOfUnit: freezed == noOfUnit
          ? _value.noOfUnit
          : noOfUnit // ignore: cast_nullable_to_non_nullable
              as dynamic,
      cummulativePrevQty: freezed == cummulativePrevQty
          ? _value.cummulativePrevQty
          : cummulativePrevQty // ignore: cast_nullable_to_non_nullable
              as dynamic,
      sorId: null == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String,
      type: null == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String,
      index: freezed == index
          ? _value.index
          : index // ignore: cast_nullable_to_non_nullable
              as int?,
      measurementLineIndex: freezed == measurementLineIndex
          ? _value.measurementLineIndex
          : measurementLineIndex // ignore: cast_nullable_to_non_nullable
              as int?,
      filteredMeasurementMeasureId: freezed == filteredMeasurementMeasureId
          ? _value.filteredMeasurementMeasureId
          : filteredMeasurementMeasureId // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$SubmitLineEventImpl implements SubmitLineEvent {
  const _$SubmitLineEventImpl(
      {required this.noOfUnit,
      required this.cummulativePrevQty,
      required this.sorId,
      required this.type,
      this.index,
      this.measurementLineIndex,
      this.filteredMeasurementMeasureId});

  @override
  final dynamic noOfUnit;
  @override
  final dynamic cummulativePrevQty;
  @override
  final String sorId;
  @override
  final String type;
  @override
  final int? index;
  @override
  final int? measurementLineIndex;
  @override
  final String? filteredMeasurementMeasureId;

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.submitLine(noOfUnit: $noOfUnit, cummulativePrevQty: $cummulativePrevQty, sorId: $sorId, type: $type, index: $index, measurementLineIndex: $measurementLineIndex, filteredMeasurementMeasureId: $filteredMeasurementMeasureId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SubmitLineEventImpl &&
            const DeepCollectionEquality().equals(other.noOfUnit, noOfUnit) &&
            const DeepCollectionEquality()
                .equals(other.cummulativePrevQty, cummulativePrevQty) &&
            (identical(other.sorId, sorId) || other.sorId == sorId) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.index, index) || other.index == index) &&
            (identical(other.measurementLineIndex, measurementLineIndex) ||
                other.measurementLineIndex == measurementLineIndex) &&
            (identical(other.filteredMeasurementMeasureId,
                    filteredMeasurementMeasureId) ||
                other.filteredMeasurementMeasureId ==
                    filteredMeasurementMeasureId));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(noOfUnit),
      const DeepCollectionEquality().hash(cummulativePrevQty),
      sorId,
      type,
      index,
      measurementLineIndex,
      filteredMeasurementMeasureId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SubmitLineEventImplCopyWith<_$SubmitLineEventImpl> get copyWith =>
      __$$SubmitLineEventImplCopyWithImpl<_$SubmitLineEventImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return submitLine(noOfUnit, cummulativePrevQty, sorId, type, index,
        measurementLineIndex, filteredMeasurementMeasureId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return submitLine?.call(noOfUnit, cummulativePrevQty, sorId, type, index,
        measurementLineIndex, filteredMeasurementMeasureId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (submitLine != null) {
      return submitLine(noOfUnit, cummulativePrevQty, sorId, type, index,
          measurementLineIndex, filteredMeasurementMeasureId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return submitLine(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return submitLine?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (submitLine != null) {
      return submitLine(this);
    }
    return orElse();
  }
}

abstract class SubmitLineEvent implements MeasurementDetailBlocEvent {
  const factory SubmitLineEvent(
      {required final dynamic noOfUnit,
      required final dynamic cummulativePrevQty,
      required final String sorId,
      required final String type,
      final int? index,
      final int? measurementLineIndex,
      final String? filteredMeasurementMeasureId}) = _$SubmitLineEventImpl;

  dynamic get noOfUnit;
  dynamic get cummulativePrevQty;
  String get sorId;
  String get type;
  int? get index;
  int? get measurementLineIndex;
  String? get filteredMeasurementMeasureId;
  @JsonKey(ignore: true)
  _$$SubmitLineEventImplCopyWith<_$SubmitLineEventImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$UpdateMsgCodeEventImplCopyWith<$Res> {
  factory _$$UpdateMsgCodeEventImplCopyWith(_$UpdateMsgCodeEventImpl value,
          $Res Function(_$UpdateMsgCodeEventImpl) then) =
      __$$UpdateMsgCodeEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({int updateCode});
}

/// @nodoc
class __$$UpdateMsgCodeEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$UpdateMsgCodeEventImpl>
    implements _$$UpdateMsgCodeEventImplCopyWith<$Res> {
  __$$UpdateMsgCodeEventImplCopyWithImpl(_$UpdateMsgCodeEventImpl _value,
      $Res Function(_$UpdateMsgCodeEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? updateCode = null,
  }) {
    return _then(_$UpdateMsgCodeEventImpl(
      updateCode: null == updateCode
          ? _value.updateCode
          : updateCode // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$UpdateMsgCodeEventImpl implements UpdateMsgCodeEvent {
  const _$UpdateMsgCodeEventImpl({required this.updateCode});

  @override
  final int updateCode;

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.updateMsgCode(updateCode: $updateCode)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$UpdateMsgCodeEventImpl &&
            (identical(other.updateCode, updateCode) ||
                other.updateCode == updateCode));
  }

  @override
  int get hashCode => Object.hash(runtimeType, updateCode);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$UpdateMsgCodeEventImplCopyWith<_$UpdateMsgCodeEventImpl> get copyWith =>
      __$$UpdateMsgCodeEventImplCopyWithImpl<_$UpdateMsgCodeEventImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return updateMsgCode(updateCode);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return updateMsgCode?.call(updateCode);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (updateMsgCode != null) {
      return updateMsgCode(updateCode);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return updateMsgCode(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return updateMsgCode?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (updateMsgCode != null) {
      return updateMsgCode(this);
    }
    return orElse();
  }
}

abstract class UpdateMsgCodeEvent implements MeasurementDetailBlocEvent {
  const factory UpdateMsgCodeEvent({required final int updateCode}) =
      _$UpdateMsgCodeEventImpl;

  int get updateCode;
  @JsonKey(ignore: true)
  _$$UpdateMsgCodeEventImplCopyWith<_$UpdateMsgCodeEventImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DeleteMeasurementLineEventImplCopyWith<$Res> {
  factory _$$DeleteMeasurementLineEventImplCopyWith(
          _$DeleteMeasurementLineEventImpl value,
          $Res Function(_$DeleteMeasurementLineEventImpl) then) =
      __$$DeleteMeasurementLineEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String sorId,
      String type,
      int index,
      int measurementLineIndex,
      String filteredMeasurementMeasureId});
}

/// @nodoc
class __$$DeleteMeasurementLineEventImplCopyWithImpl<$Res>
    extends _$MeasurementDetailBlocEventCopyWithImpl<$Res,
        _$DeleteMeasurementLineEventImpl>
    implements _$$DeleteMeasurementLineEventImplCopyWith<$Res> {
  __$$DeleteMeasurementLineEventImplCopyWithImpl(
      _$DeleteMeasurementLineEventImpl _value,
      $Res Function(_$DeleteMeasurementLineEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? sorId = null,
    Object? type = null,
    Object? index = null,
    Object? measurementLineIndex = null,
    Object? filteredMeasurementMeasureId = null,
  }) {
    return _then(_$DeleteMeasurementLineEventImpl(
      sorId: null == sorId
          ? _value.sorId
          : sorId // ignore: cast_nullable_to_non_nullable
              as String,
      type: null == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String,
      index: null == index
          ? _value.index
          : index // ignore: cast_nullable_to_non_nullable
              as int,
      measurementLineIndex: null == measurementLineIndex
          ? _value.measurementLineIndex
          : measurementLineIndex // ignore: cast_nullable_to_non_nullable
              as int,
      filteredMeasurementMeasureId: null == filteredMeasurementMeasureId
          ? _value.filteredMeasurementMeasureId
          : filteredMeasurementMeasureId // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$DeleteMeasurementLineEventImpl implements DeleteMeasurementLineEvent {
  const _$DeleteMeasurementLineEventImpl(
      {required this.sorId,
      required this.type,
      required this.index,
      required this.measurementLineIndex,
      required this.filteredMeasurementMeasureId});

  @override
  final String sorId;
  @override
  final String type;
  @override
  final int index;
  @override
  final int measurementLineIndex;
  @override
  final String filteredMeasurementMeasureId;

  @override
  String toString() {
    return 'MeasurementDetailBlocEvent.deleteMeasurementLine(sorId: $sorId, type: $type, index: $index, measurementLineIndex: $measurementLineIndex, filteredMeasurementMeasureId: $filteredMeasurementMeasureId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DeleteMeasurementLineEventImpl &&
            (identical(other.sorId, sorId) || other.sorId == sorId) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.index, index) || other.index == index) &&
            (identical(other.measurementLineIndex, measurementLineIndex) ||
                other.measurementLineIndex == measurementLineIndex) &&
            (identical(other.filteredMeasurementMeasureId,
                    filteredMeasurementMeasureId) ||
                other.filteredMeasurementMeasureId ==
                    filteredMeasurementMeasureId));
  }

  @override
  int get hashCode => Object.hash(runtimeType, sorId, type, index,
      measurementLineIndex, filteredMeasurementMeasureId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$DeleteMeasurementLineEventImplCopyWith<_$DeleteMeasurementLineEventImpl>
      get copyWith => __$$DeleteMeasurementLineEventImplCopyWithImpl<
          _$DeleteMeasurementLineEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, List<WorkflowDocument> workflowDocument)
        uploadDocument,
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
    required TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)
        addToMeasurementLineList,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)
        updateToMeasurementLineList,
    required TResult Function(bool updateView) updateViewMode,
    required TResult Function(bool cancelUpdate, String sorId,
            dynamic filteredMeasurementsMeasureId, String type)
        cancelUpdate,
    required TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)
        submitLine,
    required TResult Function(int updateCode) updateMsgCode,
    required TResult Function(String sorId, String type, int index, int measurementLineIndex, String filteredMeasurementMeasureId) deleteMeasurementLine,
  }) {
    return deleteMeasurementLine(
        sorId, type, index, measurementLineIndex, filteredMeasurementMeasureId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult? Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
    TResult? Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult? Function(bool updateView)? updateViewMode,
    TResult? Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult? Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult? Function(int updateCode)? updateMsgCode,
    TResult? Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
  }) {
    return deleteMeasurementLine?.call(
        sorId, type, index, measurementLineIndex, filteredMeasurementMeasureId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, List<WorkflowDocument> workflowDocument)?
        uploadDocument,
    TResult Function(String tenantId, String contractNumber, String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    TResult Function(
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary,
            bool single)?
        addToMeasurementLineList,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId,
            dynamic height,
            dynamic width,
            dynamic length,
            dynamic number,
            dynamic quantity,
            dynamic measurementSummary)?
        updateToMeasurementLineList,
    TResult Function(bool updateView)? updateViewMode,
    TResult Function(bool cancelUpdate, String sorId, dynamic filteredMeasurementsMeasureId, String type)?
        cancelUpdate,
    TResult Function(
            dynamic noOfUnit,
            dynamic cummulativePrevQty,
            String sorId,
            String type,
            int? index,
            int? measurementLineIndex,
            String? filteredMeasurementMeasureId)?
        submitLine,
    TResult Function(int updateCode)? updateMsgCode,
    TResult Function(String sorId, String type, int index,
            int measurementLineIndex, String filteredMeasurementMeasureId)?
        deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (deleteMeasurementLine != null) {
      return deleteMeasurementLine(sorId, type, index, measurementLineIndex,
          filteredMeasurementMeasureId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUploadDocumentBlocEvent value)
        uploadDocument,
    required TResult Function(MeasurementDetailBookBlocEvent value) create,
    required TResult Function(MeasurementDetailBlocClearEvent value) clear,
    required TResult Function(AddToMeasurementLineEvent value)
        addToMeasurementLineList,
    required TResult Function(UpdateToMeasurementLineEvent value)
        updateToMeasurementLineList,
    required TResult Function(UpdateViewModeEvent value) updateViewMode,
    required TResult Function(CancelUpdateEvent value) cancelUpdate,
    required TResult Function(SubmitLineEvent value) submitLine,
    required TResult Function(UpdateMsgCodeEvent value) updateMsgCode,
    required TResult Function(DeleteMeasurementLineEvent value)
        deleteMeasurementLine,
  }) {
    return deleteMeasurementLine(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult? Function(MeasurementDetailBookBlocEvent value)? create,
    TResult? Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult? Function(AddToMeasurementLineEvent value)?
        addToMeasurementLineList,
    TResult? Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult? Function(UpdateViewModeEvent value)? updateViewMode,
    TResult? Function(CancelUpdateEvent value)? cancelUpdate,
    TResult? Function(SubmitLineEvent value)? submitLine,
    TResult? Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult? Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
  }) {
    return deleteMeasurementLine?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUploadDocumentBlocEvent value)? uploadDocument,
    TResult Function(MeasurementDetailBookBlocEvent value)? create,
    TResult Function(MeasurementDetailBlocClearEvent value)? clear,
    TResult Function(AddToMeasurementLineEvent value)? addToMeasurementLineList,
    TResult Function(UpdateToMeasurementLineEvent value)?
        updateToMeasurementLineList,
    TResult Function(UpdateViewModeEvent value)? updateViewMode,
    TResult Function(CancelUpdateEvent value)? cancelUpdate,
    TResult Function(SubmitLineEvent value)? submitLine,
    TResult Function(UpdateMsgCodeEvent value)? updateMsgCode,
    TResult Function(DeleteMeasurementLineEvent value)? deleteMeasurementLine,
    required TResult orElse(),
  }) {
    if (deleteMeasurementLine != null) {
      return deleteMeasurementLine(this);
    }
    return orElse();
  }
}

abstract class DeleteMeasurementLineEvent
    implements MeasurementDetailBlocEvent {
  const factory DeleteMeasurementLineEvent(
          {required final String sorId,
          required final String type,
          required final int index,
          required final int measurementLineIndex,
          required final String filteredMeasurementMeasureId}) =
      _$DeleteMeasurementLineEventImpl;

  String get sorId;
  String get type;
  int get index;
  int get measurementLineIndex;
  String get filteredMeasurementMeasureId;
  @JsonKey(ignore: true)
  _$$DeleteMeasurementLineEventImplCopyWith<_$DeleteMeasurementLineEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$MeasurementDetailState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
        loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
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
abstract class $MeasurementDetailStateCopyWith<$Res> {
  factory $MeasurementDetailStateCopyWith(MeasurementDetailState value,
          $Res Function(MeasurementDetailState) then) =
      _$MeasurementDetailStateCopyWithImpl<$Res, MeasurementDetailState>;
}

/// @nodoc
class _$MeasurementDetailStateCopyWithImpl<$Res,
        $Val extends MeasurementDetailState>
    implements $MeasurementDetailStateCopyWith<$Res> {
  _$MeasurementDetailStateCopyWithImpl(this._value, this._then);

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
    extends _$MeasurementDetailStateCopyWithImpl<$Res, _$InitialImpl>
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
    return 'MeasurementDetailState.initial()';
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
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

abstract class _Initial extends MeasurementDetailState {
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
    extends _$MeasurementDetailStateCopyWithImpl<$Res, _$LoadingImpl>
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
    return 'MeasurementDetailState.loading()';
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
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

abstract class _Loading extends MeasurementDetailState {
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
      {int qtyErrorMsg,
      String? warningMsg,
      bool viewStatus,
      dynamic rawData,
      List<FilteredMeasurements> data,
      List<SorObject>? sor,
      List<SorObject>? nonSor,
      List<SorObject>? preSor,
      List<SorObject>? preNonSor});
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$MeasurementDetailStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? qtyErrorMsg = null,
    Object? warningMsg = freezed,
    Object? viewStatus = null,
    Object? rawData = freezed,
    Object? data = null,
    Object? sor = freezed,
    Object? nonSor = freezed,
    Object? preSor = freezed,
    Object? preNonSor = freezed,
  }) {
    return _then(_$LoadedImpl(
      null == qtyErrorMsg
          ? _value.qtyErrorMsg
          : qtyErrorMsg // ignore: cast_nullable_to_non_nullable
              as int,
      freezed == warningMsg
          ? _value.warningMsg
          : warningMsg // ignore: cast_nullable_to_non_nullable
              as String?,
      null == viewStatus
          ? _value.viewStatus
          : viewStatus // ignore: cast_nullable_to_non_nullable
              as bool,
      freezed == rawData
          ? _value.rawData
          : rawData // ignore: cast_nullable_to_non_nullable
              as dynamic,
      null == data
          ? _value._data
          : data // ignore: cast_nullable_to_non_nullable
              as List<FilteredMeasurements>,
      freezed == sor
          ? _value._sor
          : sor // ignore: cast_nullable_to_non_nullable
              as List<SorObject>?,
      freezed == nonSor
          ? _value._nonSor
          : nonSor // ignore: cast_nullable_to_non_nullable
              as List<SorObject>?,
      freezed == preSor
          ? _value._preSor
          : preSor // ignore: cast_nullable_to_non_nullable
              as List<SorObject>?,
      freezed == preNonSor
          ? _value._preNonSor
          : preNonSor // ignore: cast_nullable_to_non_nullable
              as List<SorObject>?,
    ));
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl(
      this.qtyErrorMsg,
      this.warningMsg,
      this.viewStatus,
      this.rawData,
      final List<FilteredMeasurements> data,
      final List<SorObject>? sor,
      final List<SorObject>? nonSor,
      final List<SorObject>? preSor,
      final List<SorObject>? preNonSor)
      : _data = data,
        _sor = sor,
        _nonSor = nonSor,
        _preSor = preSor,
        _preNonSor = preNonSor,
        super._();

  @override
  final int qtyErrorMsg;
  @override
  final String? warningMsg;
  @override
  final bool viewStatus;
  @override
  final dynamic rawData;
  final List<FilteredMeasurements> _data;
  @override
  List<FilteredMeasurements> get data {
    if (_data is EqualUnmodifiableListView) return _data;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_data);
  }

  final List<SorObject>? _sor;
  @override
  List<SorObject>? get sor {
    final value = _sor;
    if (value == null) return null;
    if (_sor is EqualUnmodifiableListView) return _sor;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<SorObject>? _nonSor;
  @override
  List<SorObject>? get nonSor {
    final value = _nonSor;
    if (value == null) return null;
    if (_nonSor is EqualUnmodifiableListView) return _nonSor;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<SorObject>? _preSor;
  @override
  List<SorObject>? get preSor {
    final value = _preSor;
    if (value == null) return null;
    if (_preSor is EqualUnmodifiableListView) return _preSor;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<SorObject>? _preNonSor;
  @override
  List<SorObject>? get preNonSor {
    final value = _preNonSor;
    if (value == null) return null;
    if (_preNonSor is EqualUnmodifiableListView) return _preNonSor;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MeasurementDetailState.loaded(qtyErrorMsg: $qtyErrorMsg, warningMsg: $warningMsg, viewStatus: $viewStatus, rawData: $rawData, data: $data, sor: $sor, nonSor: $nonSor, preSor: $preSor, preNonSor: $preNonSor)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.qtyErrorMsg, qtyErrorMsg) ||
                other.qtyErrorMsg == qtyErrorMsg) &&
            (identical(other.warningMsg, warningMsg) ||
                other.warningMsg == warningMsg) &&
            (identical(other.viewStatus, viewStatus) ||
                other.viewStatus == viewStatus) &&
            const DeepCollectionEquality().equals(other.rawData, rawData) &&
            const DeepCollectionEquality().equals(other._data, _data) &&
            const DeepCollectionEquality().equals(other._sor, _sor) &&
            const DeepCollectionEquality().equals(other._nonSor, _nonSor) &&
            const DeepCollectionEquality().equals(other._preSor, _preSor) &&
            const DeepCollectionEquality()
                .equals(other._preNonSor, _preNonSor));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      qtyErrorMsg,
      warningMsg,
      viewStatus,
      const DeepCollectionEquality().hash(rawData),
      const DeepCollectionEquality().hash(_data),
      const DeepCollectionEquality().hash(_sor),
      const DeepCollectionEquality().hash(_nonSor),
      const DeepCollectionEquality().hash(_preSor),
      const DeepCollectionEquality().hash(_preNonSor));

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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(qtyErrorMsg, warningMsg, viewStatus, rawData, data, sor,
        nonSor, preSor, preNonSor);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(qtyErrorMsg, warningMsg, viewStatus, rawData, data, sor,
        nonSor, preSor, preNonSor);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(qtyErrorMsg, warningMsg, viewStatus, rawData, data, sor,
          nonSor, preSor, preNonSor);
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

abstract class _Loaded extends MeasurementDetailState {
  const factory _Loaded(
      final int qtyErrorMsg,
      final String? warningMsg,
      final bool viewStatus,
      final dynamic rawData,
      final List<FilteredMeasurements> data,
      final List<SorObject>? sor,
      final List<SorObject>? nonSor,
      final List<SorObject>? preSor,
      final List<SorObject>? preNonSor) = _$LoadedImpl;
  const _Loaded._() : super._();

  int get qtyErrorMsg;
  String? get warningMsg;
  bool get viewStatus;
  dynamic get rawData;
  List<FilteredMeasurements> get data;
  List<SorObject>? get sor;
  List<SorObject>? get nonSor;
  List<SorObject>? get preSor;
  List<SorObject>? get preNonSor;
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
    extends _$MeasurementDetailStateCopyWithImpl<$Res, _$ErrorImpl>
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
    return 'MeasurementDetailState.error(error: $error)';
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
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
            int qtyErrorMsg,
            String? warningMsg,
            bool viewStatus,
            dynamic rawData,
            List<FilteredMeasurements> data,
            List<SorObject>? sor,
            List<SorObject>? nonSor,
            List<SorObject>? preSor,
            List<SorObject>? preNonSor)?
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

abstract class _Error extends MeasurementDetailState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
