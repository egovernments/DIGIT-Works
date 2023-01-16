import 'dart:async';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'toast_message.freezed.dart';

typedef ToastEmitter = Emitter<ToastState>;

class ToastBloc extends Bloc<ToastEvent, ToastState> {
  ToastBloc(super.initialState) {
    on<ToastShowEvent>(_onShow);
    on<ToastDismissEvent>(_onDismiss);
  }

  FutureOr<void> _onShow(ToastShowEvent event, ToastEmitter emit) async {
    emit(state.copyWith(
        showToast: true,
        toastMessage: 'Created Successfully',
        toastType: 'SUCCESS'));
  }

  FutureOr<void> _onDismiss(ToastDismissEvent event, ToastEmitter emit) async {
    emit(state.copyWith(
        showToast: false, toastMessage: 'Failed', toastType: 'ERROR'));
  }
}

@freezed
class ToastEvent with _$ToastEvent {
  const factory ToastEvent.show(
      {bool? showToast,
      String? toastMessage,
      String? toastType}) = ToastShowEvent;

  const factory ToastEvent.dismiss(
      {bool? showToast,
      String? toastMessage,
      String? toastType}) = ToastDismissEvent;
}

@freezed
class ToastState with _$ToastState {
  const ToastState._();

  const factory ToastState(
      {@Default(false) bool showToast,
      String? toastMessage,
      String? toastType}) = _ToastState;

  bool get isSuccess =>
      toastType == null || toastType == 'ERROR' ? false : true;
}
