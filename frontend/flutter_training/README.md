# flutter_training

An app to create birth and death certificates and view them.

### Pre-requisites

1. Flutter: 
The project is built using Flutter. The project uses the latest version of Flutter on stable branch. The path should contain `flutter`.
2. Dart
`dart` should be available in the path and should use the same version as the bundled version that comes with Flutter

## Getting Started

Run `flutter pub get` from the root of the flutter project to resolve the dependencies.

## Note
Every `bloc` or `freezed` model or `mappable` model need to have a part file import

For eg. if data model file name is `data_model.dart`
add an import part `'data_model.freezed.dart'`

if data model is mappable model, then in the file `data_model.dart`
add an import in the same file part `'data_model.mapper.dart'`

#### Command to generate serialization models for the above models:
 `flutter packages run build_runner build --delete-conflicting-outputs`

