// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'token_auth.dart';

// **************************************************************************
// TypeAdapterGenerator
// **************************************************************************

class AuthTokenAdapter extends TypeAdapter<AuthToken> {
  @override
  final int typeId = 0;

  @override
  AuthToken read(BinaryReader reader) {
    final numOfFields = reader.readByte();
    final fields = <int, dynamic>{
      for (int i = 0; i < numOfFields; i++) reader.readByte(): reader.read(),
    };
    return AuthToken(
      accessToken: fields[0] as String,
      expiresIn: fields[1] as int,
      refreshExpiresIn: fields[2] as int,
      refreshToken: fields[3] as String,
      tokenType: fields[4] as String,
      idToken: fields[5] as String,
      notBeforePolicy: fields[6] as int,
      sessionState: fields[7] as String,
      scope: fields[8] as String,
    );
  }

  @override
  void write(BinaryWriter writer, AuthToken obj) {
    writer
      ..writeByte(9)
      ..writeByte(0)
      ..write(obj.accessToken)
      ..writeByte(1)
      ..write(obj.expiresIn)
      ..writeByte(2)
      ..write(obj.refreshExpiresIn)
      ..writeByte(3)
      ..write(obj.refreshToken)
      ..writeByte(4)
      ..write(obj.tokenType)
      ..writeByte(5)
      ..write(obj.idToken)
      ..writeByte(6)
      ..write(obj.notBeforePolicy)
      ..writeByte(7)
      ..write(obj.sessionState)
      ..writeByte(8)
      ..write(obj.scope);
  }

  @override
  int get hashCode => typeId.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is AuthTokenAdapter &&
          runtimeType == other.runtimeType &&
          typeId == other.typeId;
}
