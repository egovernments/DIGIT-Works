import 'dart:async';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:location/location.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'location.freezed.dart';

typedef LocationStateEmitter = Emitter<LocationState>;

/// LocationBLoC
///
/// Follow setup instructions for appropriate platform
/// Android - <https://docs.page/Lyokone/flutterlocation/installation/android>
/// iOS - <https://docs.page/Lyokone/flutterlocation/installation/ios>
/// macOS - <https://docs.page/Lyokone/flutterlocation/installation/macos>
class LocationBloc extends Bloc<LocationEvent, LocationState> {
  final Location location;
  LocationBloc({required this.location}) : super(const LocationState()) {
    on<LoadLocationEvent>(_handleLoadLocation);
    on<RequestLocationPermissionEvent>(_handleRequestPermission);
    on<RequestLocationServiceEvent>(_handleRequestService);
  }

  FutureOr<void> _handleRequestPermission(
    RequestLocationPermissionEvent event,
    LocationStateEmitter emit,
  ) async {
    emit(state.copyWith(loading: true));
    try {
      final permissions = await location.hasPermission();
      final hasPermissions = [
        PermissionStatus.granted,
        PermissionStatus.grantedLimited,
      ].contains(permissions);
      emit(state.copyWith(hasPermissions: hasPermissions));
      if (!hasPermissions) {
        if (event.retry > 0) {
          await location.requestPermission();
          add(RequestLocationPermissionEvent(retry: event.retry - 1));
        } else {
          throw Exception('Location permission request rejected');
        }
      }
    } catch (error) {
      rethrow;
    } finally {
      emit(state.copyWith(loading: false));
    }
  }

  FutureOr<void> _handleRequestService(
    RequestLocationServiceEvent event,
    LocationStateEmitter emit,
  ) async {
    emit(state.copyWith(loading: true));
    try {
      final serviceEnabled = await location.serviceEnabled();
      emit(state.copyWith(serviceEnabled: serviceEnabled));
      if (!serviceEnabled) {
        if (event.retry > 0) {
          await location.requestService();
          add(RequestLocationServiceEvent(retry: event.retry - 1));
        } else {
          throw Exception('Location service request rejected');
        }
      }
    } catch (error) {
      rethrow;
    } finally {
      emit(state.copyWith(loading: false));
    }
  }

  FutureOr<void> _handleLoadLocation(
    LoadLocationEvent event,
    LocationStateEmitter emit,
  ) async {
    emit(state.copyWith(loading: true));
    try {
      final permissions = await location.hasPermission();

      if (![
        PermissionStatus.granted,
        PermissionStatus.grantedLimited,
      ].contains(permissions)) {
        emit(state.copyWith(hasPermissions: false));
        add(const RequestLocationPermissionEvent());
        throw Exception('Location permission is required');
      } else {
        emit(state.copyWith(hasPermissions: true));
      }

      final serviceEnabled = await location.serviceEnabled();
      emit(state.copyWith(serviceEnabled: serviceEnabled));

      if (!serviceEnabled) {
        add(const RequestLocationServiceEvent());
        throw Exception('Location services are not enabled');
      }

      final locationData = await location.getLocation();
      if ([
        locationData.latitude,
        locationData.longitude,
      ].any((element) => element == null)) {
        throw Exception('Could not fetch location data');
      }

      emit(state.copyWith(
        latitude: locationData.latitude,
        longitude: locationData.longitude,
        accuracy: locationData.accuracy,
      ));
    } catch (error) {
      emit(state.copyWith(latitude: null, longitude: null, accuracy: null));
      rethrow;
    } finally {
      emit(state.copyWith(loading: false));
    }
  }
}

@freezed
class LocationEvent with _$LocationEvent {
  const factory LocationEvent.load() = LoadLocationEvent;

  const factory LocationEvent.requestService({
    @Default(1) int retry,
  }) = RequestLocationServiceEvent;

  const factory LocationEvent.requestPermission({
    @Default(1) int retry,
  }) = RequestLocationPermissionEvent;
}

@freezed
class LocationState with _$LocationState {
  const LocationState._();

  const factory LocationState({
    double? latitude,
    double? longitude,
    double? accuracy,
    @Default(false) hasPermissions,
    @Default(false) bool serviceEnabled,
    @Default(false) bool loading,
  }) = _LocationState;

  String get latLngString {
    if (latitude == null || longitude == null) return 'Undefined';
    return [latitude, longitude].join(', ');
  }
}
